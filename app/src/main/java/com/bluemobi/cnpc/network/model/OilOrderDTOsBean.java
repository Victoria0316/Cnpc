package com.bluemobi.cnpc.network.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaozq on 2015/9/14.
 */
public class OilOrderDTOsBean implements Serializable{

//    "oilOrderDTOs":
//            [
//            {
//                "month": "月份",
//                    "orderListDTOs":
//                [
//                {
//                    "id": "主键",
//                        "tradeTime": "交易时间",
//                        "deptName": "加油站名称",
//                        "oilMoney": "购买金额",
//                        "oilName": "油品名称",
//                }
//                ]
//            }
//            ],

    private String month;

    private List<OrderListDTOsBean> orderListDTOs;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<OrderListDTOsBean> getOrderListDTOs() {
        return orderListDTOs;
    }

    public void setOrderListDTOs(List<OrderListDTOsBean> orderListDTOs) {
        this.orderListDTOs = orderListDTOs;
    }
}
