package com.bluemobi.cnpc.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 网络监测模块
 * @author liufy
 *
 */
public class NetworkManager
{
    public static int NOT_REACHABLE = 0;

    public static int REACHABLE_VIA_CARRIER_DATA_NETWORK = 1;

    public static int REACHABLE_VIA_WIFI_NETWORK = 2;

    public static final String WIFI = "wifi";

    public static final String WIMAX = "wimax";

    // mobile
    public static final String MOBILE = "mobile";

    // 2G network types
    public static final String GSM = "gsm";

    public static final String GPRS = "gprs";

    public static final String EDGE = "edge";

    // 3G network types
    public static final String CDMA = "cdma";

    public static final String UMTS = "umts";

    public static final String HSPA = "hspa";

    public static final String HSUPA = "hsupa";

    public static final String HSDPA = "hsdpa";

    public static final String ONEXRTT = "1xrtt";

    public static final String EHRPD = "ehrpd";

    // 4G network types
    public static final String LTE = "lte";

    public static final String UMB = "umb";

    public static final String HSPA_PLUS = "hspa+";

    // return type
    public static final String TYPE_UNKNOWN = "unknown";

    public static final String TYPE_ETHERNET = "ethernet";

    public static final String TYPE_WIFI = "wifi";

    public static final String TYPE_2G = "2g";

    public static final String TYPE_3G = "3g";

    public static final String TYPE_4G = "4g";

    public static final String TYPE_NONE = "none";

    private boolean registered = false;
    
    private Activity mActivity;

    ConnectivityManager sockMan;

    BroadcastReceiver receiver;

    private String lastStatus = "";

    private OnToggleConnection toggleConnection;

    public interface OnToggleConnection
    {
        public void onToggleCon(String thisStatus);
    }

    public NetworkManager()
    {
        this.receiver = null;
    }

    public NetworkManager(Activity activity, OnToggleConnection toggleConnection)
    {   
        this.mActivity = activity;
        this.receiver = null;
        this.toggleConnection = toggleConnection;
        this.sockMan = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //          this.connectionCallbackContext = null;

        // We need to listen to connectivity events to update navigator.connection
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        if (this.receiver == null)
        {
            this.receiver = new BroadcastReceiver()
            {
                @Override
                public void onReceive(Context context, Intent intent)
                {
                    // (The null check is for the ARM Emulator, please use Intel Emulator for better results)                 
                    updateConnectionInfo(sockMan.getActiveNetworkInfo());
                }
            };
            activity.registerReceiver(this.receiver, intentFilter);
            this.registered = true;
        }

    }
    
    public void destoryReciver()
    {
        if(receiver!=null)
        {
            //TODO
            mActivity.unregisterReceiver(receiver);
            receiver = null;
        }
    }

    /**
     * Updates the JavaScript side whenever the connection changes
     *
     * @param info the current active network info
     * @return
     */
    private void updateConnectionInfo(NetworkInfo info)
    {
        // send update to javascript "navigator.network.connection"
        // Jellybean sends its own info
        String thisStatus = this.getConnectionInfo(info);
        if (!thisStatus.equals(lastStatus))
        {
            sendUpdate(thisStatus);
            lastStatus = thisStatus;
        }

    }

    private void sendUpdate(String type)
    {
        if (toggleConnection != null)
        {
            toggleConnection.onToggleCon(type);
        }

    }

    /**
     * Get the latest network connection information
     *
     * @param info the current active network info
     * @return a JSONObject that represents the network info
     */
    private String getConnectionInfo(NetworkInfo info)
    {
        String type = TYPE_NONE;
        if (info != null)
        {
            // If we are not connected to any network set type to none
            if (!info.isConnected())
            {
                type = TYPE_NONE;
            }
            else
            {
                type = getType(info);
            }
        }
        Log.d("CordovaNetworkManager", "Connection Type: " + type);
        return type;
    }

    /**
     * Determine the type of connection
     *
     * @param info the network info so we can determine connection type.
     * @return the type of mobile network we are on
     */
    private String getType(NetworkInfo info)
    {
        if (info != null)
        {
            String type = info.getTypeName();

            if (type.toLowerCase().equals(WIFI))
            {
                return TYPE_WIFI;
            }
            else if (type.toLowerCase().equals(MOBILE))
            {
                type = info.getSubtypeName();
                if (type.toLowerCase().equals(GSM)
                        || type.toLowerCase().equals(GPRS)
                        || type.toLowerCase().equals(EDGE))
                {
                    return TYPE_2G;
                }
                else if (type.toLowerCase().startsWith(CDMA)
                        || type.toLowerCase().equals(UMTS)
                        || type.toLowerCase().equals(ONEXRTT)
                        || type.toLowerCase().equals(EHRPD)
                        || type.toLowerCase().equals(HSUPA)
                        || type.toLowerCase().equals(HSDPA)
                        || type.toLowerCase().equals(HSPA))
                {
                    return TYPE_3G;
                }
                else if (type.toLowerCase().equals(LTE)
                        || type.toLowerCase().equals(UMB)
                        || type.toLowerCase().equals(HSPA_PLUS))
                {
                    return TYPE_4G;
                }
            }
        }
        else
        {
            return TYPE_NONE;
        }
        return TYPE_UNKNOWN;
    }

}
