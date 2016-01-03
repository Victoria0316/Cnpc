package com.bluemobi.cnpc.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.OptionsPopupWindow;
import com.bluemobi.cnpc.network.request.SaveCarRequest;
import com.bluemobi.cnpc.network.request.UpdateCarRequest;
import com.bluemobi.cnpc.network.request.UserCarInfoRequest;
import com.bluemobi.cnpc.network.response.CarDetailInfoResponse;
import com.bluemobi.cnpc.network.response.SaveCarResponse;
import com.bluemobi.cnpc.network.response.UserCarInfoResponse;
import com.bluemobi.cnpc.network.response.UserCenterResponse;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.GasText;
import com.bluemobi.cnpc.view.LoadingPage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangzhijun on 2015/7/21.
 */
@ContentView(R.layout.activity_vehicle_edit)
@PageName(R.string.mine_vehicle_edit_hint)
public class VehicleEditActivity extends BaseActivity {
    /**
     * 车牌号
     */
    @Bind(R.id.vehicle_code_edit)
    EditText vehicle_code_edit;
    /**
     * 车辆类型
     */
    @Bind(R.id.vehicle_type_rl)
    RelativeLayout vehicle_type_rl;

    @Bind(R.id.vehicle_type_val)
    TextView vehicle_type_val;

/*    *//**
     * 加注油品
     *//*
    @Bind(R.id.vehicle_oil_val)
    TextView vehicle_oil_val;
    *//**
     * 购买时间
     *//*
    @Bind(R.id.vehicle_buy_time_val)
    TextView vehicle_buy_time_val;

    *//**
     * 购买时间
     *//*
    @Bind(R.id.vehicle_buy_time_rl)
    RelativeLayout vehicle_buy_time_rl;

    @Bind(R.id.vehicle_safe_endtime_rl)
    RelativeLayout vehicle_safe_endtime_rl;

    *//**
     * 保险到期时间
     *//*
    @Bind(R.id.vehicle_safe_endtime_val)
    TextView vehicle_safe_endtime_val;
    *//**
     * 购买价钱
     *//*
    @Bind(R.id.vehicle_price_edit)
    EditText vehicle_price_edit;
    *//**
     * 保险公司
     *//*
    @Bind(R.id.vehicle_safe_company_edit)
    TextView vehicle_safe_company_edit;
    *//**
     * 车架后六位
     *//*
    @Bind(R.id.vehicle_last_six_edit)
    EditText vehicle_last_six_edit;
    *//**
     * 车辆注册地
     *//*
    @Bind(R.id.vehicle_reg_addr_rl)
    RelativeLayout vehicle_reg_addr_rl;

    *//**
     * 车辆注册地
     *//*
    @Bind(R.id.vehicle_reg_addr_edit)
    TextView vehicle_reg_addr_edit;

    */
    /**
     * 汽车排量
     *//*
    @Bind(R.id.vehicle_liter_edit)
    TextView vehicle_liter_edit;

    @Bind(R.id.vehicle_liter_rl)
    RelativeLayout vehicle_liter_rl;*/

    @Bind(R.id.submit)
    TextView submit;
    private AlertDialog dialog;
    private View view;
    private GridView gridView;
    private static final int GET_TYPE = 0;
    private static final int CHANGE_CITY_TYPE = 1;

    private ArrayList<UserCenterResponse.CheckBoxDTO> checkBoxDTOs;

    private Map<String, String> sunyardoils = new HashMap<String, String>();

    private CommonAdapter<UserCenterResponse.CheckBoxDTO> mAdapter;
    private boolean isFrist = true;
    private String tx;
    private String brandID;
    private String parentBrandName;
    private String parentBrandID;
    private ArrayList<String> options1Items = new ArrayList<String>();
    private OptionsPopupWindow pwOptions;
    private OptionsPopupWindow pwOptionCarInsure;
    private UserCenterResponse.CheckBoxDTO checkBoxDTO;
    private String registerCityID;
    private CarDetailInfoResponse.CarDetailInfoData car_detail_data;
    private boolean isEdit = false;
    private ArrayList<UserCenterResponse.BusinessDictionaryInfoDTO> businessDictionaryInfoDTOs;
    private List<String> businessDictionary;
    private String businessDictionaryTx;
    private Map<String, String> businessDictionaryMap;
    private String carModel;
    private String carID;
    private String type;
    private String back_city_type;
    private String car_type_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initBase() {
        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        checkBoxDTOs = CnpcApplication.getInstance().getmCheckBoxDTO();
        for (int i = 0; i < checkBoxDTOs.size(); i++) {
            sunyardoils.put(checkBoxDTOs.get(i).name, checkBoxDTOs.get(i).id);
        }

        String from_car_detail = getIntent().getStringExtra("FROM_CAR_DETAIL");
        if ("FROM_CAR_DETAIL".equals(from_car_detail)) {
            car_detail_data =
                    (CarDetailInfoResponse.CarDetailInfoData) getIntent().getSerializableExtra("CAR_DETAIL_DATA");
            titleBarManager.showCommonTitleBar(R.string.mine_vehicle_edit_hint, R.drawable.return_arrow, true);

            carID = getIntent().getStringExtra("CAR_ID");
            isEdit = true;
        } else {

            titleBarManager.showCommonTitleBar(R.string.mine_vehicle_hint, R.drawable.return_arrow, true);
            isEdit = false;
        }
        if (isEdit) {
            if (car_detail_data == null) return;
            vehicle_code_edit.setText(car_detail_data.carPlate);
            vehicle_type_val.setText(car_detail_data.carBrandName + "/" + car_detail_data.carModelName);
           /* vehicle_oil_val.setText(car_detail_data.oilName + "#");
            vehicle_buy_time_val.setText(car_detail_data.purchaseDate);
            vehicle_safe_endtime_val.setText(car_detail_data.insuranceExpiryDate);
            vehicle_price_edit.setText(car_detail_data.carPrice);
            vehicle_safe_company_edit.setText(car_detail_data.insurerName);
            vehicle_last_six_edit.setText(car_detail_data.vinNo);
            vehicle_reg_addr_edit.setText(car_detail_data.registeredLocationName);
            vehicle_liter_edit.setText(car_detail_data.carDisplacement);*/
        }

        businessDictionaryInfoDTOs = CnpcApplication.getInstance().getmBusinessDictionaryInfoDTOs();
        businessDictionary = new ArrayList<String>();

        businessDictionaryMap = new HashMap<String, String>();
        for (UserCenterResponse.BusinessDictionaryInfoDTO dto : businessDictionaryInfoDTOs) {
            businessDictionary.add(dto.dictionaryName);
            businessDictionaryMap.put(dto.dictionaryName, dto.dictionaryCode);
        }
        pwOptions = new OptionsPopupWindow(this, R.layout.pw_options, true);
        pwOptionCarInsure = new OptionsPopupWindow(this, R.layout.pw_options, true);
        String[] stringArray = getResources().getStringArray(R.array.oil_liter);
        for (int i = 0; i < stringArray.length; i++) {
            options1Items.add(stringArray[i]);
        }
        pwOptions.setPicker(options1Items);
        pwOptionCarInsure.setPicker((ArrayList<String>) businessDictionary);

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

/*

    @OnClick(R.id.vehicle_buy_time_rl)
    public void vehicleBuyTime() {
        pickDateDialog(R.id.vehicle_buy_time_val);
    }

    @OnClick(R.id.vehicle_safe_endtime_rl)
    public void vehicleSafeEndTime() {
        pickDateDialog(R.id.vehicle_safe_endtime_val);
    }


    @OnClick(R.id.vehicle_reg_addr_rl)
    public void vehicleRegAddr() {
        Log.e("tag--onclick", "click");
        Intent intent = new Intent();
        intent.setClass(this, CityChangeActivity.class);
        intent.putExtra("change_city", "change_city");
        startActivityForResult(intent, CHANGE_CITY_TYPE);
    }
*/

  /*  *//**
     * 汽车排量
     *//*
    @OnClick(R.id.vehicle_liter_rl)
    public void vehicle_liter_rl() {

        pwOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                tx = options1Items.get(options1);
                vehicle_liter_edit.setText(tx);
            }
        });
        pwOptions.setSelectOptions(0);
        pwOptions.backgroundAlpha(0.7f);
        pwOptions.showAtLocation(vehicle_liter_rl, Gravity.BOTTOM, 0, 0);
    }

    *//**
     * 汽车排量
     *//*
    @OnClick(R.id.vehicle_safe_company_rl)
    public void vehicleSafeCompany() {

        pwOptionCarInsure.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                businessDictionaryTx = businessDictionary.get(options1);
                vehicle_safe_company_edit.setText(businessDictionaryTx);
            }
        });
        pwOptionCarInsure.setSelectOptions(1);
        pwOptionCarInsure.backgroundAlpha(0.7f);
        pwOptionCarInsure.showAtLocation(vehicle_liter_rl, Gravity.BOTTOM, 0, 0);
    }
*/

    /**
     * 车辆类型
     */
    @OnClick(R.id.vehicle_type_rl)
    public void selectType() {
        Intent intent = new Intent();
        intent.setClass(this, VehicleTypeActivity.class);
        startActivityForResult(intent, GET_TYPE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (resultCode == Activity.RESULT_OK) {
            if (GET_TYPE == requestCode) {
                car_type_result = result.getStringExtra("CAR_TYPE_RESULT");
                carModel = result.getStringExtra("type");
                brandID = result.getStringExtra("brandID");
                parentBrandName = result.getStringExtra("parentBrandName");
                parentBrandID = result.getStringExtra("parentBrandID");
                if (StringUtils.isNotEmpty(carModel)) {
                    vehicle_type_val.setText(parentBrandName + "/" + carModel);
                }
            }

          /*  if (CHANGE_CITY_TYPE == requestCode) {

                back_city_type = result.getStringExtra("BACK_CITY_TYPE");
                type = result.getStringExtra("backcity");
                registerCityID = result.getStringExtra("backcityID");
                if (StringUtils.isNotEmpty(type)) {
                    vehicle_reg_addr_edit.setText(type);
                }
            }*/

        }
    }



   /* */
    /**
     * 加注油品
     *//*
    @OnClick(R.id.vehicle_oil_rl)
    public void selectOil() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .create();
            view = LayoutInflater.from(this).inflate(R.layout.dialog_oil_select, null);
            gridView = (GridView) view.findViewById(R.id.gridview);
            gridView.setAdapter(mAdapter = new CommonAdapter<UserCenterResponse.CheckBoxDTO>(mContext, checkBoxDTOs, R.layout.adapter_grid_oil) {
                @Override
                public void convert(ViewHolder helper, UserCenterResponse.CheckBoxDTO item) {
                    GasText gasText = helper.getView(R.id.val);
                    gasText.setText(item.name);
                    RelativeLayout rl = (RelativeLayout) helper.getView(R.id.item_bg);

                    if (lastSelected == helper.getPosition()) {
                        rl.setBackgroundResource(R.drawable.item_sel);
                    } else {
                        rl.setBackgroundResource(R.drawable.item_unsel);
                    }


                }
            });
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    isFrist = false;
                    if (lastSelected != position) {
                        lastSelected = position;
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
            window.setWindowAnimations(R.style.dialog_bottom_style);  //添加动画
            dialog.show();
            dialog.setContentView(view);
            initGrid(view);
        } else {
            dialog.show();
        }
    }
*/

   /* private void initGrid(View view) {
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                checkBoxDTO = checkBoxDTOs.get(lastSelected);
                vehicle_oil_val.setText(checkBoxDTO.name+"#");

            }
        });
    }*/

    int lastSelected = 0;


    /**
     * 保存汽车
     */
    private void saveCarToServer() {
        Utils.showProgressDialog(mContext);
        SaveCarRequest request = new SaveCarRequest(new Response.Listener<SaveCarResponse>() {
            @Override
            public void onResponse(SaveCarResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(mContext, "保存成功");
                    finish();
                }
            }
        }, this);
        //TODO
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setCarPlate(vehicle_code_edit.getText().toString().trim());
        request.setCarModelName(vehicle_type_val.getText().toString().trim());
/*        request.setOilId(checkBoxDTO.id);
        request.setOilName(checkBoxDTO.name);*/
        request.setCarBrandId(parentBrandID);
        request.setCarBrandName(parentBrandName);
        request.setCarModelId(brandID);
        request.setCarModelName(carModel);
/*        request.setInsurerId(businessDictionaryMap.get(businessDictionaryTx));
        request.setInsurerName(businessDictionaryTx);
        request.setRegisteredLocationId(registerCityID);
        request.setCarDisplacement(tx);
        request.setPurchaseDate(vehicle_buy_time_val.getText().toString().trim());
        request.setInsuranceExpiryDate(vehicle_safe_endtime_val.getText().toString().trim());
        request.setCarPrice(vehicle_price_edit.getText().toString().trim());
        request.setRegisteredLocationName(vehicle_reg_addr_edit.getText().toString().trim());
        request.setVinNo(vehicle_last_six_edit.getText().toString().trim());*/
        WebUtils.doPost(request);
    }


    private void updateCarInfo() {
        Utils.showProgressDialog(mContext);
        UpdateCarRequest request = new UpdateCarRequest(new Response.Listener<SaveCarResponse>() {
            @Override
            public void onResponse(SaveCarResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(mContext, "保存成功");
                    finish();
                }
            }
        }, this);
        //TODO
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setCarPlate(vehicle_code_edit.getText().toString().trim());
        request.setCarModelName(vehicle_type_val.getText().toString().trim());
       /* String oilVal = vehicle_oil_val.getText().toString().trim();
        Log.e("tag--oilVal", oilVal);
        String[] split = null;
        if (oilVal.length() == 3) {
            //95#
            split = oilVal.split("#");
        }
        request.setOilName(split[0]);
        request.setOilId(sunyardoils.get(split[0]));
*/

        if ("CAR_TYPE_RESULT".equals(car_type_result)) {
            request.setCarBrandId(parentBrandID);
            request.setCarBrandName(parentBrandName);
            request.setCarModelId(brandID);
            request.setCarModelName(carModel);
        } else {
            request.setCarBrandId(car_detail_data.carBrandId);
            request.setCarBrandName(car_detail_data.carBrandName);
            request.setCarModelId(car_detail_data.carModelId);
            request.setCarModelName(car_detail_data.carModelName);
        }
     /*   String safeCompanyedit = vehicle_safe_company_edit.getText().toString().trim();
        request.setInsurerId(businessDictionaryMap.get(safeCompanyedit));
        request.setInsurerName(safeCompanyedit);

        if ("BACK_CITY_TYPE".equals(back_city_type)) {
            request.setRegisteredLocationId(registerCityID);
        } else {
            request.setRegisteredLocationId(car_detail_data.registeredLocationId);
        }

        request.setCarDisplacement(vehicle_liter_edit.getText().toString().trim());//排量*/
        request.setId(carID);

/*        request.setPurchaseDate(vehicle_buy_time_val.getText().toString().trim());
        request.setInsuranceExpiryDate(vehicle_safe_endtime_val.getText().toString().trim());
        request.setCarPrice(vehicle_price_edit.getText().toString().trim());
        request.setRegisteredLocationName(vehicle_reg_addr_edit.getText().toString().trim());
        request.setVinNo(vehicle_last_six_edit.getText().toString().trim());*/
        WebUtils.doPost(request);
    }

    @OnClick(R.id.submit)
    public void submit() {
        /*if (!checkUserInfo(vehicle_code_edit)) {
            return;
        }
        if (!checkUserInfo(vehicle_type_val)) {
            return;
        }*/
       /* if (!checkUserInfo(vehicle_buy_time_val)) {
            return;
        }
        if (!checkUserInfo(vehicle_safe_endtime_val)) {
            return;
        }
        if (!checkUserInfo(vehicle_price_edit)) {
            return;
        }
        if (!checkUserInfo(vehicle_reg_addr_edit)) {
            return;
        }
        if (!checkUserInfo(vehicle_last_six_edit)) {
            return;
        }*/
        if (isEdit) {
            if (checkUserPas()) {
                updateCarInfo();
            }
        } else {

            if (checkUserPas()) {
                saveCarToServer();
            }

        }

    }

    private boolean checkUserPas() {

        if (StringUtils.isEmpty(vehicle_code_edit.getText().toString())) {
            Toast.makeText(VehicleEditActivity.this, "车牌号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.checkCarNum(vehicle_code_edit.getText().toString())) {
            Toast.makeText(VehicleEditActivity.this, "车牌号不合法", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(vehicle_type_val.getText().toString())) {
            Toast.makeText(VehicleEditActivity.this, "车辆品牌/型号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

   /* private boolean checkUserInfo(View inputView) {
        String inputStr = "";
        if (inputView instanceof TextView) {
            inputStr = ((TextView) inputView).getText().toString().trim();
        } else if (inputView instanceof EditText) {
            inputStr = ((EditText) inputView).getText().toString().trim();
        }
        if (StringUtils.isEmpty(inputStr)) {
            Utils.makeToastAndShow(mContext, "请将页面内容输入完整，在尝试提交");
            return false;
        } else {
            return true;
        }
    }*/

}
