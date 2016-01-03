package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.ForgetPasswordResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/20.
 * p2-3 忘记密码_获取验证码
 */
public class ForgetPasswordRequest extends CnpcHttpJsonRequest<ForgetPasswordResponse> {

    private static final String APIPATH = "mobi/cascode/identifyingCode";
    private String pass;//	固定值“n”
    private String cellphone;//	用户手机号
    private String type;//	固定值 “forgotpassword”

    public ForgetPasswordRequest(Response.Listener<ForgetPasswordResponse> listener,
                                 Response.ErrorListener errorListener) {
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
        map.put("type", "forgotpassword");
        map.put("pass", "n");
        return map;
    }

    @Override
    public Class<ForgetPasswordResponse> getResponseClass() {
        return ForgetPasswordResponse.class;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
