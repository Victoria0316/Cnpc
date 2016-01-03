package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.PreGasTransferResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/8.
 *  P10_3_2 转入油钱包/P10_4_3 转入油钱包-预购油/借用油转入油钱包
 */
public class PreGasTransferRequest extends CnpcHttpJsonRequest<PreGasTransferResponse>{

    private static final String APIPATH = "mobi/oilfuturesorder/transferToOilPurse";

    private String userId ;  //用户登录时的ID
    private String type ;  //初始化类型 1预购油 2借用油
    private String id ;  //原订单主键id
    private String productNum ;  //转出升数
    private String totalSum ;  //转出总金额

    public PreGasTransferRequest(Response.Listener<PreGasTransferResponse> listener, Response.ErrorListener errorListener) {
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
        return map;
    }

    @Override
    public Class<PreGasTransferResponse> getResponseClass() {
        return PreGasTransferResponse.class;
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
}
