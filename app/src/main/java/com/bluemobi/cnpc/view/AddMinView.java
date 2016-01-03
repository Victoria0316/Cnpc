package com.bluemobi.cnpc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.util.DensityUtil;

/**
 * Created by liufy on 2015/7/22.
 * 能够加减的自定位View
 */
public class AddMinView extends View implements  View.OnClickListener{

    private Bitmap min;
    private Bitmap add;
    private Bitmap middle;
    private Paint mPaint;
    private int downX;
    private int downY;
    private CharSequence mText;
    private StaticLayout mTextLayout;
    private TextPaint mTextPaint;
    private Point mTextOrigin;

    public AddMinView(Context context) {
        this(context, null);
    }

    public AddMinView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddMinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {




        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextOrigin = new Point(0, 0);
        min = BitmapFactory.decodeResource(getResources(), R.drawable.min);
        add = BitmapFactory.decodeResource(getResources(), R.drawable.add);
        middle = BitmapFactory.decodeResource(getResources(), R.drawable.middle);
        mTextPaint.setTextSize(DensityUtil.px2sp(context,100));
        setOnClickListener(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(min.getWidth() + add.getWidth() + middle.getWidth(), min.getHeight());
    }

    private void updateContentBounds()
    {
        if (mText == null) {
            mText = "";
        }
        mText = "5000";
        float textWidth = mTextPaint.measureText(mText, 0, mText.length());
        mTextLayout = new StaticLayout(mText, mTextPaint, (int)textWidth,
                Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);

        int left = middle.getWidth();
        int top = 0;

        if (mTextLayout != null) {
            top = (getHeight() - mTextLayout.getHeight()) / 2;
            mTextOrigin.set(left, top);
        }
    }

    public void setText(int resId) {
        CharSequence text = getResources().getText(resId);
        setText(text);
    }

    public void setText(CharSequence text) {
        if (!TextUtils.equals(mText, text)) {
            mText = text;
            updateContentBounds();
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            updateContentBounds();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(min,0,0,mPaint);
        canvas.drawBitmap(middle,min.getWidth(),0,mPaint);
        if (mTextLayout != null) {
            canvas.save();
            canvas.translate(mTextOrigin.x, mTextOrigin.y);

            mTextLayout.draw(canvas);

            canvas.restore();
        }
        canvas.drawBitmap(add,min.getWidth()+middle.getWidth(),0,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v)
    {
        int minWidth = min.getWidth();
        int middleWidth = middle.getWidth();
        int addWidth = add.getWidth();
        if (downX>0&&downX<minWidth)
        {
            if (mListener!=null)
            {
                mListener.addView();
                Log.e("TAG", "MIN");
            }
            Log.e("TAG", "MIN---");
        }
        if (minWidth+middleWidth<downX&&downX<minWidth+middleWidth+addWidth)
        {
            if (mListener!=null)
            {
                mListener.minView();
                Log.e("TAG", "ADD");
            }
            Log.e("TAG", "ADD---");

        }
        else
        {

        }
    }


    public interface AddMinViewOnClickListener
    {
        public abstract void addView();

        public abstract void minView();

    }

    public AddMinViewOnClickListener mListener;

    public void setOnAddMinViewOnClickListener(AddMinViewOnClickListener listener)
    {
        this.mListener = listener;
    }
}
