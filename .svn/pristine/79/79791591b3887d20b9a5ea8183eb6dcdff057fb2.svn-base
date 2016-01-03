package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.GetValResponse;
import com.bluemobi.cnpc.network.response.RegisterResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/19.
 * P2_1注册_ 获取验证码
 */
public class GetValRequest extends CnpcHttpJsonRequest<GetValResponse> {

    private static final String APIPATH = "mobi/cascode/identifyingCode";
    private  String pass	;//	固定值“n”
    private  String cellphone	;//	用户手机号
    private  String type	;//	固定值 “register”

    public GetValRequest(Response.Listener<GetValResponse> listener,
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
        map.put("cellphone", cellphone);
        map.put("type", "register");
        map.put("pass", "n");
        return map;
    }

    @Override
    public Class getResponseClass() {
        return GetValResponse.class;
    }

    public static String getAPIPATH() {
        return APIPATH;
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
