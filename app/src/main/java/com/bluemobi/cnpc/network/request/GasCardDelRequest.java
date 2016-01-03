package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.GasCardDelResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/1.
 */
public class GasCardDelRequest extends CnpcHttpJsonRequest<GasCardDelResponse>{

    private static final String APIPATH = "mobi/fuelcardinfo/del";


    private String id;

    public GasCardDelRequest(Response.Listener<GasCardDelResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id",id);

        return map;
    }

    @Override
    public Class<GasCardDelResponse> getResponseClass() {
        return GasCardDelResponse.class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
