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
 * Created by wangzhijun on 2015/7/28.
 */
@ContentView(R.layout.activity_pre_gas_return)
@PageName(R.string.mine_pre_return)
public class PreGasReturnActivity extends BaseActivity
{

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
    @Bind(R.id.pre_volume)
    TextView pre_volume;
    /**
     * 预购 体积
     */
    @Bind(R.id.pre_price)
    TextView pre_price;

    /**
     * 当前金额
     */
    @Bind(R.id.price_actual)
    TextView price_actual;

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
     *最小退货数量
     */
    @Bind(R.id.minimum)
    TextView minimum;


    /**
     *退货数量
     */
    @Bind(R.id.mine_pre_return_count)
    EditText mine_pre_return_count;
    /**
     * 退货手续费
     */
    @Bind(R.id.mine_pre_return_fee)
    TextView mine_pre_return_fee;
    /**
     * 退货手续费率
     */
    @Bind(R.id.mine_pre_return_rate)
    TextView mine_pre_return_rate;
    /**
     * 退货金额
     */
    @Bind(R.id.mine_pre_return_real_amount)
    TextView mine_pre_return_real_amount;

    /**
     * 真实退货金额
     */
    @Bind(R.id.mine_pre_return_real_amount_act)
    TextView mine_pre_return_real_amount_act;

    private String mine;

    private BigDecimal amount;//单价
    private BigDecimal store;//代储费
    private BigDecimal poundage;//手续费率

    private String transfer;

    /**
     * 页面传递
     */
    private String id;

    private String Volume;

    private String minimumStr;

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
        titleBarManager.showCommonTitleBar(R.string.mine_pre_return, R.drawable.return_arrow, true);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        String formatTime = df.format(time);

        time_start.setText(formatTime);

        Intent intent = getIntent();
        BorrowGasDetailBean bean = (BorrowGasDetailBean) intent.getSerializableExtra("item");

        gas.setText(bean.getOilName());

        gas_price.setText(bean.getOilPrice() + "元/吨");
        amount = new BigDecimal(Double.parseDouble(bean.getOilPrice()));

        Volume = bean.getOilBalance();
        minimumStr = bean.getFuturesMinPurchases();
        minimum.setText(bean.getFuturesMinPurchases()+"吨");
        pre_price.setText(bean.getOilBalance() + "吨");
        pre_volume.setText(bean.getTotalPrice() + "元");
        price_actual.setText(bean.getTotalPrice() + "元");

        mine_pre_transfer_fee.setText(bean.getStoreTotalFee() + "元");
        store = new BigDecimal(Double.parseDouble(bean.getStoreTotalFee()));

        mine_pre_transfer_statistics.setText(bean.getTotalRevenue() + "元");

        mine_pre_return_rate.setText(bean.getFuturesSellProcedure()+"%");
        poundage = new BigDecimal(Double.parseDouble(bean.getFuturesSellProcedure()));

        mine = "0.00";
        mine_pre_return_fee.setText(Utils.StringTo2decimal(mine));//退货手续费
        mine_pre_return_real_amount.setText(Utils.StringTo2decimal(mine));//退货金额
        mine_pre_return_real_amount_act.setText(Utils.StringTo2decimal(mine));//真实退货金额

        mine_pre_return_count.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               //退货手续费=（当前单价*退货升数-代储费）*退货手续费率；
              // 退货金额=当前单价*退货升数-代储费-退货手续费。若退货金额<0，则提示“退货升数不足，不能退货”。
               /**
                * amount;//单价 store;//代储费 poundage;//手续费率
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

               String str = mine_pre_return_count.getText().toString();

               double number = Double.valueOf(StringUtils.isEmpty(str) ? "0" : str);
               double total = amount.multiply(new BigDecimal(number)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
               double pre = new BigDecimal(total).subtract(store).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
               double pound = new BigDecimal(pre).multiply(poundage).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
               double poundpag = new BigDecimal(pound).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
               double ACT = new BigDecimal(pre).subtract(new BigDecimal(poundpag)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


               mine_pre_return_fee.setText(Utils.StringTo2decimal(String.valueOf(poundpag)));//退货手续费
               mine_pre_return_real_amount.setText(Utils.StringTo2decimal(String.valueOf(total)));//退货金额
               mine_pre_return_real_amount_act.setText(Utils.StringTo2decimal(String.valueOf(ACT)));//真实退货金额

           }
       });

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @OnClick(R.id.submit)
    public void doSubmit(){
        transfer = mine_pre_return_real_amount_act.getText().toString();
        String minevolum = mine_pre_return_count.getText().toString();
        //Volume余量

        try {
            if (Double.parseDouble(Volume) >= Double.parseDouble(minevolum) && Double.parseDouble(minevolum) != 0) {

                BigDecimal strl = new BigDecimal(Double.parseDouble(minimumStr));
                BigDecimal result = new BigDecimal(Double.parseDouble(Volume)).subtract(new BigDecimal(Double.parseDouble(minevolum)));

                if(result.doubleValue() >= strl.doubleValue() || result.doubleValue() == 0){

                    if(transfer.compareTo("0.00")>=0){
                        Intent intent = new Intent();
                        intent.putExtra("id", id);
                        intent.putExtra("ProductNum", mine_pre_return_count.getText().toString());//退货升数
                        intent.putExtra("TotalSum", mine_pre_return_real_amount.getText().toString());//退货金额
                        intent.putExtra("pound", mine_pre_return_fee.getText().toString());//退货手续费
                        intent.setClass(this, PreGasReturnSuccessActivity.class);
                        startActivity(intent);
                    }else{
                        Utils.makeToastAndShow(getBaseContext(), "金额不足");
                    }

                }else{
                    Utils.makeToastAndShow(getBaseContext(), "剩余升数不足，请全部归还");
                }
            }else{
                Utils.makeToastAndShow(getBaseContext(), "退货吨数不足，不能退货");
            }
        } catch (Exception e) {
            Utils.makeToastAndShow(getBaseContext(), "请输入退货吨数");
        }
    }
}
