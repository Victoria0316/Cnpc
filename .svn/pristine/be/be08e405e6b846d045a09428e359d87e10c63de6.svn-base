package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.request.CarDelRequest;
import com.bluemobi.cnpc.network.request.CarFindByIdRequest;
import com.bluemobi.cnpc.network.request.UserCarInfoRequest;
import com.bluemobi.cnpc.network.response.CarDelResponse;
import com.bluemobi.cnpc.network.response.CarDetailInfoResponse;
import com.bluemobi.cnpc.network.response.UserCarInfoResponse;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.InfoDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * P10_1 车辆列表
 * Created by wangzhijun on 2015/7/20.
 */
@ContentView(R.layout.activity_vehicle_list)
@PageName(R.string.mine_vehicle_list)
public class VehicleListActivity extends BaseActivity{


    @Bind(R.id.listView)
    protected SwipeMenuListView listView;

    private TempAdapter mAdapter;

    private InfoDialog mDialog;

    private  List<UserCarInfoResponse.UserCarInfoData> mCarListData = new ArrayList<UserCarInfoResponse.UserCarInfoData>();

    private  CarDetailInfoResponse.CarDetailInfoData data;

    private String str;

    @Override
    protected void initBase() {
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.mine_vehicle_list, R.drawable.return_arrow, true);
        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        initListSetting();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("tag","onResume");
//        if (mCarListData!=null&&mCarListData.size()>0)
//            mCarListData.clear();
        getCarListFromServer();
    }

    private void getCarListFromServer()
    {
        Utils.showProgressDialog(mContext);
        Log.e("tag--->", "getCarListFromServer");
        UserCarInfoRequest request = new UserCarInfoRequest(new Response.Listener<UserCarInfoResponse>() {
            @Override
            public void onResponse(UserCarInfoResponse response) {
                    Utils.closeDialog();

                    mCarListData.clear();
                    mCarListData.addAll(response.data);
                    wrapUserCarData(mCarListData);

            }
        }, this);
        request.setHandleCustomErr(false);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        WebUtils.doPost(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
    }

    private void wrapUserCarData(final List<UserCarInfoResponse.UserCarInfoData> dataParm)
    {

            mAdapter = new TempAdapter();
            listView.setAdapter(mAdapter);



    }


    private void initListSetting() {

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Log.e("tag--position->",position+"-->"+mCarListData.get(position).id);
                        delCarInfo(mCarListData.get(position).id, position);
                        break;
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.e("tag","setOnItemClickListener");
                if (position == mAdapter.getCount()-1) {
                    Utils.moveTo(VehicleListActivity.this, VehicleEditActivity.class);
                } else {
                    View messageView = LayoutInflater.from(VehicleListActivity.this).inflate(
                            R.layout.dialog_vehicle_info, null
                    );
                    String carID = mCarListData.get(position).id;
                    CarInfoDetail(messageView,carID);
                    mDialog = new InfoDialog(VehicleListActivity.this).setMessage(messageView);
                    mDialog.setNegagiveTextColor(getResources().getColor(R.color.common_blue));
                    mDialog.setNegativeButtonClickListener("编辑",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog.dismiss();
                                    Intent intent = new Intent(mContext,VehicleEditActivity.class);
                                    intent.putExtra("CAR_DETAIL_DATA",data);
                                    intent.putExtra("FROM_CAR_DETAIL","FROM_CAR_DETAIL");
                                    intent.putExtra("CAR_ID",mCarListData.get(position).id);
                                    startActivity(intent);
                                    //finish();
                                }
                            });
                    mDialog.show();
                }
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
                    case 0:
                        SwipeMenuItem openItem = new SwipeMenuItem(
                                getApplicationContext());
                        openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                                0xCE)));
                        openItem.setWidth(Utils.dip2px(VehicleListActivity.this, 90));
                        openItem.setTitle("Open");
                        openItem.setTitleSize(18);
                        openItem.setTitleColor(Color.WHITE);
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getApplicationContext());
                        // set item background
                        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                                0x3F, 0x25)));
                        deleteItem.setWidth(Utils.dip2px(VehicleListActivity.this, 60));
                        deleteItem.setIcon(R.drawable.home_badgeview);
                        menu.addMenuItem(deleteItem);
                        break;
                    case 1:
                        break;

                }
            }

        };

        listView.setMenuCreator(creator);
    }




    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    class TempAdapter extends BaseAdapter{


        public TempAdapter() {

        }

        @Override
        public int getCount() {
            return mCarListData.size()+1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
//            if(mCarListData.size()==0||position==mCarListData.size()){
//                return 2;
//            }else{
//                return 1;
//            }
            if(position != getCount()-1){
                return 0;
            }else{
                return 1;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            switch (getItemViewType(position)){
                case 0:
                    convertView = LayoutInflater.from(VehicleListActivity.this).inflate(
                            R.layout.adapter_vehicle, parent, false);
                    TextView tv_cardPlate = (TextView) convertView.findViewById(R.id.tv_cardPlate);
                    tv_cardPlate.setText(mCarListData.get(position).carPlate);

                    break;
                case 1:
                    convertView = LayoutInflater.from(VehicleListActivity.this).inflate(
                            R.layout.adapter_vehicle_add, parent, false);

                    break;
            }
            return convertView;
        }
    }


    private void delCarInfo(String carID,final int position)
    {
        Utils.showProgressDialog(mContext);
        CarDelRequest request = new CarDelRequest(new Response.Listener<CarDelResponse>() {
            @Override
            public void onResponse(CarDelResponse response) {
                Utils.closeDialog();
                Utils.makeToastAndShow(mContext, response.getMsg());
                mCarListData.remove(position);
                listView.setAdapter(mAdapter);
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setId(carID);
        WebUtils.doPost(request);
    }


    private void CarInfoDetail(final View view,String carID)
    {
        Utils.showProgressDialog(mContext);
        CarFindByIdRequest request = new CarFindByIdRequest(new Response.Listener<CarDetailInfoResponse>() {
            @Override
            public void onResponse(CarDetailInfoResponse response) {
                Utils.closeDialog();
                data = response.data;
                wrapDetail(view, data);
            }
        }, this);
        request.setId(carID);
        WebUtils.doPost(request);
    }

    private void wrapDetail(View view, CarDetailInfoResponse.CarDetailInfoData data) {

        TextView mine_vehicle_info_code = (TextView) view.findViewById(R.id.mine_vehicle_info_code);
        TextView mine_vehicle_info_type = (TextView) view.findViewById(R.id.mine_vehicle_info_type);
        mine_vehicle_info_code.setText(data.carPlate);
        mine_vehicle_info_type.setText(data.carBrandName + " " + data.carModelName);



    }

    @Override
    protected void onStop() {
        super.onStop();
    EventBus.getDefault().unregister(this);
    }

    @Override
    public void clickBarleft() {
        if (mCarListData!=null&&mCarListData.size()>0)
        {
            if(mCarListData.get(0).carPlate != null){
                str = mCarListData.get(0).carPlate;
            }
            if (str != null) {
                EventBus.getDefault().post(str);
            }

        }
        else
        {
            str = "";
            EventBus.getDefault().post(str);
        }


        super.clickBarleft();
    }

    @Override
    public void onBackPressed() {

        if (mCarListData!=null&&mCarListData.size()>0)
        {
            if(mCarListData.get(0).carPlate != null){
                str = mCarListData.get(0).carPlate;
            }
            if (str != null) {
                EventBus.getDefault().post(str);
            }
        }
        else
        {
            str = "";
            EventBus.getDefault().post(str);
        }
        super.onBackPressed();

        /*finishAll();
        Intent intent  = new Intent();
        intent.putExtra("setTabSelection",3);
        intent.setClass(this, HomeActivity.class);
        startActivity(intent);*/
    }
}
