package com.bluemobi.cnpc.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by liufy on 2015/6/28.
 */
public class ViewUtils
{
    public static void removeParent(View v)
    {

        ViewParent parent = v.getParent();
        if(parent instanceof ViewGroup){
            ViewGroup group=(ViewGroup) parent;
            group.removeView(v);
        }
    }
}
