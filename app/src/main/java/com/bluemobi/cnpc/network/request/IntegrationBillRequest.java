package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.IntegrationBillResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/10.
 * 根据年份和塞选类型查询冻结款交易流水--P10_6 冻结款 - 根据年份和塞选类型查询冻结款交易流水
 */
public class IntegrationBillRequest extends CnpcHttpJsonRequest<IntegrationBillResponse>{

    private static final String APIPATH = "mobi/customerrechargelog/findFLogs";

    private String userId ;//	用户登录时的ID
    private String rYear ; //	交易年份
    private String rType ; //	交易类型

    public IntegrationBillRequest(Response.Listener<IntegrationBillResponse> listener, Response.ErrorListener errorListener) {
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
        return map;
    }

    @Override
    public Class<IntegrationBillResponse> getResponseClass() {
        return IntegrationBillResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getrYear() {
        return rYear;
    }

    public void setrYear(String rYear) {
        this.rYear = rYear;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }
}
