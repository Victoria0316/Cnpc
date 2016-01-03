package com.bluemobi.cnpc.network.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.CnpcHttpRequest;
import com.bluemobi.cnpc.network.response.CityListResponse;
import com.bluemobi.cnpc.network.response.HomeResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/24.
 * 得到系统全部信息 为P7城市切换提供数据地市列表
 */
public class CityListRequest extends CnpcHttpJsonRequest<CityListResponse> {

    private static final String APIPATH = "mobi/divisioninfo/findAll";

    private String cityVersion		;//否	当前系统版本号
    private String pass		;//是	固定值 “n”

    private String hotCityVersion;

    public CityListRequest(Response.Listener<CityListResponse> listener,
                       Response.ErrorListener errorListener)
    {
        super(Request.Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("cityVersion", cityVersion);
        map.put("hotCityVersion", hotCityVersion);
        map.put("pass", "n");
        return map;
    }

    @Override
    public Class<CityListResponse> getResponseClass() {
        return CityListResponse.class;
    }

    public String getHotCityVersion() {
        return hotCityVersion;
    }

    public void setHotCityVersion(String hotCityVersion) {
        this.hotCityVersion = hotCityVersion;
    }

    public String getCityVersion() {
        return cityVersion;
    }

    public void setCityVersion(String cityVersion) {
        this.cityVersion = cityVersion;
    }
}
