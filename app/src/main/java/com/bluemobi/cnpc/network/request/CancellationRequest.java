package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.CancellationResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/21.
 *
 * P10_14 设置 - 注销登录
 */
public class CancellationRequest extends CnpcHttpJsonRequest<CancellationResponse>{

    private static final String APIPATH = "mobi/adminuser/logout";

    private String userId ; // 	用户登录时的ID

    public CancellationRequest(Response.Listener<CancellationResponse> listener, Response.ErrorListener errorListener) {
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
        return map;
    }

    @Override
    public Class<CancellationResponse> getResponseClass() {
        return CancellationResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
