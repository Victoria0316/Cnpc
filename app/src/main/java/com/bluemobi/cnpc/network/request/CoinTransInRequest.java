package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.AddGasCardResponse;
import com.bluemobi.cnpc.network.response.CoinTransResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/1.
 * <p/>
 * P10_5_1 转入/P10_5_2 转出-零钱转入转出
 */
public class CoinTransInRequest extends CnpcHttpJsonRequest<CoinTransResponse> {

    private static final String APIPATH = "mobi/customerrechargelog/charge";

    private String userId	;//	用户登录时的ID
    private String money	;//	转入/转出金额
    private String type	;//	操作类型 1转入 2转出
    private String state	;//	支付方式 1银联 2支付宝 3微信
    private String username	;//	银联/支付宝/微信帐号
    private String realname	;//	真实姓名
    private String openID;	//否	 转出零钱时 选择微信支付方式 此字段必填
    private String app; //否 	转入零钱时必填 android，ios

    public CoinTransInRequest(Response.Listener<CoinTransResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("type", type);
        map.put("state", state);
        map.put("username", username);
        map.put("realname", realname);
        map.put("app",app);
        map.put("openID",openID);
        return map;
    }

    @Override
    public Class<CoinTransResponse> getResponseClass() {
        return CoinTransResponse.class;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }
}
