package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.WeChatPayResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/10/9.
 * P10_5_1 转入/P10_5_2 转出-零钱转入转出 - 微信支付
 */
public class WeChatPayRequest extends CnpcHttpJsonRequest<WeChatPayResponse>{

    private static final String APIPATH = "mobi/customerrechargelog/unifiedOrder";

    private String userId ; //	用户登录时的ID
    private String app ; //	android，ios
    private String money ; //	充值金额

    public WeChatPayRequest(Response.Listener<WeChatPayResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("money", money);
        map.put("app", "android");
        map.put("userId", CnpcApplication.getInstance().getmData().userId);
        return map;
    }

    @Override
    public Class<WeChatPayResponse> getResponseClass() {
        return WeChatPayResponse.class;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
