package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.AddGasFillingCardResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/1.
 *
 * P10_8_2_新增加油卡-查询加油站列表
 */
public class AddGasFillingCardRequest extends CnpcHttpJsonRequest<AddGasFillingCardResponse>{

    private static final String APIPATH = "mobi/gasstationinfo/findStations";

    public AddGasFillingCardRequest( Response.Listener<AddGasFillingCardResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        return map;
    }

    @Override
    public Class<AddGasFillingCardResponse> getResponseClass() {
        return AddGasFillingCardResponse.class;
    }
}
