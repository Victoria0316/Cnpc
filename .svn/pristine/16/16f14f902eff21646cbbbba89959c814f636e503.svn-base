package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.ResetPayResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/10/24.
 * P10_5_1 转入/P10_5_2 转出-重置支付状态
 */
public class ResetPayRequest extends CnpcHttpJsonRequest<ResetPayResponse>{

    private static final String APIPATH = "mobi/customerrechargelog/resetChargeState";

    private String id 	;   //流水日志主键id
    private String userId 	;   //用户id
    private String state 	;   //支付状态 1成功 2失败
    private String type 	;   //类型 1优惠充值 2加油 3预购油购买 4零钱包充值 5借用油购买

    public ResetPayRequest(Response.Listener<ResetPayResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("type", type);
        map.put("state", state);
        return map;
    }

    @Override
    public Class<ResetPayResponse> getResponseClass() {
        return ResetPayResponse.class;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
