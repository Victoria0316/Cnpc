package com.bluemobi.cnpc.app;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.CnpcHttpBase;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.WebUtils;


public class VolleyManager
{
    private static RequestQueue mRequestQueue;


    /**
     * 初始化队列及图片加载器
     * 
     * @param context
     */
    static void init(Context context)
    {
         mRequestQueue = Volley.newRequestQueue(context);
    }



    /**
     * 添加request to queue
     * 
     * @param request
     */
    public static <T> void addToQueue(Request<T> request) {
        if (request instanceof CnpcHttpBase) {
            CnpcHttpBase<T> base = (CnpcHttpBase<T>) request;

            Logger.d("VolleyManager",
                    "addToQueue-->" + base.requestUrl() + "?" + WebUtils.buildQuery(base.GetParameters()));

        }
        mRequestQueue.add(request);
    }

    /**
     * 添加request并设置tag
     * 
     * @param request
     * @param tag
     */
    public static <T> void addToQueue(Request<T> request, String tag) {
        if (request instanceof CnpcHttpBase) {
            CnpcHttpBase<T> base = (CnpcHttpBase<T>) request;
            Logger.d("VolleyManager",
                    "addToQueue-->" + base.requestUrl() + "?" + WebUtils.buildQuery(base.GetParameters()));
        }
        request.setTag(StringUtils.isEmpty(tag) ? CnpcHttpBase.class.getSimpleName() : tag);
        mRequestQueue.add(request);
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    /**
     * 取消指定tag 的请求
     * 
     * @param tag
     */
    public static void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

}
