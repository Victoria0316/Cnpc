package com.bluemobi.cnpc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.activity.FoundDetailActivity;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.model.SalesPromotionInfo;
import com.bluemobi.cnpc.network.request.SalesPromotionRequest;
import com.bluemobi.cnpc.network.response.SalesPromotionResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/23.
 *
 * p6-1 加油站详情-促销信息
 */

@ContentView(R.layout.fragment_found_info)
@PageName(R.string.sales_promotion)
public class SalesPromotionFragment extends BaseFragment{


    @Bind(R.id.plv_refresh)
    PullToRefreshListView plv_refresh;
    private CommonAdapter<SalesPromotionInfo> adapter;


    private List<SalesPromotionInfo> dataList = new ArrayList<SalesPromotionInfo>();


    private String currentpage;

    private String PrimaryId;

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        initPullToRefresh(plv_refresh);
        getPage(LOAD_REFRESH);


    }

    @Override
    protected boolean getPage(int type)
    {
        if(!super.getPage(type)) return false;
        getDataServer();
        return true;
    }

    public void getDataServer()
    {
        Utils.showProgressDialog(mContext);
        SalesPromotionRequest request = new SalesPromotionRequest
                (
                        new Response.Listener<SalesPromotionResponse>() {
                            @Override
                            public void onResponse(final SalesPromotionResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    final List<SalesPromotionInfo> info = response.getData().getInfo();
                                    plv_refresh.onRefreshComplete();
                                    currentpage = response.getData().getCurrentpage();
                                    wrapListView(info);

                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, (Response.ErrorListener) getActivity());
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(getCurPage() + "");
        WebUtils.doPost(request);
    }

    private void wrapListView(List<SalesPromotionInfo> info) {


        if (info == null)
        {
            return;
        }
        if (info.size() == 0)
        {
            return;
        }

        if (currentpage.equals("0"))
        {
            dataList.clear();
        }

        for (SalesPromotionInfo lineDto : info)
        {
            dataList.add(lineDto);
        }

        if (adapter == null)
        {
            plv_refresh.getRefreshableView().setAdapter(
                    adapter = new CommonAdapter<SalesPromotionInfo>(mContext, dataList, R.layout.lv_find_item) {
                        @Override
                        public void convert(ViewHolder helper, SalesPromotionInfo item) {
                            ImageView iv_info = helper.getView(R.id.iv_info);
                            iv_info.setScaleType(ImageView.ScaleType.FIT_XY);
                            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)iv_info.getLayoutParams();
                            TextView tv_info_name= helper.getView(R.id.tv_info_name);
                            TextView tv_info_date = helper.getView(R.id.tv_info_date);

                            float rate = 3.2f;
                            int width = Utils.getDeviceWidth(getActivity());
                            int height = (int)(width/rate);

                            params.width = width;
                            params.height = height;

                            Glide.with(mContext).load(item.getImagePath())
                                    .placeholder(R.drawable.info_bg)
                                    .into(iv_info);

                            tv_info_name.setText(item.getAttrDeptName()+"--"+item.getArtTitle());
                            tv_info_date.setText(item.getReleaseDate());


                        }

                    });
        }else
        {
            adapter.notifyDataSetChanged();


        }
        plv_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PrimaryId = dataList.get(position-1).getId();
                Intent intent = new Intent();
                intent.putExtra("PrimaryId",PrimaryId);
                intent.setClass(mContext, FoundDetailActivity.class);
                startActivity(intent);

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
