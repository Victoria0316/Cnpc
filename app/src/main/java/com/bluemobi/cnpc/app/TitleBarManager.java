package com.bluemobi.cnpc.app;

import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.base.BaseActivity;
import com.jauker.widget.BadgeView;

/**
 * Created by liufy on 2015/6/28.
 */
public class TitleBarManager implements View.OnClickListener{

    /**
     * 中间标题
     */
    private TextView tv_title;
    /**
     * 右边标题
     */
    private TextView tv_title_right;
    /**
     * 左边返回按钮
     */
    private ImageView iv_back;

    private View il_search_bar;

    /**
     * 搜索bar 搜索框
     */
    private EditText et_search;

    /**
     * 搜索bar 右边按钮
     */
    private TextView iv_home_hint;

    private BaseActivity activity;

    /**
     * 左边定位图标 地点
     */

    private ImageView position_image;
    private TextView tv_title_left;


    private ActionBar mSupportActionBar;


    private ImageView iv_right;

    public TitleBarManager() {

    }

    /**
     * 初始化 TitleView 中的控件
     *
     * @param activity
     */
    public void init(BaseActivity activity, ActionBar supportActionBar) {
        this.activity = activity;
        initTitle(supportActionBar);
        supportActionBar.setCustomView(R.layout.include_titlebar);
        tv_title = (TextView) activity.findViewById(R.id.tv_title);
        tv_title_right = (TextView) activity.findViewById(R.id.tv_title_right);
        iv_back = (ImageView) activity.findViewById(R.id.iv_back);
        il_search_bar = activity.findViewById(R.id.il_search_bar);
        et_search = (EditText) activity.findViewById(R.id.et_search);
        iv_home_hint = (TextView) activity.findViewById(R.id.iv_home_hint);

        iv_right = (ImageView) activity.findViewById(R.id.iv_right);
        il_search_bar.setVisibility(View.GONE);

        position_image  = (ImageView) activity.findViewById(R.id.position_image);
        tv_title_left  = (TextView) activity.findViewById(R.id.tv_title_left);
        setListener();
    }


    public String getSearchEditText()
    {
        return et_search.getText().toString().toString();
    }

    public void setMainText(String titleText)
    {
        tv_title_left.setText(titleText);
    }

    /**
     * 初始化titlebar
     */
    private void initTitle(ActionBar supportActionBar) {
        this.mSupportActionBar = supportActionBar;
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(false);
        supportActionBar.setDisplayShowCustomEnabled(true);
    }

    /**
     * 添加监听器
     */
    private void setListener() {
        iv_back.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        il_search_bar.setOnClickListener(this);
        iv_home_hint.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        tv_title_left.setOnClickListener(this);
        position_image.setOnClickListener(this);

    }

    /**
     * 显示最普通的Titlebar 左边按钮，中间文字
     *
     * @param textID     中间的文字
     * @param leftSrcID
     * @param isShowBack 是否显示回退按钮 true 显示
     */
    public void showCommonTitleBar(int textID, int leftSrcID, boolean isShowBack) {
        tv_title_left.setVisibility(View.GONE);
        position_image.setVisibility(View.GONE);
        tv_title.setText(textID);
        if (isShowBack) {
            iv_back.setImageResource(leftSrcID);
            iv_back.setVisibility(View.VISIBLE);
        } else {
            iv_back.setVisibility(View.GONE);
        }

        tv_title_right.setVisibility(View.GONE);
    }

    public void showCommonTextTitleBar(String text, int leftSrcID, boolean isShowBack) {
        tv_title_left.setVisibility(View.GONE);
        position_image.setVisibility(View.GONE);
        tv_title.setText(text);
        if (isShowBack) {
            iv_back.setImageResource(leftSrcID);
            iv_back.setVisibility(View.VISIBLE);
        } else {
            iv_back.setVisibility(View.GONE);
        }

        tv_title_right.setVisibility(View.GONE);
    }

    /**
     * Titlebar 左边按钮，文字（定位）
     *
     * @param textID     中间的文字
     * @param leftSrcID
     *
     */
    public void showPositionTitleBar(String position,int textID, int leftSrcID) {

        tv_title_left.setVisibility(View.VISIBLE);
        position_image.setVisibility(View.VISIBLE);

        tv_title_left.setText(position);
        tv_title.setText(textID);
        position_image.setImageResource(leftSrcID);
        tv_title_right.setVisibility(View.INVISIBLE);
        iv_back.setVisibility(View.INVISIBLE);
    }


    public void setLeftTitle(String position)
    {
        tv_title_left.setText(position);
    }


    /**
     * 显示最普通的Titlebar 左边按钮，中间文字，右边文字
     */
    public void showTitleTextBar(int textID, int leftSrcID, int rightTextID) {

        tv_title_left.setVisibility(View.GONE);
        position_image.setVisibility(View.GONE);

        tv_title.setText(textID);
        iv_back.setImageResource(leftSrcID);
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText(rightTextID);

    }

    /**
     * 显示最普通的Titlebar 左边按钮，中间文字，右边文字,左边按钮是否隐藏
     */
    public void showTitleRightBar(int textID, int leftSrcID, int rightTextID,boolean isShowBack) {

        tv_title_left.setVisibility(View.GONE);
        position_image.setVisibility(View.GONE);

        tv_title.setText(textID);
        if (isShowBack) {
            iv_back.setImageResource(leftSrcID);
            iv_back.setVisibility(View.VISIBLE);
        } else {
            iv_back.setVisibility(View.GONE);
        }
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText(rightTextID);

    }


    public void showLRBar(int textID, int leftSrcID, int rightSrcID) {

        tv_title_left.setVisibility(View.GONE);
        position_image.setVisibility(View.GONE);

        tv_title.setText(textID);
        iv_back.setImageResource(leftSrcID);
        tv_title_right.setVisibility(View.GONE);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(rightSrcID);

    }


    /**
     * P5 主页的titlebar 带搜索
     */
    public void showSearchTitleBar(int leftSrcID,int rightTextID) {


        tv_title_left.setVisibility(View.GONE);
        position_image.setVisibility(View.GONE);

        iv_back.setImageResource(leftSrcID);
        iv_home_hint.setText(rightTextID);
        tv_title.setVisibility(View.GONE);
        tv_title_right.setVisibility(View.GONE);
        il_search_bar.setVisibility(View.VISIBLE);


    }

    public void showShareBar(int textID,int rightSrcID) {


        tv_title_left.setVisibility(View.GONE);
        position_image.setVisibility(View.GONE);

        iv_back.setImageResource(R.drawable.return_arrow);
        tv_title.setText(textID);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(rightSrcID);



    }



    public void setRightText(int rightText)
    {
        tv_title_right.setText(rightText);

    }
    public void showActionBar() {
        mSupportActionBar.show();
    }


    public void setBadgeViewHint(int hint) {
        BadgeView badgeView = new BadgeView(activity);
        badgeView = new BadgeView(activity);
        badgeView.setTargetView(iv_home_hint);
        badgeView.setBackgroundResource(R.drawable.home_badgeview);
        badgeView.setBadgeGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        badgeView.setBadgeMargin(0, 0, 0, 10);
        badgeView.setBadgeCount(hint);
        badgeView.setTextSize(10);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                onClickBarleft();
                break;
            case R.id.tv_title_right:
                onClickBarRight();
                break;

            case R.id.il_search_bar:
                onClickBarSearch();
                break;
            case R.id.iv_home_hint:
                onClickBarHint();
                break;
            case R.id.iv_right:
                onClickImageRight();
                break;
            case R.id.tv_title_left:
                onClickImageLeft();
                break;

        }
    }

    public void onClickImageRight() {
        (activity).clickImageRight();
    }

    public void onClickImageLeft() {

        (activity).clickImageLeft();
    }
    /**
     * 点击右边的bar按钮
     */
    public void onClickBarRight() {
        (activity).clickBarRight();
    }

    /**
     * 点击左边的bar按钮
     */
    public void onClickBarleft() {

        Log.e("tag", "onClickBarleft");

        (activity).clickBarleft();
    }

    /**
     * 点击中间搜索的bar按钮
     */
    public void onClickBarSearch() {
        (activity).clickBarSearch();
    }

    /**
     * 点击右边按钮
     *
     */
    public void onClickBarHint() {
        (activity).clickBarHint();

    }


    public void clearSearch()
    {
        et_search.setText("");
    }

}
