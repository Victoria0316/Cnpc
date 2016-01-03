package com.bluemobi.cnpc.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.request.CustomerInfoUpdateRequest;
import com.bluemobi.cnpc.network.request.UploadHeadRequest;
import com.bluemobi.cnpc.network.response.CustomerInfoUpdateResponse;
import com.bluemobi.cnpc.network.response.UploadHeadResponse;
import com.bluemobi.cnpc.network.response.UserCenterResponse;
import com.bluemobi.cnpc.util.Base64;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.view.SelectPicPopupWindow;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * P10_1 个人信息编辑
 * Created by wangzhijun on 2015/7/20.
 */
@ContentView(R.layout.activity_person_info)
@PageName(R.string.mine_person_info)
public class PersonInfoActivity extends BaseActivity {
    /**
     * 头像
     */
    protected
    @Bind(R.id.user_avatar)
    ImageView user_avatar;
    /**
     * 姓名
     */
    protected
    @Bind(R.id.user_name)
    EditText user_name;
    /**
     * 性别选择
     */
    protected
    @Bind(R.id.gender_group)
    RadioGroup gender_group;
    /**
     * 男
     */
    protected
    @Bind(R.id.gender_man)
    RadioButton gender_man;
    /**
     * 女
     */
    protected
    @Bind(R.id.gender_woman)
    RadioButton gender_woman;
    /**
     * 手机
     */
    protected
    @Bind(R.id.user_phone)
    EditText user_phone;
    /**
     * 关注
     */
    protected
    @Bind(R.id.attention)
    TextView attention;

    /**
     * 车辆信息
     */
    protected
    @Bind(R.id.vehicle)
    TextView vehicle;

    protected
    @Bind(R.id.vehicle_hint)
    TextView vehicle_hint;

    /**
     * dialog view
     */
    private View view;

    private GridView gridView;

    private AlertDialog dialog;

    private static final int ASK_VEHICLE = 0;

    public SelectPicPopupWindow pw;

    private CommonAdapter<UserCenterResponse.CheckBoxDTO> mAdapter;

    private ArrayList<UserCenterResponse.CheckBoxDTO> checkBoxDTOs;

    private Map<String, String> sunyardoils = new HashMap<String, String>();

    private boolean isFrist = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    private String carPlate = "";

    private boolean isFromEvent = false;

    private UserCenterResponse.UserCenterData bean;

    public void onEvent(String str) {

        this.carPlate = str;
        if (StringUtils.isNotEmpty(str))
        {
            vehicle_hint.setText(carPlate);
            isFromEvent = true;
            carPlate = "";
        }
        else
        {
            vehicle_hint.setText("");
        }

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
        titleBarManager.showCommonTitleBar(R.string.mine_person_info, R.drawable.return_arrow, true);

        checkBoxDTOs = CnpcApplication.getInstance().getmCheckBoxDTO();
        if (checkBoxDTOs != null) {
            for (int i = 0; i < checkBoxDTOs.size(); i++) {
                sunyardoils.put(checkBoxDTOs.get(i).name, checkBoxDTOs.get(i).id);
                if ("1".equals(checkBoxDTOs.get(i).status)) {
                    attention.setText(checkBoxDTOs.get(i).name);
                }
            }
        }


        Intent intent = getIntent();
         bean = (UserCenterResponse.UserCenterData) intent.getSerializableExtra("mData");
        ArrayList<UserCenterResponse.CheckBoxDTO> checkBoxDTOs = bean.checkBoxDTOs;

        for (UserCenterResponse.CheckBoxDTO item : checkBoxDTOs) {
            if ("1".equals(item.status)) {
                String temp = "92".equals(item.name) ? "92#/93#" : "93".equals(item.name) ? "92#/93#" :
                        "95".equals(item.name) ? "95#/97#"
                                : "97".equals(item.name) ? "95#/97#" : item.name;
                attention.setText(temp);
            }

        }

        if ("1".equals(bean.customerGender)) {
            gender_man.setChecked(true);
        } else if ("2".equals(bean.customerGender)) {
            gender_woman.setChecked(true);
        }

        user_name.setText(bean.nickName);
        user_phone.setText(CnpcApplication.getInstance().getmData().userName);
        vehicle_hint.setText(bean.carPlateNO);

        if (StringUtils.isNotEmpty(bean.headPicUrl))
            Glide.with(mContext).load(bean.headPicUrl).into(user_avatar);
    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    /**
     * 保存
     */
    @OnClick(R.id.submit)
    public void submit() {
        connectToServer();
    }

    private void connectToServer() {
        Utils.showProgressDialog(mContext);
        CustomerInfoUpdateRequest request = new CustomerInfoUpdateRequest(new Response.Listener<CustomerInfoUpdateResponse>() {
            @Override
            public void onResponse(CustomerInfoUpdateResponse response) {
                Utils.closeDialog();
                Utils.makeToastAndShow(mContext, "保存成功");
                finish();
            }
        }, this);
        //TODO
        request.setCustomerName(user_name.getText().toString().trim());
        request.setCustomerTelephone(user_phone.getText().toString().trim());
        request.setUserId(CnpcApplication.getInstance().getmData().userId);

        int gender = 0;
        if (gender_man.isChecked()) {
            gender = 1;
        } else if (gender_woman.isChecked()) {
            gender = 2;
        } else {
            gender = 0;
        }
        request.setCustomerGender(gender + "");
        String productName = attention.getText().toString().trim();
        String[] split = productName.split("#");
        request.setAttentionProductName(productName);
        request.setAttentionProductId(sunyardoils.get(split[0]));
        WebUtils.doPost(request);
    }

    /**
     * 上传头像
     */
    @OnClick(R.id.avatar_upload)
    public void uploadAvatar() {
        getImage(user_avatar);

    }

    @Override
    public void UploadHeadImage() {
        uploadHeadPic();
    }

    public void uploadHeadPic() {

        Utils.showProgressDialog(mContext, "头像上传中...,请稍后");
        UploadHeadRequest request = new UploadHeadRequest(new Response.Listener<UploadHeadResponse>() {
            @Override
            public void onResponse(UploadHeadResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Toast.makeText(mContext, "修改头像成功", Toast.LENGTH_SHORT).show();
                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        Bitmap image = ((BitmapDrawable) user_avatar.getDrawable()).getBitmap();
        request.setPicArray(Base64.encodeBytes(Utils.Bitmap2Bytes(image)));
        request.setPicType("jpg");
        WebUtils.doPost(request);
    }


    /**
     * 添加关注
     */
    @OnClick(R.id.mine_attention_rl)
    public void editAttention() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .create();
            view = LayoutInflater.from(this).inflate(R.layout.dialog_oil_select, null);
            gridView = (GridView) view.findViewById(R.id.gridview);
            gridView.setAdapter(mAdapter = new CommonAdapter<UserCenterResponse.CheckBoxDTO>(mContext, checkBoxDTOs, R.layout.adapter_grid) {
                @Override
                public void convert(ViewHolder helper, UserCenterResponse.CheckBoxDTO item) {
                    //GasText gasText = helper.getView(R.id.val);
                    TextView gasText = helper.getView(R.id.val);
                    String temp = "92".equals(item.name) ? "92#/93#" : "93".equals(item.name) ? "92#/93#" :
                            "95".equals(item.name) ? "95#/97#"
                                    : "97".equals(item.name) ? "95#/97#" : item.name;

                    gasText.setText(temp);

                    RelativeLayout rl = (RelativeLayout) helper.getView(R.id.item_bg);

                    if (lastSelected == helper.getPosition()) {
                        rl.setBackgroundResource(R.drawable.item_sel);
                    } else {
                        rl.setBackgroundResource(R.drawable.item_unsel);
                    }


                }
            });
            //TODO
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

    private void initGrid(View view) {
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                UserCenterResponse.CheckBoxDTO checkBoxDTO = checkBoxDTOs.get(lastSelected);
                String temp = "92".equals(checkBoxDTO.name) ? "92#/93#" : "93".equals(checkBoxDTO.name) ? "92#/93#" :
                        "95".equals(checkBoxDTO.name) ? "95#/97#"
                                : "97".equals(checkBoxDTO.name) ? "95#/97#" : checkBoxDTO.name + "#";
                attention.setText(temp);

            }
        });
    }

    int lastSelected = 0;


    /**
     * 添加车辆信息
     */
    @OnClick(R.id.mine_vehicle_rl)
    public void editVehicle() {
        Utils.moveTo(this, VehicleListActivity.class);

    }

}
