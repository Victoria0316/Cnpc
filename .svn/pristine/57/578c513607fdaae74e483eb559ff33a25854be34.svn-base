package com.bluemobi.cnpc.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.bluemobi.cnpc.R;

/**
 * Created by liufy on 2015/7/17.
 */
public class CheckOilView extends View implements View.OnClickListener {

    private Context context = null;

    private Drawable background;

    private Bitmap bitmap;

    private String lefttext;

    private String righttext;

    private Paint paint;

    private boolean isClick = false;

    public CheckOilView(Context context) {
        this(context, null);
    }

    public CheckOilView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckOilView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.OilCheck, 0, defStyleAttr);
        lefttext = (String)a.getText(R.styleable.OilCheck_lefttext);
        righttext =(String) a.getText(R.styleable.OilCheck_righttext);
        background = a
                .getDrawable(R.styleable.OilCheck_defaultbackground);
        drawableToBitamp(background);
        a.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.common_black_light));
        paint.setTextSize(40);
        setOnClickListener(this);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (isClick) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.oil_type_select), 0, 0, paint);
        } else {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }

        Rect bounds = new Rect();
        paint.getTextBounds(lefttext, 0, lefttext.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width())/6;
        int y = (bitmap.getHeight() + bounds.height())/2;

        int x1 = (bitmap.getWidth() - bounds.width())/7*4;
        int y1 = (bitmap.getHeight() + bounds.height())/2;

        int x2 = (bitmap.getWidth() - bounds.width())/6*2;
        int y2 = (bitmap.getHeight() + bounds.height())/3;

        canvas.drawText(lefttext, x , y, paint);
        canvas.drawText(righttext, x1 , y1, paint);
        canvas.drawText("#", x2 , y2, paint);
    }

    private void drawableToBitamp(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        bitmap = bd.getBitmap();
    }

    private boolean isChecked()
    {
        return isClick;
    }

    @Override
    public void onClick(View v) {

        isClick = !isClick;
        invalidate();
    }
}
