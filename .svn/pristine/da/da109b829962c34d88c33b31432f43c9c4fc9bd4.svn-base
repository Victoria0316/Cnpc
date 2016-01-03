package com.bluemobi.cnpc.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
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
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.model.OilPayTransBean;
import com.bluemobi.cnpc.network.model.SettlementBean;
import com.bluemobi.cnpc.network.request.OilCustomerFinanceInfoRequest;
import com.bluemobi.cnpc.network.request.OilPaySaveOrderInfoRequest;
import com.bluemobi.cnpc.network.request.OrderIdRequest;
import com.bluemobi.cnpc.network.request.ResetPayRequest;
import com.bluemobi.cnpc.network.request.WeChatPayRequest;
import com.bluemobi.cnpc.network.response.OilCustomerFinanceInfoResponse;
import com.bluemobi.cnpc.network.response.OilPaySaveOrderInfoResponse;
import com.bluemobi.cnpc.network.response.OrderIdResponse;
import com.bluemobi.cnpc.network.response.ResetPayResponse;
import com.bluemobi.cnpc.network.response.WeChatPayResponse;
import com.bluemobi.cnpc.util.AlipayUtil;
import com.bluemobi.cnpc.util.Arith;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.util.WxPayUtils;
import com.bluemobi.cnpc.view.CnpcAlertDialog;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.view.SwitchView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/24.
 * <p/>
 * p6-2-1 订单结算
 */

@ContentView(R.layout.activity_refuel_order_settlement)
@PageName(R.string.order_settlement)
public class RefuelOrderSettlementActivity extends BaseActivity implements View.OnClickListener, AlipayUtil.AlipayStausListener {

    private final static String tag = "RefuelOrderSettlementActivity";


    @Bind(R.id.rl_oil_use)
    RelativeLayout rl_oil_use;

    @Bind(R.id.rl_oil_coin)
    RelativeLayout rl_oil_coin;

    @Bind(R.id.tv_gasName)
    TextView tv_gasName;
    @Bind(R.id.order_pay)
    TextView order_pay;

    @Bind(R.id.sv_oilMoney)
    SwitchView sv_oilMoney;

    @Bind(R.id.sv_changeMoney)
    SwitchView sv_changeMoney;

    @Bind(R.id.tv_coupon)
    TextView tv_coupon;

    @Bind(R.id.other_pay)
    RelativeLayout other_pay;

    @Bind(R.id.settlement)
    TextView settlement;

    @Bind(R.id.tv_sumPrice)
    TextView tv_sumPrice;

    @Bind(R.id.tv_oilNum)
    TextView tv_oilNum;

    @Bind(R.id.tv_pre_price)
    TextView tv_pre_price;

    @Bind(R.id.tv_actual_amount)
    TextView tv_actual_amount;

    @Bind(R.id.val)
    TextView val;


    @Bind(R.id.rl_discount_Amount)
    RelativeLayout rl_discount_Amount;

    @Bind(R.id.rl_coupon)
    RelativeLayout rl_coupon;

    @Bind(R.id.tv_discount_Amount)
    TextView tv_discount_Amount;

    private String oilMoneyBalance;
    private String changeBalance;

    private OilPayTransBean oilPayTransBean;

    @Bind(R.id.order_coupon_num)
    TextView order_coupon_num;
    @Bind(R.id.order_oil_wallet_num)
    TextView order_oil_wallet_num;
    @Bind(R.id.order_change_wallet)
    TextView order_change_wallet;
    @Bind(R.id.order_change_wallet_num)
    TextView order_change_wallet_num;
    @Bind(R.id.order_pay_num)
    TextView order_pay_num;

    @Bind(R.id.tv_oil_money)
    TextView tv_oil_money;

    @Bind(R.id.tv_change_money)
    TextView tv_change_money;

    @Bind(R.id.order_total_num)
    TextView order_total_num;

    // NumberFormat currency = NumberFormat.getCurrencyInstance(); //建立货币格式化引用

    /**
     * 支付渠道实际支付金额
     */
    private String actualamount;

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
        titleBarManager.init(RefuelOrderSettlementActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.order_settlement, R.drawable.return_arrow, true);
        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        oilPayTransBean = (OilPayTransBean) getIntent().getSerializableExtra("oilPayTransBean");
        tv_gasName.setText(oilPayTransBean.getGasName());
        val.setText(oilPayTransBean.getGasType());
        tv_pre_price.setText(oilPayTransBean.getSinglePrice() + "元/升");
        tv_sumPrice.setText(oilPayTransBean.getSumPrice() + "元");
        tv_oilNum.setText(oilPayTransBean.getCalOilNum() + "升");
        other_pay.setOnClickListener(this);
        settlement.setOnClickListener(this);
        getOrderSettleerver();

    }

    public void getOrderSettleerver() {
        Utils.showProgressDialog(mContext);
        OilCustomerFinanceInfoRequest request = new OilCustomerFinanceInfoRequest
                (
                        new Response.Listener<OilCustomerFinanceInfoResponse>() {
                            @Override
                            public void onResponse(final OilCustomerFinanceInfoResponse response) {
                                Utils.closeDialog();
                                actionAmount(response);
                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setBrandid(oilPayTransBean.getGasID());
        request.setProductTotalValue(oilPayTransBean.getCalOilNum());
        WebUtils.doPost(request);
    }

    private String couponDiscount;

    private String preferentialPrice;

    private String coupon_id;

    /**
     * 优惠券 零钱包 总额度
     */
    private double oilPursePre;

    private void actionAmount(OilCustomerFinanceInfoResponse response) {

        preferentialPrice = response.data.preferentialPrice;
        if (StringUtils.isEmpty(preferentialPrice))
            preferentialPrice = "0.0";
        //油钱包
        oilMoneyBalance = response.data.oilMoneyBalance;
        if (StringUtils.isEmpty(oilMoneyBalance)) {
            oilMoneyBalance = "0.0";
            Log.e("tagoilMoneyBalance->", oilMoneyBalance);
        }
        tv_oil_money.setText("余额" + oilMoneyBalance + "元");
        //零钱包
        changeBalance = response.data.changeBalance;
        if (StringUtils.isEmpty(changeBalance)) {
            changeBalance = "0.0";
            Log.e("tagchangeBalance->", changeBalance);
        }
        tv_change_money.setText("余额" + changeBalance + "元");

        //优惠金额
        if ("0".equals(preferentialPrice)) {
            preferentialPrice = "0.0";
            rl_discount_Amount.setVisibility(View.GONE);
            tv_discount_Amount.setText(preferentialPrice + "元");

        } else {
            tv_discount_Amount.setText(preferentialPrice + "元");
        }
        if (response.data.customerCoupon != null) {
            if (StringUtils.isNotEmpty(response.data.customerCoupon.couponName)) {
                tv_coupon.setText(response.data.customerCoupon.couponName);
            }
            //优惠券
            coupon_id = response.data.customerCoupon.id;
            couponDiscount = response.data.customerCoupon.couponDiscount;
            order_coupon_num.setText(response.data.customerCoupon.couponDiscount);

        } else {
            rl_coupon.setVisibility(View.GONE);
            order_coupon_num.setText("0.00");
            couponDiscount = "0.0";
        }
        // 实际支付 100
        final double actualPay = Arith.sub(Double.parseDouble(oilPayTransBean.getSumPrice()),
                Double.parseDouble(preferentialPrice));
        tv_actual_amount.setText(Utils.StringTo2decimal(actualPay + "") + "元");
        order_total_num.setText(Utils.StringTo2decimal("" + actualPay));

        if ("0.00".equals(oilMoneyBalance.trim()) || "0".equals(oilMoneyBalance.trim())) {
            rl_oil_use.setVisibility(View.GONE);
            sv_oilMoney.setChecked(false);
            Log.e("sv_oilMoney--false>", oilMoneyBalance + "");
        } else {
            rl_oil_use.setVisibility(View.VISIBLE);
            sv_oilMoney.setChecked(true);
            Log.e("sv_oilMoney-true->", oilMoneyBalance + "");
        }
        if ("0.00".equals(changeBalance.trim()) || "0".equals(changeBalance.trim())) {
            //TODO
            rl_oil_coin.setVisibility(View.GONE);
            sv_changeMoney.setChecked(false);

            Log.e("sv_changeMoney--false>", changeBalance + "");
        } else {
            rl_oil_coin.setVisibility(View.VISIBLE);
            sv_changeMoney.setChecked(true);
            Log.e("sv_changeMoney--true>", changeBalance + "");
        }
        //油钱包
        if (sv_oilMoney.isChecked()) {
            order_oil_wallet_num.setText(oilMoneyBalance);
        } else {
            order_oil_wallet_num.setText("0.00");
            oilMoneyBalance = "0.0";

        }
        //零钱包
        if (sv_changeMoney.isChecked()) {
            order_change_wallet_num.setText(changeBalance);
        } else {
            order_change_wallet_num.setText("0.00");
            changeBalance = "0.0";
        }

        //其他支付 changeBalance  99999 oilMoneyBalance 10000
        double add1 = Arith.add(Double.parseDouble(changeBalance), Double.parseDouble(oilMoneyBalance));
        double add2 = Arith.add(add1, Double.parseDouble(couponDiscount));

        //优惠券+油钱包
        oilPursePre = Arith.add(Double.parseDouble(couponDiscount), Double.parseDouble(oilMoneyBalance));
        if (oilPursePre >= actualPay) {
            order_coupon_num.setText(couponDiscount);  //优惠券钱
            // 实际支付-优惠券的钱 =  油钱包应库付钱
            double oilActualPay = Arith.sub(actualPay, Double.parseDouble(couponDiscount));
            order_oil_wallet_num.setText(String.valueOf(oilActualPay));//油钱包
            order_change_wallet_num.setText("0.00");   //零钱包 为0
            order_pay_num.setText("0.00");
            //TODO
            sv_changeMoney.setChecked(false);
            sv_changeMoney.setEnabled(false);
            Logger.e("油钱包够----->","sv_changeMoney.setChecked(false);");
        } else {
            // 如果优惠券+油钱包 不够，则用零钱包
            order_coupon_num.setText(couponDiscount);  //优惠券钱
            order_oil_wallet_num.setText(oilMoneyBalance);//油钱包
            double changeActualPay = Arith.sub(actualPay, oilPursePre);
            //
            if (Double.parseDouble(changeBalance) >= changeActualPay) {
                order_change_wallet_num.setText(String.valueOf(changeActualPay));//零钱包
                order_pay_num.setText("0.00");
            } else {
                //如果零钱包不够 则其他支付
                double changePursePre = Arith.add(oilPursePre, Double.parseDouble(changeBalance));
                double otherActualPay = Arith.sub(actualPay, changePursePre);
                order_change_wallet_num.setText(changeBalance);//零钱包
                order_pay_num.setText(String.valueOf(otherActualPay));

            }

            sv_changeMoney.setChecked(true);
            Logger.e("油钱包不够----->","sv_changeMoney.setChecked(true);");
        }
        //有钱包
        sv_oilMoney.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean isChecked) {
                if (isChecked) {
                    //order_oil_wallet_num.setText("¥ " + oilMoneyBalance);
                    //选中
                    if (oilPursePre >= actualPay) {
                        order_coupon_num.setText(couponDiscount);  //优惠券钱
                        // 实际支付-优惠券的钱 =  油钱包应库付钱
                        double oilActualPay = Arith.sub(actualPay, Double.parseDouble(couponDiscount));
                        order_oil_wallet_num.setText(String.valueOf(oilActualPay));//油钱包
                        order_change_wallet_num.setText("0.00");   //零钱包 为0
                        order_pay_num.setText("0.00");//其他支付
                        sv_changeMoney.setChecked(false);
                        sv_changeMoney.setEnabled(false);
                        Logger.e("油钱包够,选中----->", "sv_changeMoney.setChecked(false);");
                    } else  //零钱包是否选中
                    {
                        double changeActualPay = Arith.sub(actualPay, oilPursePre);
                        if (sv_changeMoney.isChecked()) {

                            // 如果优惠券+油钱包 不够，则用零钱包
                            order_coupon_num.setText(couponDiscount);  //优惠券钱
                            order_oil_wallet_num.setText(oilMoneyBalance);//油钱包
                            //
                            if (Double.parseDouble(changeBalance) >= changeActualPay) {
                                order_change_wallet_num.setText(String.valueOf(changeActualPay));//零钱包
                                order_pay_num.setText("0.00");
                            } else {
                                //如果零钱包不够 则其他支付
                                double changePursePre = Arith.add(oilPursePre, Double.parseDouble(changeBalance));
                                double otherActualPay = Arith.sub(actualPay, changePursePre);
                                order_change_wallet_num.setText(changeBalance);//零钱包
                                order_pay_num.setText(String.valueOf(otherActualPay));
                            }
                        } else {
                            order_oil_wallet_num.setText(oilMoneyBalance);
                            order_change_wallet_num.setText("0.00");//零钱包
                            order_pay_num.setText(String.valueOf(changeActualPay));
                        }
                    }

                } else {
                    //油钱包没有选中
                    order_oil_wallet_num.setText("0.00");
                    sv_changeMoney.setEnabled(true);
                    if (sv_changeMoney.isChecked()) {
                        //零钱包选中
                        order_oil_wallet_num.setText("0.00");
                        //优惠券+零钱包
                        double oilChangePurse = Arith.add(Double.parseDouble(couponDiscount), Double.parseDouble(changeBalance));
                        if (oilChangePurse >= actualPay) {
                            //零钱包出的钱
                            double changeActualPayED = Arith.sub(actualPay, Double.parseDouble(couponDiscount));

                            order_change_wallet_num.setText(String.valueOf(changeActualPayED));//零钱包
                            order_pay_num.setText("0.00");
                            sv_oilMoney.setEnabled(false);
                        } else {
                            // 如果零钱包钱不够
                            order_change_wallet_num.setText(changeBalance);//零钱包
                            double otherPay = Arith.sub(actualPay, oilChangePurse);
                            order_pay_num.setText(String.valueOf(otherPay));//其他支付
                        }
                    } else {
                        //零钱包也没有选中
                        order_change_wallet_num.setText("0.00");
                        double oilActualPayed = Arith.sub(actualPay, Double.parseDouble(couponDiscount));
                        order_pay_num.setText(String.valueOf(oilActualPayed));//其他支付
                    }
                }
            }
        });
        sv_changeMoney.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean isChecked) {
                if (isChecked) {


                    //order_change_wallet_num.setText("¥ " + changeBalance);
                    if (sv_oilMoney.isChecked()) //零钱包 油钱包都已经选中
                    {
                        if (oilPursePre >= actualPay) {
                            order_coupon_num.setText(couponDiscount);  //优惠券钱
                            // 实际支付-优惠券的钱 =  油钱包应库付钱
                            double oilActualPay = Arith.sub(actualPay, Double.parseDouble(couponDiscount));
                            order_oil_wallet_num.setText(String.valueOf(oilActualPay));//油钱包
                            order_change_wallet_num.setText("0.00");   //零钱包 为0
                            order_pay_num.setText("0.00");
                        } else {
                            // 如果优惠券+油钱包 不够，则用零钱包
                            order_coupon_num.setText(couponDiscount);  //优惠券钱
                            order_oil_wallet_num.setText(oilMoneyBalance);//油钱包
                            double changeActualPay = Arith.sub(actualPay, oilPursePre);
                            //
                            if (Double.parseDouble(changeBalance) >= changeActualPay) {
                                order_change_wallet_num.setText(String.valueOf(changeActualPay));//零钱包
                                order_pay_num.setText("0.00");
                            } else {
                                //如果零钱包不够 则其他支付
                                double changePursePre = Arith.add(oilPursePre, Double.parseDouble(changeBalance));
                                double otherActualPay = Arith.sub(actualPay, changePursePre);
                                order_change_wallet_num.setText(changeBalance);//零钱包
                                order_pay_num.setText(String.valueOf(otherActualPay));
                            }

                        }
                    } else  //油钱包未选中
                    {
                        order_oil_wallet_num.setText("0.00");
                        sv_changeMoney.setEnabled(true);
                        //优惠券+零钱包
                        double oilChangePurse = Arith.add(Double.parseDouble(couponDiscount), Double.parseDouble(changeBalance));
                        if (oilChangePurse >= actualPay) {
                            //零钱包出的钱
                            double changeActualPayED = Arith.sub(actualPay, Double.parseDouble(couponDiscount));

                            order_change_wallet_num.setText(String.valueOf(changeActualPayED));//零钱包
                            order_pay_num.setText("0.00");
                            sv_oilMoney.setEnabled(false);
                        } else {
                            // 如果零钱包钱不够
                            order_change_wallet_num.setText(changeBalance);//零钱包
                            double otherPay = Arith.sub(actualPay, oilChangePurse);
                            order_pay_num.setText(String.valueOf(otherPay));//其他支付
                        }
                    }
                } else {   // 如果零钱包没有选中
                    order_change_wallet_num.setText("0.00");
                    sv_oilMoney.setEnabled(true);
                    if (sv_oilMoney.isChecked()) {
                        //如果油钱包选中
                        if (oilPursePre >= actualPay) {
                            order_coupon_num.setText(couponDiscount);  //优惠券钱
                            // 实际支付-优惠券的钱 =  油钱包应库付钱
                            double oilActualPay = Arith.sub(actualPay, Double.parseDouble(couponDiscount));
                            order_oil_wallet_num.setText(String.valueOf(oilActualPay));//油钱包
                            order_change_wallet_num.setText("0.00");   //零钱包 为0
                            order_pay_num.setText("0.00");//其他支付
                        } else {
                            //如果油钱包钱不够 则其他支付
                            double otherActualPay = Arith.sub(actualPay, oilPursePre);
                            order_oil_wallet_num.setText(oilMoneyBalance);
                            order_pay_num.setText(String.valueOf(otherActualPay));
                        }
                    } else {
                        //油钱包未选中
                        order_oil_wallet_num.setText("0.00");
                        double otherActualPay = Arith.sub(actualPay, Double.parseDouble(couponDiscount));//其他支付
                        order_pay_num.setText(String.valueOf(otherActualPay));
                    }
                }
            }
        });

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

                if (Float.parseFloat(order_pay_num.getText().toString().trim()) > 0) {//当其它支付大于0

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

        Utils.showProgressDialog(mContext);
        OilPaySaveOrderInfoRequest request = new OilPaySaveOrderInfoRequest
                (
                        new Response.Listener<OilPaySaveOrderInfoResponse>() {
                            @Override
                            public void onResponse(final OilPaySaveOrderInfoResponse response) {
                                Utils.closeDialog();
                                String otherPrice = order_pay_num.getText().toString().trim();//String.valueOf(otherPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                                actualamount = otherPrice;
                                OrderId = response.getMsg();

                                if (response.getData() != null) {
                                    if (response != null && response.getStatus() == 0) {
                                        WxPayUtils.pay(RefuelOrderSettlementActivity.this, response.getData());
                                    } else {
                                        Utils.makeToastAndShow(getBaseContext(), OrderId);
                                    }

                                } else {
                                    if (isAlipayChatPay) {

                                        AlipayUtil alipayUtil = new AlipayUtil(RefuelOrderSettlementActivity.this, RefuelOrderSettlementActivity.this);
                                        if (Constants.ALIPAYDEBUG) {
                                            alipayUtil.pay("cnpc", "零钱包转入", otherPrice, OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");
                                        } else {

                                            alipayUtil.pay("cnpc", "零钱包转入", "0.01", OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");
                                        }

                                    } else {
                                        ResetPay(1);
                                    }

                                }


                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setProductId(oilPayTransBean.getProductID()); //	油品ID
        request.setProductNum(oilPayTransBean.getCalOilNum());//	加油升数 没有传0
        request.setProductTotalValue(oilPayTransBean.getSumPrice());//	加油金额 没有传0.00=
        request.setPreferentialPrice(preferentialPrice);//	优惠金额金额 没有传0.00
        request.setChangeAmount(order_change_wallet_num.getText().toString());//	零钱包金额 没有传0.00
        request.setOilAmount(order_oil_wallet_num.getText().toString());//	油钱包金额
        request.setVipAmount("0.00");//	会员优惠金额 没有传0.00
        request.setGasStationId(oilPayTransBean.getGasID());
        request.setCouponAmount((int) Double.parseDouble(couponDiscount) + "");//	优惠券优惠金额
        request.setCustomerCouponId(coupon_id); //优惠券ID ，当有优惠券金额时，此项必填

        String otherPrice = order_pay_num.getText().toString().trim();

        if (type == 999) {
            request.setPayChannel(null);//	支付渠道（1现金，2银行卡转账，3支付宝，4微信，5银联，11内部转账）
            request.setActualAmount(null);//支付渠道实际支付金额 如果支付渠道传值，此值必须传
        } else if (type == 3) {
            request.setPayChannel("4");
            request.setActualAmount(otherPrice);
        } else if (type == 2) {
            request.setPayChannel("3");
            request.setActualAmount(otherPrice);
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
                    Utils.makeToastAndShow(mContext, "提交成功");
                    Intent intent = new Intent(mContext, RefuelSettlementActivity.class);
                    intent.putExtra("item", item);
                    startActivity(intent);
                    finishAll();

                }
            }
        }, this);
        request.setId(OrderId);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("2");//类型 1优惠充值 2加油 3预购油购买 4零钱包充值 5借用油购买
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

                    if (response != null && response.getStatus() == 0) {
                        WxPayUtils.pay(RefuelOrderSettlementActivity.this, response.getData());
                    } else {
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
        request.setMoney(actualamount);
        WebUtils.doPost(request);
    }

    /**
     * 广播接收器
     */
    private class MyBroadcastReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

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
           // Utils.makeToastAndShow(this, "取消了支付");
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

                    final String otherPrice = order_pay_num.getText().toString().trim();
                    // String.valueOf(otherPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    actualamount = otherPrice;

                    AlipayUtil alipayUtil = new AlipayUtil(RefuelOrderSettlementActivity.this, RefuelOrderSettlementActivity.this);
                    if (Constants.ALIPAYDEBUG) {
                        alipayUtil.pay("cnpc", "零钱包转入", otherPrice, OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");
                    } else {

                        alipayUtil.pay("cnpc", "零钱包转入", "0.01", OrderId, "http://121.40.215.27:8801/cnpc/blue/mobelalipayorder/getAlipayOrderInfo");
                    }

                } else {
                    Toast.makeText(mContext, response.getContent(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        WebUtils.doPost(request);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Utils.closeDialog();
    }

    @Override
    public void checkResult(boolean isExist) {

    }

    @Override
    public void payOk() {

        Utils.closeDialog();
        ResetPay(1);

    }

    @Override
    public void payFailed() {
        ResetPay(2);
    }

    @Override
    public void paying() {

    }
}
