package com.bluemobi.cnpc.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;


import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.eventbus.BaseEvent;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.ViewUtils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.view.LoadingPage.LoadResult;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tendcloud.tenddata.TCAgent;

import de.greenrobot.event.EventBus;

public abstract class BaseFragment extends Fragment implements PullToRefreshListView.OnRefreshListener2{

    public Context mContext = null;

    public View view = null;

    private LoadingPage loadingPage;

    public PullToRefreshListView plv_refresh = null;

    public boolean isShowLoadPage = true;

    private View contentView;

    private CnpcHttpJsonRequest request;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
        show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        request = initRequest();
        invokeRequest();
    }



    protected CnpcHttpJsonRequest initRequest() {
        return null;
    }

    private void invokeRequest() {
        if(request == null){
            return;
        }else{
            request.setNetWorkResponseListener(loadingPage);
            pageTime = System.currentTimeMillis();
            CnpcApplication.getInstance().setPageTime(pageTime);
            WebUtils.doPost(request);
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (loadingPage == null) {
            loadingPage = new LoadingPage(getActivity()){

                @Override
                protected void successViewCompleted(View successView) {
                    BaseFragment.this.successViewCompleted(successView);
                }

                @Override
                public View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }

                @Override
                protected LoadResult load() {
                    return BaseFragment.this.load();
                }
            };
        }else{
            ViewUtils.removeParent(loadingPage);
        }

        return loadingPage;
    }

    protected abstract void successViewCompleted(View successView);

    public void initPullToRefresh(PullToRefreshListView pullToRefresh)
    {
        this.plv_refresh = pullToRefresh;
        pullToRefresh.setOnRefreshListener(this);
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

    /**
     * 初始化数据
     * @param savedInstanceState 保存状态数据使用
     */
    public abstract void initData(Bundle savedInstanceState);

    /***
     *  创建成功的界面
     * @return
     */
    public View createSuccessView(){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        if(Constants.NO_ANNOTATION == getLayoutRes()){
            contentView = createViewCustom(inflater);
        }else{
            View view = inflater.inflate(getLayoutRes(), null);
            contentView = view;
        }
        if(contentView == null ){
            //TODO:

        }
        return contentView;
    }


    public View getCurrentView() {
        return contentView;
    }

    protected View createViewCustom(LayoutInflater inflater){
        return null;
    }


    @Override
    public void onStart() {
        super.onStart();
        if(getPageName() != null){
            TCAgent.onPageStart(mContext,  "android---" +String.valueOf(getPageName()));
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        if(getPageName() != null){
            TCAgent.onPageEnd(mContext, "android---" +String.valueOf(getPageName()));
        }
    }


    protected String getPageName() {
        Class<?> handlerType = this.getClass();
        PageName pageName = handlerType.getAnnotation(PageName.class);
        if (pageName == null) {
            return null;
        }
        return getString(pageName.value());
    }

    protected int getLayoutRes(){
        Class<?> handlerType = this.getClass();
        ContentView contentView = handlerType.getAnnotation(ContentView.class);
        if(contentView == null){
            return Constants.NO_ANNOTATION;
        }
        return contentView.value();
    }

    /**
     * 请求服务器
     * @return
     */
    protected abstract LoadResult load();

    public void show(){
        if(loadingPage!=null){
            loadingPage.show(isShowLoadPage);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    public static final int LOAD_MORE = 1;
    public static final int LOAD_REFRESH = 2;
    public static final int NUMBER_PER_PAGE = Constants.CURRENTNUMBASE;// 每页条数
    protected int curPage = -1;// 当前页
    protected long pageTime = 0;

    //add getPage
    protected boolean getPage(int type) {
        boolean ret = true;
        switch (type) {
            case LOAD_MORE: {
                ListAdapter adapter = plv_refresh.getRefreshableView().getAdapter();
                int count =0;
                if (adapter!=null)
                {
                    count = adapter.getCount();
                }

                if(count == 0){
                    plv_refresh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            plv_refresh.onRefreshComplete();
                            Utils.makeToastAndShow(mContext, "已经没有更多记录", Toast.LENGTH_SHORT);
                        }
                    }, 2000);
                    return false;
                }
                int i = count-2;
                Log.e("tag-i-->",i+"");
                if (i % NUMBER_PER_PAGE == 0) {
                    curPage = i/ NUMBER_PER_PAGE;
                    Log.e("tag plv ->", "LOAD_MORE");
                } else {
                    ret = false;
                    Log.e("tag plv ELSE->", "LOAD_MORE");
                    plv_refresh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            plv_refresh.onRefreshComplete();
                            Utils.makeToastAndShow(mContext, "已经没有更多记录", Toast.LENGTH_SHORT);
                        }
                    }, 2000);

                }
            }
            break;
            case LOAD_REFRESH:
                Log.e("tag plv ->","LOAD_REFRESH");
                pageTime = System.currentTimeMillis();
                CnpcApplication.getInstance().setPageTime(pageTime);
                curPage = 0;
                break;
        }


        return ret;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase)
    {
        Log.e("tag", "onPullDownToRefresh");
        getPage(LOAD_REFRESH);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase)
    {
        Log.e("tag","onPullUpToRefresh");
        getPage(LOAD_MORE);

    }

}


