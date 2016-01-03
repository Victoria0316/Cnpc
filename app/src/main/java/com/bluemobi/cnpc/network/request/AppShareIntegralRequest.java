package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.AppShareIntegralResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/24.
 *
 *  	P10_14设置  给第一次分享的用户添加100积分
 */
public class AppShareIntegralRequest extends CnpcHttpJsonRequest<AppShareIntegralResponse>{

    private static final String APIPATH = "mobi/integralrecord/saveIntegralRecord";

    private String userId;  //用户id
    private String type;  //固定值“share”

    public AppShareIntegralRequest(Response.Listener<AppShareIntegralResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("type",type);

        return map;
    }

    @Override
    public Class<AppShareIntegralResponse> getResponseClass() {
        return AppShareIntegralResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
