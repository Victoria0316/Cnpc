package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.ForgetPasswordNextResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/20.
 *
 * p2-3 忘记密码2 修改密码
 */
public class ForgetPasswordNextRequest extends CnpcHttpJsonRequest<ForgetPasswordNextResponse> {

    private static final String APIPATH = "mobi/adminuser/forgotpassword";

    private String cellphone;//用户输入的手机号
    private String password;//	用户输入的新密码

    public ForgetPasswordNextRequest(Response.Listener<ForgetPasswordNextResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("cellphone", cellphone);
        map.put("password", password);
        return map;
    }

    @Override
    public Class<ForgetPasswordNextResponse> getResponseClass() {
        return ForgetPasswordNextResponse.class;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
