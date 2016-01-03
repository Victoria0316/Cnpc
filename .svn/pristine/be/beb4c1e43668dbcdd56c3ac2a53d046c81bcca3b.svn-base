package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.CustomerFinanceInfoResponse;
import com.bluemobi.cnpc.network.response.CustomerInfoUpdateResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/19.
 * P10_1 个人信息-更新个人信息
 */
public class CustomerInfoUpdateRequest extends CnpcHttpJsonRequest<CustomerInfoUpdateResponse> {

    private static final String APIPATH = "mobi/customerinfo/update";


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getDrivingLicenceNo() {
        return drivingLicenceNo;
    }

    public void setDrivingLicenceNo(String drivingLicenceNo) {
        this.drivingLicenceNo = drivingLicenceNo;
    }

    public String getCustomerTelephone() {
        return customerTelephone;
    }

    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    public String getAttentionProductId() {
        return attentionProductId;
    }

    public void setAttentionProductId(String attentionProductId) {
        this.attentionProductId = attentionProductId;
    }

    public String getAttentionProductName() {
        return attentionProductName;
    }

    public void setAttentionProductName(String attentionProductName) {
        this.attentionProductName = attentionProductName;
    }

    private String userId	;//	用户登录时的ID
    private String customerName	;//	姓名
    private String customerGender	;//	性别 1:男，2:女，0:未知
    private String idcardNo	;//	身份证号
    private String drivingLicenceNo	;//	驾驶证号
    private String customerTelephone	;//	手机号
    private String attentionProductId	;//	关注油品id
    private String attentionProductName	;//	关注油品名称


    public CustomerInfoUpdateRequest(Response.Listener<CustomerInfoUpdateResponse> listener,
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
        map.put("customerName", customerName);
        map.put("customerGender", customerGender);
        map.put("idcardNo", idcardNo);
        map.put("drivingLicenceNo", drivingLicenceNo);
        map.put("customerTelephone", customerTelephone);
        map.put("attentionProductId", attentionProductId);
        map.put("attentionProductName", attentionProductName);

        return map;
    }

    @Override
    public Class getResponseClass() {
        return CustomerInfoUpdateResponse.class;
    }



}
