package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.model.BorrowOrdersForSureBean;
import com.bluemobi.cnpc.network.model.PayPreferentialInfo;
import com.bluemobi.cnpc.network.model.oilInfoDTOInfo;
import com.bluemobi.cnpc.network.request.BorrowOrdersForSureRequest;
import com.bluemobi.cnpc.network.response.BorrowOrdersForSureResponse;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.CustomTextGroup;
import com.bluemobi.cnpc.view.LoadingPage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * P5_3预借升数确定
 * liufy
 */

@ContentView(R.layout.activity_pre_order_for_sure)
@PageName(R.string.s_bow_orders_for_sure)
public class BorrowOrdersForSureActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_order_prirce)
    TextView tv_order_prirce;

    @Bind(R.id.tv_tu)
    TextView tv_tu;

    @Bind(R.id.tv_order_prirce_ter)
    TextView tv_order_prirce_ter;

    @Bind(R.id.iv_min)
    ImageView iv_min;

    @Bind(R.id.iv_add)
    ImageView iv_add;

    @Bind(R.id.et_num)
    EditText et_num;

    @Bind(R.id.tv_confirm)
    TextView tv_confirm;

    @Bind(R.id.oil_Purchase)
    TextView oil_Purchase;

    @Bind(R.id.tv_oil_type)
    TextView tv_oil_type;

    @Bind(R.id.tv_oil_price)
    TextView tv_oil_price;

    @Bind(R.id.time)
    TextView timeDate;

    @Bind(R.id.tv_explain1)
    TextView tv_explain1;

    @Bind(R.id.tv_explain2)
    TextView tv_explain2;

    @Bind(R.id.tv_explain3)
    TextView tv_explain3;

    @Bind(R.id.ctg_min_purchase)
    CustomTextGroup ctg_min_purchase;

    @Bind(R.id.ctg_min_increasing)
    CustomTextGroup ctg_min_increasing;

    @Bind(R.id.ctg_pre_procedure_rates)
    CustomTextGroup ctg_pre_procedure_rates;

    /**
     * 页面传递
     */
    private String oilId;

    private BorrowOrdersForSureBean data;

    private int count = 1;
    private BigDecimal amount;//每次递增最小量

    private BigDecimal min_purchase;//最小借用量

    private BigDecimal CustomerPrice; //当前数量

    private BigDecimal currentVol; //当前升数

    private static final int MAX_VOLUME = 1000;

    private String str;

    private String price;

    private String futuresBuyProcedure;

    private double total;

    @Override
    protected void initBase() {
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(BorrowOrdersForSureActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_bow_orders_for_sure, R.drawable.return_arrow, true);
        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        String formatTime = df.format(time);

        timeDate.setText(formatTime);

        oilId = getIntent().getStringExtra("oilId");

        Request();

        iv_min.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);

        oil_Purchase.setText("借用油品");
        tv_tu.setText("请输入借用油品的吨数");
        tv_order_prirce_ter.setText("担保金额：");

        tv_explain1.setText(R.string.tv_explain1);
        tv_explain2.setText(R.string.tv_explain2);
        tv_explain3.setText(R.string.tv_explain3);

    }

    private void Request() {

        Utils.showProgressDialog(mContext);
        BorrowOrdersForSureRequest request = new BorrowOrdersForSureRequest
                (
                        new Response.Listener<BorrowOrdersForSureResponse>() {
                            @Override
                            public void onResponse(BorrowOrdersForSureResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    data = response.getData();
                                    tv_oil_type.setText(data.getProductName());

                                    price = data.getCustomerPrice();
                                    tv_oil_price.setText(price + "元/吨");
                                    CustomerPrice = new BigDecimal(Double.parseDouble(price));

                                    ctg_min_purchase.setLeftText("最小借用量");
                                    ctg_min_purchase.setRightText(data.getBorrowMinPurchases() + "吨");

                                    min_purchase = new BigDecimal(Double.parseDouble(data.getBorrowMinPurchases()))
                                            .setScale(4, BigDecimal.ROUND_HALF_UP);

                                    ctg_min_increasing.setRightText(data.getBorrowMinIncremental() + "吨");

                                    futuresBuyProcedure = data.getBorrowBuyProcedure();
                                    ctg_pre_procedure_rates.setRightText(Utils.StringTo2decimal(futuresBuyProcedure) + "%");
                                    ctg_pre_procedure_rates.setLeftText("预借手续费率");

                                    total = CustomerPrice.multiply(min_purchase)
                                            .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    tv_order_prirce.setText(String.valueOf(total));

                                    currentVol = new BigDecimal(StringUtils.isEmpty(data.getBorrowMinPurchases()) ?
                                            "0" : data.getBorrowMinPurchases());
                                    currentVol = currentVol.setScale(4, BigDecimal.ROUND_UP);
                                    et_num.setText(String.valueOf(currentVol));

                                    amount = new BigDecimal(data.getBorrowMinIncremental())
                                            .setScale(4, BigDecimal.ROUND_HALF_UP);

                                    et_num.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                        @Override
                                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                            if (actionId == EditorInfo.IME_ACTION_DONE)

                                            {

                                                //et_num.setText(Utils.StringTo2decimal(String.valueOf(total)));
                                                String s = et_num.getText().toString();
                                                str = s.toString();

                                                if (StringUtils.isNotEmpty(str)) {
                                                    BigDecimal temp = new BigDecimal(str);
                                                    currentVol = temp;
                                                    if (temp.floatValue() < min_purchase.floatValue()) {// < zui di
                                                        et_num.setText(String.valueOf(min_purchase));
                                                        tv_order_prirce.setText(String.valueOf(total));
                                                    }else if(temp.floatValue() >= MAX_VOLUME){
                                                        float a  = MAX_VOLUME - min_purchase.floatValue();
                                                        int b = (int)(a%amount.floatValue()==0?a/amount.floatValue() -1:a/amount.floatValue());
                                                        float c = min_purchase.floatValue() + b*amount.floatValue();

                                                        currentVol = new BigDecimal(c);
                                                        total = CustomerPrice.multiply(currentVol)
                                                                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                                        tv_order_prirce.setText(String.valueOf(total));
                                                        et_num.setText(String.valueOf(currentVol.floatValue()));
                                                    }
                                                    else {
                                                        float tempFloat = ((temp.subtract(min_purchase)).divide(amount, 4)).floatValue();
                                                        int tempInt = (int) tempFloat;
                                                        if (tempFloat - tempInt > 0.0) {
                                                            et_num.setText(String.valueOf(new BigDecimal(tempInt).multiply(
                                                                    amount).add(min_purchase)));
                                                        }
                                                        currentVol = new BigDecimal(tempInt).multiply(
                                                                amount).add(min_purchase);
                                                        total = CustomerPrice.multiply(currentVol)
                                                                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                                        tv_order_prirce.setText(String.valueOf(total));
                                                    }
                                                } else {
                                                    currentVol = new BigDecimal("0");
                                                    tv_order_prirce.setText(String.valueOf("0.00"));
                                                }
                                                currentVol = currentVol.setScale(4, BigDecimal.ROUND_UP);
                                                return false;

                                            }

                                            return true;

                                        }


                                    });
                                    et_num.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            String content = s == null ? null : s.toString();
                                            int size = content.length();
                                            if(content.contains(".")){
                                                int index = content.indexOf(".");
                                                String temp = content.substring(index);
                                                if (temp.length() > 5){
                                                    s.delete(size-1, size);
                                                }
                                            }

                                        }

                                    });

                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setBrandid(oilId);
        WebUtils.doPost(request);

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                currentVol = currentVol.add(amount);
                total = CustomerPrice.multiply(currentVol)
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                et_num.setText(String.valueOf(currentVol));
                tv_order_prirce.setText(String.valueOf(total));
                break;
            case R.id.iv_min:

                BigDecimal temp = currentVol.subtract(amount);
                if (temp.floatValue() < min_purchase.floatValue()) {
                    Utils.makeToastAndShow(getBaseContext(), "不能低于最小借用量");
                    //currentVol = new BigDecimal(min_purchase.floatValue());
                } else {
                    currentVol = temp;
                }
                currentVol = currentVol.setScale(4, BigDecimal.ROUND_UP);
                total = CustomerPrice.multiply(currentVol)
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                et_num.setText(String.valueOf(currentVol));
                tv_order_prirce.setText(String.valueOf(total));
                break;
            case R.id.tv_confirm:

                if (currentVol.floatValue() >= min_purchase.floatValue()) {

                    Intent intent = new Intent();
                    intent.putExtra("oilId", oilId);
                    intent.putExtra("RechargeAmount", tv_order_prirce.getText().toString());
                    intent.putExtra("ProductName", tv_oil_type.getText().toString());
                    intent.putExtra("countNum", String.valueOf(currentVol));
                    intent.putExtra("price", price);
                    intent.putExtra("futuresBuyProcedure", futuresBuyProcedure);
                    intent.setClass(this, BorrowOrderAndSettlementActivity.class);
                    startActivity(intent);

                } else {
                    Utils.makeToastAndShow(getBaseContext(), "不能低于最小借用量");
                }
                break;
        }
    }
}
