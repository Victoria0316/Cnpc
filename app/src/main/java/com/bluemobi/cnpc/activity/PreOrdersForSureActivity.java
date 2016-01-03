package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
import com.bluemobi.cnpc.network.model.PreOrdersForSureBean;
import com.bluemobi.cnpc.network.model.oilInfoDTOInfo;
import com.bluemobi.cnpc.network.request.PreOrdersForSureRequest;
import com.bluemobi.cnpc.network.response.PreOrdersForSureResponse;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.CustomTextGroup;
import com.bluemobi.cnpc.view.LoadingPage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * P5_2预购升数确定
 * liufy
 */

@ContentView(R.layout.activity_pre_order_for_sure)
@PageName(R.string.s_pre_orders_for_sure)
public class PreOrdersForSureActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.tv_order_prirce)
    TextView tv_order_prirce;

    @Bind(R.id.iv_min)
    ImageView iv_min;

    @Bind(R.id.iv_add)
    ImageView iv_add;

    @Bind(R.id.et_num)
    EditText et_num;

    @Bind(R.id.tv_confirm)
    TextView tv_confirm;

    @Bind(R.id.tv_oil_type)
    TextView tv_oil_type;

    @Bind(R.id.ctg_min_purchase)
    CustomTextGroup ctg_min_purchase;

    @Bind(R.id.ctg_min_increasing)
    CustomTextGroup ctg_min_increasing;

    @Bind(R.id.ctg_pre_procedure_rates)
    CustomTextGroup ctg_pre_procedure_rates;

    @Bind(R.id.tv_oil_price)
    TextView tv_oil_price;

    @Bind(R.id.time)
    TextView timeDate;

    /**
     * 页面传递
     */
    private String oilId;

    private PreOrdersForSureBean data;

    private int count = 1;
    private BigDecimal amount;//每次递增最小量

    private BigDecimal min_purchase;//最小预购量

    private BigDecimal CustomerPrice; //当前价钱

    private BigDecimal currentVol; //当前升数

    private static final int MAX_VOLUME = 1000;

    private String str;

    private String price;

    private String futuresBuyProcedure;

    private double total;


    @Override
    protected void initBase() {
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(PreOrdersForSureActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_pre_orders_for_sure, R.drawable.return_arrow, true);
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

    }

    private void Request() {
        Utils.showProgressDialog(mContext);
        PreOrdersForSureRequest request = new PreOrdersForSureRequest
                (
                        new Response.Listener<PreOrdersForSureResponse>() {
                            @Override
                            public void onResponse(PreOrdersForSureResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    data = response.getData();
                                    tv_oil_type.setText(data.getProductName());

                                    price = data.getCustomerPrice();
                                    tv_oil_price.setText(price + "元/吨");
                                    CustomerPrice = new BigDecimal(price);

                                    min_purchase = new BigDecimal(data.getFuturesMinPurchases())
                                            .setScale(4, BigDecimal.ROUND_HALF_UP);
                                    ctg_min_purchase.setRightText(data.getFuturesMinPurchases() + "吨");


                                    amount = new BigDecimal(data.getFuturesMinIncremental())
                                            .setScale(4, BigDecimal.ROUND_HALF_UP);
                                    ctg_min_increasing.setRightText(data.getFuturesMinIncremental() + "吨");

                                    futuresBuyProcedure = data.getFuturesBuyProcedure();
                                    ctg_pre_procedure_rates.setRightText(futuresBuyProcedure + "%");
                                    ctg_pre_procedure_rates.setLeftText("预购手续费率");

                                    total = CustomerPrice.multiply(min_purchase)
                                            .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    tv_order_prirce.setText(String.valueOf(total));

                                    currentVol = new BigDecimal(StringUtils.isEmpty(data.getFuturesMinPurchases())?
                                    "0":data.getFuturesMinPurchases());
                                    currentVol = currentVol.setScale(4, BigDecimal.ROUND_UP);
                                    et_num.setText(String.valueOf(currentVol));

                                    et_num.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                        @Override
                                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                            if (actionId == EditorInfo.IME_ACTION_DONE)

                                            {
                                               // et_num.setText(String.valueOf(currentVol));

                                                String s = et_num.getText().toString();
                                                str = s.toString();

                                                if(StringUtils.isNotEmpty(str)){
                                                    BigDecimal temp = new BigDecimal(str);
                                                    currentVol = temp;
                                                    if(temp.floatValue() < min_purchase.floatValue()){// < zui di
                                                        et_num.setText(String.valueOf(min_purchase));
                                                        tv_order_prirce.setText(String.valueOf(total));
                                                    }else if(temp.floatValue() >= MAX_VOLUME){
                                                        float a  = MAX_VOLUME - min_purchase.floatValue();
                                                        int b = (int)(a%amount.floatValue()==0?a/amount.floatValue() -1:a/amount.floatValue());
                                                        float c = min_purchase.floatValue() + b*amount.floatValue();

                                                        currentVol = new BigDecimal(c);
                                                        total = CustomerPrice.multiply(currentVol)
                                                                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                                        String strtotal = new DecimalFormat("0.00").format(total);
                                                        tv_order_prirce.setText(strtotal);
                                                        String str = new DecimalFormat("0.0000").format(currentVol);
                                                        et_num.setText(str);
                                                    } else{

                                                        float tempFloat = ((temp.subtract(min_purchase)).divide(amount, 4)).floatValue();
                                                        int tempInt = (int)tempFloat;

                                                        if(tempFloat-tempInt > 0.0){
                                                            et_num.setText(String.valueOf(new BigDecimal(tempInt).multiply(
                                                                    amount).add(min_purchase)));
                                                        }
                                                        currentVol = new BigDecimal(tempInt).multiply(
                                                                amount).add(min_purchase);

                                                        total = CustomerPrice.multiply(currentVol)
                                                                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                                        tv_order_prirce.setText(String.valueOf(total));

                                                    }
                                                }else{
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

                                            /**
                                             * amount;//每次递增最小量
                                             * min_purchase;//最小预购量
                                             * CustomerPrice; //当前数量
                                             */

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
                currentVol = currentVol.setScale(4, BigDecimal.ROUND_UP);
                total = CustomerPrice.multiply(currentVol)
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                et_num.setText(String.valueOf(currentVol));
                tv_order_prirce.setText(String.valueOf(total));
                break;
            case R.id.iv_min:
                BigDecimal temp  = currentVol.subtract(amount);
                if(temp.floatValue() < min_purchase.floatValue()){
                    Utils.makeToastAndShow(getBaseContext(), "不能低于最小预购量");
                    //currentVol = new BigDecimal(min_purchase.floatValue());
                }else{
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
                    intent.setClass(this, OrderAndSettlementActivity.class);
                    startActivity(intent);

                } else {
                    Utils.makeToastAndShow(getBaseContext(), "不能低于最小预购量");
                }

                break;
        }

    }
}
