package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.ProductSelectionBean;

/**
 * Created by gaoyn on 2015/8/26.
 */
public class ProductSelectionResponse extends CnpcHttpResponse{

    public ProductSelectionBean data;

    public ProductSelectionBean getData() {
        return data;
    }

    public void setData(ProductSelectionBean data) {
        this.data = data;
    }
}
