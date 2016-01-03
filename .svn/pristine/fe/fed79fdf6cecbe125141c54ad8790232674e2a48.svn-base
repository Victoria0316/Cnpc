package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.FrozenSectionResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/10.
 * 初始化积分页面--P10_7 积分 - 初始化积分页面
 */
public class FrozenSectionRequest extends CnpcHttpJsonRequest<FrozenSectionResponse>{

    private static final String APIPATH = "mobi/integralrecord/indexPoints";

    private String userId;//	用户登录时的ID

    public FrozenSectionRequest(Response.Listener<FrozenSectionResponse> listener, Response.ErrorListener errorListener) {
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
    public Class<FrozenSectionResponse> getResponseClass() {
        return FrozenSectionResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
