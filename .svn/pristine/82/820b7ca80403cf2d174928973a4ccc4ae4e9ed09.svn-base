package com.bluemobi.cnpc.activity;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.db.entity.CarAllListDetail;
import com.bluemobi.cnpc.db.entity.CityListDetail;
import com.bluemobi.cnpc.db.entity.DbVersion;
import com.bluemobi.cnpc.db.entity.HotCityListDetail;
import com.bluemobi.cnpc.network.request.CarAllListRequest;
import com.bluemobi.cnpc.network.request.CityListRequest;
import com.bluemobi.cnpc.network.request.LoginRequest;
import com.bluemobi.cnpc.network.request.RegisterRequest;
import com.bluemobi.cnpc.network.response.CarAllListResponse;
import com.bluemobi.cnpc.network.response.CityListResponse;
import com.bluemobi.cnpc.network.response.LoginResponse;
import com.bluemobi.cnpc.network.response.RegisterResponse;
import com.bluemobi.cnpc.util.LocationToggle;
import com.bluemobi.cnpc.util.NetworkManager;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.ThreadManager;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/16.
 * <p/>
 * p2 登录
 */

@ContentView(R.layout.activity_login)
@PageName(R.string.Login)
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.forget_password)
    TextView forget_password;

    @Bind(R.id.registered)
    TextView registered;

    @Bind(R.id.blueButton)
    Button blueButton;

    @Bind(R.id.et_phoneNum)
    EditText et_phoneNum;

    @Bind(R.id.et_pwd)
    EditText et_pwd;


    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(LoginActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.Login, R.drawable.return_arrow, false);
        showLoadingPage(false);

    }


    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        forget_password.setOnClickListener(this);
        registered.setOnClickListener(this);
        blueButton.setOnClickListener(this);

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
        switch (v.getId()) {
            case R.id.forget_password:
                Utils.moveTo(this, ForgetPasswordActivity.class);
                break;
            case R.id.registered:
                Utils.moveTo(this, RegisteredActivity.class);
                break;
            case R.id.blueButton:
                if (checkUserPas()) {
                    connectToServerLogin();
                }

                break;
        }
    }

    private void connectToServerLogin() {

        Utils.showProgressDialog(mContext);
        LoginRequest request = new LoginRequest
                (
                        new Response.Listener<LoginResponse>() {
                            @Override
                            public void onResponse(LoginResponse response) {
                                Utils.closeDialog();
                                finish();
                                if (response != null && response.getStatus() == 0) {// success
                                    LoginResponse.LoginData data = response.data;
                                    CnpcApplication.getInstance().setmData(data);
                                    try {
                                        PreferencesService.getInstance(mContext).saveBool("loginSucceed", true);

                                        PreferencesService.getInstance(mContext).save("userId", data.userId);
                                        PreferencesService.getInstance(mContext).save("ssid", data.ssid);
                                        PreferencesService.getInstance(mContext).save("pwd", et_pwd.getText().toString().trim());
                                        PreferencesService.getInstance(mContext).save("phoneNum", et_phoneNum.getText().toString().trim());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    Utils.moveTo(mContext, HomeActivity.class);
                                } else {
                                    Utils.makeToastAndShow(getBaseContext(), response.getMsg());
                                }
                            }
                        }, this);
        request.setPassword(et_pwd.getText().toString().trim());
        request.setUsername(et_phoneNum.getText().toString().trim());
        WebUtils.doPost(request);
    }

    private boolean checkUserPas() {

        /*et_phoneNum.setText("13610813125");
        et_pwd.setText("123456");*/

       /* et_phoneNum.setText("13804040812");
        et_pwd.setText("123456");*/

        /*et_phoneNum.setText("13478850582");
        et_pwd.setText("q12345678");*/

        if (StringUtils.isEmpty(et_phoneNum.getText().toString())) {
            Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(et_pwd.getText().toString())) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.checkPhoneNum(et_phoneNum.getText().toString())) {
            Toast.makeText(LoginActivity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (et_pwd.getText().toString().length() < 6 || et_pwd.getText().toString().length() > 16) {
            Toast.makeText(this, "密码长度为6-16", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
