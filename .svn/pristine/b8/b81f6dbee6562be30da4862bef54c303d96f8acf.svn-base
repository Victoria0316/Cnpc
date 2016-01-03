package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.RegistrationAgreementResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/20.
 *
 * p2-2 注册协议
 */
public class RegistrationAgreementRequest extends CnpcHttpJsonRequest<RegistrationAgreementResponse> {

    private static final String APIPATH = "mobi/systemintroduces/getSystemIntroducesInfo";

    private String pass; //固定值“n”
    private String articleid;//注册协议 固定值“1”，关于我们 固定值“4”


    public RegistrationAgreementRequest(Response.Listener<RegistrationAgreementResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("pass", "n");
        map.put("articleid",articleid);
        return map;
    }

    @Override
    public Class<RegistrationAgreementResponse> getResponseClass() {
        return RegistrationAgreementResponse.class;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }
}
