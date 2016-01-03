package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.view.View;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.db.entity.Position;
import com.bluemobi.cnpc.network.request.NearnestGasStationRequest;
import com.bluemobi.cnpc.network.request.NearnestListGasStationRequest;
import com.bluemobi.cnpc.network.response.NearNotBdGasStationResponse;
import com.bluemobi.cnpc.network.response.NearnestGasStationResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/22.
 * p6 加油支付
 * modified by liufengyu the interface  alignment
 */

@ContentView(R.layout.activity_pay)
@PageName(R.string.search)
public class PayActivity extends BaseActivity{

    @Bind(R.id.plv_refresh)
    PullToRefreshListView plv_refresh;
    private TitleBarManager titleBarManager;
    private CommonAdapter<NearNotBdGasStationResponse.NearnestGasStationInfo> adapter;
    private List<NearNotBdGasStationResponse.NearnestGasStationInfo> dataLists = new ArrayList<NearNotBdGasStationResponse.NearnestGasStationInfo>();
    private String currentpage;

    /*public static PayActivity instance;


    public static PayActivity getInstance(){
        return instance;
    }
*/
    @Override
    protected void initBase() {
        titleBarManager = new TitleBarManager();
        titleBarManager.init(PayActivity.this,getSupportActionBar());
        titleBarManager.showSearchTitleBar(R.drawable.return_arrow,R.string.search);
        showLoadingPage(false);
        getPage(LOAD_REFRESH);
       /* instance = this;*/

    }

    @Override
    protected boolean getPage(int type) {
        if (!super.getPage(type)) return false;
        String searchEditText = titleBarManager.getSearchEditText();
        getDataServer(searchEditText);
        return true;
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        initPullToRefresh(plv_refresh);

    }

    public void getDataServer(String stationName) {
        Utils.showProgressDialog(mContext);
        NearnestListGasStationRequest request = new NearnestListGasStationRequest
                (
                        new Response.Listener<NearNotBdGasStationResponse>() {
                            @Override
                            public void onResponse(final NearNotBdGasStationResponse response) {
                                Utils.closeDialog();
                                plv_refresh.onRefreshComplete();
                                currentpage = response.data.getCurrentpage();
                                ArrayList<NearNotBdGasStationResponse.NearnestGasStationInfo> info = response.data.info;
                                wrapListView(info);
                            }
                        }, this);
       // lat-41.698479-lon-123.490136
        //TODO
        request.setLatitude(SharedPreferencesUtil.getFromFileByDefault(mContext,
                "latitude",Constants.DEFAULT_LATITUDE));
        request.setLongitude(SharedPreferencesUtil.getFromFileByDefault(mContext,
                "longitude", Constants.DEFAULT_LONGITUDE));
        /*request.setLatitude("41.698479");
        request.setLongitude("123.490136");*/

        if (StringUtils.isNotEmpty(stationName))
        {
            request.setStationName(stationName);
        }
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(getCurPage() + "");
        WebUtils.doPost(request);
    }


    private void wrapListView(ArrayList<NearNotBdGasStationResponse.NearnestGasStationInfo> info) {

        if (info == null) {
            return;
        }
        if (info.size() == 0) {
            return;
        }
        if (currentpage.equals("0")) {
            dataLists.clear();
        }
        for (NearNotBdGasStationResponse.NearnestGasStationInfo lineDto : info) {
            dataLists.add(lineDto);
        }

        if (adapter == null) {
            plv_refresh.setAdapter(
                    adapter = new CommonAdapter<NearNotBdGasStationResponse.NearnestGasStationInfo>(mContext, dataLists, R.layout.lv_pay) {
                        @Override
                        public void convert(ViewHolder helper, final NearNotBdGasStationResponse.NearnestGasStationInfo item) {
                            helper.setText(R.id.tv_deptName, item.deptName);

                            helper.setText(R.id.tv_distance, String.valueOf(new BigDecimal(item.distance).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                            if (StringUtils.isNotEmpty(item.deptAddress))
                            {
                                helper.setText(R.id.tv_address,item.deptAddress);
                            }

                            if (item.productInfoDTOs!=null&&item.productInfoDTOs.size()==1)
                                
                            {
                                helper.setText(R.id.model,"#");
                                helper.setText(R.id.tv_oil1, item.productInfoDTOs.get(0).productName);
                                helper.setText(R.id.tv_oil_model1, item.productInfoDTOs.get(0).customerPrice+"元/升");

                            }else if (item.productInfoDTOs!=null&&item.productInfoDTOs.size()==2)
                            {
                                helper.setText(R.id.model,"#");
                                helper.setText(R.id.model2,"#");
                                helper.setText(R.id.tv_oil1, item.productInfoDTOs.get(0).productName);
                                helper.setText(R.id.tv_oil_model1, item.productInfoDTOs.get(0).customerPrice+"元/升");
                                helper.setText(R.id.tv_oil2, item.productInfoDTOs.get(1).productName);
                                helper.setText(R.id.tv_oil_model2, item.productInfoDTOs.get(1).customerPrice+"元/升");
                                helper.setText(R.id.tv_oil3, item.productInfoDTOs.get(2).productName);
                                helper.setText(R.id.tv_oil_model3, item.productInfoDTOs.get(2).customerPrice+"元/升");
                                helper.setText(R.id.tv_oil4, item.productInfoDTOs.get(3).productName);
                                helper.setText(R.id.tv_oil_model4, item.productInfoDTOs.get(3).customerPrice+"元/升");
                            }else if (item.productInfoDTOs!=null&&item.productInfoDTOs.size()==3)
                            {
                                helper.setText(R.id.model,"#");
                                helper.setText(R.id.model2,"#");
                                helper.setText(R.id.model3,"#");
                                helper.setText(R.id.tv_oil1, item.productInfoDTOs.get(0).productName);
                                helper.setText(R.id.tv_oil_model1, item.productInfoDTOs.get(0).customerPrice+"元/升");
                                helper.setText(R.id.tv_oil2, item.productInfoDTOs.get(1).productName);
                                helper.setText(R.id.tv_oil_model2, item.productInfoDTOs.get(1).customerPrice+"元/升");
                                helper.setText(R.id.tv_oil3, item.productInfoDTOs.get(2).productName);
                                helper.setText(R.id.tv_oil_model3, item.productInfoDTOs.get(2).customerPrice+"元/升");
                            }else if (item.productInfoDTOs!=null&&item.productInfoDTOs.size()==4)
                            {
                                helper.setText(R.id.model,"#");
                                helper.setText(R.id.model2,"#");
                                helper.setText(R.id.model3,"#");
                                helper.setText(R.id.model4,"#");
                                helper.setText(R.id.tv_oil1, item.productInfoDTOs.get(0).productName);
                                helper.setText(R.id.tv_oil_model1, item.productInfoDTOs.get(0).customerPrice+"元/升");
                                helper.setText(R.id.tv_oil2, item.productInfoDTOs.get(1).productName);
                                helper.setText(R.id.tv_oil_model2, item.productInfoDTOs.get(1).customerPrice+"元/升");
                                helper.setText(R.id.tv_oil3, item.productInfoDTOs.get(2).productName);
                                helper.setText(R.id.tv_oil_model3, item.productInfoDTOs.get(2).customerPrice+"元/升");
                                helper.setText(R.id.tv_oil4, item.productInfoDTOs.get(3).productName);
                                helper.setText(R.id.tv_oil_model4, item.productInfoDTOs.get(3).customerPrice+"元/升");
                            }

                            helper.getView(R.id.details).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext,GasStationDetailsActivity.class);
                                    intent.putExtra("GAS_ID",item.id);
                                    intent.putExtra("GAS_NAME",item.deptName);

                                    startActivity(intent);
                                }
                            });
                        }
                    });
        } else {
            adapter.notifyDataSetChanged();
        }


    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    @Override
    public void clickBarHint() {
        if (dataLists!=null&&dataLists.size()>0)
         {
             dataLists.clear();
             if (adapter!=null)
             {
                 adapter.notifyDataSetChanged();
             }
         }
         getPage(LOAD_REFRESH);
        //titleBarManager.clearSearch();
    }
}
