package com.bluemobi.cnpc.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.activity.BonusDetailedActivity;
import com.bluemobi.cnpc.activity.TheCouponCodeActivity;
import com.bluemobi.cnpc.adapter.BonusListAdapter;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.CouponListAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.model.BonusItem;
import com.bluemobi.cnpc.network.model.CouponItem;
import com.bluemobi.cnpc.network.request.BonusListRequest;
import com.bluemobi.cnpc.network.request.CouponListRequest;
import com.bluemobi.cnpc.network.response.BonusListResponse;
import com.bluemobi.cnpc.network.response.CouponListResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/23.
 *
 * 10_10优惠券 有限
 */

@ContentView(R.layout.fragment_coupon_info)
public class CouponFragment extends BaseFragment{


    @Bind(R.id.plv_refresh)
    PullToRefreshListView plv_refresh;

    private CouponListAdapter mAdapter;

    private ArrayList<CouponItem> dataLists = new ArrayList<>();

    /**
     * 1有效 2已用 3失效
     */
    private String type;

    public static final String VALID = "1";

    public static final String INVALID = "3";

    public static final String UESD = "2";

    private BaseActivity baseActivity;

    private boolean debug = false;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        baseActivity = (BaseActivity)activity;
    }

    @Override
    protected boolean getPage(int type) {
        if(!super.getPage(type)) return false;
        connectToSercer(type);
        return true;
    }

    private void connectToSercer(int type) {
        if(type == LOAD_REFRESH){
            dataLists.clear();
        }
        CouponListRequest request = new CouponListRequest(new Response.Listener<CouponListResponse>() {
            @Override
            public void onResponse(CouponListResponse response) {
                Utils.closeDialog();
                plv_refresh.onRefreshComplete();
                if(debug){
                    if(mAdapter == null){
                        mAdapter = new CouponListAdapter(baseActivity, dataLists,R.layout.lv_coupons_and_related_item);
                        plv_refresh.setAdapter(mAdapter);
                    }
                    dataLists.add(getDebugItem());
                    mAdapter.notifyDataSetChanged();
                }else{
                    if(response != null && response.getStatus() ==0 ){
                        if(mAdapter == null){
                            mAdapter = new CouponListAdapter(baseActivity, dataLists,R.layout.lv_coupons_and_related_item);
                            plv_refresh.setAdapter(mAdapter);
                        }
                        dataLists.addAll(response.getData().getInfo());
                        mAdapter.notifyDataSetChanged();
                    }else{
                        Utils.makeToastAndShow(baseActivity, response.getContent());
                    }
                }

            }
        }, baseActivity);
        request.setCurrentnum(String.valueOf(Constants.CURRENTNUMBASE));
        request.setCurrentpage(String.valueOf(getCurPage()));
        request.setType(this.type);
        request.setHandleCustomErr(false);
        WebUtils.doPost(request);
        Utils.showProgressDialog(baseActivity);
    }

    private CouponItem getDebugItem() {
        CouponItem item = new CouponItem();
        item.setId("1");
        item.setCouponDiscount("50");
        item.setCouponStartTime("2015-01-01");
        item.setCouponEndTime("2015-02-01");
        item.setCouponName("大红包");
        item.setCouponValid("5天");
        item.setOilLimit("1");
        item.setMinOrderAmount("10");
        item.setDeptCode("002");
        item.setDeptName("dept2");
        return item;
    }


    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);

        initPullToRefresh(plv_refresh);
        getPage(LOAD_REFRESH);

        plv_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CouponItem item = dataLists.get(position - 1);
                if(item != null){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), TheCouponCodeActivity.class);
                    intent.putExtra("item", item);
                    getActivity().startActivity(intent);
                }
            }
        });


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isShowLoadPage = false;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
