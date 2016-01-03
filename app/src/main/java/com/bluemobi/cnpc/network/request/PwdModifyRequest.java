package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.PwdModifyResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/20.
 *
 * P10_14_1密码修改
 */
public class PwdModifyRequest extends CnpcHttpJsonRequest<PwdModifyResponse>{

    private static final String APIPATH = "mobi/adminuser/updatePassword";

    private String userId ;//用户登录时返回的userId对应的值
    private String password ; //用户输入的原密码
    private String newpassword  ; //用户输入的新密码

    public PwdModifyRequest(Response.Listener<PwdModifyResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("password", password);
        map.put("newpassword", newpassword);
        return map;
    }

    @Override
    public Class<PwdModifyResponse> getResponseClass() {
        return PwdModifyResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

}
