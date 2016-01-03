package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.request.ForgetPasswordRequest;
import com.bluemobi.cnpc.network.request.SmsCodeValidateRequest;
import com.bluemobi.cnpc.network.response.ForgetPasswordResponse;
import com.bluemobi.cnpc.network.response.SmsCodeValidateResponse;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/17.
 *
 * p2-3 忘记密码
 */

@ContentView(R.layout.activity_forget_password)
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.next_button)
    Button next_button;

    @Bind(R.id.validationCode)
    TextView validationCode;

    @Bind(R.id.edt_verification)
    EditText edt_verification;

    @Bind(R.id.phone)
    TextView phone;

    private String currentPhone;
    private TimeCount time;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(ForgetPasswordActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.title_password, R.drawable.return_arrow, true);
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        time = new TimeCount(120000, 1000);//构造CountDownTimer对象

        next_button.setOnClickListener(this);
        validationCode.setOnClickListener(this);

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.next_button:
                doValidate();
                break;
            case R.id.validationCode:
                getValFromServer();
                break;
        }
    }

    private void doValidate() {
        if(StringUtils.isEmpty(edt_verification.getText().toString())){
            Utils.makeToastAndShow(getBaseContext(), "请输入验证码");
            return;
        }
        SmsCodeValidateRequest request = new SmsCodeValidateRequest(new Response.Listener<SmsCodeValidateResponse>() {
            @Override
            public void onResponse(SmsCodeValidateResponse response) {
                if(response != null && response.getStatus() == 0){
                    Intent intent = new Intent();
                    intent.putExtra("cellphone", currentPhone);
                    intent.setClass(mContext, ForgetPasswordNextActivity.class);
                    startActivity(intent);
                }else{
                    Utils.makeToastAndShow(getBaseContext(), "验证码验证失败");
                }
            }
        }, this);
        request.setCellphone(currentPhone);
        request.setValidationCode(edt_verification.getText().toString());
        WebUtils.doPost(request);
    }

    public void getValFromServer() {
        currentPhone = phone.getText().toString();
        if (StringUtils.isEmpty(currentPhone)) {
            Utils.makeToastAndShow(getBaseContext(), "手机号不能为空");
            return;
        }
        if (!Utils.checkPhoneNum(currentPhone)) {
            Utils.makeToastAndShow(getBaseContext(), "手机号不合法");
            return;
        }
        ForgetPasswordRequest request = new ForgetPasswordRequest
                (
                        new Response.Listener<ForgetPasswordResponse>() {
                            @Override
                            public void onResponse(ForgetPasswordResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    Utils.makeToastAndShow(getBaseContext(), "验证码已发送");
                                    time.start();
                                } else {
                                    Utils.makeToastAndShow(getBaseContext(), "验证码获取失败，请重新获取");
                                }
                            }

                        }, this);
        request.setCellphone(currentPhone);
        WebUtils.doPost(request);

    }

    private class TimeCount extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

            validationCode.setClickable(false);
            validationCode.setText(millisUntilFinished /1000+"秒");
        }

        @Override
        public void onFinish() {//计时完毕时触发
            validationCode.setText("重新验证");
            validationCode.setClickable(true);
        }
    }
}
