package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.FindOilCLogDetailResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询加油消费明细--P10_13_1 加油消费明细 - 查询加油消费明细
 * gaozq
 */
public class FindOilCLogDetailRequest extends CnpcHttpJsonRequest<FindOilCLogDetailResponse> {

    private static final String APIPATH = "mobi/oilfuturesorder/findOilCLogDetail";


    private String checkUserId;       //用户登录时返回的userId对应的值
    private String ssid;       //session ID
    private String id;       //加油消费订单主键id

    public FindOilCLogDetailRequest(Response.Listener<FindOilCLogDetailResponse> listener,
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
        map.put("checkUserId", checkUserId);
        map.put("ssid", ssid);
        map.put("id", id);
        return map;
    }

    @Override
    public Class getResponseClass() {
        return FindOilCLogDetailResponse.class;
    }


    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
