package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.CarDelResponse;
import com.bluemobi.cnpc.network.response.CarDetailInfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/24.
 * P10_1_2 车辆信息-查询爱车信息详情
 * 查询爱车信息详情
 */
public class CarFindByIdRequest extends CnpcHttpJsonRequest<CarDetailInfoResponse> {

    private static final String APIPATH = "mobi/carinfo/findByID";
    private String id	;//	主键id

    public CarFindByIdRequest(Response.Listener<CarDetailInfoResponse> listener,
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
        return map;
    }

    @Override
    public Class<CarDetailInfoResponse> getResponseClass() {
        return CarDetailInfoResponse.class;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
