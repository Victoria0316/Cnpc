package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/8/19.
 */
public class UserCenterResponse extends CnpcHttpResponse {

    public UserCenterData data;

    public static class UserCenterData implements Serializable {
        public String headPicUrl;// 头像地址,
        public String nickName;// 昵称,
        public String oilMoneyBalance;// 油钱包余额,
        public String vipPoint;// 历史总积分
        public String availableAmount;// 可用积分
        public String changeBalance;//零钱余额
        public String frozenPayment;// 冻结款,
        public String oilBorrowAmount;//借用油总金额,
        public String oilFuturesAmount;//预购油总金额,
        public String customerGender;//"性别1:男，2:女，0:未知
        public String carPlateNO;//车牌号

        public ArrayList<CheckBoxDTO> checkBoxDTOs = new ArrayList<CheckBoxDTO>();
        public ArrayList<BusinessDictionaryInfoDTO> businessDictionaryInfoDTOs = new ArrayList<BusinessDictionaryInfoDTO>();

    }

    public static class CheckBoxDTO implements Serializable {
        public String id;//油品主键,
        public String name;//油品名称,
        public String status;//0;//未关注油品1;//已关注油品,
    }

    public static class BusinessDictionaryInfoDTO implements Serializable {
        public String dictionaryCode;// 字典编号,
        public String dictionaryName;// 字典名称,
        public String dictionaryValue;// 字典值,
        public String id;// 保险公司主键id主键
    }
}
