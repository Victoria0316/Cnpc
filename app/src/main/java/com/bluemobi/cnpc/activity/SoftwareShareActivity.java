package com.bluemobi.cnpc.activity;

import android.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.request.AppShareIntegralRequest;
import com.bluemobi.cnpc.network.request.LoginRequest;
import com.bluemobi.cnpc.network.request.RegisterRequest;
import com.bluemobi.cnpc.network.response.AppShareIntegralResponse;
import com.bluemobi.cnpc.network.response.LoginResponse;
import com.bluemobi.cnpc.network.response.RegisterResponse;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by gaoyn on 2015/7/16.
 * <p/>
 * p2-4  软件分享
 */

@ContentView(R.layout.activity_software_share)
public class SoftwareShareActivity extends BaseActivity implements PlatformActionListener {

    @Bind(R.id.annotation)
    TextView annotation;

    @Bind(R.id.invitation_button)
    Button invitation_button;

    private AlertDialog dialog;
    private View view;

    /**
     * 页面传递
     */
    private String setOilId;
    /**
     * 页面传递
     */
    private String setPassword;
    /**
     * 页面传递
     */
    private String setCellphone;
    /**
     * 页面传递
     */
    private String setValidationCode;
    /**
     * 页面传递
     */
    private String setLongitude;
    /**
     * 页面传递
     */
    private String setLatitude;
    /**
     * 页面传递
     */
    private String setAddressid;


    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(SoftwareShareActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.Software_share, R.drawable.return_arrow, true);

        showLoadingPage(false);
        ShareSDK.initSDK(this);

    }

    @Override
    protected CnpcHttpJsonRequest initRequest() {

        setOilId = getIntent().getStringExtra("setOilId");
        setPassword = getIntent().getStringExtra("setPassword");
        setCellphone = getIntent().getStringExtra("setCellphone");
        setValidationCode = getIntent().getStringExtra("setValidationCode");
        setLongitude = getIntent().getStringExtra("setLongitude");
        setLatitude = getIntent().getStringExtra("setLatitude");
        setAddressid = getIntent().getStringExtra("setAddressid");

        Utils.showProgressDialog(mContext);
        RegisterRequest request = new RegisterRequest
                (
                        new Response.Listener<RegisterResponse>() {
                            @Override
                            public void onResponse(RegisterResponse response) {
                                Utils.closeDialog();
                                RegisterResponse.RegisterData data = response.data;
                                CnpcApplication.getInstance().setCheckID(data.id);
                            }

                        }, this);
        request.setOilId(setOilId);
        request.setPassword(setPassword);
        request.setCellphone(setCellphone);
        request.setValidationCode(setValidationCode);
        request.setLongitude(setLongitude);
        request.setLatitude(setLatitude);
        request.setAddressid(setAddressid);
        return request;
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        String temp = "100";
        int size = temp.length();
        String text = String.format(getResources().getString(R.string.annotation), temp, temp);
        int index[] = new int[2];
        index[0] = text.indexOf(temp);
        index[1] = text.lastIndexOf(temp);

        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_pink)), index[0], index[0] + size, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_pink)), index[1], index[1] + size, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        annotation.setText(style);

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finishAll();
        connectToServerLogin();
    }


    @Override
    public void onBackPressed() {
        finishAll();
        connectToServerLogin();
    }

    @OnClick(R.id.invitation_button)
    public void shareApp() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .create();
            view = LayoutInflater.from(this).inflate(R.layout.dialog_share_app, null);
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
                    Log.e("pengyouquan", "pengyouquan");
                    share(2);
                    againApp();
                }
            });
            share_wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("weixin", "weixinweixinweixin");
                    againApp();
                    share(1);
                }
            });
            share_sina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Log.e("sina", "sinasinasina");
                    againApp();
                    share(3);
                    */
                    Utils.makeToastAndShow(getBaseContext(), "正在努力开发中…");
                }
            });
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
            window.setWindowAnimations(R.style.dialog_bottom_style);  //添加动画
            dialog.show();
            dialog.setContentView(view);
        } else {
            dialog.show();
        }
    }

    private void againApp() {

        Utils.showProgressDialog(this);
        AppShareIntegralRequest request = new AppShareIntegralRequest(new Response.Listener<AppShareIntegralResponse>() {
            @Override
            public void onResponse(AppShareIntegralResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success

                    Utils.makeToastAndShow(getBaseContext(), "再送100积分");
                }
            }
        }, this);
        request.setHandleCustomErr(false);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("register");
        WebUtils.doPost(request);

    }

    public void share(int i) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("「油管家」加油还能赚钱，邀你一起来！");
        sp.setText("不仅加油享优惠，还能理财增值，快来下载吧~ ");
        sp.setImageUrl("http://101.200.193.187:8888/Fload/cnpc/2015-09-24/9CC8F138E4954923B2804AF959CEF19A.png?id=9CC8F138E4954923B2804AF959CEF19A&fn=2015-09-24&fs=png&appkey=cnpc");
        sp.setUrl("http://www.sydoil.com/app");
        switch (i) {
            case 1:
                Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
                weixin.setPlatformActionListener(SoftwareShareActivity.this); // 设置分享事件回调
                sp.setShareType(Platform.SHARE_WEBPAGE);
                weixin.share(sp);
                break;
            case 2:
                Platform wechatmoments = ShareSDK.getPlatform(WechatMoments.NAME);
                wechatmoments.setPlatformActionListener(SoftwareShareActivity.this); // 设置分享事件回调
                sp.setShareType(Platform.SHARE_WEBPAGE);
                wechatmoments.share(sp);
                break;
            case 3:
                sp.setTitleUrl("http://www.sydoil.com/app");
                Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                qq.setPlatformActionListener(SoftwareShareActivity.this); // 设置分享事件回调
                qq.share(sp);
                break;
            default:
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e("error", "errorrrrr");
    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    private void connectToServerLogin() {

        setPassword = getIntent().getStringExtra("setPassword");
        setCellphone = getIntent().getStringExtra("setCellphone");

        Utils.showProgressDialog(mContext);
        LoginRequest request = new LoginRequest
                (
                        new Response.Listener<LoginResponse>() {
                            @Override
                            public void onResponse(LoginResponse response) {
                                Utils.closeDialog();
                                finish();
                                if (response != null && response.getStatus() == 0) {// success
                                    LoginResponse.LoginData data = response.data;
                                    CnpcApplication.getInstance().setmData(data);
                                    try {
                                        PreferencesService.getInstance(mContext).saveBool("loginSucceed", true);
                                        PreferencesService.getInstance(mContext).save("userId", data.userId);
                                        PreferencesService.getInstance(mContext).save("ssid", data.ssid);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    Utils.moveTo(mContext, HomeActivity.class);
                                } else {
                                    Utils.makeToastAndShow(getBaseContext(), response.getMsg());
                                }
                            }
                        }, this);
        request.setPassword(setPassword);
        request.setUsername(setCellphone);
        WebUtils.doPost(request);
    }
}
