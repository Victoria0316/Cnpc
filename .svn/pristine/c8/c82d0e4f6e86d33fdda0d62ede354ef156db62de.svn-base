package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.DetailLogDTO;

import java.util.ArrayList;

/**
 * Created by gaoyn on 2015/9/10.
 */
public class FrozenSectionResponse extends CnpcHttpResponse{

    public FrozenSectionBean data;

    public static class FrozenSectionBean {
        public ArrayList<CardTradeLogDTOs> cardTradeLogDTOs = new ArrayList<CardTradeLogDTOs>();
        public ArrayList<CheckBoxDTO> checkBoxDTOs = new ArrayList<CheckBoxDTO>();
        public ArrayList<String> years = new ArrayList<String>();
        public String pointsBalance; //可用积分
    }

    public static class CheckBoxDTO
    {
        public String id;// 油品主键,
        public String name;// 油品名称
    }

    public static class CardTradeLogDTOs
    {
        public String month;
        public ArrayList<DetailLogDTO> oilCardTradeDetailLogDTOs;
    }

}
