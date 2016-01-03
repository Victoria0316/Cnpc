package com.bluemobi.cnpc.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.db.entity.AllCity;
import com.bluemobi.cnpc.db.entity.CityListDetail;
import com.bluemobi.cnpc.fragment.HomeFoundFragment;
import com.bluemobi.cnpc.fragment.HomeMyFragment;
import com.bluemobi.cnpc.fragment.HomeNearFragment;
import com.bluemobi.cnpc.fragment.HomePageFragment;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.LocationToggle;
import com.bluemobi.cnpc.util.NetworkManager;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.tendcloud.tenddata.TCAgent;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * Created by gaoyn on 2015/7/17.
 * <p/>
 * p3 首页
 */

@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private HomePageFragment homePageFragment; //首页
    private HomeNearFragment homeNearFragment; //附近
    private HomeFoundFragment homeFoundFragment; //发现
    private HomeMyFragment homeMyFragment; //我的
    //private NetworkManager networkManager;
    private List<CityListDetail> dtos;

    public boolean isPage =true;

    @Bind(R.id.home_page)
    ImageView home_page;
    @Bind(R.id.home_near)
    ImageView home_near;
    @Bind(R.id.home_found)
    ImageView home_found;
    @Bind(R.id.home_My)
    ImageView home_My;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    private FrameLayout frameLayout;

    TitleBarManager titleBarManager;

    public String cityName;

    public AllCity singleCity;

    private LocationReceiver receiver ;
    public double latitude ;
    public double longitude;

    private long exitTime;

    public static HomeActivity instance;

    /**
     *
     *页面传值
     */
    private int setSelection;

    private NetworkManager networkManager;

    public static HomeActivity getInstance(){
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void initBase() {
        showLoadingPage(false);
        receiver = new LocationReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.cnpc.location");
        registerReceiver(receiver, intentFilter);
        networkManager = new NetworkManager(this, new LocationToggle(mContext));
        if (!Utils.isX86CPU())
            startLoc();
        instance = this;


    }


    public TitleBarManager getTitleBarManager() {
        return titleBarManager;
    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        boolean loginSucceed  = PreferencesService.getInstance(mContext).getPreferencesBool("loginSucceed"); //第一次false

        if (loginSucceed)
        {
            goToLogin(); //首次登陆 不执行登陆
            Logger.e("HomeActivity-->", loginSucceed);
        }


        SharedPreferencesUtil.saveToFile(mContext,
                "cityNamed", "text");
        singleCity = CnpcApplication.getInstance().getSingleCity();
        cityName = SharedPreferencesUtil.getFromFile(mContext, "cityName");
        if (singleCity!=null)
            cityName = singleCity.getName();

        titleBarManager = new TitleBarManager();
        titleBarManager.init(HomeActivity.this, getSupportActionBar());
        titleBarManager.showPositionTitleBar(Constants.DEFAULT_CITYNAME, R.string.home_page, R.drawable.common_positioning);
        home_page.setOnClickListener(this);
        home_near.setOnClickListener(this);
        home_found.setOnClickListener(this);
        home_My.setOnClickListener(this);
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setSelection = getIntent().getIntExtra("setTabSelection", -2);
        if(setSelection == -2){
            setTabSelection(0);
        }else{
            setTabSelection(setSelection);
        }
    }

    /**
     * 是否切换城市
     */
    public boolean isChangeCity ;

    @Override
    protected void onResume() {
        super.onResume();
        singleCity = CnpcApplication.getInstance().getSingleCity();
        if (singleCity!=null)
        {
            if  (receiver!=null)
            {
                unregisterReceiver(receiver);
                receiver =null;
            }
            cityName = singleCity.getName();
            if (isPage)
            {
                titleBarManager.setMainText(cityName);

            }
            String cityName =SharedPreferencesUtil.getFromFileByDefault(mContext, "cityName", Constants.DEFAULT_LATITUDE);
            String province= SharedPreferencesUtil.getFromFileByDefault(mContext, "province", Constants.DEFAULT_LONGITUDE);
            StringBuffer sb = new StringBuffer();
            sb.append(province);
            sb.append(cityName);

            //切换城市
            if (singleCity != null)
            {
                String cityStr = singleCity.getName();

                if(sb.indexOf(cityStr)>-1) //包含
                {
                    isChangeCity = false;
                }
                else
                {
                    isChangeCity = true;
                }
            }

            if (homePageFragment!=null)
            {

                homePageFragment.connectToServerMain();
                Logger.e("tagggggggg--->","connectToServerMain");
            }

        }



    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {

        boolean loginSucceed  = PreferencesService.getInstance(mContext).getPreferencesBool("loginSucceed"); //第一次false
        Logger.e("loginSucceed-->",loginSucceed);
        if (!loginSucceed) {
            Utils.moveTo(mContext, LoginActivity.class);
            finishAll();
            return;
        }
        switch (v.getId()) {
            case R.id.home_page:

                setTabSelection(0);
                break;
            case R.id.home_near:
                setTabSelection(1);
                break;
            case R.id.home_found:
                setTabSelection(2);
                break;
            case R.id.home_My:
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        titleBarManager = new TitleBarManager();
        titleBarManager.init(HomeActivity.this, getSupportActionBar());
        switch (index) {
            case 0:
                //TODO
                if (StringUtils.isEmpty(cityName))
                {
                    cityName =Constants.DEFAULT_CITYNAME;
                }

                titleBarManager.showPositionTitleBar(cityName, R.string.home_page, R.drawable.common_positioning);

                home_page.setImageResource(R.drawable.home_page);
                if (homePageFragment == null) {
                    homePageFragment = new HomePageFragment();
                    transaction.add(R.id.frameLayout, homePageFragment);
                } else {
                    transaction.show(homePageFragment);

                }
                break;
            case 1:


                titleBarManager.showTitleRightBar(R.string.home_near, R.drawable.return_arrow,R.string.map_list,false);
                home_near.setImageResource(R.drawable.home_near);
                if (homeNearFragment == null) {
                    homeNearFragment = new HomeNearFragment();

                    transaction.add(R.id.frameLayout, homeNearFragment);
                } else {
                    transaction.show(homeNearFragment);
                }
                break;
            case 2:

                titleBarManager.showCommonTitleBar(R.string.home_found, R.drawable.return_arrow, false);
                home_found.setImageResource(R.drawable.home_found);
                if (homeFoundFragment == null) {
                    homeFoundFragment = new HomeFoundFragment();
                    transaction.add(R.id.frameLayout, homeFoundFragment);
                } else {
                    transaction.show(homeFoundFragment);
                }
                break;
            case 3:

                titleBarManager.showCommonTitleBar(R.string.home_my, R.drawable.return_arrow, false);
                home_My.setImageResource(R.drawable.home_my);

                if (homeMyFragment == null) {
                    homeMyFragment = new HomeMyFragment();
                    transaction.add(R.id.frameLayout, homeMyFragment);
                } else {
                    transaction.show(homeMyFragment);
                }
                break;
        }
        transaction.commit();
    }


    public void showActivityTitleBar(int id)
    {
        titleBarManager.showCommonTitleBar(id, R.drawable.return_arrow, false);
    }



    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        home_page.setImageResource(R.drawable.home_page_selected);

        home_near.setImageResource(R.drawable.home_near_selected);

        home_found.setImageResource(R.drawable.home_found_selected);

        home_My.setImageResource(R.drawable.home_my_selected);

    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homePageFragment != null) {
            transaction.hide(homePageFragment);
        }
        if (homeNearFragment != null) {
            transaction.hide(homeNearFragment);
        }
        if (homeFoundFragment != null) {
            transaction.hide(homeFoundFragment);
        }
        if (homeMyFragment != null) {
            transaction.hide(homeMyFragment);
        }
    }


    @Override
    public void clickImageLeft() {
/*        boolean bLogin = goToLogin();
        if (bLogin)
        {
            Utils.moveTo(mContext, LoginActivity.class);
            return;
        }*/

        boolean loginSucceed  = PreferencesService.getInstance(mContext).getPreferencesBool("loginSucceed"); //第一次false
        Logger.e("loginSucceed-->", loginSucceed);
        if (!loginSucceed) {
            Utils.moveTo(mContext, LoginActivity.class);
            finishAll();
            return;
        }else
        {
            Utils.moveTo(mContext, CityChangeActivity.class);
        }


    }

    @Override
    public void clickBarRight() {
        /*boolean bLogin = goToLogin();*/

        boolean loginSucceed  = PreferencesService.getInstance(mContext).getPreferencesBool("loginSucceed"); //第一次false
        Logger.e("loginSucceed-->", loginSucceed);
        if (!loginSucceed) {
            Utils.moveTo(mContext, LoginActivity.class);
            finishAll();
            return;
        }else
        {
            Utils.moveTo(mContext, MapListActivity.class);
        }

    }


    public void startLoc() {
        LocationClient mLocClient = CnpcApplication.getInstance().mLocClient;
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("gcj02");
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       if (networkManager != null)
            networkManager.destoryReciver();
        if (receiver!=null)
        {
            unregisterReceiver(receiver);
            receiver = null;
        }

    }

   private class LocationReceiver extends BroadcastReceiver
   {

       @Override
       public void onReceive(Context context, Intent intent) {

           cityName =  intent.getStringExtra("cityName");

           if (titleBarManager!=null)
           {
               titleBarManager.setMainText(cityName);
               if (homePageFragment!=null)
               homePageFragment.locationSucceed(cityName);
           }
           latitude = intent.getDoubleExtra("latitude",Double.parseDouble(Constants.DEFAULT_LATITUDE));

           longitude = intent.getDoubleExtra("longitude",Double.parseDouble(Constants.DEFAULT_LONGITUDE));


       }
   }

    /**
     * 返回键退出登录
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                Toast.makeText(getApplicationContext(), "再按一次将退出应用",
                        Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                CnpcApplication.getInstance().setmData(null);
                this.finish();
            }
        }
        return true;
    }

}
