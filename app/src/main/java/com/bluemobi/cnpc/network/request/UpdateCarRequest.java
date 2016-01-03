package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.SaveCarResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/30.
 * P10_1_1 添加爱车-新增车辆信息
 */
public class UpdateCarRequest extends CnpcHttpJsonRequest<SaveCarResponse> {


    private static final String APIPATH = "mobi/carinfo/update";


    private String userId	;//	用户登录时的ID
    private String carPlate	;//	车牌号
    private String carBrandId	;//	品牌id
    private String carBrandName	;//	品牌名称
    private String carModelId	;//	型号id
    private String carModelName	;//	型号名称
    private String oilId	;//	加注油品id
    private String oilName	;//	加注油品名称
    private String purchaseDate	;//	购买年月
    private String insuranceExpiryDate	;//	保险到期日
    private String carPrice	;//	车价(万元)
    private String insurerId	;//	保险公司ID
    private String  insurerName	;//	保险公司名称
    private String registeredLocationId	;//	注册地ID
    private String registeredLocationName	;//	注册地名称
    private String carDisplacement	;//	排量
    private String vinNo	;//	车架号后6位
    private String id	;//	是	主键id

    public UpdateCarRequest(Response.Listener<SaveCarResponse> listener,
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
        map.put("id",id);
        map.put("userId",userId);
        map.put("carPlate",carPlate);
        map.put("carBrandId",carBrandId);
        map.put("carBrandName",carBrandName);
        map.put("carModelName",carModelName);
        map.put("oilId",oilId);
        map.put("oilName",oilName);
        map.put("purchaseDate",purchaseDate);
        map.put("insuranceExpiryDate",insuranceExpiryDate);
        map.put("carPrice",carPrice);
        map.put("insurerId",insurerId);
        map.put("insurerName",insurerName);
        map.put("registeredLocationId",registeredLocationId);
        map.put("registeredLocationName",registeredLocationName);
        map.put("carDisplacement",carDisplacement);
        map.put("vinNo",vinNo);
        return map;
    }

    @Override
    public Class<SaveCarResponse> getResponseClass() {
        return SaveCarResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarBrandId() {
        return carBrandId;
    }

    public void setCarBrandId(String carBrandId) {
        this.carBrandId = carBrandId;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public String getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(String carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getOilId() {
        return oilId;
    }

    public void setOilId(String oilId) {
        this.oilId = oilId;
    }

    public String getOilName() {
        return oilName;
    }

    public void setOilName(String oilName) {
        this.oilName = oilName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }

    public void setInsuranceExpiryDate(String insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(String carPrice) {
        this.carPrice = carPrice;
    }

    public String getInsurerId() {
        return insurerId;
    }

    public void setInsurerId(String insurerId) {
        this.insurerId = insurerId;
    }

    public String getInsurerName() {
        return insurerName;
    }

    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }

    public String getRegisteredLocationName() {
        return registeredLocationName;
    }

    public void setRegisteredLocationName(String registeredLocationName) {
        this.registeredLocationName = registeredLocationName;
    }

    public String getRegisteredLocationId() {
        return registeredLocationId;
    }

    public void setRegisteredLocationId(String registeredLocationId) {
        this.registeredLocationId = registeredLocationId;
    }

    public String getCarDisplacement() {
        return carDisplacement;
    }

    public void setCarDisplacement(String carDisplacement) {
        this.carDisplacement = carDisplacement;
    }

    public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
