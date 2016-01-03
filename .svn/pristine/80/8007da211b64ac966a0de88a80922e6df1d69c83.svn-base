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
import com.bluemobi.cnpc.network.request.OrderPaymentSuccessRequest;
import com.bluemobi.cnpc.network.request.OrderSettlementRequest;
import com.bluemobi.cnpc.network.request.ResetPayRequest;
import com.bluemobi.cnpc.network.request.WeChatPayRequest;
import com.bluemobi.cnpc.network.response.OrderIdResponse;
import com.bluemobi.cnpc.network.response.OrderPaymentSuccessResponse;
import com.bluemobi.cnpc.network.response.OrderSettlementResponse;
import com.bluemobi.cnpc.network.response.ResetPayResponse;
import com.bluemobi.cnpc.network.response.WeChatPayResponse;
import com.bluemobi.cnpc.util.AlipayUtil;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.util.WxPayUtils;
import com.bluemobi.cnpc.view.CnpcAlertDialog;
import com.bluemobi.cnpc.view.CustomTextGroup;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.view.SwitchView;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * created by liufy
 * P5_3_1订单结算
 */
@ContentView(R.layout.activity_borrow_order_and_settlement)
@PageName(R.string.order_settlement)
public class BorrowOrderAndSettlementActivity extends BaseActivity implements View.OnClickListener, AlipayUtil.AlipayStausListener {

    @Bind(R.id.other_pay)
    RelativeLayout other_pay;

    @Bind(R.id.settlement)
    TextView settlement;

    @Bind(R.id.Start_time)
    TextView timeDate;

    @Bind(R.id.change_button)
    SwitchView change_button;

    @Bind(R.id.tv_oil_type)
    TextView tv_oil_type;

    @Bind(R.id.tv_oil_price)
    TextView tv_oil_price;

    @Bind(R.id.changeBalance)
    TextView changeBalance;

    @Bind(R.id.ctg_min_purchase)
    CustomTextGroup ctg_min_purchase;

    @Bind(R.id.ctg_min_increasing)
    CustomTextGroup ctg_min_increasing;

    @Bind(R.id.ctg_pre_procedure_rates)
    CustomTextGroup ctg_pre_procedure_rates;

    @Bind(R.id.ctg_procedure_fee)
    CustomTextGroup ctg_procedure_fee;

    @Bind(R.id.ctg_actual_amount)
    CustomTextGroup ctg_actual_amount;

    @Bind(R.id.ActAmount)
    TextView ActAmount;

    @Bind(R.id.order_change_wallet_num)
    TextView order_change_wallet_num;

    @Bind(R.id.order_pay_num)
    TextView order_pay_num;

    @Bind(R.id.rl_money)
    RelativeLayout rl_money;


    /**
     * 页面传递
     */
    private String RechargeAmount;
    /**
     * 页面传递
     */
    private String ProductName;
    /**
     * 页面传递
     */
    private String countNum;
    /**
     * 页面传递
     */
    private String price;
    /**
     * 页面传递
     */
    private String futuresBuyProcedure;
    /**
     * 页面传递
     */
    private String oilId;

    private OrderSettlementBean data;

    private BigDecimal totalValue;

    private String changePreferential;

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

    private String totalChangeAmount;

    private String OrderId;

    @Bind(R.id.payAmount_image)
    ImageView payAmount_image;

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
        titleBarManager.init(BorrowOrderAndSettlementActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.order_settlement, R.drawable.return_arrow, true);
        showLoadingPage(false);

    }

    @Override
    protected CnpcHttpJsonRequest initRequest() {
        RechargeAmount = getIntent().getStringExtra("RechargeAmount");
        ProductName = getIntent().getStringExtra("ProductName");
        countNum = getIntent().getStringExtra("countNum");
        price = getIntent().getStringExtra("price");
        futuresBuyProcedure = getIntent().getStringExtra("futuresBuyProcedure");
        oilId = getIntent().getStringExtra("oilId");

        OrderSettlementRequest request;
        request = new OrderSettlementRequest
                (
                        new Response.Listener<OrderSettlementResponse>() {
                            @Override
                            public void onResponse(OrderSettlementResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    data = response.getData();

                                    changeBalance.setText("余额" + data.getChangeBalance() + "元");
                                    coin = new BigDecimal(Double.parseDouble(
                                            data.getChangeBalance() == null ? "0d" : data.getChangeBalance()
                                    ));
                                    if (coin.doubleValue() <= 0) {
                                        rl_money.setVisibility(View.GONE);
                                    }
                                    change_button.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(View view, boolean isChecked) {
                                            getOtherPayAmount(isChecked);
                                        }
                                    });

                                    getOtherPayAmount(true);
                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("03");
        request.setProductTotalValue(RechargeAmount);
        return request;
    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        String formatTime = df.format(time);

        timeDate.setText(formatTime);

        tv_oil_type.setText(ProductName);
        tv_oil_price.setText(price + "元/吨");
        ctg_min_purchase.setRightText(countNum + "吨");

        ctg_min_increasing.setRightText(RechargeAmount + "元");
        ctg_pre_procedure_rates.setRightText(futuresBuyProcedure + "%");

        totalValue = new BigDecimal(Double.parseDouble(RechargeAmount));

        double totalvalue = totalValue.multiply(new BigDecimal(Double.parseDouble(futuresBuyProcedure)))
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        double total = new BigDecimal(totalvalue).divide(new BigDecimal(100))
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        double actualAmount = totalValue.add(new BigDecimal(total))
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


        totalChangeAmount = String.valueOf(total);

        ctg_procedure_fee.setRightText(totalChangeAmount + "元");

        ctg_actual_amount.setRightText(String.valueOf(actualAmount) + "元");

        ActAmount.setText(String.valueOf(actualAmount));


        other_pay.setOnClickListener(this);
        settlement.setOnClickListener(this);

    }


    private void getOtherPayAmount(boolean checked) {

        String Amount = ActAmount.getText().toString();
        BigDecimal bigAmount = new BigDecimal(Double.parseDouble(Amount));

        if (checked) {

            if (coin.doubleValue() >= bigAmount.doubleValue()) {
                otherPayAmount = new BigDecimal(0d);
                realCoinUse = String.valueOf(bigAmount.doubleValue());
            } else {
                otherPayAmount = bigAmount.subtract(coin);
                realCoinUse = String.valueOf(coin.doubleValue());
            }
        } else {
            realCoinUse = "0.0";
            otherPayAmount = bigAmount;
        }
        order_change_wallet_num.setText("¥" + realCoinUse);

        changePreferential = String.valueOf(otherPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        order_pay_num.setText("¥" + changePreferential);

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
                        Toast.makeText(mContext,"银联努力开发中…",Toast.LENGTH_SHORT).show();
                    } else if ("3".equals(returnedData)) {
                        //weChatPay();
                        Settlement(3);
                    } else {
                        //pay();
                        Settlement(2);
                        isAlipayChatPay = true;
                    }

                }
                else {
                    Settlement(999);
                    isAlipayChatPay = false;
                }

                break;
        }

    }

    private void Settlement(int type) {

        OrderPaymentSuccessRequest request;
        request = new OrderPaymentSuccessRequest
                (
                        new Response.Listener<OrderPaymentSuccessResponse>() {
                            @Override
                            public void onResponse(OrderPaymentSuccessResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    String otherPrice = String.valueOf(otherPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                                    OrderId = response.getMsg();

                                    if(response.getData() != null ){

                                        if (response != null && response.getStatus() == 0) {
                                            WxPayUtils.pay(BorrowOrderAndSettlementActivity.this, response.getData());
                                        } else {
                                            Utils.makeToastAndShow(getBaseContext(), OrderId);
                                        }
                                    }else{

                                        if(isAlipayChatPay){
                                            AlipayUtil alipayUtil = new AlipayUtil(BorrowOrderAndSettlementActivity.this, BorrowOrderAndSettlementActivity.this);
                                            if (Constants.ALIPAYDEBUG) {
                                                alipayUtil.pay("cnpc", "零钱包转入",otherPrice, OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");
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
        request.setProductId(oilId); //	油品ID
        request.setOrderType("5");
        request.setProductNum(countNum);//	预购油升数 没有传0
        request.setProductTotalValue(RechargeAmount); // 	预购金额 没有传0.00
        request.setProductProcedure(totalChangeAmount); //	手续费金额 没有传0.00
        request.setVipAmount(null);  //	会员优惠金额 没有传0.00
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
                    intent.putExtras(bundle);
                    intent.setClass(mContext, BorrowOrderPaymentSuccessActivity.class);
                    startActivity(intent);

                }
            }
        }, this);
        request.setId(OrderId);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("5");//类型 1优惠充值 2加油 3预购油购买 4零钱包充值 5借用油购买
        if(state == 1){
            request.setState("1");//支付状态 1成功 2失败
        }else{
            request.setState("2");
        }

        WebUtils.doPost(request);

    }


    private void weChatPay() {
        Utils.showProgressDialog(mContext);

        WeChatPayRequest request = new WeChatPayRequest(new Response.Listener<WeChatPayResponse>() {
            @Override
            public void onResponse(WeChatPayResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {
                    OrderId = response.getMsg();

                    if(response != null&& response.getStatus()==0){
                        WxPayUtils.pay(BorrowOrderAndSettlementActivity.this, response.getData());
                    }else{
                        Utils.makeToastAndShow(getBaseContext(), OrderId);
                    }
                } else {
                    Toast.makeText(mContext, response.getContent(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setMoney(changePreferential);
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
           payFailed();
        }
        if (-2 == weiPayCode) {//取消支付
            Utils.makeToastAndShow(this, "取消了支付");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReciver);
    }
    private void pay() {

        Utils.showProgressDialog(this);

        OrderIdRequest request = new OrderIdRequest(new Response.Listener<OrderIdResponse>() {
            @Override
            public void onResponse(OrderIdResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {

                    OrderId = response.getMsg();

                    final String otherPrice = String.valueOf(otherPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    AlipayUtil alipayUtil = new AlipayUtil(BorrowOrderAndSettlementActivity.this, BorrowOrderAndSettlementActivity.this);
                    if  (Constants.ALIPAYDEBUG){
                        alipayUtil.pay("cnpc", "零钱包转入", otherPrice,  OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");
                    }else{
                        alipayUtil.pay("cnpc", "零钱包转入", "0.01",  OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");
                    }

                } else {
                    Toast.makeText(mContext, response.getContent(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        },this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        WebUtils.doPost(request);

    }

    @Override
    public void checkResult(boolean isExist) {

    }

    @Override
    public void payOk() {

        ResetPay(1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Utils.closeDialog();
    }

    @Override
    public void payFailed() {
        ResetPay(2);
    }

    @Override
    public void paying() {

    }
}
