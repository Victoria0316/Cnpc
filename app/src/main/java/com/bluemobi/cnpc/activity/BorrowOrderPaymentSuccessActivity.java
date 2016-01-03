package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.model.OrderPaymentSuccessBean;
import com.bluemobi.cnpc.network.model.SettlementBean;
import com.bluemobi.cnpc.network.request.OrderPaymentSuccessRequest;
import com.bluemobi.cnpc.network.response.OrderPaymentSuccessResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/23.
 * P5_3_2预借-提交订单
 */

@ContentView(R.layout.activity_preoil_order_success)
@PageName(R.string.s_order_payment_success)
public class BorrowOrderPaymentSuccessActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.success)
    TextView success;

    @Bind(R.id.orderNo)
    TextView orderNo;

    @Bind(R.id.transactionTime)
    TextView transactionTime;

    @Bind(R.id.model)
    TextView model;

    @Bind(R.id.price)
    TextView price;

    @Bind(R.id.productNum)
    TextView productNum;

    @Bind(R.id.payAmount)
    TextView payAmount;

    @Bind(R.id.order_annotation)
    TextView oilFuturesLiters;

    @Bind(R.id.productProcedure)
    TextView productProcedure;

    @Bind(R.id.preferentialAmount)
    TextView preferentialAmount;

    @Bind(R.id.changeAmount)
    TextView changeAmount;

    @Bind(R.id.actualAmount)
    TextView actualAmount;

    @Bind(R.id.actualPayment)
    TextView actualPayment;

    @Bind(R.id.awardedIntegral)
    TextView awardedIntegral;

    @Bind(R.id.availableAmount)
    TextView availableAmount;

    @Bind(R.id.text_oil)
    TextView text_oil;

    @Bind(R.id.text_productNum)
    TextView text_productNum;

    @Bind(R.id.text_payAmount)
    TextView text_payAmount;

    @Bind(R.id.rl_shake)
    RelativeLayout rl_shake;


    /**
     * 页面传递
     */
    private String oilId;

    /**
     * 页面传递
     */
    private String totalChangeAmount;

    /**
     * 页面传递
     */
    private String RechargeAmount;
    /**
     * 页面传递
     */
    private String realCoinUse;
    /**
     * 页面传递
     */
    private String countNum;


    private OrderPaymentSuccessBean data;

    @Bind(R.id.ll_productProcedure)
    LinearLayout ll_productProcedure;

    @Bind(R.id.ll_changeAmount)
    LinearLayout ll_changeAmount;

    @Bind(R.id.ll_actualAmount)
    LinearLayout ll_actualAmount;

    @Bind(R.id.ll_awardedIntegral)
    LinearLayout ll_awardedIntegral;

    @Bind(R.id.ll_availableAmount)
    LinearLayout ll_availableAmount;

    /**
     * 支付渠道
     */
    private String paychannel;

    /**
     * 支付渠道实际支付金额
     */
    private String actualamount;

    /**
     * 订单ID
     */
    private String OrderId;


    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_order_payment_success, R.drawable.return_arrow, true);

        showLoadingPage(false);

    }


    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        success.setText("恭喜您，预借成功");
        text_oil.setText("预借油品 ");
        text_productNum.setText("预借吨数：");
        text_payAmount.setText("担保金额：");

        Intent intent=getIntent();
        SettlementBean data= (SettlementBean) intent.getSerializableExtra("item");

        orderNo.setText(data.getOrderNo());
        transactionTime.setText(data.getTransactionTime());
        model.setText(data.getProductName());
        price.setText(data.getProductPrice());
        productNum.setText(data.getProductNum() + "吨");
        payAmount.setText(data.getPayAmount() + "元");
        oilFuturesLiters.setText("(已转入借用油帐内，当前累计借用油" + data.getOilBorrowLiters() + "吨)");
        productProcedure.setText(data.getProductProcedure() + "元");
        preferentialAmount.setText(data.getPreferentialAmount() + "元");
        changeAmount.setText(data.getChangeAmount() + "元");
        actualAmount.setText(data.getActualAmount() + "元");
        actualPayment.setText(data.getActualPayment() + "元");
        awardedIntegral.setText(data.getAwardedIntegral());
        availableAmount.setText(data.getAvailableAmount());


        if ("0".equals((int) Double.parseDouble(data.getProductProcedure()) + "")) {
            ll_productProcedure.setVisibility(View.GONE);
        }
        if ("0".equals((int) Double.parseDouble(data.getChangeAmount()) + "")) {
            ll_changeAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int) Double.parseDouble(data.getActualAmount()) + "")) {
            ll_actualAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int) Double.parseDouble(data.getAwardedIntegral()) + "")) {
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
        Utils.moveTo(this, ProductSelectionActivity.class);
        finishAll();
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
