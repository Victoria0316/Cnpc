package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.BorrowOrdersForSureBean;

/**
 * Created by gaoyn on 2015/8/26.
 */
public class BorrowOrdersForSureResponse extends CnpcHttpResponse{

    private BorrowOrdersForSureBean data;

    public BorrowOrdersForSureBean getData() {
        return data;
    }

    public void setData(BorrowOrdersForSureBean data) {
        this.data = data;
    }
}
