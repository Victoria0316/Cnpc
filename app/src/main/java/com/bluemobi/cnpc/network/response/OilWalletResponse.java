package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.OilCardTradeDetailLogDTOs;

import java.util.ArrayList;

/**
 * Created by gaoyn on 2015/8/29.
 */
public class OilWalletResponse extends CnpcHttpResponse {

    public OilWalletBean data;

    public static class OilWalletBean {

        public String bindOilCard; //是否绑定油卡0已绑定1未绑定
        public String oilBalance; //油钱包余额
        public String changeBalance; //零钱余额（只有初始化零钱时才有）
        public String defaultCardId;//默认加油卡id",
        public String defaultCardName;//默认加油卡名称",
        public ArrayList<String> years = new ArrayList<String>();
        public ArrayList<cardTradeLogDTOs> cardTradeLogDTOs = new ArrayList<cardTradeLogDTOs>();
        public ArrayList<checkBoxDTOs> checkBoxDTOs = new ArrayList<checkBoxDTOs>();

    }

    public static class cardTradeLogDTOs {

        public String month; //月份
        public ArrayList<OilCardTradeDetailLogDTOs> oilCardTradeDetailLogDTOs;

    }



    public static class checkBoxDTOs{
        public String id; //编码
        public String name; //名称
    }


}
