package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.BorrowGasDetailResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/7.
 *  P10_3_1预购油详情/P10_4_1预借油详情-查询订单详情
 */
public class BorrowGasDetailRequest extends CnpcHttpJsonRequest<BorrowGasDetailResponse>{

    private static final String APIPATH = "mobi/oilfuturesorder/findOilDetail";

    private String userId; //用户登录时的ID
    private String type ; //初始化类型 1预购油 2借用油
    private String id; //订单主键id

    public BorrowGasDetailRequest(Response.Listener<BorrowGasDetailResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST,APIPATH, listener, errorListener);
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
        map.put("id", id);
        return map;
    }

    @Override
    public Class<BorrowGasDetailResponse> getResponseClass() {
        return BorrowGasDetailResponse.class;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
