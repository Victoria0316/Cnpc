package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.OilCardTradeDetailLogDTO;

import java.util.ArrayList;

/**
 * Created by gaoyn on 2015/9/10.
 */
public class IntegrationBillResponse extends CnpcHttpResponse {

    public ArrayList<IntegrationBillBean> data = new ArrayList<IntegrationBillBean>();

    public static class IntegrationBillBean {
        public String month;
        public String monthAmount; //月份合计
        public String yearAmount; //年份合计
        public ArrayList<OilCardTradeDetailLogDTO> oilCardTradeDetailLogDTOs = new ArrayList<OilCardTradeDetailLogDTO>();
    }


}
