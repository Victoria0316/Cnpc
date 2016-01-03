package com.bluemobi.cnpc.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.request.CoinTransInRequest;
import com.bluemobi.cnpc.network.request.WXOauth2Request;
import com.bluemobi.cnpc.network.response.CoinTransResponse;
import com.bluemobi.cnpc.network.response.WXOauthResponse;
import com.bluemobi.cnpc.util.AlipayUtil;
import com.bluemobi.cnpc.util.KeyBoardUtils;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.util.WxPayUtils;
import com.bluemobi.cnpc.view.CnpcAlertDialog;
import com.bluemobi.cnpc.view.LoadingPage;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gaoyn on 2015/7/25.
 * <p/>
 * p10-5-2  转出
 */

@ContentView(R.layout.activity_coin_transfer_out)
@PageName(R.string.transfer_go)
public class CoinTransferOutActivity extends BaseActivity implements AlipayUtil.AlipayStausListener {

    private final static String tag = "CoinTransferOutActivity";

    @Bind(R.id.confirm_price)
    TextView confirm_price;

    private AlertDialog dialog;
    private View view;

    @Bind(R.id.tv_time)
    TextView tv_time;

    @Bind(R.id.balance)
    TextView balance;

    @Bind(R.id.radio_group)
    RadioGroup radioGroup;

    @Bind(R.id.WeChat)
    RadioButton WeChat;
    @Bind(R.id.Internet)
    RadioButton Internet;

    @Bind(R.id.edit_price)
    EditText edit_price;

    @Bind(R.id.bank_car_num)
    EditText bank_car_num;

    @Bind(R.id.realname)
    EditText realname;

    private String mchagebalance;

    private int gender = 2;

    private boolean enabled = true;

    private String OrderId;
    /**
     * wx outh
     */
    private String wxCode;

    private int weiPayCode = -999;

    private MyBroadcastReciver mReciver;

    private IWXAPI api;

    private String openid;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(CoinTransferOutActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.transfer_go, R.drawable.return_arrow, true);

        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        String formatTime = df.format(time);
        tv_time.setText(formatTime);

        mchagebalance = getIntent().getStringExtra("balance_val");
        balance.setText(mchagebalance);

        confirmTransIn("0");
        edit_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edt) {

                String temp = edt.toString();

                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    confirmTransIn(temp);
                    return;
                }
                if (temp.length() - posDot - 1 > 2) {

                    edt.delete(posDot + 3, posDot + 4);
                }
                confirmTransIn(edt.toString());

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == Internet.getId()) {
                    gender = 1;
                    enabled = true;
                    bank_car_num.setEnabled(true);
                    bank_car_num.setFocusableInTouchMode(true);
                    bank_car_num.setFocusable(true);
                    realname.setEnabled(true);
                    realname.setFocusableInTouchMode(true);
                    realname.setFocusable(true);
                    KeyBoardUtils.closeKeybord(bank_car_num, mContext);
                } else if (checkedId == WeChat.getId()) {
                    gender = 3;
                    enabled = false;
                    bank_car_num.setEnabled(false);
                    bank_car_num.setFocusableInTouchMode(false);
                    bank_car_num.setFocusable(false);
                    realname.setEnabled(false);
                    realname.setFocusableInTouchMode(false);
                    realname.setFocusable(false);
                    KeyBoardUtils.closeKeybord(bank_car_num, mContext);
                } else {
                    enabled = true;
                    bank_car_num.setEnabled(true);
                    bank_car_num.setFocusableInTouchMode(true);
                    bank_car_num.setFocusable(true);
                    realname.setEnabled(true);
                    realname.setFocusableInTouchMode(true);
                    realname.setFocusable(true);
                    KeyBoardUtils.closeKeybord(bank_car_num, mContext);
                    gender = 2;
                }

            }
        });
    }

    private void confirmTransIn(String temp) {

        int size = temp.length();
        String text = String.format(getResources().getString(R.string.test_confirm_price_out), temp);

        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_pink)), text.indexOf(temp), text.indexOf(temp) + size, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        confirm_price.setText(style);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    @OnClick(R.id.transfer_out)
    public void transferOut() {

        if (checkInput()) {
            /*if (dialog == null) {*/
                dialog = new AlertDialog.Builder(this)
                        .create();
                view = LayoutInflater.from(this).inflate(R.layout.dialog_transfer_success, null);
                String price = edit_price.getText().toString();
                TextView transfer_into = (TextView) view.findViewById(R.id.transfer_into);
                transfer_into.setText("转出金额：" + price + "元");

                TextView existing_price = (TextView) view.findViewById(R.id.existing_price);
                existing_price.setText("转出金额在T+1日后到账");

                TextView confirm = (TextView) view.findViewById(R.id.confirm);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (gender == 2) {
                            payOk();
                        } else if (gender == 1) {
                            Utils.makeToastAndShow(getBaseContext(), "转出银联支付,努力开发中");
                        } else if (gender == 3) {
                            /*weChatPay();*/
                            // send oauth request
                            api = WXAPIFactory.createWXAPI(CoinTransferOutActivity.this, WxPayUtils.WX_APP_ID);
                            api.registerApp(WxPayUtils.WX_APP_ID);
                            SendAuth.Req req = new SendAuth.Req();
                            req.scope = "snsapi_userinfo";
                            req.state = "wechat_sdk_demo_test";
                            api.sendReq(req);
                        }
                        dialog.dismiss();
                    }
                });

                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
                //window.setWindowAnimations(R.style.dialog_bottom_style);  //添加动画
                dialog.show();
                dialog.setContentView(view);
           /* } else {
                dialog.show();
            }*/
        }
    }

    private boolean checkInput() {
        if (StringUtils.isEmpty(edit_price.getText().toString())) {
            Utils.makeToastAndShow(mContext, "请输入转入金额，再尝试提交");
            return false;
        }

        if(Double.parseDouble(edit_price.getText().toString().trim()) >Double.parseDouble(mchagebalance.trim())){
            Utils.makeToastAndShow(getBaseContext(),"余额不足");
            return false;
        }
        return true;
    }


    @Override
    public void checkResult(boolean isExist) {

    }

    @Override
    public void payOk() {
        coinTransferInto();
    }


    @Override
    public void payFailed() {

    }

    @Override
    public void paying() {

    }

    private void coinTransferInto() {

        String price = edit_price.getText().toString();

        CoinTransInRequest request = new CoinTransInRequest(new Response.Listener<CoinTransResponse>() {
            @Override
            public void onResponse(CoinTransResponse response) {
//                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    finish();
//                    Utils.makeToastAndShow(mContext, "恭喜你，转入成功");
                    Logger.d(tag, "恭喜你，转入成功，向后台服务器传入数据");
                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setMoney(price);
        request.setType("2");//操作类型 1转入 2转出
        if(gender == 3){
            request.setOpenID(openid);
            request.setUsername(null);
            request.setRealname(null);
        }else{
            request.setOpenID(null);
            if(check()){
                request.setUsername(bank_car_num.getText().toString());
                request.setRealname(realname.getText().toString());
            }
        }
        request.setState(gender + "");//支付方式 1银联 2支付宝 3微信
        WebUtils.doPost(request);
    }

    private boolean check() {

        if (StringUtils.isEmpty(bank_car_num.getText().toString())) {
            Utils.makeToastAndShow(mContext, "银行卡号/支付宝账号不能为空");
            return false;
        }


        if (StringUtils.isEmpty(realname.getText().toString())) {
            Utils.makeToastAndShow(mContext, "真是姓名不能为空");
            return false;
        }

        return true;
    }

    /**
     * 广播接收器
     */
    private class MyBroadcastReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals("cn.abel.action.broadcast")) {
                weiPayCode = intent.getIntExtra("errCode", -999);
                wxCode = intent.getStringExtra("code");
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.abel.action.broadcast");
        mReciver = new MyBroadcastReciver();
        this.registerReceiver(mReciver, intentFilter);
        if (0 == weiPayCode) {//同意
            //Utils.makeToastAndShow(this, "同意");
            WXOauth2Request request = new WXOauth2Request(new Response.Listener<WXOauthResponse>() {
                @Override
                public void onResponse(WXOauthResponse response) {
                    Utils.closeDialog();
                    if(response != null){
                        openid = response.getOpenid();
                        payOk();

                    }
                }
            }, this);
            request.setCode(wxCode);

            WebUtils.doPost(request);
            Utils.showProgressDialog(CoinTransferOutActivity.this);

        }
        if (-1 == weiPayCode) {//失败
            final CnpcAlertDialog dialog = new CnpcAlertDialog(this);
            dialog
                    .setTitle("支付失败")
                    .setMessage("请重新支付。")
                    .setNegativeButtonClickListener("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    })
            ;
            dialog.show();
        }
        if (-2 == weiPayCode) {//取消支付
            //Utils.makeToastAndShow(this, "取消了支付");
        }
        if (-4 == weiPayCode) {//用户拒绝
            Utils.makeToastAndShow(this, "用户拒绝");
        }
    }


}
