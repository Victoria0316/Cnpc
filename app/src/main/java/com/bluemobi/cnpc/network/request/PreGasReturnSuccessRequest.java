package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.PreGasReturnSuccessResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/11.
 * P10_3_3 转入退货/P10_4_3 归还-预购油退货/借用油归还
 */
public class PreGasReturnSuccessRequest extends CnpcHttpJsonRequest<PreGasReturnSuccessResponse> {

    private static final String APIPATH = "mobi/oilfuturesorder/restore";

    private String userId ; //用户登录时的ID
    private String type ; //初始化类型 3预购油退货 4借用油归还
    private String id ; //原订单主键id
    private String productNum ; //归还/退货升数
    private String totalSum ; //归还/退货总金额
    private String poundage ; //归还/退货手续费

    public PreGasReturnSuccessRequest(Response.Listener<PreGasReturnSuccessResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("id", id);
        map.put("productNum", productNum);
        map.put("totalSum", totalSum);
        map.put("poundage", poundage);
        return map;
    }

    @Override
    public Class<PreGasReturnSuccessResponse> getResponseClass() {
        return PreGasReturnSuccessResponse.class;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum = totalSum;
    }

    public String getPoundage() {
        return poundage;
    }

    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }
}
