package com.bluemobi.cnpc.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.model.PreGasTransferBean;
import com.bluemobi.cnpc.network.request.PreGasTransferRequest;
import com.bluemobi.cnpc.network.response.PreGasTransferResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangzhijun on 2015/7/28.
 * 借用油转入成功界面
 */
@ContentView(R.layout.activity_borrow_gas_transfer_success)
@PageName(R.string.mine_pre_transfer_success)
public class BorrowGasTransferSuccessActivity extends BaseActivity {
    @Bind(R.id.order_code)
    TextView order_code;

    @Bind(R.id.transfer_time)
    TextView transfer_time;
    /**
     * 借用油品
     */
    @Bind(R.id.mine_borrow_gas_borrow_type)
    TextView mine_borrow_gas_borrow_type;
    /**
     * 当前单价
     */
    @Bind(R.id.mine_pre_transfer_cur_unit_price)
    TextView mine_pre_transfer_cur_unit_price;
    /**
     * 借用单价
     */
    @Bind(R.id.mine_borrow_gas_unit_price_)
    TextView mine_borrow_gas_unit_price_;
    /**
     * 借用费
     */
    @Bind(R.id.mine_borrow_gas_borrow_fee)
    TextView mine_borrow_gas_borrow_fee;
    /**
     * 转出升数
     */
    @Bind(R.id.mine_pre_transfer_out_volume)
    TextView mine_pre_transfer_out_volume;
    /**
     * 转出金额
     */
    @Bind(R.id.mine_pre_transfer_out_amount)
    TextView mine_pre_transfer_out_amount;
    /**
     * 实际转出金额
     */
    @Bind(R.id.mine_pre_transfer_out_amount_real)
    TextView mine_pre_transfer_out_amount_real;
    /**
     * 剩余借用升数
     */
    @Bind(R.id.mine_borrow_gas_borrow_left_)
    TextView mine_borrow_gas_borrow_left_;
    /**
     * 油钱包后余额
     */
    @Bind(R.id.mine_pre_transfer_left_amount)
    TextView mine_pre_transfer_left_amount;

    /**
     * 页面传递
     */
    private String id;

    /**
     * 页面传递
     */
    private String ProductNum;

    /**
     * 页面传递
     */
    private String TotalSum;

    private PreGasTransferBean data;


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
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.mine_pre_transfer_success, R.drawable.return_arrow, true);

        Request();
    }

    private void Request() {

        id = getIntent().getStringExtra("id");
        ProductNum = getIntent().getStringExtra("ProductNum");
        TotalSum = getIntent().getStringExtra("TotalSum");
        PreGasTransferRequest request = new PreGasTransferRequest(new Response.Listener<PreGasTransferResponse>() {
            @Override
            public void onResponse(PreGasTransferResponse response) {

                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success

                    data = response.getData();

                    order_code.setText(data.getOrderNo());
                    transfer_time.setText(data.getTradeTime());//交易时间
                    mine_borrow_gas_borrow_type.setText(data.getOilName()+"#");
                    mine_pre_transfer_cur_unit_price.setText(data.getOilPrice()+"元");
                    mine_borrow_gas_unit_price_.setText(data.getOilBorrowPrice()+"元");
                    mine_borrow_gas_borrow_fee.setText(data.getBorrowTotalFee()+"元");
                    mine_pre_transfer_out_volume.setText(data.getOilVolume()+"吨");
                    mine_pre_transfer_out_amount.setText(TotalSum+"元");//归还金额
                    mine_pre_transfer_out_amount_real.setText(data.getRealTotalPrice()+"元");//实际归还金额
                    mine_borrow_gas_borrow_left_.setText(data.getOilBalance()+"吨");
                    mine_pre_transfer_left_amount.setText(data.getOilMoneyBalance()+"元");

                } else {// failed
                    Log.e("error", "error");
                }
            }
        },this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("2");//初始化类型 1预购油 2借用油
        request.setId(id);
        request.setProductNum(ProductNum);//转出升数
        request.setTotalSum(TotalSum);//转出总金额
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        Utils.moveTo(this, BorrowGasActivity.class);
        finishAll();
    }
}
