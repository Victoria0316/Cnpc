package com.bluemobi.cnpc.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.activity.TheCouponCodeActivity;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.util.Utils;
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
 * 10_10优惠券 已用
 */

@ContentView(R.layout.fragment_coupon_info)
public class UsedCouponFragment extends BaseFragment{


    @Bind(R.id.plv_refresh)
    PullToRefreshListView plv_refresh;
    private CommonAdapter<String> adapter;
    private List<String> lv = new ArrayList<String>();

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);

        initPullToRefresh(plv_refresh);

        plv_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        plv_refresh.onRefreshComplete();
                    }
                }, 500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plv_refresh.onRefreshComplete();

                    }
                }, 500);

            }
        });
        for (int i = 0;i<10;i++)
        {
            lv.add("ddd"+i);
        }
        plv_refresh.setAdapter(
                adapter = new CommonAdapter<String>(mContext, lv, R.layout.lv_coupons_and_related_item) {
                    @Override
                    public void convert(ViewHolder helper, String item) {


                    }
                });


        plv_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.moveTo(mContext, TheCouponCodeActivity.class);
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
}
