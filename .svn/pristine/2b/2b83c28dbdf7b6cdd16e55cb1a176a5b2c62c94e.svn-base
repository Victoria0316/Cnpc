package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.model.BorrowGasDetailBean;
import com.bluemobi.cnpc.network.model.BorrowGasDetailInfo;
import com.bluemobi.cnpc.network.request.BorrowGasDetailRequest;
import com.bluemobi.cnpc.network.response.BorrowGasDetailResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.CustomListView;
import com.bluemobi.cnpc.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 借用油详情
 * Created by wangzhijun on 2015/7/27.
 */
@ContentView(R.layout.activity_borrow_gas_detail)
@PageName(R.string.borrow_gas_detail_title)
public class BorrowGasDetailActivity extends BaseActivity{

    @Bind(R.id.listView)
    protected CustomListView mListView;
    /**
     * 订单编号
     */
    @Bind(R.id.order_code)
    protected TextView order_code;

    /**
     * 交易时间
     */
    @Bind(R.id.transfer_time)
    protected TextView transfer_time;
    /**
     * 借用油品
     */
    @Bind(R.id.mine_borrow_gas_borrow_type)
    protected TextView mine_borrow_gas_borrow_type;
    /**
     * 单价
     */
    @Bind(R.id.mine_pre_gas_unit_price)
    protected TextView mine_pre_gas_unit_price;
    /**
     * 借用余量
     */
    @Bind(R.id.mine_borrow_gas_borrow_left)
    protected TextView mine_borrow_gas_borrow_left;
    /**
     * 当前金额
     */
    @Bind(R.id.mine_pre_gas_cur_amount)
    protected TextView mine_pre_gas_cur_amount;
    /**
     * 担保金
     */
    @Bind(R.id.mine_borrow_gas_guarantee)
    protected TextView mine_borrow_gas_guarantee;
    /**
     * 借用费
     */
    @Bind(R.id.mine_borrow_gas_borrow_fee)
    protected TextView mine_borrow_gas_borrow_fee;

    /**
     * 已付手续费
     */
    @Bind(R.id.mine_pre_gas_fee)
    protected TextView mine_pre_gas_fee;
    /**
     * 当前盈亏
     */
    @Bind(R.id.mine_pre_gas_statistics)
    protected TextView mine_pre_gas_statistics;


    /**
     * 页面传递
     */
    private String id;

    private TempAdapter mAdapter;

    private BorrowGasDetailBean data;

    private List<BorrowGasDetailInfo> lists = new ArrayList<>();

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
        titleBarManager.showCommonTitleBar(R.string.borrow_gas_detail_title, R.drawable.return_arrow, true);
        mAdapter = new TempAdapter();
        
        Request();
    }

    private void Request() {

        id = getIntent().getStringExtra("id");
        BorrowGasDetailRequest request = new BorrowGasDetailRequest(new Response.Listener<BorrowGasDetailResponse>() {
            @Override
            public void onResponse(BorrowGasDetailResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    data = response.getData();
                    order_code.setText(data.getOrderNo());
                    transfer_time.setText(data.getTradeTime());//交易时间
                    mine_borrow_gas_borrow_type.setText(data.getOilName()+"#");
                    mine_pre_gas_unit_price.setText(data.getOilPrice());
                    mine_borrow_gas_borrow_left.setText(data.getOilBalance()+"吨");//借用余量
                    mine_pre_gas_cur_amount.setText(data.getTotalPrice()+"元");//当前金额
                    mine_borrow_gas_guarantee.setText(data.getGuarantyMoney()+"元");
                    mine_borrow_gas_borrow_fee.setText(data.getBorrowTotalFee()+"元");
                    mine_pre_gas_fee.setText(data.getHandlingCharge()+"元");
                    mine_pre_gas_statistics.setText(data.getTotalRevenue()+"元");

                    lists = response.getData().getOilOrderListDTOs();
                    if(lists != null && lists.size()>0)
                    mListView.setAdapter(mAdapter);
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("2");//初始化类型 1预购油 2借用油
        request.setId(id);
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    /**
     * 转入油钱包
     */
    @OnClick(R.id.transfer)
    public void transfer(){

        BorrowGasDetailBean item = data;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        intent.putExtras(bundle);
        intent.putExtra("id",id);
        intent.setClass(this, BorrowGasTransferActivity.class);
        startActivity(intent);
    }

    /**
     * 归还
     */
    @OnClick(R.id.order_back)
    public void orderBack(){
        BorrowGasDetailBean item = data;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        intent.putExtras(bundle);
        intent.putExtra("id",id);
        intent.setClass(this, BorrowGasReturnActivity.class);
        startActivity(intent);
    }


    class TempAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(BorrowGasDetailActivity.this).inflate(
                    R.layout.adapter_pre_gas,parent,false);

            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView gas = (TextView) convertView.findViewById(R.id.gas);
            TextView volume = (TextView) convertView.findViewById(R.id.volume);
            TextView amount = (TextView) convertView.findViewById(R.id.amount);

            time.setText(lists.get(position).getTradeTime());
            gas.setText(lists.get(position).getTradeType());
            volume.setText(lists.get(position).getOilVolume());
            amount.setText(lists.get(position).getOilMoney());

            return convertView;
        }
    }
}
