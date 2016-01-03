package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.AddGasCardResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/1.
 * <p/>
 * P10_8_2_新增加油卡-新增加油卡
 */
public class AddGasCardRequest extends CnpcHttpJsonRequest<AddGasCardResponse> {

    private static final String APIPATH = "mobi/fuelcardinfo/save";

    private String userId;  //用户id
    private String cardNumber;  //加油卡号
    private String gasStationId;  //加油站id
    private String gasStationCode;  //加油站编码
    private String gasStationName;  //加油站名称
    private String cellphone; //手机号

    public AddGasCardRequest(Response.Listener<AddGasCardResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("cardNumber", cardNumber);
        map.put("gasStationId", gasStationId);
        map.put("gasStationCode", gasStationCode);
        map.put("gasStationName", gasStationName);
        map.put("cellphone", cellphone);
        return map;
    }

    @Override
    public Class<AddGasCardResponse> getResponseClass() {
        return AddGasCardResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getGasStationId() {
        return gasStationId;
    }

    public void setGasStationId(String gasStationId) {
        this.gasStationId = gasStationId;
    }

    public String getGasStationCode() {
        return gasStationCode;
    }

    public void setGasStationCode(String gasStationCode) {
        this.gasStationCode = gasStationCode;
    }

    public String getGasStationName() {
        return gasStationName;
    }

    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
