package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.DelReceiptinfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 删除发票信息--P10_15 开票信息 - 删除发票信息
 * gaozq
 */
public class DelReceiptinfoRequest extends CnpcHttpJsonRequest<DelReceiptinfoResponse> {

    private static final String APIPATH = "mobi/receiptinfo/del";

    private String checkUserId;     //用户登录时返回的userId对应的值
    private String ssid;     //session ID
    private String id;     //发票记录主键id


    public DelReceiptinfoRequest(Response.Listener<DelReceiptinfoResponse> listener,
                                 Response.ErrorListener errorListener) {
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
        return DelReceiptinfoResponse.class;
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
