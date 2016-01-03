package com.bluemobi.cnpc.util;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bluemobi.cnpc.app.CnpcApplication;

import de.greenrobot.event.EventBus;


/**
 * 网络监听器
 * @author liufy
 *
 */
public class LocationToggle implements NetworkManager.OnToggleConnection
{
    private Context context;

    public LocationToggle(Context context)
    {
        this.context = context;
    }

    @Override
    public void onToggleCon(String thisStatus)
    {

        if (!NetworkManager.TYPE_NONE.equals(thisStatus)
                && !NetworkManager.TYPE_UNKNOWN.equals(thisStatus))
        {
            //启动定位
            if(!Utils.isX86CPU())
                startLoc();

        }
    }
    
    /**
     * 开启定位
     */
    private void startLoc()
    {
        LocationClient mLocClient = CnpcApplication.getInstance().mLocClient;
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();

    }

}
