package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.WinawardinfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * gaozq
 * 摇积分--P10_11 摇积分 - 摇积分
 */
public class WinawardinfoRequest extends CnpcHttpJsonRequest<WinawardinfoResponse> {

    private static final String APIPATH = "mobi/winawardinfo/winward";


    private String checkUserId;     //用户登录时返回的userId对应的值
    private String ssid;            //session ID
    private String userId;           //用户登录时的ID
    private String longitude;         //坐标经度
    private String latitude;         //坐标纬度


    public WinawardinfoRequest(Response.Listener<WinawardinfoResponse> listener,
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
        map.put("userId", userId);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        return map;
    }

    @Override
    public Class getResponseClass() {
        return WinawardinfoResponse.class;
    }


    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
