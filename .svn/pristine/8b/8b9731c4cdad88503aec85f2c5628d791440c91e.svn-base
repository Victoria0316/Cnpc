package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.OrderIdResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/28.
 *
 * 生成订单id--生成订单id
 */
public class OrderIdRequest extends CnpcHttpJsonRequest<OrderIdResponse>{

    private static final String APIPATH = "mobi/oilfuturesorder/gerenateOrderid";

    private String userId;  //用户id

    public OrderIdRequest(Response.Listener<OrderIdResponse> listener, Response.ErrorListener errorListener) {
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
    public Class<OrderIdResponse> getResponseClass() {
        return OrderIdResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
