package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.ListGasStationDetailResponse;
import com.bluemobi.cnpc.network.response.NearNotBdGasStationResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/24.
 * P6_1加油站详情-查询加油站详情
 */
public class ListGasStationDetailRequest extends CnpcHttpJsonRequest<ListGasStationDetailResponse> {

    private static final String APIPATH = "mobi/gasstationinfo/findByID";

    private String id	;//string	是	加油站主键id

    private String userId;

    public ListGasStationDetailRequest(Response.Listener<ListGasStationDetailResponse> listener,
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
    public Class<ListGasStationDetailResponse> getResponseClass() {
        return ListGasStationDetailResponse.class;
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
