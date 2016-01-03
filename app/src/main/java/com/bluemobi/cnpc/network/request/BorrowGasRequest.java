package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.BorrowGasResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/30.
 * P10_3预购油/P10_4借用油-初始化油品订单页面
 */
public class BorrowGasRequest extends CnpcHttpJsonRequest<BorrowGasResponse>{

    private static final String APIPATH = "mobi/oilfuturesorder/indexOilOrder";

    private String userId;//	用户登录时的ID
    private String type;//	初始化类型 1预购油 2借用油

    public BorrowGasRequest(Response.Listener<BorrowGasResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("type", type);
        return map;
    }

    @Override
    public Class<BorrowGasResponse> getResponseClass() {
        return BorrowGasResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
