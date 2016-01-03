package com.bluemobi.cnpc.activity;

import android.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.model.FoundDetailBean;
import com.bluemobi.cnpc.network.request.FoundDetailRequest;
import com.bluemobi.cnpc.network.response.FoundDetailResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bumptech.glide.Glide;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by liufy
 * <p/>
 * P9_1详情
 */

@ContentView(R.layout.fragment_webview)
@PageName(R.string.details)
public class FoundDetailActivity extends BaseActivity implements PlatformActionListener {

    @Bind(R.id.webView)
    WebView mWebView;

    @Bind(R.id.artTitle)
    TextView artTitle;

    @Bind(R.id.releaseDate)
    TextView releaseDate;

    @Bind(R.id.iv_info)
    ImageView iv_info;

    private AlertDialog dialog;
    private View view;
    TitleBarManager titleBarManager;

    /**
     * 页面传递
     */
    private String PrimaryId;

    private FoundDetailBean data;


    @Override
    protected void initBase() {

        titleBarManager = new TitleBarManager();
        titleBarManager.init(FoundDetailActivity.this, getSupportActionBar());
        titleBarManager.showShareBar(R.string.details, R.drawable.share);

        showLoadingPage(false);
        ShareSDK.initSDK(this);

    }

    @Override
    protected CnpcHttpJsonRequest initRequest() {

        PrimaryId = getIntent().getStringExtra("PrimaryId");

        FoundDetailRequest request = new FoundDetailRequest(new Response.Listener<FoundDetailResponse>() {
            @Override
            public void onResponse(FoundDetailResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    data = response.getData();

                    if(data.getDeptAddress() == null){
                        artTitle.setText(data.getArtTitle());
                    }else {
                        artTitle.setText(data.getArtTitle()+"  （"+data.getDeptAddress()+"）");
                    }
                    releaseDate.setText(data.getReleaseDate());

                    if(data.getArtContent() != null){
                        mWebView.loadData(data.getArtContent(), "text/html; charset=UTF-8", null);
                    }

                    iv_info.setScaleType(ImageView.ScaleType.FIT_XY);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)iv_info.getLayoutParams();

                    float rate = 3.2f;
                    int width = Utils.getDeviceWidth();
                    int height = (int)(width/rate);

                    params.width = width;
                    params.height = height;

                    Glide.with(mContext).load(data.getImagePath())
                            .placeholder(R.drawable.info_bg).into(iv_info);

                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setId(PrimaryId);
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

    @Override
    public void clickImageRight() {
        shareApp();
    }

    public void shareApp() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .create();
            view = LayoutInflater.from(this).inflate(R.layout.dialog_share_app, null);
            TextView tv_share = (TextView) view.findViewById(R.id.tv_share);
            tv_share.setVisibility(View.VISIBLE);
            ImageButton share_wechat = (ImageButton) view.findViewById(R.id.share_wechat);
            ImageButton share_circle_friends = (ImageButton) view.findViewById(R.id.share_circle_friends);
            ImageButton share_sina = (ImageButton) view.findViewById(R.id.share_sina);
            Button cancel = (Button) view.findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            share_circle_friends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("pengyouquan","pengyouquan");
                    share(2);
                }
            });
            share_wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("weixin", "weixinweixinweixin");
                    share(1);
                }
            });
            share_sina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Log.e("sina","sinasinasina");
                    share(3);*/
                    Utils.makeToastAndShow(getBaseContext(), "正在努力开发中…");
                }
            });

            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
            window.setWindowAnimations(R.style.dialog_bottom_style);  //添加动画
            dialog.show();
            //ViewGroup.LayoutParams.MATCH_PARENT
            ViewGroup.LayoutParams params  = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.setContentView(view,params );
        } else {
            dialog.show();
        }
    }

    public void share(int  i) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("「油管家」加油还能赚钱，邀你一起来！");
        sp.setText("不仅加油享优惠，还能理财增值，快来下载吧~ ");
        sp.setImageUrl("http://101.200.193.187:8888/Fload/cnpc/2015-09-24/9CC8F138E4954923B2804AF959CEF19A.png?id=9CC8F138E4954923B2804AF959CEF19A&fn=2015-09-24&fs=png&appkey=cnpc");
        sp.setUrl("http://www.sydoil.com/app");
        switch (i) {
            case 1:
                Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
                weixin.setPlatformActionListener(FoundDetailActivity.this); // 设置分享事件回调
                sp.setShareType(Platform.SHARE_WEBPAGE);
                weixin.share(sp);
                break;
            case 2:
                Platform wechatmoments = ShareSDK.getPlatform(WechatMoments.NAME);
                wechatmoments.setPlatformActionListener(FoundDetailActivity.this); // 设置分享事件回调
                sp.setShareType(Platform.SHARE_WEBPAGE);
                wechatmoments.share(sp);
                break;
            case 3:
                sp.setTitleUrl("http://www.sydoil.com/app");
                Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                qq.setPlatformActionListener(FoundDetailActivity.this); // 设置分享事件回调
                qq.share(sp);
                break;
            default:
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        finish();
        Utils.makeToastAndShow(getBaseContext(),"分享成功");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e("error","errorrrrr");
    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
