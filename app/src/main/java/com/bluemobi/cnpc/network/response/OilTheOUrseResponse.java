package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/9/12.
 */
public class OilTheOUrseResponse extends CnpcHttpResponse{

    public ArrayList<OilTheOUrseData> data = new ArrayList<OilTheOUrseData>();

    public static class OilTheOUrseData
    {
        public String month;
        public ArrayList<OilCardTradeDetailLogDTO> oilCardTradeDetailLogDTOs = new ArrayList<OilCardTradeDetailLogDTO>();

    }
    public static class OilCardTradeDetailLogDTO
    {
        public String id;// 主键,
        public String       tradeTime;// 交易时间,
        public String      tradeType;// 交易类别,
        public String      tradeMoney;// 交易金额,
    }

}
