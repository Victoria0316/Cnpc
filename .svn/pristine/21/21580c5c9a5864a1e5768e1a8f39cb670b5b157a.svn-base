package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.ListGasStationDetailResponse;
import com.bluemobi.cnpc.network.response.OilCustomerFinanceInfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/31.
 * 查看用户现有的财务信息--P6_2_1订单结算
 */
public class OilCustomerFinanceInfoRequest extends CnpcHttpJsonRequest<OilCustomerFinanceInfoResponse> {

    private static final String APIPATH = "mobi/customerinfo/getCustomerFinanceInfo";

    private String userId	;//	用户登录时的ID

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductTotalValue() {
        return productTotalValue;
    }

    public void setProductTotalValue(String productTotalValue) {
        this.productTotalValue = productTotalValue;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    private String type	;//	结算类型，此时固定值“04”
    private String productTotalValue	;//	商品总价值，此时为加油总价值
    private String brandid	;//	加油站ID
    

    public OilCustomerFinanceInfoRequest(Response.Listener<OilCustomerFinanceInfoResponse> listener,
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
        map.put("userId", userId);
        map.put("type", "04");
        map.put("productTotalValue", productTotalValue);
        map.put("brandid", brandid);
        return map;
    }

    @Override
    public Class<OilCustomerFinanceInfoResponse> getResponseClass() {
        return OilCustomerFinanceInfoResponse.class;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
