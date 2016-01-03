package com.bluemobi.cnpc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 公共的ViewHolder
 * @author liufy
 *
 */
public class ViewHolder
{   
    private final SparseArray<View> mViews;

    private int mPosition;

    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId,
            int position)
    {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     * 
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView,
            ViewGroup parent, int layoutId, int position)
    {
        if (convertView == null)
        {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }
    /**
     * 获取 mConvertView
     * @return
     */
    public View getConvertView()
    {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     * 
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);

            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 
     * 
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }


    /**
     *
     *
     * @param viewId
     * @param textColor
     * @return
     */
    public ViewHolder setTextColor(int viewId, int  textColor)
    {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    /**
     * 为ImageView设置图片
     * 
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
    * 为Button设置背景图片
    * 
    * @param viewId
    * @return
    */
    public ViewHolder setRlBackgroundColor(int viewId, int colorId,
            int hintNum, Context ctx)
    {
        RelativeLayout view = getView(viewId);
        int strokeWidth = 5; 
        int roundRadius = 10; 
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(colorId);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, colorId);
        view.setBackgroundDrawable(gd);

        if (hintNum != 0)
        {

        }

        return this;
    }

    /**
     * 为ImageView设置图片
     * 
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }
    


    /**
     * 为ImageView设置图片
     * 
     * @param viewId
     * @param viewId
     * @return
     */
    public ViewHolder setImageByUrl(int viewId, String url)
    {
       /* ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
                (ImageView) getView(viewId));*/
        return this;
    }

    public int getPosition()
    {
        return mPosition;
    }

}
