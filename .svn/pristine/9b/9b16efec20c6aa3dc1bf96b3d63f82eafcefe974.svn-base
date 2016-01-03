package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.IndexOilConsumeResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * gaozq
 * 初始化加油消费页面--P10_13 加油消费 - 初始化加油消费页面
 */
public class IndexOilConsumeRequest extends CnpcHttpJsonRequest<IndexOilConsumeResponse> {



    private static final String APIPATH = "mobi/oilfuturesorder/indexOilConsume";




    private String checkUserId;   //用户登录时返回的userId对应的值
    private String ssid;   //session ID
    private String userId;   //用户登录时的ID


    public IndexOilConsumeRequest(Response.Listener<IndexOilConsumeResponse> listener,
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
        map.put("userId", userId);
        return map;
    }

    @Override
    public Class getResponseClass() {
        return IndexOilConsumeResponse.class;
    }


    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
