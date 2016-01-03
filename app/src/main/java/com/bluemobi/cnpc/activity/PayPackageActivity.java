package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.model.PayPreferentialInfo;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.LoadingPage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by gaoyn on 2015/7/21.
 *
 * p4_1 充值套餐选择
 */

@ContentView(R.layout.activity_pay_package)
@PageName(R.string.pay_package)
public class PayPackageActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.time_start)
    TextView time_start;

    @Bind(R.id.total_price)
    TextView total_price;

    @Bind(R.id.confirm)
    TextView confirm;

    @Bind(R.id.iv_min)
    ImageView iv_min;

    @Bind(R.id.iv_add)
    ImageView iv_add;

    @Bind(R.id.time)
    TextView ComboName;

    @Bind(R.id.instructions)
    TextView instructions;

    @Bind(R.id.discount)
    TextView discount;

    @Bind(R.id.depositRate)
    TextView depositRate;

    @Bind(R.id.buyPrice)
    TextView buyPrice;

    @Bind(R.id.preferentialPrice)
    TextView preferentialPrice;

    @Bind(R.id.et_num)
    EditText et_num;

    private BigDecimal amount;
    private BigDecimal preferential;

    private int count = 1;

    private String str;

    private String preStr;

    private String countNum;

    private String PackageId;

    private String timeLimit;

    private double total;


    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(PayPackageActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.pay_package, R.drawable.return_arrow, true);

        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);

        confirm.setOnClickListener(this);
        iv_min.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        Intent intent=getIntent();
        PayPreferentialInfo bean= (PayPreferentialInfo) intent.getSerializableExtra("item");

        timeLimit = bean.getTimeLimit();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final long time = System.currentTimeMillis();
        String formatTime = df.format(time);

        time_start.setText(formatTime);

        PackageId = bean.getId();
        ComboName.setText(bean.getComboName());
        discount.setText(bean.getDepositRate());
        depositRate.setText(bean.getDepositRate()+"%");

        buyPrice.setText(Utils.StringTo2decimal(bean.getBuyPrice())+"元");
        preferentialPrice.setText(Utils.StringTo2decimal(bean.getPreferentialPrice())+"元");
        if(bean.getComboType().equals("1")){
            instructions.setText("第一个月到账"+bean.getFirstArrive()+"%"+","+"其他月份到账"+bean.getOtherArrive()+"%");
        }

        et_num.setText(Utils.StringTo2decimal(bean.getBuyPrice()));

        amount = new BigDecimal(Double.parseDouble(bean.getBuyPrice()));
        preferential = new BigDecimal(Double.parseDouble(bean.getPreferentialPrice()));
        double pre = preferential.multiply(new BigDecimal(new Double(count))).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        preStr = String.valueOf(pre);

       // total_price.setText(Utils.StringTo2decimal(bean.getBuyPrice()));

        String totalPrice = new DecimalFormat("0.00").format(amount.add(preferential).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        total_price.setText(totalPrice);

        et_num.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE)

                {

                    et_num.setText(Utils.StringTo2decimal(String.valueOf(total)));
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
                    if (temp.length() > 3){
                        s.delete(size-1, size);
                    }
                }

                str = content;

                count = (int) (Double.valueOf(StringUtils.isEmpty(str) ? "0" : str) / amount.doubleValue());
                total = amount.multiply(new BigDecimal(new Double(count))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                double pre = preferential.multiply(new BigDecimal(new Double(count))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                double totalPrice = new BigDecimal(new Double(total)).add(new BigDecimal(new Double(pre))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                preStr = String.valueOf(pre);
                total_price.setText(Utils.StringTo2decimal(String.valueOf(totalPrice)));


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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.iv_add:
                count++;
                break;
            case R.id.iv_min:
                if(count > 1){
                    count--;
                }else{
                    Utils.makeToastAndShow(getBaseContext(),"最少充值一份金额");
                }
                break;
            case R.id.confirm:
                String temp = et_num.getText().toString();
                int countInt = (int)(Double.valueOf(StringUtils.isEmpty(temp)? "0":temp)/amount.doubleValue());

                countNum = String.valueOf(countInt);

                String str = et_num.getText().toString();

                BigDecimal realPayAmount = new BigDecimal(Double.parseDouble(str))
                        .subtract(amount);

                if(realPayAmount.doubleValue()>=0){
                    Intent intent = new Intent();
                    intent.putExtra("RechargeAmount", total_price.getText().toString());
                    intent.putExtra("preferential",preStr);
                    intent.putExtra("countNum",countNum);
                    intent.putExtra("PackageId",PackageId);
                    intent.putExtra("timeLimit",timeLimit);
                    intent.setClass(this, OrderSettlementActivity.class);
                    startActivity(intent);
                }else {
                    Utils.makeToastAndShow(getBaseContext(),"最少充值一份金额");
                }
                break;
        }
        BigDecimal bigDecimal = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        String total =  new DecimalFormat("0.00").format(bigDecimal.multiply(new BigDecimal(new Double(count))).doubleValue());
        et_num.setText(total);
    }
}
