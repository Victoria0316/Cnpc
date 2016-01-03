package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.PreGasTransferBean;

/**
 * Created by gaoyn on 2015/9/8.
 */
public class PreGasTransferResponse extends CnpcHttpResponse{

    private PreGasTransferBean data;

    public PreGasTransferBean getData() {
        return data;
    }

    public void setData(PreGasTransferBean data) {
        this.data = data;
    }
}
