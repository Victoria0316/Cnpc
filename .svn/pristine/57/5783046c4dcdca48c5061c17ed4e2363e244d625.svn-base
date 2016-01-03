package com.bluemobi.cnpc.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.logic.task.POISearchTask;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.activity.BNDemoGuideActivity;
import com.bluemobi.cnpc.activity.GasStationDetailsActivity;
import com.bluemobi.cnpc.activity.HomeActivity;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.db.entity.Position;
import com.bluemobi.cnpc.network.request.NearnestGasStationRequest;
import com.bluemobi.cnpc.network.response.NearnestGasStationResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.AlertDialog;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.view.SlidingViewPager;
import com.bluemobi.cnpc.view.ViewPagerItemView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/20.
 * modify by liufengyu
 * <p/>
 * p8-附近
 */
@ContentView(R.layout.fragment_home_near)
@PageName(R.string.home_near)
public class HomeNearFragment extends BaseFragment {


    private MyLocationConfiguration.LocationMode mLocationMode;

    private BaiduMap mBaiduMap;

    private BitmapDescriptor mIconLocation;

    private BitmapDescriptor mRedMarker;

    private BitmapDescriptor mBlueMarker;

    @Bind(R.id.bmapView)
    MapView mMapView;

   /* @Bind(R.id.plv_refresh)
    PullToRefreshListView plv_refresh;*/

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    MyPoiPagerAdapter myPoiPagerAdapter;

    private String deptLatitude;
    private String deptLongitude;
    private String deptName;

    private CommonAdapter<NearnestGasStationResponse.NearnestGasStationInfo> adapter;

    private ArrayList<NearnestGasStationResponse.NearnestGasStationInfo> info;

    private String currentpage = "0";
    private static final String APP_FOLDER_NAME = "BNSDKDemo";
    private String mSDCardPath = null;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    private List<Position> cooperGasStations;

    private List<Position> noCooperGasStations;

    private TextView mRedPoi;
    private TextView mBluePoi;

    private BitmapDescriptor mRedMarker01;
    private BitmapDescriptor mRedMarker02;
    private BitmapDescriptor mRedMarker03;
    private BitmapDescriptor mRedMarker04;
    private BitmapDescriptor mRedMarker05;
    private BitmapDescriptor mRedMarker06;
    private BitmapDescriptor mRedMarker07;
    private BitmapDescriptor mRedMarker08;
    private BitmapDescriptor mRedMarker09;
    private BitmapDescriptor mRedMarker10;


    private BitmapDescriptor mBlueMarker01;
    private BitmapDescriptor mBlueMarker02;
    private BitmapDescriptor mBlueMarker03;
    private BitmapDescriptor mBlueMarker04;
    private BitmapDescriptor mBlueMarker05;
    private BitmapDescriptor mBlueMarker06;
    private BitmapDescriptor mBlueMarker07;
    private BitmapDescriptor mBlueMarker08;
    private BitmapDescriptor mBlueMarker09;
    private BitmapDescriptor mBlueMarker10;


    private List<BitmapDescriptor> bitmapBlueDescriptor = new ArrayList<BitmapDescriptor>();
    private List<BitmapDescriptor> bitmapDescriptor = new ArrayList<BitmapDescriptor>();

    private AlertDialog tipDialog;

    @Override
    public void initData(Bundle savedInstanceState) {

        isShowLoadPage = false;
        cooperGasStations = new ArrayList<Position>();
        noCooperGasStations = new ArrayList<Position>();
        ((HomeActivity) mContext).startLoc();
        if (initDirs()) {
            initNavi();
        }

    }


    private boolean isOnScollPage = true;

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        ((HomeActivity) mContext).isPage = true;
        getDataServer();
        mBaiduMap = mMapView.getMap();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                if (position == 9 && isOnScollPage) {
                    isOnScollPage = false;
                    getDataServer();

                }
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        mRedMarker01 = BitmapDescriptorFactory.fromResource(R.drawable.red_1);
        mRedMarker02 = BitmapDescriptorFactory.fromResource(R.drawable.red_2);
        mRedMarker03 = BitmapDescriptorFactory.fromResource(R.drawable.red_3);
        mRedMarker04 = BitmapDescriptorFactory.fromResource(R.drawable.red_4);
        mRedMarker05 = BitmapDescriptorFactory.fromResource(R.drawable.red_5);
        mRedMarker06 = BitmapDescriptorFactory.fromResource(R.drawable.red_6);
        mRedMarker07 = BitmapDescriptorFactory.fromResource(R.drawable.red_7);
        mRedMarker08 = BitmapDescriptorFactory.fromResource(R.drawable.red_8);
        mRedMarker09 = BitmapDescriptorFactory.fromResource(R.drawable.red_9);
        mRedMarker10 = BitmapDescriptorFactory.fromResource(R.drawable.red_10);

        bitmapDescriptor.add(mRedMarker01);
        bitmapDescriptor.add(mRedMarker02);
        bitmapDescriptor.add(mRedMarker03);
        bitmapDescriptor.add(mRedMarker04);
        bitmapDescriptor.add(mRedMarker05);
        bitmapDescriptor.add(mRedMarker06);
        bitmapDescriptor.add(mRedMarker07);
        bitmapDescriptor.add(mRedMarker08);
        bitmapDescriptor.add(mRedMarker09);
        bitmapDescriptor.add(mRedMarker10);

        mBlueMarker01 = BitmapDescriptorFactory.fromResource(R.drawable.blue_1);
        mBlueMarker02 = BitmapDescriptorFactory.fromResource(R.drawable.blue_2);
        mBlueMarker03 = BitmapDescriptorFactory.fromResource(R.drawable.blue_3);
        mBlueMarker04 = BitmapDescriptorFactory.fromResource(R.drawable.blue_4);
        mBlueMarker05 = BitmapDescriptorFactory.fromResource(R.drawable.blue_5);
        mBlueMarker06 = BitmapDescriptorFactory.fromResource(R.drawable.blue_6);
        mBlueMarker07 = BitmapDescriptorFactory.fromResource(R.drawable.blue_7);
        mBlueMarker08 = BitmapDescriptorFactory.fromResource(R.drawable.blue_8);
        mBlueMarker09 = BitmapDescriptorFactory.fromResource(R.drawable.blue_9);
        mBlueMarker10 = BitmapDescriptorFactory.fromResource(R.drawable.blue_10);
        bitmapBlueDescriptor.add(mBlueMarker01);
        bitmapBlueDescriptor.add(mBlueMarker02);
        bitmapBlueDescriptor.add(mBlueMarker03);
        bitmapBlueDescriptor.add(mBlueMarker04);
        bitmapBlueDescriptor.add(mBlueMarker05);
        bitmapBlueDescriptor.add(mBlueMarker06);
        bitmapBlueDescriptor.add(mBlueMarker07);
        bitmapBlueDescriptor.add(mBlueMarker08);
        bitmapBlueDescriptor.add(mBlueMarker09);
        bitmapBlueDescriptor.add(mBlueMarker10);


        mBlueMarker = BitmapDescriptorFactory.fromResource(R.drawable.blue_poi);
        //点击POI点出来的信息Toast
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                Position position = (Position) extraInfo
                        .getSerializable("info");
                mBaiduMap.hideInfoWindow();
                InfoWindow mInfoWindow;
                TextView tv = new TextView(mContext);
                tv.setBackgroundResource(R.drawable.location_tips);
                tv.setPadding(30, 20, 30, 50);
                tv.setTextColor(Color.parseColor("#ffffff"));
                tv.setText(position.getDeptName());
                InfoWindow.OnInfoWindowClickListener listener = null;
                listener = new InfoWindow.OnInfoWindowClickListener() {
                    public void onInfoWindowClick() {

                    }
                };
                LatLng ll = marker.getPosition();
                mInfoWindow = new InfoWindow(
                        BitmapDescriptorFactory.fromView(tv), ll,
                        -47, listener);
                mBaiduMap.showInfoWindow(mInfoWindow);

                return true;
            }
        });
        //点击POI 点击Toast后的处理
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {

                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                mBaiduMap.hideInfoWindow();
            }
        });


    }


    public void getDataServer() {

        Utils.showProgressDialog(mContext);

        NearnestGasStationRequest request = new NearnestGasStationRequest
                (
                        new Response.Listener<NearnestGasStationResponse>() {
                            @Override
                            public void onResponse(final NearnestGasStationResponse response) {

                                currentpage = response.data.getCurrentpage();

                                if (cooperGasStations != null && cooperGasStations.size() > 0)
                                    cooperGasStations.clear();
                                if (info != null && info.size() > 0)
                                    info.clear();
                                info = response.data.info;
                                for (NearnestGasStationResponse.NearnestGasStationInfo bean : info) {
                                    Position position = new Position();
                                    position.setLatitude(Double.parseDouble(bean.deptLatitude));
                                    position.setLongitude(Double.parseDouble(bean.deptLongitude));
                                    position.setDeptName(bean.deptName);
                                    position.setCooper(bean.cooperationState);
                                    cooperGasStations.add(position);
                                }
                                addOverlays(cooperGasStations);
                                if (myPoiPagerAdapter != null) {
                                    myPoiPagerAdapter.notifyDataSetChanged();
                                } else {
                                    myPoiPagerAdapter = new MyPoiPagerAdapter(mContext);
                                    viewPager.setAdapter(myPoiPagerAdapter);

                                }

                                viewPager.setCurrentItem(0);
                                isOnScollPage = true;
                                Utils.closeDialog();

                            }
                        }, (Response.ErrorListener) getActivity());
        request.setLatitude(SharedPreferencesUtil.getFromFileByDefault(mContext,
                "latitude", Constants.DEFAULT_LATITUDE));
        request.setLongitude(SharedPreferencesUtil.getFromFileByDefault(mContext,
                "longitude", Constants.DEFAULT_LONGITUDE));
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(currentpage);

        request.setCityName(SharedPreferencesUtil.getFromFileByDefault(mContext,
                "cityName", Constants.DEFAULT_CITYNAME)); // 	是 	地市名称
        request.setProvinceName(SharedPreferencesUtil.getFromFileByDefault(mContext,
                "province", Constants.DEFAULT_CITYNAME)); // 	是 	省份名称
        WebUtils.doPost(request);
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 初始化导航
     */
    private void initNavi() {
        BaiduNaviManager.getInstance().setNativeLibraryPath(mSDCardPath + "/BaiduNaviSDK_SO");
        BaiduNaviManager.getInstance().init(getActivity(), mSDCardPath, APP_FOLDER_NAME,
                new BaiduNaviManager.NaviInitListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                    }

                    public void initSuccess() {
                        Toast.makeText(mContext, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                    }

                    public void initStart() {
                        Toast.makeText(mContext, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    public void initFailed() {
                        Toast.makeText(mContext, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                }, null /*mTTSCallback*/);
    }

    /**
     * 获取SD 卡路径
     *
     * @return
     */
    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    /**
     * 根据经纬度算路
     *
     * @param coType
     */
    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType) {
        final double longitude = Double.parseDouble(SharedPreferencesUtil.getFromFile(mContext, "longitude"));
        final double latitude = Double.parseDouble(SharedPreferencesUtil.getFromFile(mContext, "latitude"));
        final String cityName = SharedPreferencesUtil.getFromFile(mContext, "cityName");
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;
        switch (coType) {

            case GCJ02: {
                sNode = new BNRoutePlanNode(longitude, latitude,
                        cityName, null, coType);
                eNode = new BNRoutePlanNode(Double.parseDouble(deptLongitude), Double.parseDouble(deptLatitude),
                        deptName, null, coType);
                break;
            }
            default:
                ;
        }
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(getActivity(), list, 1, true, new DemoRoutePlanListener(sNode));
        }
    }

    /**
     * 算路成功后的回调 成功后是跳转到导航页面
     */
    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            Utils.closeDialog();
            Intent intent = new Intent(getActivity(), BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub

        }
    }

    /**
     * 添加图层覆盖物附近的poi
     *
     * @param lbsPositions
     */
    private void addOverlays(List<Position> lbsPositions) {
        mBaiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options = null;

        for (int i = 0; i < lbsPositions.size(); i++) {
            latLng = new LatLng(lbsPositions.get(i).getLatitude(), lbsPositions.get(i).getLongitude());
            if ("0".equals(lbsPositions.get(i).getCooper())) {

                options = new MarkerOptions().position(latLng).icon(bitmapBlueDescriptor.get(i))
                        .zIndex(5);
            } else if ("1".equals(lbsPositions.get(i).getCooper())) {
                options = new MarkerOptions().position(latLng).icon(bitmapDescriptor.get(i))
                        .zIndex(5);

            }
            marker = (Marker) mBaiduMap.addOverlay(options);
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", lbsPositions.get(i));
            marker.setExtraInfo(bundle);
        }

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);


    }

    /**
     * [ViewPager适配器]
     */
    private class MyPoiPagerAdapter extends PagerAdapter {

        private Context mContext;

        public MyPoiPagerAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return info.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {


            ViewPagerItemView itemView = new ViewPagerItemView(mContext);
            int colorID = 0;
            if ("0".equals(info.get(position).cooperationState)) {
                colorID = getResources().getColor(R.color.common_blue);
            } else if ("1".equals(info.get(position).cooperationState)) {
                colorID = getResources().getColor(R.color.common_red);
            }
            TextView details = (TextView) itemView.findViewById(R.id.details);
            details.setTextColor(colorID);

            TextView model = (TextView) itemView.findViewById(R.id.model);
            model.setTextColor(colorID);
            TextView model2 = (TextView) itemView.findViewById(R.id.model2);
            model2.setTextColor(colorID);
            TextView model3 = (TextView) itemView.findViewById(R.id.model3);
            model3.setTextColor(colorID);
            TextView model4 = (TextView) itemView.findViewById(R.id.model4);
            model4.setTextColor(colorID);
            TextView address = (TextView) itemView.findViewById(R.id.address);
            address.setTextColor(colorID);
            TextView tv_distance = (TextView) itemView.findViewById(R.id.tv_distance);
            tv_distance.setTextColor(colorID);
            TextView tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_address.setTextColor(colorID);


            TextView tv_cooperation = (TextView) itemView.findViewById(R.id.tv_cooperation);
            tv_cooperation.setTextColor(colorID);

            TextView tv_oil1 = (TextView) itemView.findViewById(R.id.tv_oil1);
            tv_oil1.setTextColor(colorID);
            TextView tv_oil_model1 = (TextView) itemView.findViewById(R.id.tv_oil_model1);
            tv_oil_model1.setTextColor(colorID);
            TextView tv_oil2 = (TextView) itemView.findViewById(R.id.tv_oil2);
            tv_oil2.setTextColor(colorID);
            TextView tv_oil_model2 = (TextView) itemView.findViewById(R.id.tv_oil_model2);
            tv_oil_model2.setTextColor(colorID);
            TextView tv_oil3 = (TextView) itemView.findViewById(R.id.tv_oil3);
            tv_oil3.setTextColor(colorID);
            TextView tv_oil_model3 = (TextView) itemView.findViewById(R.id.tv_oil_model3);
            tv_oil_model3.setTextColor(colorID);
            TextView tv_oil4 = (TextView) itemView.findViewById(R.id.tv_oil4);
            tv_oil4.setTextColor(colorID);
            TextView tv_oil_model4 = (TextView) itemView.findViewById(R.id.tv_oil_model4);
            tv_oil_model4.setTextColor(colorID);

            address.setText(info.get(position).deptName);
            tv_distance.setText(info.get(position).distance);
            tv_address.setText(info.get(position).deptAddress);

            String cooperationState = info.get(position).cooperationState;
            if ("0".equals(cooperationState)) {
                tv_cooperation.setVisibility(View.INVISIBLE);
            } else {
                tv_cooperation.setVisibility(View.VISIBLE);
            }

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deptLatitude = info.get(position).deptLatitude;
                    deptLongitude = info.get(position).deptLongitude;
                    deptName = info.get(position).deptName;
                    if (BaiduNaviManager.isNaviInited()) {
                        Utils.showProgressDialog(mContext, "导航正在启动中，请稍后....");
                        routeplanToNavi(CoordinateType.GCJ02);
                    }
                }
            });

            if (info.get(position).productInfos == null || info.get(position).productInfos.size() == 0) {
                // return ;
            } else if (info.get(position).productInfos != null && info.get(position).productInfos.size() == 1) {
                tv_oil1.setText(info.get(position).productInfos.get(0).productName);
                tv_oil_model1.setText(info.get(position).productInfos.get(0).customerPrice);
            } else if (info.get(position).productInfos != null && info.get(position).productInfos.size() == 2) {
                tv_oil1.setText(info.get(position).productInfos.get(0).productName);
                tv_oil_model1.setText(info.get(position).productInfos.get(0).customerPrice);
                tv_oil2.setText(info.get(position).productInfos.get(1).productName);
                tv_oil_model2.setText(info.get(position).productInfos.get(1).customerPrice);
            } else if (info.get(position).productInfos != null && info.get(position).productInfos.size() == 3) {
                tv_oil1.setText(info.get(position).productInfos.get(0).productName);
                tv_oil_model1.setText(info.get(position).productInfos.get(0).customerPrice);
                tv_oil2.setText(info.get(position).productInfos.get(1).productName);
                tv_oil_model2.setText(info.get(position).productInfos.get(1).customerPrice);
                tv_oil3.setText(info.get(position).productInfos.get(2).productName);
                tv_oil_model3.setText(info.get(position).productInfos.get(2).customerPrice);
            } else if (info.get(position).productInfos != null && info.get(position).productInfos.size() == 4) {
                tv_oil1.setText(info.get(position).productInfos.get(0).productName);
                tv_oil_model1.setText(info.get(position).productInfos.get(0).customerPrice);
                tv_oil2.setText(info.get(position).productInfos.get(1).productName);
                tv_oil_model2.setText(info.get(position).productInfos.get(1).customerPrice);
                tv_oil3.setText(info.get(position).productInfos.get(2).productName);
                tv_oil_model3.setText(info.get(position).productInfos.get(2).customerPrice);
                tv_oil4.setText(info.get(position).productInfos.get(3).productName);
                tv_oil_model4.setText(info.get(position).productInfos.get(3).customerPrice);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (NearnestGasStationResponse.NearnestGasStationInfo lineDto : info) {
                        Log.e("tag--onItemClick->", lineDto.cooperationState);
                    }


                    String cooperationStated = info.get(position).cooperationState;
                    if ("0".equals(cooperationStated))   // 合作状态（0，非合作，1合作）
                    {
                        //TODO
                        tipDialog = new AlertDialog(mContext)
                                .setTitle(getString(R.string.global_tip))
                                .setMessage("非合作加油站，是否启用导航")
                                .setPositiveButtonClickListener(getString(R.string.global_sure), new View.OnClickListener(

                                ) {
                                    @Override
                                    public void onClick(View v) {
                                        deptLatitude = info.get(position).deptLatitude;
                                        deptLongitude = info.get(position).deptLongitude;
                                        deptName = info.get(position).deptName;
                                        tipDialog.dismiss();
                                        if (BaiduNaviManager.isNaviInited()) {
                                            Utils.showProgressDialog(mContext, "导航正在启动中，请稍后....");
                                            routeplanToNavi(CoordinateType.GCJ02);
                                        }
                                    }
                                })
                                .setNegativeButtonClickListener(getString(R.string.crop__cancel), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tipDialog.dismiss();
                                    }
                                });
                        tipDialog.show();
                    } else if ("1".equals(cooperationStated)) {
                        Intent intent = new Intent(mContext, GasStationDetailsActivity.class);
                        intent.putExtra("GAS_ID", info.get(position).id);
                        intent.putExtra("GAS_NAME", info.get(position).deptName);
                        startActivity(intent);
                    }
                }

            });
            ((ViewPager) container).addView(itemView, 0);
            return itemView;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view == object) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 语音提示的回调
     */
    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

        @Override
        public void stopTTS() {

        }

        @Override
        public void resumeTTS() {
        }

        @Override
        public void releaseTTSPlayer() {

        }

        @Override
        public int playTTSText(String speech, int bPreempt) {

            return 0;
        }

        @Override
        public void phoneHangUp() {

        }

        @Override
        public void phoneCalling() {
        }

        @Override
        public void pauseTTS() {
        }

        @Override
        public void initTTSPlayer() {
        }

        @Override
        public int getTTSState() {
            return 0;
        }
    };

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mMapView != null)
            mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
