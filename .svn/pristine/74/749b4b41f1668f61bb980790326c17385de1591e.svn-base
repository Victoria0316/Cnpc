package com.bluemobi.cnpc.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.request.CoinTransInRequest;
import com.bluemobi.cnpc.network.request.ConsumptionDetailedRequest;
import com.bluemobi.cnpc.network.request.OrderIdRequest;
import com.bluemobi.cnpc.network.request.ResetPayRequest;
import com.bluemobi.cnpc.network.request.WeChatPayRequest;
import com.bluemobi.cnpc.network.response.CoinTransResponse;
import com.bluemobi.cnpc.network.response.ConsumptionDetailedResponse;
import com.bluemobi.cnpc.network.response.OrderIdResponse;
import com.bluemobi.cnpc.network.response.ResetPayResponse;
import com.bluemobi.cnpc.network.response.WeChatPayResponse;
import com.bluemobi.cnpc.util.AlipayUtil;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.util.WxPayUtils;
import com.bluemobi.cnpc.view.CnpcAlertDialog;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.wxapi.WXPayEntryActivity;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.transform.ErrorListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gaoyn on 2015/7/25.
 * <p/>
 * p10-5-1 转入
 */

@ContentView(R.layout.activity_coin_transfer_into)
@PageName(R.string.transfer_come)
public class CoinTransferIntoActivity extends BaseActivity implements AlipayUtil.AlipayStausListener {
    private final static String tag = "CoinTransferIntoActivity";

    @Bind(R.id.confirm_price)
    TextView confirm_price;

    @Bind(R.id.et_transin_price)
    EditText et_transin_price;

    @Bind(R.id.WeChat)
    RadioButton WeChat;
    @Bind(R.id.Internet)
    RadioButton Internet;

    protected
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;

    @Bind(R.id.tv_time)
    TextView tv_time;
    private String mchagebalance;
    private AlertDialog dialog;
    private View view;

    private String OrderId;

    private int gender = 2;

    private int weiPayCode = -4;

    private MyBroadcastReciver mReciver;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(CoinTransferIntoActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.transfer_come, R.drawable.return_arrow, true);
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        mchagebalance = getIntent().getStringExtra("MCHAGEBALANCE");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        String formatTime = df.format(time);
        tv_time.setText(formatTime);
        confirmTransIn("0");
        et_transin_price.addTextChangedListener(new TextWatcher() {
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

        //TODO
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == Internet.getId()) {
                    gender = 1;
                } else if (checkedId == WeChat.getId()) {
                    gender = 3;
                } else {
                    gender = 2;
                }

            }
        });
    }


    private void confirmTransIn(String temp) {
        int size = temp.length();
        String text = String.format(getResources().getString(R.string.test_confirm_price), temp);
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_pink)), text.indexOf(temp), text.indexOf(temp) + size, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        confirm_price.setText(style);
    }


    private void coinTransferInto(int gender) {

        final String transInprice = et_transin_price.getText().toString();

        CoinTransInRequest request = new CoinTransInRequest(new Response.Listener<CoinTransResponse>() {
            @Override
            public void onResponse(CoinTransResponse response) {
                if (response != null && response.getStatus() == 0) {// success

                    OrderId = response.getMsg();

                    if(response.getData() != null ){

                        if (response != null && response.getStatus() == 0) {
                            WxPayUtils.pay(CoinTransferIntoActivity.this, response.getData());
                        } else {
                            Utils.makeToastAndShow(getBaseContext(), OrderId);
                        }
                    }else{

                        AlipayUtil alipayUtil = new AlipayUtil(CoinTransferIntoActivity.this, CoinTransferIntoActivity.this);
                        if (Constants.ALIPAYDEBUG) {

                            alipayUtil.pay("cnpc", "零钱包转入", transInprice, OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");

                        } else {

                            alipayUtil.pay("cnpc", "零钱包转入", "0.01", OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");
                        }
                    }
                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setMoney(transInprice);
        request.setType("1");//操作类型 1转入 2转出
        request.setState(gender + "");//支付方式 1银联 2支付宝 3微信
        request.setApp("android");
        WebUtils.doPost(request);
    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    @OnClick(R.id.transfer_in)
    public void transferIn() {

        if (checkInput()) {

           /* if (dialog == null) {*/
            dialog = new AlertDialog.Builder(this)
                    .create();
            view = LayoutInflater.from(this).inflate(R.layout.dialog_transfer_success, null);
            TextView transfer_into = (TextView) view.findViewById(R.id.transfer_into);
            TextView existing_price = (TextView) view.findViewById(R.id.existing_price);
            TextView confirm = (TextView) view.findViewById(R.id.confirm);
            final String transInprice = et_transin_price.getText().toString();
            transfer_into.setText("转入金额：" + transInprice + "元");
            existing_price.setText("当前零钱包余额：" + mchagebalance + "元");

            // 1银联 2支付宝 3微信
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (gender == 2) {
                        coinTransferInto(2);
                    } else if (gender == 1) {
                        Utils.makeToastAndShow(getBaseContext(), "银联支付");
                    } else if (gender == 3) {
                        coinTransferInto(3);
                    }
                    dialog.dismiss();
                }
            });

            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
            dialog.show();
            dialog.setContentView(view);
          /*  } else {
                dialog.show();
            }*/

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.abel.action.broadcast");
        mReciver = new MyBroadcastReciver();
        this.registerReceiver(mReciver, intentFilter);
        if (0 == weiPayCode) {//支付成功
            payOk();
        }
        if (-1 == weiPayCode) {//失败
            /*final CnpcAlertDialog dialog = new CnpcAlertDialog(this);
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
            dialog.show();*/

            payFailed();
        }
        if (-2 == weiPayCode) {//取消支付
            //Utils.makeToastAndShow(this, "取消了支付");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReciver);
    }

    private boolean checkInput() {

        if (StringUtils.isEmpty(et_transin_price.getText().toString())) {
            Utils.makeToastAndShow(mContext, "请输入转入金额，再尝试提交");
            return false;
        }
        return true;
    }


    @Override
    public void checkResult(boolean isExist) {

    }


    @Override
    public void payOk() {
        finish();
        ResetPay(1);

    }

    @Override
    public void payFailed() {
        finish();
        ResetPay(2);
    }

    @Override
    public void paying() {

    }

    private void ResetPay(int state) {

        ResetPayRequest request = new ResetPayRequest(new Response.Listener<ResetPayResponse>() {
            @Override
            public void onResponse(ResetPayResponse response) {
                if (response != null && response.getStatus() == 0) {// success


                }
            }
        }, this);
        request.setId(OrderId);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("4");//类型 1优惠充值 2加油 3预购油购买 4零钱包充值 5借用油购买
        if(state == 1){
            request.setState("1");//支付状态 1成功 2失败
        }else{
            request.setState("2");
        }

        WebUtils.doPost(request);

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
                weiPayCode = intent.getIntExtra("errCode", -4);
            }
        }

    }

}
