package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.CnpcHttpRequest;
import com.bluemobi.cnpc.network.response.AddGasCardResponse;
import com.bluemobi.cnpc.network.response.WXOauthResponse;
import com.bluemobi.cnpc.util.WxPayUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/1.
 * <p/>
 * P10_8_2_新增加油卡-新增加油卡
 */
public class WXOauth2Request extends CnpcHttpRequest<WXOauthResponse> {

    private static final String APIPATH = "sns/oauth2/access_token";

    private String code;  //用户id

    public WXOauth2Request(Response.Listener<WXOauthResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, "https://api.weixin.qq.com/" , APIPATH, listener, errorListener);
    }




    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", WxPayUtils.WX_APP_ID);
        map.put("secret", WxPayUtils.WX_APP_SECRER);
        map.put("code", code);
        map.put("grant_type", "authorization_code");
        return map;
    }

    @Override
    public Class<WXOauthResponse> getResponseClass() {
        return WXOauthResponse.class;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
