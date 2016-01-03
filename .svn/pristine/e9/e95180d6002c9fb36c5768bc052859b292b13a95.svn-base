package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.db.entity.CityListDetail;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.request.GetValRequest;
import com.bluemobi.cnpc.network.request.RegisterRequest;
import com.bluemobi.cnpc.network.request.SunyardOilRequset;
import com.bluemobi.cnpc.network.response.GetValResponse;
import com.bluemobi.cnpc.network.response.RegisterResponse;
import com.bluemobi.cnpc.network.response.SunyardOilResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.LocationToggle;
import com.bluemobi.cnpc.util.NetworkManager;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.CustomSpinnerOption;
import com.bluemobi.cnpc.view.LoadingPage;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gaoyn on 2015/7/17.
 * <p/>
 * p2-1 注册
 */

@ContentView(R.layout.activity_registered)
public class RegisteredActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.registered_success)
    Button registered_success;

    @Bind(R.id.registration_agreement)
    TextView registration_agreement;

    @Bind(R.id.oil_model)
    CustomSpinnerOption spinner;

    protected
    @Bind(R.id.et_telNo)
    EditText et_telNo;

    protected
    @Bind(R.id.et_valNo)
    EditText et_valNo;

    protected
    @Bind(R.id.et_pwd)
    EditText et_pwds;

    protected
    @Bind(R.id.et_pwd_again)
    EditText et_pwd_again;

    protected
    @Bind(R.id.tv_getVal)
    TextView tv_getVal;

    private String currentPhone;
    private TimeCount time;


    private List<String> oilDataList = new ArrayList<String>();

    private Map<String, String> sunyardoils = new HashMap<String, String>();

    private String latitude;
    private String longitude;
    private String cityName;
    private String cityId;

    private NetworkManager networkManager;//开启定位

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(RegisteredActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.title_registered, R.drawable.return_arrow, true);
        showLoadingPage(false);
        networkManager = new NetworkManager(this, new LocationToggle(mContext));
        if (!Utils.isX86CPU())
            startLoc();

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
    }


    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        getSunyardOil();
        registered_success.setOnClickListener(this);
        registration_agreement.setOnClickListener(this);

        latitude = SharedPreferencesUtil.getFromFileByDefault(mContext, "latitude", Constants.DEFAULT_LATITUDE);
        longitude = SharedPreferencesUtil.getFromFileByDefault(mContext, "longitude", Constants.DEFAULT_LONGITUDE);
        cityName = SharedPreferencesUtil.getFromFileByDefault(mContext, "cityName", Constants.DEFAULT_CITYNAME);
        List<CityListDetail> cityListDetails = DataSupport.select("sid", "divisionname").where("divisionname like ?", cityName + '%').find(CityListDetail.class);

        // cityListDetails.get(0).getSid()
        //SharedPreferencesUtil.getFromFileByDefault(mContext,"",Constants.DEFAULTY_CITYCODE);

        if (cityListDetails != null && cityListDetails.size() > 0) {
            cityId = cityListDetails.get(0).getSid();
        } else {
            cityId = Constants.DEFAULTY_CITYCODE;
        }

        time = new TimeCount(120000, 1000);//构造CountDownTimer对象

    }


    public void getSunyardOil() {
        Utils.showProgressDialog(mContext);
        SunyardOilRequset request = new SunyardOilRequset
                (
                        new Response.Listener<SunyardOilResponse>() {
                            @Override
                            public void onResponse(SunyardOilResponse response) {
                                Utils.closeDialog();
                                ArrayList<SunyardOilResponse.SunyardOilDto> sunyardOilsData = response.data;
                                wrapOilsData(sunyardOilsData);


                            }


                        }, this);

        WebUtils.doPost(request);
    }

    private void wrapOilsData(ArrayList<SunyardOilResponse.SunyardOilDto> data) {
        for (int i = 0; i < data.size(); i++) {
            String temp = "92".equals(data.get(i).productName) ? "92#/93#" : "93".equals(data.get(i).productName) ? "92#/93#" :
                    "95".equals(data.get(i).productName) ? "95#/97#"
                            : "97".equals(data.get(i).productName) ? "95#/97#" : data.get(i).productName+"#";
            sunyardoils.put(temp, data.get(i).id);
            oilDataList.add(temp);
        }
        spinner.setDatas(oilDataList);
        spinner.setLabelText("关注油品");
        spinner.setLabelTextSize(13);
        spinner.setLabelTextColor(Color.parseColor("#323232"));
        spinner.setDefaultText();
        spinner.setSpinnerTextSize(13);
    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    @OnClick(R.id.tv_getVal)
    public void getVal() {

        if (StringUtils.isEmpty(et_telNo.getText().toString().trim())) {
            Toast.makeText(RegisteredActivity.this, "用户手机号不能为空", Toast.LENGTH_SHORT).show();
        } else {
            if (!Utils.checkPhoneNum(et_telNo.getText().toString())) {
                Toast.makeText(this, "手机号不合法", Toast.LENGTH_SHORT).show();
            } else {

                getValFromServer();
            }
        }

    }

    private void getValFromServer() {

        Utils.showProgressDialog(mContext);
        GetValRequest request = new GetValRequest
                (
                        new Response.Listener<GetValResponse>() {
                            @Override
                            public void onResponse(GetValResponse response) {
                                Utils.closeDialog();

                                GetValResponse.ValDateGet data = response.data;
                                time.start();
                                String value = data.value;
                                if (StringUtils.isEmpty(value)) {
                                    return;
                                } else {
                                    try {
                                        PreferencesService.getInstance(mContext).save("value", value);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                        }, this);
        request.setCellphone(et_telNo.getText().toString());
        WebUtils.doPost(request);


    }

    private boolean checkInput() {

        if (StringUtils.isEmpty(et_telNo.getText().toString().trim())) {
            Toast.makeText(RegisteredActivity.this, "用户手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.checkPhoneNum(et_telNo.getText().toString())) {
            Toast.makeText(this, "手机号不合法", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(et_valNo.getText().toString().trim())) {
            Toast.makeText(RegisteredActivity.this, "用户验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(et_pwds.getText().toString().trim())) {
            Toast.makeText(RegisteredActivity.this, "用户密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(et_pwd_again.getText().toString().trim())) {
            Toast.makeText(RegisteredActivity.this, "用户确认密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!et_pwds.getText().toString().trim().equals(et_pwd_again.getText().toString().trim())) {
            Toast.makeText(RegisteredActivity.this, "密码与确认密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.registered_success:

                if (checkInput()) {

                    Intent intent = new Intent();
                    intent.putExtra("setOilId", sunyardoils.get(spinner.getSpinnerText()));
                    intent.putExtra("setPassword",et_pwds.getText().toString().trim());
                    intent.putExtra("setCellphone",et_telNo.getText().toString().trim());
                    intent.putExtra("setValidationCode",et_valNo.getText().toString().trim());
                    intent.putExtra("setLongitude", longitude);
                    intent.putExtra("setLatitude", latitude);
                    intent.putExtra("timeLimit", cityId);
                    intent.setClass(this, SoftwareShareActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.registration_agreement:
                Utils.moveTo(this, RegistrationAgreementActivity.class);
                break;
        }


    }

    private void connectToServerRegister() {
        if (checkInput()) {

            Utils.showProgressDialog(mContext);
            RegisterRequest request = new RegisterRequest
                    (
                            new Response.Listener<RegisterResponse>() {
                                @Override
                                public void onResponse(RegisterResponse response) {
                                    Utils.closeDialog();
                                    RegisterResponse.RegisterData data = response.data;
                                    CnpcApplication.getInstance().setCheckID(data.id);
                                    Utils.moveTo(mContext, SoftwareShareActivity.class);
                                }

                            }, this);
            request.setOilId(sunyardoils.get(spinner.getSpinnerText()));
            request.setPassword(et_pwds.getText().toString().trim());
            request.setCellphone(et_telNo.getText().toString().trim());
            request.setValidationCode(et_valNo.getText().toString().trim());
            request.setLongitude(longitude);
            request.setLatitude(latitude);
            request.setAddressid(cityId);
            WebUtils.doPost(request);
        }
    }

    private class TimeCount extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

            tv_getVal.setClickable(false);
            tv_getVal.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {//计时完毕时触发
            tv_getVal.setText("重新验证");
            tv_getVal.setClickable(true);
        }
    }
}
