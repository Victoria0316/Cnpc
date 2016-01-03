package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.MakeOutAddInvoiceResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/9/9.
 *
 *  	P10_15 开票信息 - 添加发票信息
 */
public class MakeOutAddInvoiceRequest extends CnpcHttpJsonRequest<MakeOutAddInvoiceResponse>{

    private static final String APIPATH = "mobi/receiptinfo/save";

    private String userId ; //用户登录时的ID
    private String receiptType ; //发票类型 1个人 2公司
    private String companyName ; //公司名称(发票类型为2时填写)

    public MakeOutAddInvoiceRequest(Response.Listener<MakeOutAddInvoiceResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("receiptType", receiptType);
        map.put("companyName", companyName == null? "":companyName);
        return map;
    }

    @Override
    public Class<MakeOutAddInvoiceResponse> getResponseClass() {
        return MakeOutAddInvoiceResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
