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
import com.bluemobi.cnpc.network.request.RegistrationAgreementRequest;
import com.bluemobi.cnpc.network.response.RegistrationAgreementResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/9/21.
 *
 * p10_14 关于我们
 */

@ContentView(R.layout.activity_about_us)
public class AboutUsActivity extends BaseActivity{

    @Bind(R.id.about)
    WebView mWebView;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(AboutUsActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_about_us, R.drawable.return_arrow, true);
        showLoadingPage(false);

    }

    @Override
    protected CnpcHttpJsonRequest initRequest() {
        RegistrationAgreementRequest request = new RegistrationAgreementRequest(new Response.Listener<RegistrationAgreementResponse>() {
            @Override
            public void onResponse(RegistrationAgreementResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    RegistrationAgreementResponse.RegistrationAgreementBean data = response.getData();
                    mWebView.loadData(data.getIntroduceInfo(), "text/html; charset=UTF-8", null);
                } else {// failed
                    Log.e("error", "error");
                }
            }
        },this);

        Utils.showProgressDialog(this);
        request.setArticleid("4");
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
