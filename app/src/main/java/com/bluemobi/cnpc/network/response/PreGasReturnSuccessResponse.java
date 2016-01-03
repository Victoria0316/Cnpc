package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.PreGasReturnSuccessBean;

/**
 * Created by gaoyn on 2015/9/11.
 */
public class PreGasReturnSuccessResponse extends CnpcHttpResponse {

    private PreGasReturnSuccessBean data;

    public PreGasReturnSuccessBean getData() {
        return data;
    }

    public void setData(PreGasReturnSuccessBean data) {
        this.data = data;
    }
}
