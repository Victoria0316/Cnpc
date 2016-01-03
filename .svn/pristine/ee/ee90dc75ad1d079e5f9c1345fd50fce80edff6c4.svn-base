package com.bluemobi.cnpc.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.bluemobi.cnpc.base.utils.Logger;

/**
 * Created by wangzhijun on 2015/7/22.
 */
public class SideMonthBar extends View {
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
    public static String[] b = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "月"};

    public static String[] br = {"a", "b", "c", "d", "e", "f", "g", "h", "i",
            "j", "k", "l"};
    private int choose = -1;// 选中
    private Paint paint = new Paint();

    private TextView mTextDialog;

    /**
     * 为SideBar设置显示字母的TextView
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }


    public SideMonthBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideMonthBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideMonthBar(Context context) {
        super(context);
    }

    public void getVal(String dis){

    }
    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / b.length;// 获取每一个字母的高度

        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.rgb(153, 153, 153));
            // paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20);
            // 选中的状态
            if (i == choose) {
                paint.setColor(Color.parseColor("#129cd0"));//蓝
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        if(choose == b.length-1){
            return true;
        }
        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null && c!= b.length-1) {
                            listener.onTouchingLetterChanged(br[c], b[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }


                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public String getDis(int sectionForPosition) {
        for(int i=0; i<br.length; i++){
            if(br[i].charAt(0) == sectionForPosition){
                return b[i];
            }
        }
        return "";
    }

    public void setSelection(int selection){
        for(int i=0; i<br.length; i++){
            if(Integer.parseInt(b[i]) == selection){
                choose = i;
                invalidate();
            }
        }

    }
}
