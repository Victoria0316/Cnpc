package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.BonusListModel;
import com.bluemobi.cnpc.network.model.CollectionListModel;

/**
 * Created by wangzhijun on 2015/9/1.
 */
public class CollectionListResponse extends CnpcHttpResponse{
    private CollectionListModel data;

    public CollectionListModel getData() {
        return data;
    }

    public void setData(CollectionListModel data) {
        this.data = data;
    }
}
