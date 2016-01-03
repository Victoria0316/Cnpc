package com.bluemobi.cnpc.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.request.PwdModifyRequest;
import com.bluemobi.cnpc.network.response.PwdModifyResponse;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liufy on 2015/7/29.
 * P10_14_1密码修改
 */
@ContentView(R.layout.activity_pwd_modfiy)
public class PwdModifyActivity extends BaseActivity {

    @Bind(R.id.password)
    EditText password;

    @Bind(R.id.newPassword)
    EditText newPassword;

    @Bind(R.id.again_newPassword)
    EditText again_newPassword;

    private String str_password;
    private String str_newPassword;
    private String str_again_newPassword;

    @Override
    protected void initBase() {
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_password_settings, R.drawable.return_arrow, true);
        showLoadingPage(false);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm(){
       Request();
    }

    private void Request() {

        str_password = password.getText().toString();
        str_newPassword = newPassword.getText().toString();
        str_again_newPassword = again_newPassword.getText().toString();

        if (StringUtils.isEmpty(str_password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (str_password.length() < 6 || password.length() > 22) {
            Toast.makeText(this, "密码长度为6-22", Toast.LENGTH_SHORT).show();
            return;
        }
        if (str_password.equals(str_newPassword)) {
            Toast.makeText(this, "密码没有改变", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(str_newPassword)) {
            Toast.makeText(this, "新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (str_newPassword.length() < 6 || password.length() > 22) {
            Toast.makeText(this, "新密码长度为6-22", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!str_newPassword.equals(str_again_newPassword)) {
            Toast.makeText(this, "新密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        PwdModifyRequest request = new PwdModifyRequest
                (
                        new Response.Listener<PwdModifyResponse>() {
                            @Override
                            public void onResponse(PwdModifyResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    Utils.makeToastAndShow(getBaseContext(), "密码修改成功");
                                    finish();
                                } else {
                                    Log.e("error", "error");
                                }
                            }

                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setPassword(str_password);
        request.setNewpassword(str_newPassword);
        WebUtils.doPost(request);
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
}
