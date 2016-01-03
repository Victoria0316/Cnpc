package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.model.PayPreferentialInfo;
import com.bluemobi.cnpc.network.model.SettlementBean;
import com.bluemobi.cnpc.network.request.SettlementRequest;
import com.bluemobi.cnpc.network.response.SettlementResponse;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/22.
 * <p/>
 * p4-4 套餐—提交订单
 */

@ContentView(R.layout.activity_settlement)
@PageName(R.string.pay_success)
public class SettlementActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_preferentialPrice)
    LinearLayout ll_preferentialPrice;

    @Bind(R.id.ll_changeAmount)
    LinearLayout ll_changeAmount;

    @Bind(R.id.ll_redAmount)
    LinearLayout ll_redAmount;

    @Bind(R.id.ll_actualAmount)
    LinearLayout ll_actualAmount;

    @Bind(R.id.ll_awardedIntegral)
    LinearLayout ll_awardedIntegral;

    @Bind(R.id.ll_availableAmount)
    LinearLayout ll_availableAmount;


    @Bind(R.id.order_annotation)
    TextView order_annotation;

    @Bind(R.id.rl_shake)
    RelativeLayout rl_shake;

    @Bind(R.id.orderNo)
    TextView orderNo;

    @Bind(R.id.transactionTime)
    TextView transactionTime;

    @Bind(R.id.payAmount)
    TextView payAmount;

    @Bind(R.id.preferentialPrice)
    TextView preferentialPrice;

    @Bind(R.id.preferentialAmount)
    TextView preferentialAmount;

    @Bind(R.id.changeAmount)
    TextView changeAmount;

    @Bind(R.id.redAmount)
    TextView redAmount;

    @Bind(R.id.actualAmount)
    TextView actualAmount;

    @Bind(R.id.actualPayment)
    TextView actualPayment;

    @Bind(R.id.awardedIntegral)
    TextView awardedIntegral;

    @Bind(R.id.availableAmount)
    TextView availableAmount;

    @Bind(R.id.order_annotation_text)
    TextView order_annotation_text;

    private String timeLimit;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(SettlementActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.pay_success, R.drawable.return_arrow, true);

        showLoadingPage(false);

    }

/*    @Override
    protected CnpcHttpJsonRequest initRequest() {

        PackageId = getIntent().getStringExtra("PackageId");
        countNum = getIntent().getStringExtra("countNum");
        preferential = getIntent().getStringExtra("preferentxial");
        actual_cost = getIntent().getStringExtra("actual_cost");
        RedId = getIntent().getStringExtra("RedId");
        RedPreferential = getIntent().getStringExtra("RedPreferential");
        realCoinUse = getIntent().getStringExtra("realCoinUse");
        timeLimit = getIntent().getStringExtra("timeLimit");
        paychannel = getIntent().getStringExtra("paychannel");
        actualamount = getIntent().getStringExtra("actualamount");
        OrderId = getIntent().getStringExtra("OrderId");

        Utils.showProgressDialog(this);
        SettlementRequest request;
        request = new SettlementRequest
                (
                        new Response.Listener<SettlementResponse>() {
                            @Override
                            public void onResponse(SettlementResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    data = response.getData();

                                    orderNo.setText(data.getOrderNo());
                                    transactionTime.setText(data.getTransactionTime());
                                    payAmount.setText(data.getPayAmount() + "元");
                                    redAmount.setText(data.getRedAmount() + "元");
                                    preferentialAmount.setText(data.getPreferentialAmount() + "元");
                                    preferentialPrice.setText(data.getPreferentialPrice() + "元");
                                    changeAmount.setText(data.getChangeAmount() + "元");
                                    actualAmount.setText(data.getActualAmount() + "元");
                                    actualPayment.setText(data.getActualPayment() + "元");
                                    awardedIntegral.setText(data.getAwardedIntegral());
                                    availableAmount.setText(data.getAvailableAmount());

                                    if (timeLimit.equals("0")) {
                                        order_annotation_text.setText("(已转入油钱包，当前油钱包余额");
                                        order_annotation.setText(data.getOilBalance());
                                    } else {
                                        order_annotation.setText(data.getFrozenPayment());
                                    }

                                    if ("0".equals((int) Double.parseDouble(data.getPreferentialPrice()) + "")) {
                                        ll_preferentialPrice.setVisibility(View.GONE);
                                    }
                                    if ("0".equals((int) Double.parseDouble(data.getChangeAmount()) + "")) {
                                        ll_changeAmount.setVisibility(View.GONE);
                                    }
                                    if ("0".equals((int) Double.parseDouble(data.getRedAmount()) + "")) {
                                        ll_redAmount.setVisibility(View.GONE);
                                    }
                                    if ("0".equals((int) Double.parseDouble(data.getActualAmount()) + "")) {
                                        ll_actualAmount.setVisibility(View.GONE);
                                    }
                                    if ("0".equals((int) Double.parseDouble(data.getAwardedIntegral()) + "")) {
                                        ll_awardedIntegral.setVisibility(View.GONE);
                                        ll_availableAmount.setVisibility(View.GONE);
                                    }


                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setId(OrderId);//
        request.setProductId(PackageId); //	套餐ID
        request.setProductNum(countNum);//	套餐交易数量 没有传0
        request.setPreferentialPrice(preferential); // 	优惠价格 没有传0.00
        request.setPreferentialAmount(actual_cost); //	优惠后应该支付金额 没有传0.00
        request.setCustomerBonusId(RedId);  //	使用的红包ID 如果没有传“-1”
        request.setRedAmount(RedPreferential);//	红包优惠金额 没有传0.00
        request.setChangeAmount(realCoinUse); //	零钱包优惠金额 没有传0.00

        if ("999".equals(paychannel)) {
            request.setPayChannel(null);//	支付渠道（1现金，2银行卡转账，3支付宝，4微信，5银联，11内部转账）
            request.setActualAmount(null);//支付渠道实际支付金额 如果支付渠道传值，此值必须传
        } else {

            request.setPayChannel(paychannel);//	支付渠道（1现金，2银行卡转账，3支付宝，4微信，5银联，11内部转账）
            request.setActualAmount(actualamount);//支付渠道实际支付金额 如果支付渠道传值，此值必须传
        }
        return request;
    }*/

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);

        Intent intent = getIntent();
        SettlementBean data = (SettlementBean) intent.getSerializableExtra("item");

        timeLimit = getIntent().getStringExtra("timeLimit");

        orderNo.setText(data.getOrderNo());
        transactionTime.setText(data.getTransactionTime());
        payAmount.setText(data.getPreferentialAmount() + "元");
        redAmount.setText(data.getRedAmount() + "元");
        preferentialAmount.setText(data.getPayAmount() + "元");
        preferentialPrice.setText(data.getPreferentialPrice() + "元");
        changeAmount.setText(data.getChangeAmount() + "元");
        actualAmount.setText(data.getActualAmount() + "元");
        actualPayment.setText(data.getActualPayment() + "元");
        awardedIntegral.setText(data.getAwardedIntegral());
        availableAmount.setText(data.getAvailableAmount());

        if (timeLimit.equals("0")) {
            order_annotation_text.setText("(已转入油钱包，当前油钱包余额");
            order_annotation.setText(data.getOilBalance());
        } else {
            order_annotation.setText(data.getFrozenPayment());
        }

        if (Double.parseDouble(data.getPreferentialPrice()) <= 0) {
            ll_preferentialPrice.setVisibility(View.GONE);
        }
        if ( Double.parseDouble(data.getChangeAmount()) <= 0) {
            ll_changeAmount.setVisibility(View.GONE);
        }
        if (Double.parseDouble(data.getRedAmount()) <= 0) {
            ll_redAmount.setVisibility(View.GONE);
        }
        if ( Double.parseDouble(data.getActualAmount()) <= 0) {
            ll_actualAmount.setVisibility(View.GONE);
        }
        if ( Double.parseDouble(data.getAwardedIntegral()) <= 0) {
            ll_awardedIntegral.setVisibility(View.GONE);
            ll_availableAmount.setVisibility(View.GONE);
        }


        rl_shake.setOnClickListener(this);


    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finishAll();
        Utils.moveTo(this, PayPreferentialActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_shake:
                Utils.moveTo(this, ShakeActivity.class);
                break;
        }
    }
}
