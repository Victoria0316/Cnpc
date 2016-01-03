package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.io.Serializable;

/**
 * Created by liufy on 2015/9/11.
 */
public class CarDetailInfoResponse extends CnpcHttpResponse {

    public CarDetailInfoData data;

    public static class CarDetailInfoData implements Serializable
    {
        public String carBrandId;// 品牌id,
        public String carBrandName;// 品牌名称,
        public String carDisplacement;// 排量,
        public String carModelId;// 型号id,
        public String carModelName;// 型号名称,
        public String carPlate;// 车牌号,
        public String carPrice;// 车价(万元),
        public String insuranceExpiryDate;// 保险到期日,
        public String insurerId;// 保险公司ID,
        public String insurerName;// 保险公司名称,
        public String oilId;// 加注油品id,
        public String oilName;// 加注油品名称,
        public String purchaseDate;// 购买年月,
        public String registeredLocationId;// 注册地ID,
        public String registeredLocationName;// 注册地名称,
        public String vinNo;// 车架号后6位
    }



}
