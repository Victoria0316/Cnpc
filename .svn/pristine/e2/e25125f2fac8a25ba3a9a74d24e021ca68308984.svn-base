package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.ShakeInitializeResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/10/10.
 *
 * 获得用户当前可用积分--P10_11 摇积分 - 摇积分 获得用户当前可用积分
 */
public class ShakeInitializeRequest extends CnpcHttpJsonRequest<ShakeInitializeResponse> {

    private static final String APIPATH = "mobi/customerinfo/getPoints";
    private String userId;  //用户id

    public ShakeInitializeRequest(Response.Listener<ShakeInitializeResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", CnpcApplication.getInstance().getmData().userId);
        return map;
    }

    @Override
    public Class<ShakeInitializeResponse> getResponseClass() {
        return ShakeInitializeResponse.class;
    }
}
