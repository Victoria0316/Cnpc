package com.bluemobi.cnpc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.cnpc.R;


public class CustomSpinnerOption extends CustomSpinnerBase
{

    private TextView tv_spinner_text;

    /**
     * 标签控件
     */
    private TextView tv_label;

    /**
     * 下拉的选择按钮
     */
   // private ImageView iv_option;
    private LinearLayout ll_option;

    private RelativeLayout  ll_wrap_spinner;


    public CustomSpinnerOption(Context context)
    {
        this(context, null);
    }

    public CustomSpinnerOption(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomSpinnerOption(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

    }


    @Override
    public void initViews(Context context, AttributeSet attrs, int defStyle)
    {

        inflate = LayoutInflater.from(context).inflate(
                R.layout.spinner_item, this);

        ll_wrap_spinner = (RelativeLayout) findViewById(R.id.ll_wrap_spinner);
        tv_label = (TextView) findViewById(R.id.tv_spinner);
       // iv_option = (ImageView) findViewById(R.id.iv_option);
        ll_option = (LinearLayout)findViewById(R.id.ll_option);
        tv_spinner_text = (TextView) findViewById(R.id.tv_spinner_text);
        tv_label.setText(lableText);
        tv_spinner_text.setText(hintText);
        tv_spinner_text.setOnClickListener(this);
       // iv_option.setOnClickListener(this);
        ll_option.setOnClickListener(this);

    }
    public void setLabelText(String text)

    {
        tv_label.setText(text);
    }

    public void setSpinnerText(String text)

    {
        tv_spinner_text.setText(text);
    }

    public String getSpinnerText()
    {
        //spinnerText = et_spinner_text.getText().toString().trim();
        return  tv_spinner_text.getText().toString().trim();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.ll_option:
            showDataListDialog(datas, tv_spinner_text, ll_wrap_spinner);
            break;
        default:
            break;
        }

    }

    @Override
    public void onLvItemClick(int postion)
    {
        spinnerText = datas.get(postion);
        tv_spinner_text.setText(spinnerText);
        if(mListener != null){
            mListener.onCustomSpinnerClick(postion);
        }
    }

    public void setDefaultText()
    {
        tv_spinner_text.setText(datas.get(0));
    }


    public void setSpinnerTextSize(int size){
        tv_spinner_text.setTextSize(size);
    }

    public void setLabelTextSize(int size){
        tv_label.setTextSize(size);
    }

    public void setLabelTextColor(int res){
        tv_label.setTextColor(res);
    }

}
