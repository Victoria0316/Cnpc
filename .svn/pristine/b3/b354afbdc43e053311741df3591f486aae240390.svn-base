package com.bluemobi.cnpc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.cnpc.R;


public class CustomSpinnerOptionHome extends CustomSpinnerBase {

    private TextView tv_spinner_text;


    /**
     * 下拉的选择按钮
     */
    private ImageView iv_option;

    private RelativeLayout ll_wrap_spinner;

    private boolean isEnabled = true;


    public CustomSpinnerOptionHome(Context context) {
        this(context, null);
    }

    public CustomSpinnerOptionHome(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSpinnerOptionHome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    public void initViews(Context context, AttributeSet attrs, int defStyle) {

        inflate = LayoutInflater.from(context).inflate(
                R.layout.spinner_item_home, this);

        ll_wrap_spinner = (RelativeLayout) findViewById(R.id.ll_wrap_spinner);
        iv_option = (ImageView) findViewById(R.id.iv_option);
        tv_spinner_text = (TextView) findViewById(R.id.tv_spinner_text);
        tv_spinner_text.setText(hintText);
        tv_spinner_text.setOnClickListener(this);
        iv_option.setOnClickListener(this);

    }

    public void setSpinnerText(String text)

    {
        tv_spinner_text.setText(text);
    }

    public String getSpinnerText() {
        //spinnerText = et_spinner_text.getText().toString().trim();
        return tv_spinner_text.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_option:
                if(!isEnabled){
                    return;
                }
                showDataListDialog(datas, tv_spinner_text, iv_option);
                break;
            default:
                break;
        }

    }

    @Override
    public void onLvItemClick(int postion) {

        spinnerText = datas.get(postion);
        tv_spinner_text.setText(spinnerText);
        if (mListener != null) {
            mListener.onCustomSpinnerClick(postion);
        }
    }

    public void setDefaultText() {
        tv_spinner_text.setText(datas.get(1));
    }

    public void setSpinnerTextSize(int size) {
        tv_spinner_text.setTextSize(size);
    }

    public void setEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
    }

}
