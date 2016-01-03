package com.bluemobi.cnpc.db.control;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by liufy on 2015/6/24.
 */
public abstract  class BaseDbControl<T>
{

    public <T> List<T> findAllData(Class<T> clazz)
    {
        List<T>  datas = DataSupport.findAll(clazz);
        return  datas;
    }


    public void deleteAllData(Class<T> clazz)
    {
        DataSupport.deleteAll(clazz);
    }
}
