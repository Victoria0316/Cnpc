package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.OilCardTradeDetailLogDTO;

import java.util.ArrayList;

/**
 * Created by gaoyn on 2015/9/10.
 */
public class IntegrationResponse extends CnpcHttpResponse{

    public IntegrationBean data;

    public static class IntegrationBean {
        public ArrayList<CardTradeLogDTOs> cardTradeLogDTOs = new ArrayList<CardTradeLogDTOs>();
        public ArrayList<CheckBoxDTO> checkBoxDTOs = new ArrayList<CheckBoxDTO>();
        public ArrayList<String> years = new ArrayList<String>();
        public String frozenBalance; //冻结款余额
    }

    public static class CheckBoxDTO
    {
        public String id;// 油品主键,
        public String name;// 油品名称
    }

    public static class CardTradeLogDTOs
    {
        public String month;
        public String monthAmount; //月份合计
        public String yearAmount; //年份合计
        public ArrayList<OilCardTradeDetailLogDTO> oilCardTradeDetailLogDTOs;
    }
}
