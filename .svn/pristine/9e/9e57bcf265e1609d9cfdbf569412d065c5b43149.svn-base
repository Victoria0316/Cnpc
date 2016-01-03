package com.bluemobi.cnpc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.cnpc.db.entity.CarAllListDetail;
import com.bluemobi.cnpc.db.entity.CityListDetail;
import com.bluemobi.cnpc.db.entity.DbVersion;
import com.bluemobi.cnpc.db.entity.HotCityListDetail;
import com.bluemobi.cnpc.network.request.CarAllListRequest;
import com.bluemobi.cnpc.network.request.CityListRequest;
import com.bluemobi.cnpc.network.response.CarAllListResponse;
import com.bluemobi.cnpc.network.response.CityListResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.DbChecker;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.ThreadManager;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by wangzhijun on 2015/7/17.
 */
public class AppStartActivity extends Activity {

    private boolean firstLoadApp;

    private String cityVersion;
    private  String hotCityStatus;

    private boolean debug = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isSucceed = PreferencesService.getInstance(this).getPreferencesBool("isSucceed");
        DbChecker checker = new DbChecker(getApplicationContext());
        checker.execute();
        //connectToServerCityList();
        if (!isSucceed)
        {
            allCarFromServer();
        }
        //默认为true，加载引导页
        firstLoadApp = Boolean.parseBoolean(SharedPreferencesUtil.
                getFromFileByDefault(this, Constants.FIRSTLOADAPP, "true"));
        process();
    }
    private void process() {
        if(debug) {
            if (firstLoadApp) {//首次加载、跳入引导页面
                Utils.moveTo(this, GuideActivity.class);
            } else {
                Utils.moveTo(this, LauncherActivity.class);
            }
        }else{
            Utils.moveTo(this, LoginActivity.class);
        }
        finish();
    }

    public void allCarFromServer()
    {
        CarAllListRequest request = new CarAllListRequest(new Response.Listener<CarAllListResponse>() {
            @Override
            public void onResponse(CarAllListResponse response) {
                if (response != null && response.getStatus() == 0) {// success
                    saveCarIntoDb(response.data);
                    try {
                        PreferencesService.getInstance(AppStartActivity.this).saveBool("isSucceed", true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        WebUtils.doPost(request);
    }

    private void saveCarIntoDb(final ArrayList<CarAllListResponse.AllCarDto> data)
    {
        ThreadManager.getInstance().createLongPool().execute(new Runnable() {
            @Override
            public void run() {
                for (CarAllListResponse.AllCarDto dto : data) {
                    CarAllListDetail carAllListDetail = new CarAllListDetail();
                    carAllListDetail.setBrandName(dto.brandName);
                    carAllListDetail.setCarId(dto.id);
                    carAllListDetail.setFrontLetter(dto.frontLetter);
                    carAllListDetail.setIsSale(dto.isSale);
                    carAllListDetail.setLevel(dto.level);
                    carAllListDetail.setParentBrandCode(dto.parentBrandCode);
                    carAllListDetail.save();
                }
            }
        });

    }



    private void connectToServerCityList() {
        final DbVersion dbVersion = DataSupport.findFirst(DbVersion.class);
        final CityListRequest request = new CityListRequest
                (
                        new Response.Listener<CityListResponse>() {
                            @Override
                            public void onResponse(CityListResponse response) {
                                cityVersion = response.data.cityVersion;
                                hotCityStatus = response.data.hotCityVersion;
                                final ArrayList<CityListResponse.CityListDto> divisionInfoDTOs = response.data.divisionInfoDTOs;
                                final ArrayList<CityListResponse.HotCityInfoDTO> hotCityInfoDTOs = response.data.hotCityInfoDTOs;
                                if (dbVersion==null)
                                {

                                    if ((divisionInfoDTOs!=null&&divisionInfoDTOs.size()>0)&&hotCityInfoDTOs!=null&&hotCityInfoDTOs.size()>0)
                                        saveIntoDb(divisionInfoDTOs,hotCityInfoDTOs,cityVersion,hotCityStatus);
                                    return;
                                }

                                if ("1".equals(response.data.cityStatus)) //"城市状态0不需要更新1需要更新
                                {

                                    updateCityDb();
                                    if (divisionInfoDTOs!=null&&divisionInfoDTOs.size()>0)
                                        saveIntoDb(divisionInfoDTOs,null, cityVersion,hotCityStatus);
                                }
                                if ("1".equals(response.data.hotCityStatus)) //"城市状态0不需要更新1需要更新
                                {

                                    updateHotCityDb();
                                    if (hotCityInfoDTOs!=null&&hotCityInfoDTOs.size()>0)
                                        saveIntoDb(null,hotCityInfoDTOs, cityVersion,hotCityStatus);
                                }

                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }});
        if (dbVersion==null)
        {
            request.setCityVersion("");
            request.setHotCityVersion("");
        }
        else
        {
            request.setCityVersion(dbVersion.getVersion());
            request.setHotCityVersion(dbVersion.getHotCityVersion());
        }

        WebUtils.doPost(request);
    }


    private void updateCityDb()
    {
        DataSupport.deleteAll(DbVersion.class);
        DataSupport.deleteAll(CityListDetail.class);

    }

    private void updateHotCityDb()
    {
        DataSupport.deleteAll(DbVersion.class);
        DataSupport.deleteAll(HotCityListDetail.class);

    }

    private void saveIntoDb(final ArrayList<CityListResponse.CityListDto> data, final ArrayList<CityListResponse.HotCityInfoDTO> hotCityInfoDTOs, final String version, final String hotVersion) {


        //into success
        ThreadManager.getInstance().createLongPool().execute(new Runnable() {
            @Override
            public void run() {
                DbVersion dbVersion = new DbVersion();
                dbVersion.setVersion(version);
                dbVersion.setHotCityVersion(hotVersion);
                dbVersion.save();
                if (data!=null)
                {
                    for (CityListResponse.CityListDto dto : data) {
                        CityListDetail cityDetails = new CityListDetail();
                        cityDetails.setDivisionJp(dto.divisionJp);
                        cityDetails.setDivisionName(dto.divisionName);
                        cityDetails.setDivisionNameJp(dto.divisionNameJp);
                        cityDetails.setDivisionType(dto.divisionType);
                        cityDetails.setSid(dto.id);
                        cityDetails.setPid(Integer.parseInt(dto.pid));
                        cityDetails.save();

                    }
                }
                if (hotCityInfoDTOs!=null)
                {
                    for (CityListResponse.HotCityInfoDTO dto : hotCityInfoDTOs) {
                        HotCityListDetail hotCityListDetail = new HotCityListDetail();
                        hotCityListDetail.setSid(dto.id);
                        hotCityListDetail.setPid(dto.pid);
                        hotCityListDetail.setDivisionName(dto.divisionName);
                        hotCityListDetail.save();

                    }
                }




            }
        });

    }



}
