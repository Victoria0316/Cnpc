package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.model.BorrowGasDetailBean;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.GasText;
import com.bluemobi.cnpc.view.LoadingPage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 借用油转入油钱包P10_4_3
 * Created by wangzhijun on 2015/7/27.
 */
@ContentView(R.layout.activity_borrow_gas_transfer)
@PageName(R.string.mine_pre_gas_transfer)
public class BorrowGasTransferActivity extends BaseActivity {
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

    @Bind(R.id.ter_price)
    TextView ter_price;

    /**
     * 借用单价
     */
    @Bind(R.id.mine_borrow_gas_unit_price)
    TextView mine_borrow_gas_unit_price;
    /**
     * 担保金额
     */
    @Bind(R.id.mine_borrow_gas_guarantee_)
    TextView mine_borrow_gas_guarantee_;
    /**
     * 借用费
     */
    @Bind(R.id.mine_borrow_gas_borrow_fee_)
    TextView mine_borrow_gas_borrow_fee_;

    /**
     * 当前亏盈
     */
    @Bind(R.id.mine_pre_transfer_statistics)
    TextView mine_pre_transfer_statistics;
    /**
     * 转出升数
     */
    @Bind(R.id.mine_borrow_gas_transfer_volume)
    EditText mine_borrow_gas_transfer_volume;
    /**
     * 转出金额
     */
    @Bind(R.id.mine_borrow_gas_transfer_out_amount_real)
    TextView mine_borrow_gas_transfer_out_amount_real;

    /**
     * 实际转出金额
     */
    @Bind(R.id.mine_borrow_gas_transfer_out_amount_real_actual)
    TextView mine_borrow_gas_transfer_out_amount_real_actual;

    private BigDecimal amount;//借用单价
    private BigDecimal nowAmount;//当前单价
    private BigDecimal borrowFee;//借用费

    private String transfer;

    /**
     * 页面传递
     */
    private String id;

    @Bind(R.id.instructions)
    TextView instructions;
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

        instructions.setText("资金将自动转入油钱包内。");
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
        nowAmount = new BigDecimal(Double.parseDouble(bean.getOilPrice()));

        pre_price.setText(bean.getTotalPrice() + "元");

        Volume = bean.getOilBalance();
        pre_volume.setText(bean.getOilBalance() + "吨");

        ter_price.setText(bean.getTotalPrice() + "元");

        mine_borrow_gas_unit_price.setText(bean.getOilBorrowPrice() + "元");
        amount = new BigDecimal(Double.parseDouble(bean.getOilBorrowPrice()));

        mine_borrow_gas_guarantee_.setText(bean.getGuarantyMoney() + "元");
        mine_borrow_gas_borrow_fee_.setText(bean.getBorrowTotalFee() + "元");
        borrowFee = new BigDecimal(Double.parseDouble(bean.getBorrowTotalFee()));

        mine_pre_transfer_statistics.setText(bean.getTotalRevenue() + "元");

        mine_borrow_gas_transfer_out_amount_real.setText("0.00");
        mine_borrow_gas_transfer_out_amount_real_actual.setText("0.00");
        mine_borrow_gas_transfer_volume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 转出实际金额（=2*（借用单价-当前单价）*转出升数-借用费）。
                // amount;//借用单价 nowAmount;//当前单价  borrowFee;//借用费
                //TODO
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
                double number = Double.valueOf(StringUtils.isEmpty(str) ? "0" : str);//转出升数

                double pre = amount.subtract(nowAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                double total = new BigDecimal(pre).multiply(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                double rollOut = new BigDecimal(total).multiply(new BigDecimal(number)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                double actual = new BigDecimal(rollOut).subtract(borrowFee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                mine_borrow_gas_transfer_out_amount_real.setText(Utils.StringTo2decimal(String.valueOf(rollOut)));//转出金额
                mine_borrow_gas_transfer_out_amount_real_actual.setText(Utils.StringTo2decimal(String.valueOf(actual)));//实际转出金额
            }
        });


    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @OnClick(R.id.submit)
    public void doSubmit() {

        transfer = mine_borrow_gas_transfer_out_amount_real_actual.getText().toString();
        String minevolum = mine_borrow_gas_transfer_volume.getText().toString();

        try {
            if (Double.parseDouble(Volume) >= Double.parseDouble(minevolum)) {
                if (transfer.compareTo("0.0") >= 0) {
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    intent.putExtra("ProductNum", mine_borrow_gas_transfer_volume.getText().toString());//转出升数
                    intent.putExtra("TotalSum", mine_borrow_gas_transfer_out_amount_real.getText().toString());//转出金额
                    intent.setClass(this, BorrowGasTransferSuccessActivity.class);
                    startActivity(intent);

                }
            } else {
                Utils.makeToastAndShow(getBaseContext(), "转出吨数不足，不能转出");
            }
        } catch (Exception e) {
            Utils.makeToastAndShow(getBaseContext(), "请输入转出吨数");
        }
    }
}
