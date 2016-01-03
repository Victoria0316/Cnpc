package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.Response;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.db.entity.Position;
import com.bluemobi.cnpc.network.request.NearnestGasStationRequest;
import com.bluemobi.cnpc.network.response.NearnestGasStationResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.AlertDialog;
import com.bluemobi.cnpc.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/10/21.
 *
 * 地图列表
 */

@ContentView(R.layout.activity_map_list)
@PageName(R.string.map_list_title)
public class MapListActivity extends BaseActivity {

    @Bind(R.id.plv_refresh)
    PullToRefreshListView plv_refresh;

    private CommonAdapter<NearnestGasStationResponse.NearnestGasStationInfo> adapter;
    private List<NearnestGasStationResponse.NearnestGasStationInfo> dataLists = new ArrayList<NearnestGasStationResponse.NearnestGasStationInfo>();

    private String currentpage;

    TitleBarManager titleBarManager;

    private List<Position> cooperGasStations;

    private List<Position> noCooperGasStations;

    private String deptLatitude;
    private String deptLongitude;
    private String deptName;

    private   AlertDialog  tipDialog;

    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    @Override
    protected void initBase() {

        titleBarManager = new TitleBarManager();
        titleBarManager.init(MapListActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.map_list_title, R.drawable.return_arrow, true);
        showLoadingPage(false);

        cooperGasStations = new ArrayList<Position>();
        noCooperGasStations = new ArrayList<Position>();

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        initPullToRefresh(plv_refresh);
        getPage(LOAD_REFRESH);

        plv_refresh.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                for (NearnestGasStationResponse.NearnestGasStationInfo lineDto : dataLists) {
                    Log.e("tag--onItemClick->", lineDto.cooperationState);
                }

                //Log.e("tag-onItemClick-position->",position+"");

                String cooperationStated = dataLists.get(position - 1).cooperationState;
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
                                    deptLatitude = dataLists.get(position - 1).deptLatitude;
                                    deptLongitude = dataLists.get(position - 1).deptLongitude;
                                    deptName = dataLists.get(position - 1).deptName;
                                    tipDialog.dismiss();
                                    if (BaiduNaviManager.isNaviInited()) {
                                        Utils.showProgressDialog(mContext, "导航正在启动中，请稍后....");
                                        routeplanToNavi(BNRoutePlanNode.CoordinateType.GCJ02);
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
                    intent.putExtra("GAS_ID", dataLists.get(position - 1).id);
                    intent.putExtra("GAS_NAME", dataLists.get(position - 1).deptName);
                    startActivity(intent);

                }
            }
        });

    }

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
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
        }
    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            Utils.closeDialog();
            Intent intent = new Intent(mContext, BNDemoGuideActivity.class);
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


    @Override
    protected boolean getPage(int type) {
        if (!super.getPage(type)) return false;
        getDataServer();
        return true;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return  LoadingPage.LoadResult.success;
    }

    public void getDataServer() {
        Utils.showProgressDialog(mContext);
        NearnestGasStationRequest request = new NearnestGasStationRequest
                (
                        new Response.Listener<NearnestGasStationResponse>() {
                            @Override
                            public void onResponse(final NearnestGasStationResponse response) {
                                Utils.closeDialog();
                                plv_refresh.onRefreshComplete();
                                currentpage = response.data.getCurrentpage();
                                if (cooperGasStations!=null&&cooperGasStations.size()>0)
                                    cooperGasStations.clear();
                                ArrayList<NearnestGasStationResponse.NearnestGasStationInfo> info = response.data.info;
                                for (NearnestGasStationResponse.NearnestGasStationInfo bean : info) {
                                    Position position = new Position();
                                    position.setLatitude(Double.parseDouble(bean.deptLatitude));
                                    position.setLongitude(Double.parseDouble(bean.deptLongitude));
                                    position.setDeptName(bean.deptName);
                                    position.setCooper(bean.cooperationState);
                                    cooperGasStations.add(position);
                                }

                                //addOverlays(cooperGasStations);
                                wrapListView(info);

                            }
                        }, this);
        request.setLatitude(SharedPreferencesUtil.getFromFileByDefault(mContext,
                "latitude", Constants.DEFAULT_LATITUDE));
        request.setLongitude(SharedPreferencesUtil.getFromFileByDefault(mContext,
                "longitude", Constants.DEFAULT_LONGITUDE));
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(getCurPage() + "");
        request.setCityName(SharedPreferencesUtil.getFromFileByDefault(mContext,
                "cityName", Constants.DEFAULT_CITYNAME)); // 	是 	地市名称
        request.setProvinceName(SharedPreferencesUtil.getFromFileByDefault(mContext,
                "province", Constants.DEFAULT_CITYNAME)); // 	是 	省份名称
        WebUtils.doPost(request);
    }



    private void wrapListView(ArrayList<NearnestGasStationResponse.NearnestGasStationInfo> info) {
        if (info == null) {
            return;
        }
        if (info.size() == 0) {
            return;
        }

        if (currentpage.equals("0")) {
            dataLists.clear();
        }

        for (NearnestGasStationResponse.NearnestGasStationInfo lineDto : info) {
            dataLists.add(lineDto);
        }

        for (NearnestGasStationResponse.NearnestGasStationInfo lineDto : dataLists)
        {
            Log.e("tag--cooperationState->", lineDto.cooperationState);
        }

        Log.e("tag dataLists-->", dataLists.size() + "");

        if (adapter == null) {
            plv_refresh.setAdapter(
                    adapter = new CommonAdapter<NearnestGasStationResponse.NearnestGasStationInfo>(mContext, dataLists, R.layout.lv_lbs_gas_station) {
                        @Override
                        public void convert(ViewHolder helper, final NearnestGasStationResponse.NearnestGasStationInfo item) {
                            int colorID = 0;
                            if ("0".equals(item.cooperationState))
                            {
                                colorID = getResources().getColor(R.color.common_blue);
                            }
                            else if ("1".equals(item.cooperationState))
                            {
                                colorID = getResources().getColor(R.color.common_red);
                            }
                            TextView details = helper.getView(R.id.details);
                            details.setTextColor(colorID);
                            helper.setTextColor(R.id.model, colorID);
                            helper.setTextColor(R.id.model2, colorID);
                            helper.setTextColor(R.id.model3, colorID);
                            helper.setTextColor(R.id.model4, colorID);
                            helper.setTextColor(R.id.address, colorID);
                            helper.setTextColor(R.id.tv_distance, colorID);
                            helper.setTextColor(R.id.tv_address, colorID);
                            TextView tv_cooperation = helper.getView(R.id.tv_cooperation);
                            tv_cooperation.setTextColor(colorID);
                            helper.setTextColor(R.id.tv_oil1, colorID);
                            helper.setTextColor(R.id.tv_oil_model1, colorID);
                            helper.setTextColor(R.id.tv_oil2, colorID);
                            helper.setTextColor(R.id.tv_oil_model2, colorID);
                            helper.setTextColor(R.id.tv_oil3, colorID);
                            helper.setTextColor(R.id.tv_oil_model3, colorID);
                            helper.setTextColor(R.id.tv_oil4, colorID);
                            helper.setTextColor(R.id.tv_oil_model4, colorID);
                            helper.setText(R.id.address, item.deptName);
                            helper.setText(R.id.tv_distance, item.distance);
                            helper.setText(R.id.tv_address, item.deptAddress);
                            String cooperationState = item.cooperationState;
                            if ("0".equals(cooperationState)) {
                                tv_cooperation.setVisibility(View.INVISIBLE);
                            } else {
                                tv_cooperation.setVisibility(View.VISIBLE);
                            }

                            details.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    deptLatitude = item.deptLatitude;
                                    deptLongitude = item.deptLongitude;
                                    deptName = item.deptName;
                                    if (BaiduNaviManager.isNaviInited()) {
                                        Utils.showProgressDialog(mContext, "导航正在启动中，请稍后....");
                                        routeplanToNavi(BNRoutePlanNode.CoordinateType.GCJ02);
                                    }
                                }
                            });

                            if (item.productInfos==null||item.productInfos.size()==0)
                            {
                                return;
                            }
                            else if (item.productInfos!=null&&item.productInfos.size()==1)
                            {
                                helper.setText(R.id.tv_oil1, item.productInfos.get(0).productName);
                                helper.setText(R.id.tv_oil_model1, item.productInfos.get(0).customerPrice);
                            }
                            else if (item.productInfos!=null&&item.productInfos.size()==2)
                            {
                                helper.setText(R.id.tv_oil1, item.productInfos.get(0).productName);
                                helper.setText(R.id.tv_oil_model1, item.productInfos.get(0).customerPrice);
                                helper.setText(R.id.tv_oil2, item.productInfos.get(1).productName);
                                helper.setText(R.id.tv_oil_model2, item.productInfos.get(1).customerPrice);
                            }
                            else if (item.productInfos!=null&&item.productInfos.size()==3)
                            {
                                helper.setText(R.id.tv_oil1, item.productInfos.get(0).productName);
                                helper.setText(R.id.tv_oil_model1, item.productInfos.get(0).customerPrice);
                                helper.setText(R.id.tv_oil2, item.productInfos.get(1).productName);
                                helper.setText(R.id.tv_oil_model2, item.productInfos.get(1).customerPrice);
                                helper.setText(R.id.tv_oil3, item.productInfos.get(2).productName);
                                helper.setText(R.id.tv_oil_model3, item.productInfos.get(2).customerPrice);
                            }
                            else if (item.productInfos!=null&&item.productInfos.size()==4)
                            {
                                helper.setText(R.id.tv_oil1, item.productInfos.get(0).productName);
                                helper.setText(R.id.tv_oil_model1, item.productInfos.get(0).customerPrice);
                                helper.setText(R.id.tv_oil2, item.productInfos.get(1).productName);
                                helper.setText(R.id.tv_oil_model2, item.productInfos.get(1).customerPrice);
                                helper.setText(R.id.tv_oil3, item.productInfos.get(2).productName);
                                helper.setText(R.id.tv_oil_model3, item.productInfos.get(2).customerPrice);
                                helper.setText(R.id.tv_oil4, item.productInfos.get(3).productName);
                                helper.setText(R.id.tv_oil_model4, item.productInfos.get(3).customerPrice);
                            }




                        }
                    });


        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
