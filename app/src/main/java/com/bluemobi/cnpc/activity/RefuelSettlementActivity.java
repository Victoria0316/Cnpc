package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.model.SettlementBean;
import com.bluemobi.cnpc.network.response.OilPaySaveOrderInfoResponse;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/24.
 * <p/>
 * p6-2-2 加油-提交订单
 */

@ContentView(R.layout.activity_refuel_settlement)
@PageName(R.string.pay_success)
public class RefuelSettlementActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.refuel_price)
    TextView refuel_price;

    @Bind(R.id.rl_shake)
    RelativeLayout rl_shake;

    @Bind(R.id.tv_orderNo)
    TextView tv_orderNo;

    @Bind(R.id.tv_order_success_time)
    TextView tv_order_success_time;

    @Bind(R.id.val)
    TextView val;

    @Bind(R.id.tv_countMoney)
    TextView tv_countMoney;
    @Bind(R.id.tv_oilNum)
    TextView tv_oilNum;


    @Bind(R.id.tv_preferentialAmount)
    TextView tv_preferentialAmount;

    @Bind(R.id.tv_payAmount)
    TextView tv_payAmount;


    @Bind(R.id.tv_preferentialPrice)
    TextView tv_preferentialPrice;

    @Bind(R.id.tv_couponAmount)
    TextView tv_couponAmount;

    @Bind(R.id.tv_oilAmount)
    TextView tv_oilAmount;

    @Bind(R.id.tv_changeAmount)
    TextView tv_changeAmount;

    @Bind(R.id.tv_actualAmount)
    TextView tv_actualAmount;
    @Bind(R.id.tv_actualPayment)
    TextView tv_actualPayment;
    @Bind(R.id.tv_awardedIntegral)
    TextView tv_awardedIntegral;

    @Bind(R.id.tv_availableAmount)
    TextView tv_availableAmount;

    @Bind(R.id.ll_preferentialAmount)
    LinearLayout ll_preferentialAmount;//实际金额

    @Bind(R.id.ll_payAmount)
    LinearLayout ll_payAmount;//加油金额

    @Bind(R.id.ll_preferentialPrice)
    LinearLayout ll_preferentialPrice;//优惠金额

    @Bind(R.id.ll_couponAmount)
    LinearLayout ll_couponAmount;//优惠劵

    @Bind(R.id.ll_oilAmount)
    LinearLayout ll_oilAmount;//油钱包

    @Bind(R.id.ll_changeAmount)
    LinearLayout ll_changeAmount;//零钱包

    @Bind(R.id.ll_actualAmount)
    LinearLayout ll_actualAmount;//其他支付

    @Bind(R.id.ll_actualPayment)
    LinearLayout ll_actualPayment;//实际支付

    @Bind(R.id.ll_awardedIntegral)
    LinearLayout ll_awardedIntegral;//赠积分

    @Bind(R.id.ll_availableAmount)
    LinearLayout ll_availableAmount;//总积分


    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(RefuelSettlementActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.pay_success, R.drawable.return_arrow, true);
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);

        Intent intent=getIntent();
        SettlementBean data= (SettlementBean) intent.getSerializableExtra("item");

        tv_orderNo.setText(data.getOrderNo());
        tv_order_success_time.setText(data.getTransactionTime());
        val.setText(data.getProductName());
        tv_orderNo.setText(data.getOrderNo());
        wrapPrice(data.getProductPrice());

        if ("0".equals((int)Double.parseDouble(data.getPreferentialAmount()) + "")) {
            ll_preferentialAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.getPayAmount()) + "")) {
            ll_payAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.getPreferentialPrice()) + "")) {
            ll_preferentialPrice.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.getCouponAmount()) + "")) {
            ll_couponAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.getOilAmount()) + "")) {
            ll_oilAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.getActualPayment()) + "")) {
            ll_actualPayment.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.getChangeAmount()) + "")) {
            ll_changeAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.getAwardedIntegral()) + "")) {
            ll_awardedIntegral.setVisibility(View.GONE);
            ll_availableAmount.setVisibility(View.GONE);
        }
        tv_countMoney.setText(data.getPayAmount()+"元");
        tv_oilNum.setText("升数：" + data.getProductNum() + "升");
        tv_preferentialAmount.setText(data.getPreferentialAmount()+"元");
        tv_payAmount.setText(data.getPayAmount()+"元");
        tv_preferentialPrice.setText(data.getPreferentialPrice()+"元");
        tv_couponAmount.setText(data.getCouponAmount()+"元");
        tv_oilAmount.setText(data.getOilAmount()+"元");
        tv_changeAmount.setText(data.getChangeAmount()+"元");
        if (StringUtils.isNotEmpty(data.getActualAmount()))
        {
            tv_actualAmount.setText(data.getActualAmount()+"元");
        }else{
            tv_actualAmount.setText("0.00");
            if ("0".equals((int)Double.parseDouble(tv_actualAmount.getText().toString()) + "")) {
                ll_actualAmount.setVisibility(View.GONE);
            }
        }
        tv_actualAmount.setText(data.getActualAmount()+"元");
        tv_actualPayment.setText(data.getActualPayment()+"元");
        tv_awardedIntegral.setText(data.getAwardedIntegral());
        tv_availableAmount.setText(data.getAvailableAmount());


        rl_shake.setOnClickListener(this);

    }

   /* private void wrapData(SettlementBean data) {

        if (data == null) {
            return;
        }


        tv_orderNo.setText(data.orderNo);
        tv_order_success_time.setText(data.transactionTime);
        val.setText(data.productName);
        tv_orderNo.setText(data.orderNo);
        wrapPrice(data.productPrice);

        if ("0".equals((int)Double.parseDouble(data.preferentialAmount) + "")) {
            ll_preferentialAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.payAmount) + "")) {
            ll_payAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.preferentialPrice) + "")) {
            ll_preferentialPrice.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.couponAmount) + "")) {
            ll_couponAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.oilAmount) + "")) {
            ll_oilAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.actualPayment) + "")) {
            ll_actualPayment.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.changeAmount) + "")) {
            ll_changeAmount.setVisibility(View.GONE);
        }
        if ("0".equals((int)Double.parseDouble(data.awardedIntegral) + "")) {
            ll_awardedIntegral.setVisibility(View.GONE);
            ll_availableAmount.setVisibility(View.GONE);
        }
        tv_countMoney.setText(data.payAmount+"元");
        tv_oilNum.setText("升数：" + data.productNum + "升");
        tv_preferentialAmount.setText(data.preferentialAmount+"元");
        tv_payAmount.setText(data.payAmount+"元");
        tv_preferentialPrice.setText(data.preferentialPrice+"元");
        tv_couponAmount.setText(data.couponAmount+"元");
        tv_oilAmount.setText(data.oilAmount+"元");
        tv_changeAmount.setText(data.changeAmount+"元");
        if (StringUtils.isNotEmpty(data.actualAmount))
        {
            tv_actualAmount.setText(data.actualAmount+"元");
        }else{
            tv_actualAmount.setText("0.00");
            if ("0".equals((int)Double.parseDouble(tv_actualAmount.getText().toString()) + "")) {
                ll_actualAmount.setVisibility(View.GONE);
            }
        }
        tv_actualAmount.setText(data.actualAmount+"元");
        tv_actualPayment.setText(data.actualPayment+"元");
        tv_awardedIntegral.setText(data.awardedIntegral);
        tv_availableAmount.setText(data.availableAmount);
    }*/
    private void wrapPrice(String price) {
        int size = price.length();
        String text = String.format(getResources().getString(R.string.refuel_price), price);

        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_pink)), text.indexOf(price), text.indexOf(price) + size, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        refuel_price.setText(style);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        Utils.moveTo(this,GasStationDetailsActivity.class);
       /* finishSearch();*/
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
