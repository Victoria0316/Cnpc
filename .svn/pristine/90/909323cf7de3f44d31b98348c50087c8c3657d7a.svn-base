package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.HomeResponse;
import com.bluemobi.cnpc.network.response.LoginResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/19.
 * P3首页 接口用途： 	查询最近的加油站信息
 */
public class HomeRequest extends CnpcHttpJsonRequest<HomeResponse> {

    private static final String APIPATH = "mobi/gasstationinfo/nearestGasStation";
    private String  longitude   	;// 	否 	当前用户坐标经度
    private String  latitude 	;//  	否 	当前用户坐标纬度
    private String  addressid 	;//  	是 	当前用户所在城市ID，如果定位不到传上海市ID

    public HomeRequest(Response.Listener<HomeResponse> listener,
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
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("addressid", addressid);
        return map;
    }

    @Override
    public Class getResponseClass() {
        return HomeResponse.class;
    }


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }


}
