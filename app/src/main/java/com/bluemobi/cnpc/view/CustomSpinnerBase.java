package com.bluemobi.cnpc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author liufy
 *
 */
public abstract class CustomSpinnerBase extends RelativeLayout implements
        View.OnClickListener, OnItemClickListener
{

    public View inflate;

    public PopupWindow pw;

    public Context mContext = null;

    /**
     * 下拉数据
     */
    public List<String> datas;

    /**
     * 下拉数据value
     */
    public List<String> values;


    /**
     * spinner 上显示的数据
     */
    public String spinnerText;

    /**
     * 输入项 提示
     */
    public CharSequence hintText;

    /**
     *  输入项标签
     */
    public CharSequence lableText;

    /**
     * ViewGroup 背景
     */
    public Drawable background;

    private int leftMargin;


    public OnCustomSpinnerItemClickListener mListener;

    public interface OnCustomSpinnerItemClickListener
    {
        public void onCustomSpinnerClick(int option);
    }

    public void setOnCustomClickListener(
            OnCustomSpinnerItemClickListener listener)
    {
        mListener = listener;
    }

    public CustomSpinnerBase(Context context)
    {
        this(context, null);
    }

    public CustomSpinnerBase(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomSpinnerBase(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SpinnerText, 0, defStyle);
        hintText = a.getText(R.styleable.SpinnerText_android_hint);
        lableText = a.getText(R.styleable.SpinnerText_android_text);
        background = a.getDrawable(R.styleable.SpinnerText_android_background);

        a.recycle();
        initViews(context, attrs, defStyle);
    }

    /**
     * 初始化 View
     * @param context  上下文
     * @param attrs    属性 
     * @param defStyle 默认
     */
    public abstract void initViews(Context context, AttributeSet attrs,
            int defStyle);

    /**
     *  下拉选择ListView 单击事件
     * @param postion
     */
    public abstract void onLvItemClick(int postion);

    /**
     * 显示下拉数据
     * @param
     */
    public void showDataListDialog(List<String> datas, View view,View arrow)
    {
        ListView lv = initListView();
        initPopWindow(datas, view, lv, arrow);

    }


    /**
     * 初始化下拉ListView
     * @return
     */
    private ListView initListView()
    {
        ListView lv = new ListView(mContext);
        lv.setBackgroundResource(R.drawable.listview_background);
        lv.setVerticalScrollBarEnabled(false);
        lv.setDivider(null);
        lv.setDividerHeight(0);
        lv.setCacheColorHint(000000);
        lv.setOnItemClickListener(this);
        if (datas != null)
        {
            MySpinnerApapter mAdapter = new MySpinnerApapter(datas);
            lv.setAdapter(mAdapter);
        }

        return lv;
    }

    /**
     * 初始化POPwindow
     * @param datas  下拉选择数据
     * @param view   哪个View的下面
     * @param lv     ListView
     * @param viewWidth 宽度
     */
    public void initPopWindow(List<String> datas, View view,
            ListView lv, int viewWidth)
    {

        pw = new PopupWindow(lv, viewWidth, Utils.dip2px(mContext, 100));
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.showAsDropDown(view, 0, 0);
    }

    public void initPopWindow(List<String> datas, View editView,
             ListView lv,View arrow)
    {
        int width = editView.getWidth() + arrow.getWidth();
        initPopWindow(datas, editView, lv, width);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int postion,
            long id)
    {
        onLvItemClick(postion);
        if (pw != null && pw.isShowing())
        {
            pw.dismiss();
        }
    }

    

    public String getSpinnerText()
    {
        return spinnerText != null ? spinnerText : "";
    }



    public void setDatas(List<String> datas)
    {
        if (datas == null)
        {
            datas = new ArrayList<String>();
        }
        this.datas = datas;
    }

    /**
     * 下拉选择框适配器
     * @author liufy
     *
     */
    public class MySpinnerApapter extends BaseAdapter
    {
        private List<String> datas = new ArrayList<String>();

        public MySpinnerApapter(List<String> datas)
        {
            this.datas = datas;
        }

        @Override
        public int getCount()
        {
            return datas.size();
        }

        @Override
        public View getView(final int position, View convertView,
                ViewGroup parent)
        {
            View view = null;
            if (convertView == null)
            {
                view = View.inflate(mContext, R.layout.spinner_text_item,
                        null);
            }
            else
            {
                view = convertView;
            }
            TextView tv_number_item = (TextView) view
                    .findViewById(R.id.tv_number_item);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv_number_item.getLayoutParams();
            params.leftMargin = leftMargin;
            tv_number_item.setLayoutParams(params);
            tv_number_item.setText(datas.get(position));
            return view;
        }

        @Override
        public Object getItem(int position)
        {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
