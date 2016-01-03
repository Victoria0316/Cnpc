package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/8/28.
 */
public class CustomerFinanceInfoResponse extends CnpcHttpResponse {

    public CustomerFinanceInfoData data;

    public static class CustomerFinanceInfoData {
        public String id;//  用户的顾客ID ,
        public String userId;//  用户登录的ID ,
        public String changeBalance;//  零钱包余额 ,
        public String oilMoneyBalance;//  油钱包余额 ,
        public String preferentialPrice;//  优惠金额 ,

        public ArrayList<CustomerFinanceCoupon> customerCoupon = new ArrayList<CustomerFinanceCoupon>();

    }

    public static class CustomerFinanceCoupon {
        public String id;// 用户拥有的优惠券ID,
        public String couponName;// 优惠券名称,
        public String minOrderAmount;// 最少消费标准,
        public String couponDiscount;// 优惠券抵扣金额,
    }
}
