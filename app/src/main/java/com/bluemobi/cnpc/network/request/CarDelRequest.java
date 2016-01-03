package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.CarAllListResponse;
import com.bluemobi.cnpc.network.response.CarDelResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/24.
 * P10_1 车辆列表-根据id删除爱车
 */
public class CarDelRequest extends CnpcHttpJsonRequest<CarDelResponse> {

    private static final String APIPATH = "mobi/carinfo/del";
    private String id	;//	主键id
    private String userId	;//	用户id

    public CarDelRequest(Response.Listener<CarDelResponse> listener,
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
        map.put("id", id);
        map.put("userId", userId);
        return map;
    }

    @Override
    public Class<CarDelResponse> getResponseClass() {
        return CarDelResponse.class;
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
