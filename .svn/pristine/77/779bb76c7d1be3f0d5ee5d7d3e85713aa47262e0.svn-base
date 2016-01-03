package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.model.BorrowGasDetailBean;
import com.bluemobi.cnpc.network.model.PreGasTransferBean;
import com.bluemobi.cnpc.network.request.PreGasTransferRequest;
import com.bluemobi.cnpc.network.response.PreGasTransferResponse;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.GasText;
import com.bluemobi.cnpc.view.LoadingPage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预购油转入油钱包P10_3_2
 * Created by wangzhijun on 2015/7/27.
 */
@ContentView(R.layout.activity_pre_gas_transfer)
@PageName(R.string.mine_pre_gas_transfer)
public class PreGasTransferActivity extends BaseActivity {
    /**
     * 时间
     */
    @Bind(R.id.time)
    TextView time_start;
    /**
     * 油品
     */
    @Bind(R.id.gas)
    TextView gas;
    /**
     * 油品价格
     */
    @Bind(R.id.gas_price)
    TextView gas_price;
    /**
     * 预购 价钱
     */
    @Bind(R.id.pre_price)
    TextView pre_price;
    /**
     * 预购 体积
     */
    @Bind(R.id.pre_volume)
    TextView pre_volume;
    /**
     * 输入 体积
     */
    @Bind(R.id.mine_pre_transfer_volume)
    EditText mine_pre_transfer_volume;
    /**
     * 转入金额
     */
    @Bind(R.id.mine_pre_transfer_amount)
    TextView mine_pre_transfer_amount;
    /**
     * 代储费
     */
    @Bind(R.id.mine_pre_transfer_fee)
    TextView mine_pre_transfer_fee;

    /**
     * 当前亏盈
     */
    @Bind(R.id.mine_pre_transfer_statistics)
    TextView mine_pre_transfer_statistics;

    /**
     * 实际转出金额
     */
    @Bind(R.id.mine_pre_transfer_actual)
    TextView mine_pre_transfer_actual;

    private BigDecimal amount;//单价
    private BigDecimal store;//代储费

    private String mine;

    private String transfer;

    /**
     * 页面传递
     */
    private String id;

    private String Volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBase() {
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        final NumberFormat currency = NumberFormat.getCurrencyInstance(); //建立货币格式化引用

        id = getIntent().getStringExtra("id");
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.mine_pre_gas_transfer, R.drawable.return_arrow, true);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        String formatTime = df.format(time);

        time_start.setText(formatTime);

        Intent intent = getIntent();
        BorrowGasDetailBean bean = (BorrowGasDetailBean) intent.getSerializableExtra("item");

        gas.setText(bean.getOilName());
        gas_price.setText(bean.getOilPrice() + "元/吨");

        amount = new BigDecimal(Double.parseDouble(bean.getOilPrice()));

        pre_price.setText(bean.getTotalPrice() + "元");

        Volume = bean.getOilBalance();
        pre_volume.setText(Volume + "吨");
        mine_pre_transfer_fee.setText(bean.getStoreTotalFee() + "元");
        store = new BigDecimal(Double.parseDouble(bean.getStoreTotalFee()));

        mine_pre_transfer_statistics.setText(bean.getTotalRevenue() + "元");

        mine = "0.00";
        mine_pre_transfer_amount.setText(Utils.StringTo2decimal(mine));
        mine_pre_transfer_actual.setText(Utils.StringTo2decimal(mine));
        mine_pre_transfer_volume.addTextChangedListener(new TextWatcher() {
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
                    if(temp.length() > 5){
                        s.delete(size-1, size);
                    }
                }
                String str = s.toString();
                double number = Double.valueOf(StringUtils.isEmpty(str) ? "0" : str);

                double total = amount.multiply(new BigDecimal(number)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                double pre = new BigDecimal(total).subtract(store).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                mine = String.valueOf(pre);
                mine_pre_transfer_amount.setText(Utils.StringTo2decimal(String.valueOf(total)));//转出金额
                mine_pre_transfer_actual.setText(Utils.StringTo2decimal(mine));//实际转出金额
            }
        });

    }



    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @OnClick(R.id.submit)
    public void doSubmit() {
        transfer = mine_pre_transfer_actual.getText().toString();
        String minevolum = mine_pre_transfer_volume.getText().toString();

        try {
            if (Double.parseDouble(Volume) >= Double.parseDouble(minevolum)) {
                if (transfer.compareTo("0.00") >= 0) {
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    intent.putExtra("ProductNum", mine_pre_transfer_volume.getText().toString());//转出升数
                    intent.putExtra("TotalSum", mine_pre_transfer_amount.getText().toString());//转出金额
                    intent.setClass(this, PreGasTransferSuccessActivity.class);
                    startActivity(intent);

                }
            }else{
                Utils.makeToastAndShow(getBaseContext(), "转出吨数不足，不能转出");
            }
        } catch (Exception e) {
            Utils.makeToastAndShow(getBaseContext(), "请输入转出吨数");
        }
    }
}
