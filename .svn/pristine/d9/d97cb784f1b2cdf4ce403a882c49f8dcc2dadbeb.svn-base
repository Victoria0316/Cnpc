package com.bluemobi.cnpc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.util.StringUtils;

/**
 * Created by liufy on 2015/7/22.
 * 两边带文字的TextView9
 */
public class CustomTextGroup extends RelativeLayout implements View.OnClickListener {

    private Drawable background;

    private Bitmap bitmap;

    private String lefttext;

    private String righttext;

    private int leftTextColor;

    private int rightTextColor;

    private float leftTextSize;

    private float rightTextSize;

    private RelativeLayout inflateView;
    private TextView tv_left_text;
    private TextView tv_right_text;
    public CustomTextGroup(Context context) {
        this(context, null);
    }

    public CustomTextGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TextViewGroup, 0, defStyleAttr);
        lefttext = (String)a.getText(R.styleable.TextViewGroup_leftText);
        righttext =(String) a.getText(R.styleable.TextViewGroup_rightText);
        leftTextColor = a.getColor(R.styleable.TextViewGroup_leftTextColor, Color.BLACK);
        rightTextColor = a.getColor(R.styleable.TextViewGroup_rightTextColor, Color.BLACK);
        leftTextSize = a.getDimension(R.styleable.TextViewGroup_leftTextSize, 10);
        rightTextSize = a.getDimension(R.styleable.TextViewGroup_rightTextSize,10);
        rightTextColor = a.getColor(R.styleable.TextViewGroup_rightTextColor, Color.BLACK);
        background = a
                .getDrawable(R.styleable.TextViewGroup_defaultBackground);

        a.recycle();
        initViews(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    public  void initViews(Context context, AttributeSet attrs, int defStyle)
    {
        inflateView = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.custom_textview_group, this);
        if (background!=null)
            inflateView.setBackgroundDrawable(background);
        tv_left_text = (TextView) inflateView.findViewById(R.id.tv_left_text);
        tv_right_text = (TextView) inflateView.findViewById(R.id.tv_right_text);
        if (StringUtils.isNotEmpty(lefttext))
        tv_left_text.setText(lefttext);
        if (StringUtils.isNotEmpty(lefttext))
            tv_right_text.setText(righttext);
        //tv_left_text.setTextSize(leftTextSize);
        tv_left_text.setTextColor(leftTextColor);
        //tv_right_text.setTextSize(rightTextSize);
        tv_right_text.setTextColor(rightTextColor);
    }

    public void setRightText(String strParm)
    {
        if (!TextUtils.isEmpty(strParm))
        tv_right_text.setText(strParm);
    }

    public void setLeftText(String strParm)
    {
        if (!TextUtils.isEmpty(strParm))
            tv_left_text.setText(strParm);
    }

    @Override
    public void onClick(View v) {

    }
}
