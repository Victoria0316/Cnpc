package com.bluemobi.cnpc.activity;


import android.util.Log;

import android.view.View;
import android.webkit.WebView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.request.OilWebViewDetailRequest;
import com.bluemobi.cnpc.network.response.OilWebViewDetailResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy
 *
 * P5-1 功能介绍
 */

@ContentView(R.layout.fragment_txt)
public class OilWebViewDetailActivity extends BaseActivity{

    @Bind(R.id.product_introduced)
    WebView mWebView;

    TitleBarManager titleBarManager;

   /* String s = "<html>\n" +
            "\t<header><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" /></header>\n" +
            "<body>\n" +
            "1.用户应当同意本协议的条款并按照页面上的提示完成全部的注册程序。用户在进行注册程序过程中点击“同意”按钮即表示用户与数据堂达成协议，完全接受本协议项下的全部条款。\n" +
            "<br/>\n" +
            "2.注册成功后，数据堂将给予每个用户一个用户账号及相应的密码，该用户账号和密码由用户负责保管；用户应当对以其用户账号进行的所有活动和事件负法律责任。\n" +
            "<br/>\n" +
            "3.用户可以使用数据堂各个频道单项服务，当用户使用数据堂各单项服务时，用户的使用行为视为其对该单项服务的服务条款以及数据堂在该单项服务中发出来的各类公告的同意。\n" +
            "<br/>\n" +
            "4.数据堂会员服务协议以及各个频道单项服务条款和公告可由数据堂随时更新，且无需另行通知。您在使用相关服务时，应关注并遵守其所有适用的条款。如您不同意本服务协议及/或随时对其的修改，你可以主动取消数据堂提供的服务；您一旦\n" +
            "</body>\n" +
            "</html>";*/

    @Override
    protected void initBase() {

        titleBarManager = new TitleBarManager();
        titleBarManager.init(OilWebViewDetailActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_function_introduction, R.drawable.return_arrow, true);

        showLoadingPage(false);

    }

    @Override
    protected CnpcHttpJsonRequest initRequest() {
        OilWebViewDetailRequest request = new OilWebViewDetailRequest(new Response.Listener<OilWebViewDetailResponse>() {
            @Override
            public void onResponse(OilWebViewDetailResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    OilWebViewDetailResponse.OilWebViewDetailBean data = response.getData();
                    mWebView.loadData(data.getIntroduceInfo(), "text/html; charset=UTF-8", null);
                } else {// failed
                    Log.e("error", "error");
                }
            }
        },this);

        Utils.showProgressDialog(this);
        return request;
    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);


    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }
}
