package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.AddGasCardResponse;
import com.bluemobi.cnpc.network.response.OilTheOUrseResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/9/12.
 * <p/>
 * P10_2油钱包/P10_5零钱-按年份和交易类型查询交易流水日志
 */
public class OilThePurseRequest extends CnpcHttpJsonRequest<OilTheOUrseResponse> {

    private static final String APIPATH = "mobi/fuelcardinfo/findRlogs";

    private String userId;//	用户登录时的ID
    private String rYear;//	交易年份
    private String rType;//	否	交易类型
    private String type;//	初始化类型 1油钱包 2零钱

    public OilThePurseRequest(Response.Listener<OilTheOUrseResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("rYear", rYear);
        map.put("rType", rType);
        map.put("type", type);
        return map;
    }

    @Override
    public Class<OilTheOUrseResponse> getResponseClass() {
        return OilTheOUrseResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getrYear() {
        return rYear;
    }

    public void setrYear(String rYear) {
        this.rYear = rYear;
    }
}
