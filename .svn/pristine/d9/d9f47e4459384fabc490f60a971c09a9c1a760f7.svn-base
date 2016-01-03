package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.SmsCodeValidateResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/20.
 *
 * 验证码验证
 */
public class SmsCodeValidateRequest extends CnpcHttpJsonRequest<SmsCodeValidateResponse>{

    private static final String APIPATH = "mobi/cascode/checkIdentifyingCode";

    private String pass ;//	固定值“n”
    private String cellphone ; //用户手机号
    private String type ;//	固定值 “forgotpassword”
    private String validationCode ;//用户输入的验证码

    public SmsCodeValidateRequest(Response.Listener<SmsCodeValidateResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "forgotpassword");
        map.put("cellphone", cellphone);
        map.put("pass", "n");
        map.put("validationCode", validationCode);
        return map;
    }

    @Override
    public Class<SmsCodeValidateResponse> getResponseClass() {
        return SmsCodeValidateResponse.class;
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

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }
}
