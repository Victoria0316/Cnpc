package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.IntegrationResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/10.
 * 初始化冻结款页面--P10_6 冻结款 - 初始化冻结款页面
 */
public class IntegrationRequest extends CnpcHttpJsonRequest<IntegrationResponse>{

    private static final String APIPATH = "mobi/customerrechargelog/indexFrozenMoney";

    private String userId;//	用户登录时的ID

    public IntegrationRequest(Response.Listener<IntegrationResponse> listener, Response.ErrorListener errorListener) {
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
    public Class<IntegrationResponse> getResponseClass() {
        return IntegrationResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
