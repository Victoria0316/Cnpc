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
import com.bluemobi.cnpc.network.model.PreGasReturnSuccessBean;
import com.bluemobi.cnpc.network.request.PreGasReturnSuccessRequest;
import com.bluemobi.cnpc.network.response.PreGasReturnSuccessResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 归还成功
 * Created by wangzhijun on 2015/7/28.
 */

@ContentView(R.layout.activity_borrow_gas_return_success)
@PageName(R.string.mine_borrow_gas_return_success)
public class BorrowGasReturnSuccessActivity extends BaseActivity {

    @Bind(R.id.order_code)
    TextView order_code;

    @Bind(R.id.transfer_time)
    TextView transfer_time;
    /**
     * 预购油品
     */
    @Bind(R.id.mine_pre_gas_type)
    TextView mine_pre_gas_type;
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
    @Bind(R.id.mine_borrow_gas_unit_money)
    TextView mine_borrow_gas_unit_money;
    /**
     * 归还升数
     */
    @Bind(R.id.mine_borrow_gas_borrow_volume_)
    TextView mine_borrow_gas_borrow_volume_;
    /**
     * 归还金额
     */
    @Bind(R.id.mine_borrow_gas_borrow_return_amount)
    TextView mine_borrow_gas_borrow_return_amount;
    /**
     * 实际归还金额
     */
    @Bind(R.id.mine_borrow_gas_borrow_return_amount_real)
    TextView mine_borrow_gas_borrow_return_amount_real;
    /**
     * 零钱包余额
     */
    @Bind(R.id.mine_borrow_gas_borrow_return_left)
    TextView mine_borrow_gas_borrow_return_left;
    /**
     *
     */
    @Bind(R.id.mine_borrow_gas_borrow_return_litre)
    TextView mine_borrow_gas_borrow_return_litre;
    /**
     * 归还手续费
     */
    @Bind(R.id.mine_borrow_gas_borrow_return_poundage)
    TextView mine_borrow_gas_borrow_return_poundage;


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

    /**
     * 页面传递
     */
    private String pound;

    private PreGasReturnSuccessBean data;

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
        titleBarManager.showCommonTitleBar(R.string.mine_borrow_gas_return_success, R.drawable.return_arrow, true);

        Request();
    }

    private void Request() {

        id = getIntent().getStringExtra("id");
        ProductNum = getIntent().getStringExtra("ProductNum");
        TotalSum = getIntent().getStringExtra("TotalSum");
        pound = getIntent().getStringExtra("pound");

        PreGasReturnSuccessRequest request = new PreGasReturnSuccessRequest(new Response.Listener<PreGasReturnSuccessResponse>() {
            @Override
            public void onResponse(PreGasReturnSuccessResponse response) {

                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success

                    data = response.getData();

                    order_code.setText(data.getOrderNo());
                    transfer_time.setText(data.getTradeTime());//交易时间
                    mine_pre_gas_type.setText(data.getOilName() + "#");
                    mine_pre_transfer_cur_unit_price.setText(data.getOilPrice() + "元");
                    mine_borrow_gas_unit_price_.setText(data.getOilBorrowPrice() + "元");
                    mine_borrow_gas_unit_money.setText(data.getBorrowTotalFee() + "元");
                    mine_borrow_gas_borrow_volume_.setText(data.getOilVolume() + "吨");
                    mine_borrow_gas_borrow_return_amount.setText(TotalSum + "元");//归还金额
                    mine_borrow_gas_borrow_return_amount_real.setText(data.getRealTotalPrice() + "元");//实际归还金额
                    mine_borrow_gas_borrow_return_left.setText(data.getChangeBalance() + "元");
                    mine_borrow_gas_borrow_return_litre.setText(data.getOilBalance() + "吨");
                    mine_borrow_gas_borrow_return_poundage.setText(data.getBorrowSellPoundage() + "元");

                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("4");//初始化类型 3预购油退货 4借用油归还
        request.setId(id);
        request.setProductNum(ProductNum);//退货升数
        request.setTotalSum(TotalSum);//退货金额
        request.setPoundage(pound);//退货手续费
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
