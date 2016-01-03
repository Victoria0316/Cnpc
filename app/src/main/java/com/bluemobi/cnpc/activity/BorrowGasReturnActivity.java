package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
 * P10_4_2归还
 * Created by wangzhijun on 2015/7/28.
 */
@ContentView(R.layout.activity_borrow_gas_return)
@PageName(R.string.mine_borrow_gas_return)
public class BorrowGasReturnActivity extends BaseActivity {

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

    @Bind(R.id.pre_price)
    TextView pre_price;

    @Bind(R.id.pre_volume)
    TextView pre_volume;
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
     * 费率
     */
    @Bind(R.id.mine_borrow_gas_borrow_rate)
    TextView mine_borrow_gas_borrow_rate;

    /**
     * 归还升数
     */
    @Bind(R.id.mine_borrow_gas_borrow_volume)
    EditText mine_borrow_gas_borrow_volume;
    /**
     * 归还手续费
     */
    @Bind(R.id.mine_borrow_gas_borrow_return_fee_)
    TextView mine_borrow_gas_borrow_return_fee_;

    /**
     *最小退货数量
     */
    @Bind(R.id.minimum)
    TextView minimum;

    /**
     * 归还金额
     */
    @Bind(R.id.mine_borrow_gas_borrow_return_amount_)
    TextView mine_borrow_gas_borrow_return_amount_;

    /**
     * 归还实际金额
     */
    @Bind(R.id.mine_borrow_gas_borrow_return_amount_art)
    TextView mine_borrow_gas_borrow_return_amount_art;

    /**
     * 当前亏盈
     */
    @Bind(R.id.mine_pre_transfer_statistics)
    TextView mine_pre_transfer_statistics;

    /**
     * 页面传递
     */
    private String id;

    private String mine;

    private BigDecimal amount;//借用单价
    private BigDecimal nowAmount;//当前单价
    private BigDecimal borrowFee;//借用费
    private BigDecimal borrowFeeRate;//归还手续费率

    private String transfer;

    private String  Volume;

    private String  minimumStr;

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
        id = getIntent().getStringExtra("id");

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.mine_borrow_gas_return, R.drawable.return_arrow, true);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        String formatTime = df.format(time);

        time_start.setText(formatTime);

        Intent intent = getIntent();
        BorrowGasDetailBean bean = (BorrowGasDetailBean) intent.getSerializableExtra("item");

        gas.setText(bean.getOilName());

        nowAmount = new BigDecimal(Double.parseDouble(bean.getOilPrice()));
        gas_price.setText(bean.getOilPrice() + "元/吨");

        pre_price.setText(bean.getTotalPrice() + "元");

        Volume = bean.getOilBalance();
        minimumStr = bean.getBorrowMinPurchases();
        minimum.setText(bean.getBorrowMinPurchases()+"吨");
        pre_volume.setText(bean.getOilBalance() + "吨");

        amount = new BigDecimal(Double.parseDouble(bean.getOilBorrowPrice()));
        mine_borrow_gas_unit_price.setText(bean.getOilBorrowPrice()+"元");

        mine_borrow_gas_guarantee_.setText(bean.getGuarantyMoney()+"元");

        mine_borrow_gas_borrow_fee_.setText(bean.getBorrowTotalFee() + "元");
        borrowFee = new BigDecimal(Double.parseDouble(bean.getBorrowTotalFee()));

        mine_pre_transfer_statistics.setText(bean.getTotalRevenue() + "元");

        mine_borrow_gas_borrow_rate.setText("0.00"+"%");
        borrowFeeRate = new BigDecimal(Double.parseDouble("0.00"));

        mine = "0.00";
        mine_borrow_gas_borrow_return_fee_.setText(Utils.StringTo2decimal(mine));//归还手续费
        mine_borrow_gas_borrow_return_amount_.setText(Utils.StringTo2decimal(mine));//归还金额
        mine_borrow_gas_borrow_return_amount_art.setText(Utils.StringTo2decimal(mine));//真实归还金额

        mine_borrow_gas_borrow_volume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //归还手续费=（（2*借用单价-当前单价）*借用升数-借用费）*归还手续费率；
                //归还金额=（2*借用单价-当前单价）*借用升数-借用费-归还手续费。若归还金额<0，则提示“归还升数不足，不能归还”。
                /**
                 * amount;//借用单价 nowAmount;//当前单价  borrowFee;//借用费 borrowFeeRate;//归还手续费率
                 */
                String content = s == null ? null : s.toString();
                int size = content.length();
                if(content.contains(".")){
                    int index = content.indexOf(".");
                    String temp = content.substring(index);
                    if(temp.length() > 5){
                        s.delete(size-1, size);
                    }
                }
                String str = mine_borrow_gas_borrow_volume.getText().toString();

                double number = Double.valueOf(StringUtils.isEmpty(str) ? "0" : str);


                double Borrow = amount.multiply(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                double pre = new BigDecimal(Borrow).subtract(nowAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                double total = new BigDecimal(pre).multiply(new BigDecimal(number)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                double dou = new BigDecimal(total).subtract(borrowFee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                double rollOut = new BigDecimal(dou).multiply(borrowFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                double poundpag = new BigDecimal(rollOut).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                double actual = new BigDecimal(dou).subtract(new BigDecimal(poundpag)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


                mine_borrow_gas_borrow_return_fee_.setText(Utils.StringTo2decimal(String.valueOf(poundpag)));//归还手续费
                mine_borrow_gas_borrow_return_amount_.setText(Utils.StringTo2decimal(String.valueOf(total)));//归还金额
                mine_borrow_gas_borrow_return_amount_art.setText(Utils.StringTo2decimal(String.valueOf(actual)));//真实归还金额

            }
        });
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @OnClick(R.id.submit)
    public void doSubmit() {

        transfer = mine_borrow_gas_borrow_return_amount_art.getText().toString();
        String minevolum = mine_borrow_gas_borrow_volume.getText().toString();

        //Volume余量
        try {
            if (Double.parseDouble(Volume) >= Double.parseDouble(minevolum) && Double.parseDouble(minevolum) != 0) {

                BigDecimal strl = new BigDecimal(Double.parseDouble(minimumStr));
                BigDecimal result = new BigDecimal(Double.parseDouble(Volume)).subtract(new BigDecimal(Double.parseDouble(minevolum)));

                if(result.doubleValue() >= strl.doubleValue() || result.doubleValue() == 0){

                    if(transfer.compareTo("0.00")>=0){
                        Intent intent = new Intent();
                        intent.putExtra("id", id);
                        intent.putExtra("ProductNum", mine_borrow_gas_borrow_volume.getText().toString());//退货升数
                        intent.putExtra("TotalSum", mine_borrow_gas_borrow_return_amount_.getText().toString());//退货金额
                        intent.putExtra("pound", mine_borrow_gas_borrow_return_fee_.getText().toString());//退货手续费
                        intent.setClass(this, BorrowGasReturnSuccessActivity.class);
                        startActivity(intent);
                    }else{
                        Utils.makeToastAndShow(getBaseContext(), "金额不足");
                    }

                }else{
                    Utils.makeToastAndShow(getBaseContext(), "剩余升数不足，请全部归还");
                }

            }else{
                Utils.makeToastAndShow(getBaseContext(), "归还吨数不足，不能归还");
            }
        } catch (Exception e) {
            Utils.makeToastAndShow(getBaseContext(), "请输入归还吨数");
        }
    }
}
