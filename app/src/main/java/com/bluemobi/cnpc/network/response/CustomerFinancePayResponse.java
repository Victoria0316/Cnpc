package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;

/**
 * Created by liufy on 2015/8/28.
 */
public class CustomerFinancePayResponse extends CnpcHttpResponse {

    public OilFuturesOrderDTO oilFuturesOrderDTO;

    public static class OilFuturesOrderDTO {

        public String actualAmount;// "其他支付",
        public String actualPayment;// "实际支付",
        public String availableAmount;// "当前累计积分",
        public String awardedIntegral;// "赠积分",
        public String changeAmount;// "零钱包支付",
        public String couponAmount;// "优惠券优惠金额",
        public String oilAmount;// "油钱包优惠金额",
        public String orderNo;// "订单号",
        public String payAmount;// "金额和加油金额",
        public String preferentialAmount;// "实际金额",
        public String preferentialPrice;// "优惠金额",
        public String productName;// "油品名称",
        public String productPrice;// "油品单价",
        public String transactionTime;// "订单创建时间"

    }


}
