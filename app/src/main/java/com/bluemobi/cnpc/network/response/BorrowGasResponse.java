package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.OrderListDTO;

import java.util.ArrayList;

/**
 * Created by gaoyn on 2015/8/30.
 */
public class BorrowGasResponse extends CnpcHttpResponse {

    public OilOrderData data;

    public static class OilOrderData {
        public ArrayList<OrderListDTOS> oilOrderDTOs = new ArrayList<OrderListDTOS>();
        public ArrayList<CheckBoxDTO> checkBoxDTOs = new ArrayList<CheckBoxDTO>();
        public ArrayList<String> years = new ArrayList<String>();
    }

    public static class CheckBoxDTO
    {
        public String id;// 油品主键,
        public String name;// 油品名称
    }


    public static class OrderListDTOS {
        public String month;
        public ArrayList<OrderListDTO> orderListDTOs;

    }

}
