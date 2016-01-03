package com.bluemobi.cnpc.fragment.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


/**
 * liufengyu
 * @author Administrator
 *
 */
public abstract class BasePage
{
    public Context pageContext = null;

    public View view = null;

    public PullToRefreshListView plv_refresh = null;


    public BasePage(Context context)
    {
        this.pageContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = initView(inflater);

    }

    public void initPullToRefresh(PullToRefreshListView pullToRefresh)
    {
        pullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabels = pullToRefresh
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("正在载入...");
        startLabels.setReleaseLabel("放开刷新...");
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");
    }

    public View getRootView()
    {
        return view;
    }

    public abstract View initView(LayoutInflater inflater);

    public abstract void initData();
}
