package com.bluemobi.cnpc.activity;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.view.LoadingPage;

/**
 * Created by liufy on 2015/7/20.
 * P5_1功能介绍
 */
public class FunctionIntroductionActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void initBase()
    {
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_function_introduction,R.drawable.return_arrow,true);
        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {


        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(10,10,10,10);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.common_bg));
        mWebView = new WebView(mContext);
        relativeLayout.addView(mWebView,lp);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }

    @Override
    protected LoadingPage.LoadResult load() {

/*        mWebView.loadData(htmlcode,
                "text/html; charset=UTF-8", null);*/
        return LoadingPage.LoadResult.success;
    }

}


