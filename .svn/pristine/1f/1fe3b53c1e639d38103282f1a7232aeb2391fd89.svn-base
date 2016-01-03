package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.CarAllListResponse;
import com.bluemobi.cnpc.network.response.CityListResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/24.
 * P10_1_1 添加爱车-查询汽车品牌字典
 */
public class CarAllListRequest extends CnpcHttpJsonRequest<CarAllListResponse> {

    private static final String APIPATH = "mobi/carbrandinfocnpc/findAll";

    private String pass		;//是	固定值 “n”

    public CarAllListRequest(Response.Listener<CarAllListResponse> listener,
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
        map.put("pass", "n");
        return map;
    }

    @Override
    public Class<CarAllListResponse> getResponseClass() {
        return CarAllListResponse.class;
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
