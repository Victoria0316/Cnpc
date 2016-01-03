package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.LoginResponse;
import com.bluemobi.cnpc.network.response.VersionUpdatingResponse;

import java.util.HashMap;
import java.util.Map;


/**
 * 版本更新
 * gaozq
 */
public class VersionUpdatingRequest extends CnpcHttpJsonRequest<VersionUpdatingResponse> {

    private static final String APIPATH = "mobi/systemintroduces/updating";

    private String checkUserId;      //用户登录时返回的userId对应的值
    private String ssid;      //session ID
    private String app;      //app标识 默认为'android'
    private String version;      //app当前版本号


    public VersionUpdatingRequest(Response.Listener<VersionUpdatingResponse> listener,
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
        map.put("app", app);
        map.put("version", version);
        return map;
    }

    @Override
    public Class getResponseClass() {
        return VersionUpdatingResponse.class;
    }


    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
