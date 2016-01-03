package com.bluemobi.cnpc.app;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.bluemobi.cnpc.db.entity.AllCity;
import com.bluemobi.cnpc.network.response.ListGasStationDetailResponse;
import com.bluemobi.cnpc.network.response.LoginResponse;
import com.bluemobi.cnpc.network.response.NearNotBdGasStationResponse;
import com.bluemobi.cnpc.network.response.UserCenterResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.CrashHandler;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.StringUtils;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;


/**
 * Created by liufy on 2015/6/23.
 */
public class CnpcApplication extends LitePalApplication {
    public LocationClient mLocClient;

    public static CnpcApplication instance;

    private final String SD_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    public final String FILE_PATH = SD_PATH + "/CNPCLOG/";

    private static int mainTid;
    private static Handler handler;
    /**
     * 分页查询 往server 传递 时间
     */
    private long pageTime;

    private String checkID;

    private LoginResponse.LoginData mData;

    public MyLocationListenner myListener = new MyLocationListenner();

    private AllCity singleCity;

    private Map<String, String> sunyardoils;

    private ArrayList<UserCenterResponse.CheckBoxDTO> mCheckBoxDTO;

    public List<String> getOilDataList() {
        return oilDataList;
    }

    public void setOilDataList(List<String> oilDataList) {
        this.oilDataList = oilDataList;
    }

    private List<String> oilDataList;

    private ArrayList<UserCenterResponse.BusinessDictionaryInfoDTO> mBusinessDictionaryInfoDTOs;

    private ArrayList<ListGasStationDetailResponse.GasStationDetailInfo> mProductInfos;

    private  ArrayList<NearNotBdGasStationResponse.NearnestGasStationProductInfo> NearnestGasStationProductInfos;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        initBase();
        com.tendcloud.tenddata.TCAgent.init(getApplicationContext());
        instance = this;
        initLocation();
        mainTid = android.os.Process.myTid();
        handler = new Handler();

    }

    private void initBase() {
        VolleyManager.init(this);
    }


    public static CnpcApplication getInstance() {
        return instance;
    }

    private void initLocation() {
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myListener);

    }

    public static int getMainTid() {
        return mainTid;
    }

    public static Handler getHandler() {
        return handler;
    }

    public long getPageTime() {
        return pageTime;
    }

    public void setPageTime(long pageTime) {
        this.pageTime = pageTime;
    }

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public LoginResponse.LoginData getmData() {
        return mData;
    }

    public void setmData(LoginResponse.LoginData mData) {
        this.mData = mData;
    }

    public AllCity getSingleCity() {
        return singleCity;
    }

    public void setSingleCity(AllCity singleCity) {
        this.singleCity = singleCity;
    }

    public Map<String, String> getSunyardoils() {
        return sunyardoils;
    }

    public void setSunyardoils(Map<String, String> sunyardoils) {
        this.sunyardoils = sunyardoils;
    }

    public ArrayList<UserCenterResponse.BusinessDictionaryInfoDTO> getmBusinessDictionaryInfoDTOs() {
        return mBusinessDictionaryInfoDTOs;
    }

    public void setmBusinessDictionaryInfoDTOs(ArrayList<UserCenterResponse.BusinessDictionaryInfoDTO> mBusinessDictionaryInfoDTOs) {
        this.mBusinessDictionaryInfoDTOs = mBusinessDictionaryInfoDTOs;
    }

    public ArrayList<NearNotBdGasStationResponse.NearnestGasStationProductInfo> getNearnestGasStationProductInfos() {
        return NearnestGasStationProductInfos;
    }

    public void setNearnestGasStationProductInfos(ArrayList<NearNotBdGasStationResponse.NearnestGasStationProductInfo> nearnestGasStationProductInfos) {
        NearnestGasStationProductInfos = nearnestGasStationProductInfos;
    }


    /**
     * 百度定位回调监听
     *
     * @author liufy
     */
    public class MyLocationListenner implements BDLocationListener {
        public MyLocationListenner() {
        }

        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;

            final double latitude = location.getLatitude();
            final double longitude = location.getLongitude();
            final String cityCode = location.getCityCode();
            final String cityName = location.getCity();
            final String province = location.getProvince();
            Log.e("tag", "start-----:{-cityCode->" + cityCode + "-city-" + cityName + "-lat-" + latitude + "-lon-" + longitude
                    + "-msg-end}");
            if (StringUtils.isNotEmpty(cityName)) {
                SharedPreferencesUtil.saveToFile(getApplicationContext(),
                        "cityName", cityName);

            } else {
                SharedPreferencesUtil.saveToFile(getApplicationContext(),
                        "cityName", Constants.DEFAULT_CITYNAME);
            }
            if (StringUtils.isNotEmpty(province)) {
                SharedPreferencesUtil.saveToFile(getApplicationContext(),
                        "province", province);

            } else {
                SharedPreferencesUtil.saveToFile(getApplicationContext(),
                        "province", Constants.DEFAULT_CITYNAME);
            }

            if (StringUtils.isNotEmpty(cityCode)) {
                SharedPreferencesUtil.saveToFile(getApplicationContext(),
                        "cityCode", cityCode);
            }

            if (StringUtils.isNotEmpty(latitude + "")) {
                SharedPreferencesUtil.saveToFile(getApplicationContext(),
                        "latitude", String.valueOf(latitude));
            } else {
                SharedPreferencesUtil.saveToFile(getApplicationContext(),
                        "latitude", Constants.DEFAULT_LATITUDE + "");
            }
            // 123.483749   41.692144
            if (StringUtils.isNotEmpty(longitude + "")) {
                SharedPreferencesUtil.saveToFile(getApplicationContext(),
                        "longitude", String.valueOf(longitude));
            } else {
                SharedPreferencesUtil.saveToFile(getApplicationContext(),
                        "longitude", Constants.DEFAULT_LONGITUDE + "");

            }


            Intent intent = new Intent();
            intent.setAction("com.cnpc.location");
            intent.putExtra("cityName", cityName);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            getApplicationContext().sendBroadcast(intent);



            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mLocClient.stop();
                }
            }, 60000);
        }

        public void onReceivePoi(BDLocation location) {
        }
    }

    public ArrayList<UserCenterResponse.CheckBoxDTO> getmCheckBoxDTO() {
        return mCheckBoxDTO;
    }

    public void setmCheckBoxDTO(ArrayList<UserCenterResponse.CheckBoxDTO> mCheckBoxDTO) {
        this.mCheckBoxDTO = mCheckBoxDTO;
    }

    public ArrayList<ListGasStationDetailResponse.GasStationDetailInfo> getmProductInfos() {
        return mProductInfos;
    }

    public void setmProductInfos(ArrayList<ListGasStationDetailResponse.GasStationDetailInfo> mProductInfos) {
        this.mProductInfos = mProductInfos;
    }
}