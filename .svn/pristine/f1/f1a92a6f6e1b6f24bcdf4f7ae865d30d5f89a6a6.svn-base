package com.bluemobi.cnpc.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.request.FeedBackRequest;
import com.bluemobi.cnpc.network.response.FeedBackResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liufy on 2015/7/29.
 * P10_14_2意见反馈
 */
@ContentView(R.layout.activity_feedback)
public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.btn_confirm)
    Button btn_confirm;

    @Bind(R.id.title)
    EditText title;

    @Bind(R.id.content)
    EditText content;

    @Override
    protected void initBase() {
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_feedback, R.drawable.return_arrow, true);
        showLoadingPage(false);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm(){
        if(title.getText().toString() == null || title.getText().toString().length()==0){
            Utils.makeToastAndShow(getBaseContext(),"标题不能为空");
        }else if(content.getText().toString() == null || content.getText().toString().length()==0){
            Utils.makeToastAndShow(getBaseContext(),"内容不能为空");
        }else{
            Request();
        }
    }

    private void Request() {
        FeedBackRequest request = new FeedBackRequest(new Response.Listener<FeedBackResponse>() {
            @Override
            public void onResponse(FeedBackResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    finish();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setTitle(title.getText().toString().trim());
        request.setContent(content.getText().toString().trim());
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

}
