package com.bluemobi.cnpc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;

/**
 * 97#
 * Created by wangzhijun on 2015/7/27.
 */
public class GasText extends RelativeLayout{
    private TextView val;
    private TextView label;
    private Context mContext;
    private String text;
    private int marginTop;
    private int style;
    public GasText(Context context) {
        super(context);
    }

    public GasText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(attrs);
    }

    public GasText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs){
        if (attrs != null) {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                    R.styleable.GasText);
            String text = typedArray.getString(R.styleable.GasText_text);
            this.text = text;
            this.style = typedArray.getResourceId(R.styleable.GasText_gasStyle, -1);
//            getInteger(R.styleable.GasText_gasStyle, -1);
            typedArray.recycle();
        }
//        marginTop = Utils.dip2px(mContext, mContext.getResources()
//                .getDimension(R.dimen.common_margin_little));
        marginTop = Utils.dip2px(mContext, 5);
        val = new TextView(mContext);
        RelativeLayout.LayoutParams valParam = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT
        );
        valParam.topMargin = marginTop;
        val.setLayoutParams(valParam);
        val.setId(10035);
        if(StringUtils.isNotEmpty(this.text)){
            val.setText(this.text);
        }
        this.addView(val);

        label = new TextView(mContext);
        label.setText("#");
        RelativeLayout.LayoutParams labelParam = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT
        );
        labelParam.addRule(RelativeLayout.RIGHT_OF, val.getId());
        label.setLayoutParams(labelParam);
        this.addView(label);
        if(style != -1){
            label.setTextAppearance(mContext, style);
            val.setTextAppearance(mContext, style);
        }
    }

    public void setTextSize(float size)
    {
        label.setTextSize(size);
        val.setTextSize(size);
    }

    public void setText(String text){
        val.setText(text);
    }
}
