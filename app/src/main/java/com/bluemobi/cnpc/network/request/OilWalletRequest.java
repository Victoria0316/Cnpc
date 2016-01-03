package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.OilWalletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/29.
 *
 * P10_2油钱包-初始化油钱包页面
 */
public class OilWalletRequest extends CnpcHttpJsonRequest<OilWalletResponse> {

    private static final String APIPATH = "mobi/fuelcardinfo/indexOilpurse";

    private String userId ; //用户登录时的ID

    private String type; //初始化类型 1油钱包 2零钱


    public OilWalletRequest(Response.Listener<OilWalletResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("type", type);
        return map;
    }

    @Override
    public Class<OilWalletResponse> getResponseClass() {
        return OilWalletResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


