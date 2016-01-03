package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.OrderListDTO;

import java.util.ArrayList;

/**
 * Created by gaoyn on 2015/8/31.
 */
public class BorrowGasBillResponse extends CnpcHttpResponse{

    public ArrayList<BorrowGasBillBean> data = new ArrayList<BorrowGasBillBean>();

    public static class BorrowGasBillBean{
        public String type;
        public String month;
        public ArrayList<OrderListDTO> orderListDTOs = new ArrayList<OrderListDTO>();

    }



}
