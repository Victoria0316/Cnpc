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
import com.bluemobi.cnpc.network.request.ForgetPasswordNextRequest;
import com.bluemobi.cnpc.network.response.ForgetPasswordNextResponse;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/17.
 *
 * p2-3 忘记密码2
 */

@ContentView(R.layout.activity_forget_password_next)
public class ForgetPasswordNextActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.confirm_button)
    Button confirm_button;

    @Bind(R.id.new_password)
    EditText new_password;

    @Bind(R.id.again_new_password)
    EditText again_new_password;

    private String password;
    private String again_password;
    private String cellphone;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(ForgetPasswordNextActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.title_password, R.drawable.return_arrow, true);
        showLoadingPage(false);

        cellphone = getIntent().getStringExtra("cellphone");

    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        confirm_button.setOnClickListener(this);

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
            case R.id.confirm_button:
                Request();
                break;
        }
    }

    private void Request() {
        password = new_password.getText().toString();
        again_password = again_new_password.getText().toString();

        if (StringUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6 || password.length() > 22) {
            Toast.makeText(this, "密码长度为6-22", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(again_password)) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        ForgetPasswordNextRequest request = new ForgetPasswordNextRequest
                (
                        new Response.Listener<ForgetPasswordNextResponse>() {
                            @Override
                            public void onResponse(ForgetPasswordNextResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    Utils.makeToastAndShow(getBaseContext(), "密码修改成功");
                                    finish();
                                } else {
                                    Log.e("error", "error");
                                }
                            }

                        }, this);
        request.setCellphone(cellphone);
        request.setPassword(password);
        WebUtils.doPost(request);
    }
}
