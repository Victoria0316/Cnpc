package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.AddGasCardResponse;
import com.bluemobi.cnpc.network.response.ConsumptionDetailedResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/9/14.
 * <p/>
 * P10_2_3 消费款明细/P10_5_3 详细界面 - 查看订单详情
 */
public class ConsumptionDetailedRequest extends CnpcHttpJsonRequest<ConsumptionDetailedResponse> {

    private static final String APIPATH = "mobi/customerrechargelog/detail";
    private  String userId	;//用户登录时的ID
    private  String id	;//	是	主键id
    private  String type;

    public ConsumptionDetailedRequest(Response.Listener<ConsumptionDetailedResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("id", id);
        map.put("type",type);
        return map;
    }

    @Override
    public Class<ConsumptionDetailedResponse> getResponseClass() {
        return ConsumptionDetailedResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
