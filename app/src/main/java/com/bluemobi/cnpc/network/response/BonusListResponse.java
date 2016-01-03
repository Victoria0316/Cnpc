package com.bluemobi.cnpc.network.response;

import com.bluemobi.cnpc.network.CnpcHttpResponse;
import com.bluemobi.cnpc.network.model.BonusListModel;

/**
 * Created by wangzhijun on 2015/9/1.
 */
public class BonusListResponse extends CnpcHttpResponse{
    private BonusListModel data;

    public BonusListModel getData() {
        return data;
    }

    public void setData(BonusListModel data) {
        this.data = data;
    }
}
