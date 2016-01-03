package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.GetValResponse;
import com.bluemobi.cnpc.network.response.LoginResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/19.
 * P2_1注册_ 获取验证码
 */
public class LoginRequest extends CnpcHttpJsonRequest<LoginResponse> {

    private static final String APIPATH = "mobi/adminuser/login";
    private String username ;//	string 	是 	用户名（手机号）
    private String password ;//	string 	是 	密码
    private String pass ;//	string 	是 	固定值“n”

    public LoginRequest(Response.Listener<LoginResponse> listener,
                        Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        map.put("pass", "n");
        return map;
    }

    @Override
    public Class getResponseClass() {
        return LoginResponse.class;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
