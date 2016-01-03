package com.bluemobi.cnpc.eventbus;


/**
 * Created by wangzhijun on 2015/9/25.
 */
public class SuccessRefreshEvent extends BaseEvent{
    public SuccessRefreshEvent(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
