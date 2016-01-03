package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.CollectionListAdapter;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.model.CollectionItem;
import com.bluemobi.cnpc.network.request.CollectionListRequest;
import com.bluemobi.cnpc.network.response.CollectionListResponse;
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
 * Created by gaoyn on 2015/7/24.
 * <p/>
 * p10-12 收藏
 */

@ContentView(R.layout.activity_collection)
@PageName(R.string.collection)
public class CollectionActivity extends BaseActivity {

    @Bind(R.id.plv_refresh)
    PullToRefreshListView plv_refresh;

    TitleBarManager titleBarManager;

    private CollectionListAdapter mAdapter;

    private ArrayList<CollectionItem> dataLists = new ArrayList<CollectionItem>();

    private boolean isDelete;

    @Override
    protected void initBase() {

        titleBarManager = new TitleBarManager();
        titleBarManager.init(CollectionActivity.this, getSupportActionBar());
        titleBarManager.showTitleTextBar(R.string.collection, R.drawable.return_arrow, R.string.editor);
        showLoadingPage(false);

    }

    @Override
    protected boolean getPage(int type) {
        if (!super.getPage(type)) return false;
        connectToSercer(type);
        return true;
    }

    private void connectToSercer(int type) {
        if (type == LOAD_REFRESH) {
            dataLists.clear();
        }
        CollectionListRequest request = new CollectionListRequest(new Response.Listener<CollectionListResponse>() {
            @Override
            public void onResponse(CollectionListResponse response) {
                Utils.closeDialog();
                plv_refresh.onRefreshComplete();
                if (response != null && response.getStatus() == 0) {
                    if (response.getData() != null && response.getData()
                            .getInfo() != null) {
                        dataLists.addAll(response.getData().getInfo());
                        if (mAdapter == null) {
                            mAdapter = new CollectionListAdapter(
                                    CollectionActivity.this, dataLists, R.layout.lv_collection
                            );
                            plv_refresh.setAdapter(mAdapter);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    Utils.makeToastAndShow(getBaseContext(), response.getContent());
                }
            }
        }, this);
        request.setCurrentnum(String.valueOf(Constants.CURRENTNUMBASE));
        request.setCurrentpage(String.valueOf(getCurPage()));
        request.setHandleCustomErr(false);
        WebUtils.doPost(request);
        Utils.showProgressDialog(this);
    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);

        initPullToRefresh(plv_refresh);

        plv_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int index = i - 1;

                Intent intent = new Intent();
                intent.setClass(mContext, GasStationDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("GAS_ID", dataLists.get(index).getCollectionId());
                bundle.putString("GAS_NAME", dataLists.get(index).getCollectionName());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        getPage(LOAD_REFRESH);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    @Override
    public void clickBarRight() {
        if (dataLists.size() != 0) {
            isDelete = !isDelete;
            if(isDelete){
                titleBarManager.setRightText(R.string.crop__done);
            }else {
                titleBarManager.setRightText(R.string.editor);
            }
            mAdapter.setIsDelete(isDelete);
            mAdapter.notifyDataSetChanged();
        }
    }

}
