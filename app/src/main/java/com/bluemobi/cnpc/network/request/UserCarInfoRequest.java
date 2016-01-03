package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.CityListResponse;
import com.bluemobi.cnpc.network.response.UserCarInfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/30.
 * P10_1 车辆列表-分页查询个人爱车列表
 */
public class UserCarInfoRequest extends CnpcHttpJsonRequest<UserCarInfoResponse> {

    private static final String APIPATH = "mobi/carinfo/findByPage";

    private String userId;

    public UserCarInfoRequest(Response.Listener<UserCarInfoResponse> listener,
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
        map.put("userId", userId);
        return map;
    }

    @Override
    public Class<UserCarInfoResponse> getResponseClass() {
        return UserCarInfoResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
