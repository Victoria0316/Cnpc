package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.OilInResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/29.
 *
 * 	P10_2_1转入油卡-油钱包余额转入油卡余额
 */
public class OilInRequest extends CnpcHttpJsonRequest<OilInResponse>{

    private static final String APIPATH = "mobi/fuelcardinfo/rechargeToOilcard";

    private String userId ; //	用户登录时的ID
    private String money ;// 	金额
    private String oilCardId ;//	油卡id

    public OilInRequest(Response.Listener<OilInResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("money", money);
        map.put("oilCardId", oilCardId);
        return map;
    }

    @Override
    public Class<OilInResponse> getResponseClass() {
        return OilInResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOilCardId() {
        return oilCardId;
    }

    public void setOilCardId(String oilCardId) {
        this.oilCardId = oilCardId;
    }
}
