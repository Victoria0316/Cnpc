package com.bluemobi.cnpc.activity;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.Response;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.request.FrozenSectionRequest;
import com.bluemobi.cnpc.network.request.ShakeInitializeRequest;
import com.bluemobi.cnpc.network.request.WinawardinfoRequest;
import com.bluemobi.cnpc.network.response.FrozenSectionResponse;
import com.bluemobi.cnpc.network.response.ShakeInitializeResponse;
import com.bluemobi.cnpc.network.response.WinawardinfoResponse;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


/**
 * Created by gaoyn on 2015/7/24.
 * <p/>
 * p10-11 摇积分
 */

@ContentView(R.layout.activity_shake)
@PageName(R.string.shake_title)
public class ShakeActivity extends BaseActivity implements PlatformActionListener {
    private final static String tag = "ShakeActivity";

    @Bind(R.id.shake_text_orange)
    ImageView shake_text_orange;

    @Bind(R.id.shake_text)
    ImageView shake_text;

    @Bind(R.id.shake_round)
    ImageView shake_round;

    SensorManager sm;
    SensorL listener;

    private boolean isRefresh = false;

    AlertDialog dialog;
    AlertDialog dialogShare;
    View view;
    View view_ok;

    ObjectAnimator animator;
    @Bind(R.id.total)
    TextView total;//当前剩余积分


    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(ShakeActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.shake_title, R.drawable.return_arrow, true);
        ShareSDK.initSDK(this);
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {


        ButterKnife.bind(this, successView);

        integralRequest();


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        listener = new SensorL();
        sm.registerListener(listener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        dialog = new AlertDialog.Builder(this).create();
        /*成功*/
        view_ok = LayoutInflater.from(this).inflate(R.layout.dialog_shake_success, null);



        /*失败*/
        view = LayoutInflater.from(this).inflate(R.layout.dialog_shake_failure, null);
        ImageButton dialog_shake_shut = (ImageButton) view.findViewById(R.id.dialog_shake_shut);
        dialog_shake_shut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integralRequest();
                dialog.dismiss();
            }
        });
        Button again = (Button) view.findViewById(R.id.again);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integralRequest();
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
    }

    private void integralRequest() {

       /* FrozenSectionRequest request = new FrozenSectionRequest(new Response.Listener<FrozenSectionResponse>() {
            @Override
            public void onResponse(FrozenSectionResponse response) {
                if (response != null && response.getStatus() == 0) {
                    Logger.d(tag, "积分获得成功:" + response.data.pointsBalance);
                    total.setText(response.data.pointsBalance);
                }

            }
        }, ShakeActivity.this);*/

        ShakeInitializeRequest request = new ShakeInitializeRequest(new Response.Listener<ShakeInitializeResponse>() {
            @Override
            public void onResponse(ShakeInitializeResponse response) {
                if (response != null && response.getStatus() == 0) {
                    Logger.d(tag, "积分获得成功:" + response.getMsg());
                    total.setText(response.getMsg());
                }

            }
        }, ShakeActivity.this);
        WebUtils.doPost(request);
    }


    private void request() {
        WinawardinfoRequest request = new WinawardinfoRequest(new Response.Listener<WinawardinfoResponse>() {
            @Override
            public void onResponse(final WinawardinfoResponse response) {
                if (response != null && response.getStatus() == 0) {
                    Logger.d(tag, "摇积分 ok");
                    isRefresh = false;
                    TextView tv_time = (TextView) view_ok.findViewById(R.id.tv_time);//时间
                    tv_time.setText("使用时间：" + response.getData().getStartTime() + "-" + response.getData().getEndTime());
                    TextView tv_money = (TextView) view_ok.findViewById(R.id.tv_money);//金额
                    tv_money.setText("获得了" + response.getData().getAwardName());
                    TextView tv_rule_1 = (TextView) view_ok.findViewById(R.id.tv_rule_1);//规则1
                    tv_rule_1.setText("1:订单金额不低于" + response.getData().getMinOrderAmount() + "元方可使用");
                    TextView tv_rule_2 = (TextView) view_ok.findViewById(R.id.tv_rule_2);//规则2
                    tv_rule_2.setText("2:可抵用为" + response.getData().getDiscount() + "元现金");

                    ImageButton dialog_shake_shut_ok = (ImageButton) view_ok.findViewById(R.id.dialog_shake_shut);
                    dialog_shake_shut_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            integralRequest();
                            dialog.dismiss();
                        }
                    });
                    final Button btn_red_packet = (Button) view_ok.findViewById(R.id.btn_red_packet);//红包
                    if ("1".equals(response.getData().getWtype())) {  //"wtype": "1红包2优惠卷",
                        btn_red_packet.setText("我的红包");
                    } else {
                        btn_red_packet.setText("我的优惠卷");
                    }
                    btn_red_packet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.d(tag, "你单击了分享左边");

                            if ("1".equals(response.getData().getWtype())) {  //"wtype": "1红包2优惠卷",
                                Logger.d(tag, "跳到红包页");
                                Intent intent = new Intent();
                                intent.setClass(ShakeActivity.this, BonusActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Logger.d(tag, "跳到优惠卷页");
                                Intent intent_1 = new Intent();
                                intent_1.setClass(ShakeActivity.this, CouponActivity.class);
                                startActivity(intent_1);
                                finish();
                            }
                        }
                    });
                    Button btn_share = (Button) view_ok.findViewById(R.id.btn_share);//分享
                    btn_share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.d(tag, "你单击了分享");
                            if (dialogShare == null) {
                                dialogShare = new AlertDialog.Builder(ShakeActivity.this).create();
                                view = LayoutInflater.from(ShakeActivity.this).inflate(R.layout.dialog_share_app, null);
                                TextView tv_share = (TextView) view.findViewById(R.id.tv_share);
                                tv_share.setVisibility(View.VISIBLE);
                                ImageButton share_wechat = (ImageButton) view.findViewById(R.id.share_wechat);
                                ImageButton share_circle_friends = (ImageButton) view.findViewById(R.id.share_circle_friends);
                                ImageButton share_sina = (ImageButton) view.findViewById(R.id.share_sina);
                                Button cancel = (Button) view.findViewById(R.id.cancel);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogShare.dismiss();
                                    }
                                });
                                share_circle_friends.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.e("pengyouquan", "pengyouquan");
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
                                        /*Log.e("sina", "sinasinasina");
                                        share(3);*/
                                        Utils.makeToastAndShow(getBaseContext(), "正在努力开发中…");
                                    }
                                });

                                Window window = dialogShare.getWindow();
                                window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
                                window.setWindowAnimations(R.style.dialog_bottom_style);  //添加动画
                                dialogShare.show();
                                dialogShare.setContentView(view);
                            } /*else {
                                dialogShare.show();
                            }*/
                        }
                    });

                    dialog.show();
                    dialog.setContentView(view_ok);

                } else if (response.getStatus() == 21) {
                    Toast.makeText(ShakeActivity.this, "" + response.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Logger.d(tag, "摇积分 fail");

                    isRefresh = false;

                    dialog.show();
                    dialog.setContentView(view);

                }
            }
        }, ShakeActivity.this);

//        private String checkUserId;     //用户登录时返回的userId对应的值
//        private String ssid;            //session ID
//        private String userId;           //用户登录时的ID
//        private String longitude;         //坐标经度
//        private String latitude;         //坐标纬度

        Logger.d(tag, "坐标经度 " + SharedPreferencesUtil.getFromFile(getApplicationContext(), "longitude"));
        Logger.d(tag, "坐标纬度 " + SharedPreferencesUtil.getFromFile(getApplicationContext(), "latitude"));


        request.setCheckUserId(PreferencesService.getInstance(ShakeActivity.this).getPreferences("userId"));
        request.setSsid(PreferencesService.getInstance(ShakeActivity.this).getPreferences("ssid"));
        request.setUserId(PreferencesService.getInstance(ShakeActivity.this).getPreferences("userId"));
        request.setLongitude(SharedPreferencesUtil.getFromFile(getApplicationContext(), "longitude"));
        request.setLatitude(SharedPreferencesUtil.getFromFile(getApplicationContext(), "latitude"));
        request.setHandleCustomErr(false);

        WebUtils.doPost(request);


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


    private class SensorL implements SensorEventListener {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (isRefresh)
                    return;
                float newX = Math.abs(event.values[SensorManager.DATA_X]);
                float newY = Math.abs(event.values[SensorManager.DATA_Y]);
                float newZ = Math.abs(event.values[SensorManager.DATA_Z]);
                // X
                if (newX >= 18 || newY >= 20 || newZ >= 20) {
                    //Toast.makeText(ShakeActivity.this, "newX" + newX, Toast.LENGTH_SHORT).show();
                    isRefresh = true;
                    ShakeStart();
                    //request();//遥一摇请求服务器
                    return;
                }
                // Y
               /* if (newY >= 20) {
                    //Toast.makeText(ShakeActivity.this, "newY" + newY, Toast.LENGTH_SHORT).show();
                    ShakeStart();
                    isRefresh = true;
//                    request();//遥一摇请求服务器
                    return;
                }
                // Z
                if (newZ >= 20) {
                    //Toast.makeText(ShakeActivity.this, "newZ" + newZ, Toast.LENGTH_SHORT).show();
                    ShakeStart();
                    isRefresh = true;
//                    request();//遥一摇请求服务器
                    return;
                }*/

            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                // Log.e("TYPE_MAGNETIC_FIELD", ""+event.sensor.toString());
            }
            if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                // Log.e("TYPE_PRESSURE", ""+event.sensor.toString());
            }
        }

    }

    @Override
    protected void onPause() {


        if (sm != null) {// 取消监听器
            sm.unregisterListener(listener);
        }
        super.onPause();
    }


    @Override
    protected void onResume() {



        if (sm != null) {// 注册监听器
            sm.registerListener(listener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
        super.onResume();
    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    public static ObjectAnimator tada(View view) {
        return tada(view, 1f);
    }

    public static ObjectAnimator tada(View view, float shakeFactor) {

        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(.1f, -3f * shakeFactor),
                Keyframe.ofFloat(.2f, -3f * shakeFactor),
                Keyframe.ofFloat(.3f, 3f * shakeFactor),
                Keyframe.ofFloat(.4f, -3f * shakeFactor),
                Keyframe.ofFloat(.5f, 3f * shakeFactor),
                Keyframe.ofFloat(.6f, -3f * shakeFactor),
                Keyframe.ofFloat(.7f, 3f * shakeFactor),
                Keyframe.ofFloat(.8f, -3f * shakeFactor),
                Keyframe.ofFloat(.9f, 3f * shakeFactor),
                Keyframe.ofFloat(1f, 0)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhRotate).
                setDuration(1000);
    }

    private boolean isStart = true;

    public void ShakeStart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animator = tada(shake_text_orange);

                // animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setDuration(1000);
                animator.start();

                animator = tada(shake_text);
                // animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setDuration(1000);
                animator.start();

                animator = tada(shake_round);
                // animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setDuration(1000);
                animator.start();
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        request();//遥一摇请求服务器
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

            }
        }, 0);
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
                weixin.setPlatformActionListener(ShakeActivity.this); // 设置分享事件回调
                sp.setShareType(Platform.SHARE_WEBPAGE);
                weixin.share(sp);
                break;
            case 2:
                Platform wechatmoments = ShareSDK.getPlatform(WechatMoments.NAME);
                wechatmoments.setPlatformActionListener(ShakeActivity.this); // 设置分享事件回调
                sp.setShareType(Platform.SHARE_WEBPAGE);
                wechatmoments.share(sp);
                break;
            case 3:
                sp.setTitleUrl("http://www.sydoil.com/app");
                Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                qq.setPlatformActionListener(ShakeActivity.this); // 设置分享事件回调
                qq.share(sp);
                break;
            default:
                break;
        }
    }
}
