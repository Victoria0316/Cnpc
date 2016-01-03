package com.bluemobi.cnpc.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bluemobi.cnpc.R;

public class ViewPagerItemView extends LinearLayout
{

    public ViewPagerItemView(Context context)
    {
        super(context);
        initView();
    }

    public ViewPagerItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    public ViewPagerItemView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView()
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.lv_lbs_gas_station, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        view.setLayoutParams(lp);

        addView(view);
    }

}
