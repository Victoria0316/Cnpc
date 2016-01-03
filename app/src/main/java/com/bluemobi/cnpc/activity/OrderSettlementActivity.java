package com.bluemobi.cnpc.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.model.OrderSettlementBean;
import com.bluemobi.cnpc.network.model.SettlementBean;
import com.bluemobi.cnpc.network.request.OrderIdRequest;
import com.bluemobi.cnpc.network.request.OrderSettlementRequest;
import com.bluemobi.cnpc.network.request.ResetPayRequest;
import com.bluemobi.cnpc.network.request.SettlementRequest;
import com.bluemobi.cnpc.network.request.WeChatPayRequest;
import com.bluemobi.cnpc.network.response.OrderIdResponse;
import com.bluemobi.cnpc.network.response.OrderSettlementResponse;
import com.bluemobi.cnpc.network.response.ResetPayResponse;
import com.bluemobi.cnpc.network.response.SettlementResponse;
import com.bluemobi.cnpc.network.response.WeChatPayResponse;
import com.bluemobi.cnpc.util.AlipayUtil;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.util.WxPayUtils;
import com.bluemobi.cnpc.view.CnpcAlertDialog;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.view.SwitchView;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/21.
 * <p/>
 * p4-2 订单结算
 */

@ContentView(R.layout.activity_order_settlement)
@PageName(R.string.order_settlement)
public class OrderSettlementActivity extends BaseActivity implements View.OnClickListener, AlipayUtil.AlipayStausListener {

    @Bind(R.id.other_pay)
    RelativeLayout other_pay;

    @Bind(R.id.settlement)
    TextView settlement;

    @Bind(R.id.Recharge_amount)
    TextView Recharge_amount;

    @Bind(R.id.preferential)
    TextView prefer;

    @Bind(R.id.actual_cost)
    TextView actual_cost;

    @Bind(R.id.coin_purse)
    TextView coin_purse;

    @Bind(R.id.change_button)
    SwitchView change_button;

    @Bind(R.id.order_oil_wallet_num)
    TextView order_oil_wallet_num;

    @Bind(R.id.order_coupon_num)
    TextView order_coupon_num;

    @Bind(R.id.red_packet)
    TextView red_packet;

    @Bind(R.id.order_change_wallet_num)
    TextView order_change_wallet_num;

    @Bind(R.id.total)
    TextView total;

    @Bind(R.id.time_start)
    TextView time_start;

    @Bind(R.id.rl_coin)
    RelativeLayout rl_coin;

    @Bind(R.id.Bonus)
    RelativeLayout Bonus;

    @Bind(R.id.payAmount_image)
    ImageView payAmount_image;

    /**
     * 页面传递
     */
    private String RechargeAmount;

    /**
     * 页面传递
     */
    private String preferential;

    /**
     * 页面传递
     */
    private String countNum;

    /**
     * 页面传递
     */
    private String timeLimit;

    /**
     * 页面传递
     */
    private String PackageId;


    private OrderSettlementBean data;

    private List<OrderSettlementBean> datas = new ArrayList<OrderSettlementBean>();

    private String RedId;

    private String actual_cost_str;

    private String RedPreferential;
    private String changePreferential;
    /**
     * 实际支付
     */
    private BigDecimal realPayAmount;

    /**
     * 红包
     */
    private BigDecimal bonus;

    /**
     * 零钱
     */
    private BigDecimal coin;


    /**
     * 其他支付
     */
    private BigDecimal otherPayAmount;

    /**
     * 实际使用零钱
     */
    private String realCoinUse;

    private String OrderId;


    private String returnedData;

    private int weiPayCode = -4;

    private MyBroadcastReciver mReciver;

    /**
     * true :Alipay  false : thirdPart
     */
    private boolean isAlipayChatPay = true;
    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(OrderSettlementActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.order_settlement, R.drawable.return_arrow, true);

        showLoadingPage(false);

        PackageId = getIntent().getStringExtra("PackageId");
        countNum = getIntent().getStringExtra("countNum");
        timeLimit = getIntent().getStringExtra("timeLimit");

    }

    @Override
    protected CnpcHttpJsonRequest initRequest() {
        RechargeAmount = getIntent().getStringExtra("RechargeAmount");
        preferential = getIntent().getStringExtra("preferential");
        final OrderSettlementRequest request;
        request = new OrderSettlementRequest
                (
                        new Response.Listener<OrderSettlementResponse>() {
                            @Override
                            public void onResponse(OrderSettlementResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    data = response.getData();

                                    coin_purse.setText("余额" + data.getChangeBalance() + "元");
                                    coin = new BigDecimal(Double.parseDouble(
                                            data.getChangeBalance() == null ? "0d" : data.getChangeBalance()
                                    ));
                                    if (coin.doubleValue() <= 0) {
                                        /*change_button.setChecked(false);
                                        change_button.setEnabled(false);*/
                                        rl_coin.setVisibility(View.GONE);
                                    }
                                    change_button.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(View view, boolean isChecked) {
                                            getOtherPayAmount(isChecked);
                                        }
                                    });
                                    realPayAmount = new BigDecimal(Double.parseDouble(RechargeAmount))
                                            .subtract(new BigDecimal(Double.parseDouble(preferential)));
                                    //－红包
                                    bonus = new BigDecimal(
                                            Double.parseDouble(data.getCustomerBonus() == null ? "0d" :
                                                    StringUtils.isEmpty(data.getCustomerBonus().getBonusDiscount())
                                                            ? "0d" : data.getCustomerBonus().getBonusDiscount()));

                                    RedId = data.getCustomerBonus() == null ? "-1" : data.getCustomerBonus().getId();
                                    order_oil_wallet_num.setText("¥" + Utils.StringTo2decimal(data.getChangeBalance()));
                                    if (data.getCustomerBonus() != null) {
                                        RedPreferential = data.getCustomerBonus().getBonusDiscount();
                                        if (RedPreferential != null) {
                                            red_packet.setText(data.getCustomerBonus().getBonusName());
                                            order_coupon_num.setText("¥" + Utils.StringTo2decimal(RedPreferential));
                                        }
                                    } else {
                                        Bonus.setVisibility(View.GONE);
                                        order_coupon_num.setText("¥0.00");
                                    }

                                    getOtherPayAmount(true);

                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("01");
        request.setProductTotalValue(RechargeAmount);
        return request;
    }

    private void getOtherPayAmount(boolean checked) {
        if (checked) {
            //去除红包
            BigDecimal temp = realPayAmount.subtract(bonus);
            if (coin.doubleValue() >= temp.doubleValue()) {
                otherPayAmount = new BigDecimal(0d);
                realCoinUse = String.valueOf(temp.doubleValue());
            } else {
                otherPayAmount = temp.subtract(coin);
                realCoinUse = String.valueOf(coin.doubleValue());
            }
        } else {
            realCoinUse = "0.0";
            otherPayAmount = realPayAmount.subtract(bonus);
        }
        order_oil_wallet_num.setText("¥" + Utils.StringTo2decimal(realCoinUse));

        changePreferential = String.valueOf(otherPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        order_change_wallet_num.setText("¥" + Utils.StringTo2decimal(changePreferential));

    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        String formatTime = df.format(time);

        time_start.setText(formatTime);

        BigDecimal result = new BigDecimal(Double.parseDouble(RechargeAmount))
                .subtract(new BigDecimal(Double.parseDouble(preferential)));
        actual_cost_str = new DecimalFormat("0.00").format(result.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        Recharge_amount.setText(actual_cost_str + "元");
        prefer.setText(Utils.StringTo2decimal(preferential) + "元");
        actual_cost.setText(RechargeAmount+ "元");
        total.setText("¥" + Utils.StringTo2decimal(actual_cost_str));
        other_pay.setOnClickListener(this);
        settlement.setOnClickListener(this);


    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    returnedData = result.getStringExtra("payId");

                    if ("1".equals(returnedData)) {//1银联 2支付宝 3微信
                        payAmount_image.setImageResource(R.drawable.yinlian);
                    } else if ("3".equals(returnedData)) {
                        payAmount_image.setImageResource(R.drawable.weixinzhifu);
                    } else {
                        payAmount_image.setImageResource(R.drawable.pay_treasure);
                    }
                }
                break;
            default:
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.other_pay:

                Intent inten = new Intent(this, PaymentModeOtherActivity.class);
                startActivityForResult(inten, 1);

                break;
            case R.id.settlement:

                if (otherPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0) {//当其它支付大于0 才调用支付宝

                    if ("1".equals(returnedData)) {//1银联 2支付宝 3微信
                        Toast.makeText(mContext, "银联努力开发中…", Toast.LENGTH_SHORT).show();
                    } else if ("3".equals(returnedData)) {
                        //weChatPay();
                        Settlement(3);
                    } else {
                       // pay();
                        Settlement(2);
                        isAlipayChatPay = true;
                    }

                } else {
                    Settlement(999);
                    isAlipayChatPay = false;
                }

                break;
        }

    }

    private void Settlement(int type) {

        Utils.showProgressDialog(this);
        SettlementRequest request;
        request = new SettlementRequest
                (
                        new Response.Listener<SettlementResponse>() {
                            @Override
                            public void onResponse(SettlementResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {

                                    String otherPrice = String.valueOf(otherPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                                    OrderId = response.getMsg();

                                    if(response.getData() != null ){

                                        if (response != null && response.getStatus() == 0) {
                                            WxPayUtils.pay(OrderSettlementActivity.this, response.getData());
                                        } else {
                                            Utils.makeToastAndShow(getBaseContext(), OrderId);
                                        }
                                    }else{

                                        if(isAlipayChatPay){
                                            AlipayUtil alipayUtil = new AlipayUtil(OrderSettlementActivity.this, OrderSettlementActivity.this);
                                            if (Constants.ALIPAYDEBUG) {
                                                alipayUtil.pay("cnpc", "零钱包转入", otherPrice, OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");
                                            } else {

                                                alipayUtil.pay("cnpc", "零钱包转入", "0.01", OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");
                                            }
                                        }else{
                                            ResetPay(1);
                                        }
                                    }

                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setProductId(PackageId); //	套餐ID
        request.setProductNum(countNum);//	套餐交易数量 没有传0
        request.setPreferentialPrice(preferential); // 	优惠价格 没有传0.00
        request.setPreferentialAmount(actual_cost_str); //	优惠后应该支付金额 没有传0.00
        request.setCustomerBonusId(RedId);  //	使用的红包ID 如果没有传“-1”
        request.setRedAmount(RedPreferential);//	红包优惠金额 没有传0.00
        request.setChangeAmount(realCoinUse); //	零钱包优惠金额 没有传0.00

        if (type == 999) {
            request.setPayChannel(null);//	支付渠道（1现金，2银行卡转账，3支付宝，4微信，5银联，11内部转账）
            request.setActualAmount(null);//支付渠道实际支付金额 如果支付渠道传值，此值必须传
        } else if(type == 3){
            request.setPayChannel("4");
            request.setActualAmount(changePreferential);
        }else if(type == 2){
            request.setPayChannel("3");
            request.setActualAmount(changePreferential);
        }

        WebUtils.doPost(request);
    }


    private void ResetPay(int state) {

        Utils.showProgressDialog(this);
        ResetPayRequest request = new ResetPayRequest(new Response.Listener<ResetPayResponse>() {
            @Override
            public void onResponse(ResetPayResponse response) {
                if (response != null && response.getStatus() == 0) {// success
                    Utils.closeDialog();
                    SettlementBean item = response.getData();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("item", item);
                    intent.putExtra("timeLimit",timeLimit);
                    intent.putExtras(bundle);
                    intent.setClass(mContext, SettlementActivity.class);
                    startActivity(intent);

                }
            }
        }, this);
        request.setId(OrderId);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("1");//类型 1优惠充值 2加油 3预购油购买 4零钱包充值 5借用油购买
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
           /* final CnpcAlertDialog dialog = new CnpcAlertDialog(this);
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


    @Override
    public void checkResult(boolean isExist) {

    }


    @Override
    public void payOk() {

        ResetPay(1);

    }

    @Override
    public void payFailed() {

        ResetPay(2);

    }

    @Override
    public void paying() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
