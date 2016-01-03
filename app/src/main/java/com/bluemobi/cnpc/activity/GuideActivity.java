package com.bluemobi.cnpc.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.db.entity.CarAllListDetail;
import com.bluemobi.cnpc.network.request.CarAllListRequest;
import com.bluemobi.cnpc.network.response.CarAllListResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.ThreadManager;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;

import java.util.ArrayList;

/**
 * Created by wangzhijun on 2015/7/17.
 */
public class GuideActivity extends Activity implements GestureDetector.OnGestureListener {

    private ViewFlipper mGuideFlipper;

    private GestureDetector mGestureDetector;

    private static final int FLING_MIN_DISTANCE = 100;
    private static final int FLING_MIN_VELOCITY = 200;
    private int currentIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //首次登陆设置false，防止引导页再次运行
        SharedPreferencesUtil.saveToFile(this, Constants.FIRSTLOADAPP, "false");
        setContentView(R.layout.activity_guide);
        initGuidePage();

    }

    private void initGuidePage() {
        mGuideFlipper = (ViewFlipper)findViewById(R.id.guide_flipper);
        ImageView guideImg1 = new ImageView(this);
        guideImg1.setBackgroundResource(R.drawable.guide1);

        ImageView guideImg2 = new ImageView(this);
        guideImg2.setBackgroundResource(R.drawable.guide2);

        ImageView guideImg3 = new ImageView(this);
        guideImg3.setBackgroundResource(R.drawable.guide3);
        guideImg3.setTag("end");
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(guideImg3, FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        TextView startImage = new TextView(this);

        startImage.setText("马上进入");
        startImage.setTextColor(getResources().getColor(R.color.white));
        startImage.setGravity(Gravity.CENTER);
        startImage.setBackgroundColor(getResources().getColor(R.color.common_blue));
        startImage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        startImage.setPadding(Utils.dip2px(this, 20), Utils.dip2px(this, 8),
                Utils.dip2px(this, 20), Utils.dip2px(this, 8));

        /*Drawable drawable = getResources().getDrawable(R.drawable.btn_bg);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        startImage.setCompoundDrawables(null, null, drawable, null);
        startImage.setCompoundDrawablePadding(Utils.dip2px(this, 5));
        startImage.setBackgroundResource(R.drawable.btn_bg);
        startImage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);

        startImage.setPadding(Utils.dip2px(this, 20), Utils.dip2px(this, 8),
                Utils.dip2px(this, 20), Utils.dip2px(this, 8));*/

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = Utils.dip2px(this, 40);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        // params.width = Utils.dip2px(this, 80);
        // params.height = Utils.dip2px(this, 40);
        frameLayout.addView(startImage, params);

        startImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.saveToFile(GuideActivity.this,
                        Constants.FIRSTLOADAPP, "false");
                Utils.moveTo(GuideActivity.this, LauncherActivity.class);
                finish();
            }
        });

        mGuideFlipper.addView(guideImg1, 0);
        mGuideFlipper.addView(guideImg2, 1);
        mGuideFlipper.addView(frameLayout, 2);
        mGestureDetector = new GestureDetector(this, this);
        mGuideFlipper.setLongClickable(true);
        mGuideFlipper.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (mGuideFlipper.getChildCount() < 2) {
            return false;
        }

        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            // 当左侧滑动的时候
            if (currentIndex == mGuideFlipper.getChildCount()) {// 最后一页 不响应循环
                return false;
            }
            mGuideFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.translate_in_from_right));
            mGuideFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.translate_out_from_left));
            mGuideFlipper.showNext();
            currentIndex++;
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            // 设置View进入屏幕时候使用的动画
            if (currentIndex == 1) {// 第一页 不响应循环
                return false;
            }
            mGuideFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.translate_in_from_left));
            mGuideFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.translate_out_from_right));
            mGuideFlipper.showPrevious();
            currentIndex--;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GuideActivity.this.finish();
    }



}
