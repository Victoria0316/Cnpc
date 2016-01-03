package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.RegisterResponse;
import com.bluemobi.cnpc.network.response.SunyardOilResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/19.
 * P2_1注册_关注油品
 */
public class SunyardOilRequset extends CnpcHttpJsonRequest<SunyardOilResponse> {

    private static final String APIPATH = "mobi/productinfo/getSunyardOilList";

    public SunyardOilRequset(Response.Listener<SunyardOilResponse> listener,
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
    public Class<SunyardOilResponse> getResponseClass() {
        return SunyardOilResponse.class;
    }
}
