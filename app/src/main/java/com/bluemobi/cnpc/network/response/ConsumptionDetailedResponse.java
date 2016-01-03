package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

/**
 * Created by liufy on 2015/9/14.
 */
public class ConsumptionDetailedResponse extends CnpcHttpResponse {

    public ConsumptionDetailedData data;

    public static class ConsumptionDetailedData {
        public String id;// 主键id,
        public String tradeTime;// 订单日期,
        public String orderNO;// 订单号,
        public String actualPayMoney;// 实际支付金额/充值金额,
        public String preferential;// 优惠金额,
        public String transferInMoney;// 转入金额/实际转入金额,
        public String transferOutMoney;// 转出金额/实际转出金额,
        public String oilName;// 油品名称,
        public String unitPrice;// 单价,
        public String val;// 升数,
        public String money;// 金额(单价*升数),
        public String storageMoney;// 代储费/借用费/手续费,
        public String rType;// 交易类型(1资金转入,2资金转出,3充值,4油品预购转出,5油品借用转出,6充值消费,7油品退货转入,8油品归还转入,11冻结款转入,12预购油转入,13借用油转入,14转入油卡,15加油消费),
        public String rTypeName;// 交易类型名称,
        public String objType;// 操作对象类型(1零钱包,10油钱包,20冻结款,30油卡),
        public String deptName;// 加油站名称,
        public String cardNo;// 加油卡号,
        public String payType;// 支付渠道(1现金,2银行卡转账,3支付宝,4微信,5银联,6油钱包余额),
    }
}
