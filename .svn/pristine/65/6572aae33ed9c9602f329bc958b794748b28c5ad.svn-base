package com.bluemobi.cnpc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.VehicleTypeAdapter;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.db.entity.CarAllListDetail;
import com.bluemobi.cnpc.db.entity.PinyinComparator;
import com.bluemobi.cnpc.db.entity.VehicleType;
import com.bluemobi.cnpc.network.request.CarAllListRequest;
import com.bluemobi.cnpc.network.response.CarAllListResponse;
import com.bluemobi.cnpc.util.CharacterParser;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.ThreadManager;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.view.OnTouchingLetterChangedListener;
import com.bluemobi.cnpc.view.SideBar;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangzhijun on 2015/7/22.
 * P10_1_1_1车辆 品牌型号
 */
@ContentView(R.layout.activity_vehicle_type)
@PageName(R.string.mine_vehicle_type_title)
public class VehicleTypeActivity extends BaseActivity {

    @Bind(R.id.listView)
    protected ExpandableListView sortListView;

    @Bind(R.id.sideBar)
    protected SideBar sideBar;

    private VehicleTypeAdapter adapter;

    private static final int DATA_SUCCESS = 0x001;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    private List<VehicleType> SourceDateList;

    private List<VehicleType> tempDatList;

    private List<List<VehicleType>> children = new ArrayList<List<VehicleType>>();
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 根据a-z进行排序源数据

            Collections.sort(SourceDateList, pinyinComparator);

            adapter = new VehicleTypeAdapter(mContext, SourceDateList, children);
            sortListView.setAdapter(adapter);

            Utils.closeDialog();
        }
    };


    @Override
    protected void initBase() {
        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.mine_vehicle_type_title, R.drawable.return_arrow, true);
        initViews();
    }

    private void initViews() {
        //Utils.showProgressDialog(mContext);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s, String dis) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });


        sortListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String parentBrandName = SourceDateList.get(groupPosition).getBrandName();
                String parentBrandID = SourceDateList.get(groupPosition).getCarId();
                final String brandName = children.get(groupPosition).get(childPosition).getBrandName();
                final String brandID = children.get(groupPosition).get(childPosition).getCarId();
                Intent intent = new Intent();

                intent.putExtra("CAR_TYPE_RESULT", "CAR_TYPE_RESULT");
                intent.putExtra("parentBrandName", parentBrandName);
                intent.putExtra("parentBrandID", parentBrandID);
                intent.putExtra("type", brandName);
                intent.putExtra("brandID", brandID);
                setResult(Activity.RESULT_OK, intent);
                finish();
                return false;
            }
        });


        int result = 1;// DataSupport.where("level = ?", "1").count(CarAllListDetail.class);

        if (result == 0) {
            allCarFromServer();
        } else {
            Utils.showProgressDialog(mContext);
            new Thread() {
                @Override
                public void run() {
                    final List<CarAllListDetail> all = DataSupport.where("level = ?", "1").find(CarAllListDetail.class);
                    SourceDateList = filledData(all);
                    Message msg = Message.obtain();
                    msg.what = DATA_SUCCESS;
                    mHandler.sendMessage(msg);
                }
            }.start();


        }

    }

    public void allCarFromServer() {
        Utils.showProgressDialog(this);
        CarAllListRequest request = new CarAllListRequest(new Response.Listener<CarAllListResponse>() {
            @Override
            public void onResponse(CarAllListResponse response) {
                if (response != null && response.getStatus() == 0) {// success
                    saveCarIntoDb(response.data);
                    try {
                        PreferencesService.getInstance(VehicleTypeActivity.this).saveBool("isSucceed", true);
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

    private void saveCarIntoDb(final ArrayList<CarAllListResponse.AllCarDto> data) {
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

    /**
     * 为ListView填充数据
     *
     * @param data
     * @return
     */
    private List<VehicleType> filledData(List<CarAllListDetail> data) {
        List<VehicleType> mSortList = new ArrayList<VehicleType>();


        for (int i = 0; i < data.size(); i++) {
            List<CarAllListDetail> carAllListDetails = DataSupport.where("parentbrandcode = ?", data.get(i).getCarId()).find(CarAllListDetail.class);
            tempDatList = new ArrayList<VehicleType>();
            for (CarAllListDetail dto : carAllListDetails) {
                VehicleType vehicleType = new VehicleType();
                vehicleType.setBrandName(dto.getBrandName());
                vehicleType.setCarId(dto.getCarId());
                vehicleType.setParentBrandCode(dto.getParentBrandCode());
                vehicleType.setLevel(dto.getLevel());
                vehicleType.setIsSale(dto.getIsSale());
                vehicleType.setFrontLetter(dto.getFrontLetter());
                tempDatList.add(vehicleType);
            }
            children.add(tempDatList);
            VehicleType sortModel = new VehicleType();
            sortModel.setBrandName(data.get(i).getBrandName());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(data.get(i).getBrandName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
}
