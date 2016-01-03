package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.GasCardDefaultResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/1.
 */
public class GasCardDefaultRequest extends CnpcHttpJsonRequest<GasCardDefaultResponse>{

    private static final String APIPATH = "mobi/fuelcardinfo/setDefault";

    private String id ; //加油卡主键id
    private String userId ; //用户id

    public GasCardDefaultRequest(Response.Listener<GasCardDefaultResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("id", id);
        return map;
    }

    @Override
    public Class<GasCardDefaultResponse> getResponseClass() {
        return GasCardDefaultResponse.class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
