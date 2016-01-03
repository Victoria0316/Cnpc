package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.OrderSettlementResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/20.
 *
 * 查看用户现有的财务信息--P4_2订单结算 type=01
 * 查看用户现有的财务信息--P5_2_1订单结算 type=02
 * 查看用户现有的财务信息--P5_3_1订单结算 type=03
 *
 */
public class OrderSettlementRequest extends CnpcHttpJsonRequest<OrderSettlementResponse>{

    private static final String APIPATH = "mobi/customerinfo/getCustomerFinanceInfo";

    private String userId ; //	用户登录时的ID
    private String type ;
    private String productTotalValue ; //	商品总价值，此时为套餐总价值，例如：订了两个1000元的套餐时，此值为2000

    public OrderSettlementRequest(Response.Listener<OrderSettlementResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("productTotalValue", productTotalValue);
        return map;
    }

    @Override
    public Class<OrderSettlementResponse> getResponseClass() {
        return OrderSettlementResponse.class;
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

    public String getProductTotalValue() {
        return productTotalValue;
    }

    public void setProductTotalValue(String productTotalValue) {
        this.productTotalValue = productTotalValue;
    }
}
