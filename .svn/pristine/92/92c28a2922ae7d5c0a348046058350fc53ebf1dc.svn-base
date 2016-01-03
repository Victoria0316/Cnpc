package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.OptionsPopupWindow;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.model.AddGasFillingCardBean;
import com.bluemobi.cnpc.network.request.AddGasCardRequest;
import com.bluemobi.cnpc.network.request.AddGasFillingCardRequest;
import com.bluemobi.cnpc.network.response.AddGasCardResponse;
import com.bluemobi.cnpc.network.response.AddGasFillingCardResponse;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.AlertDialog;
import com.bluemobi.cnpc.view.LoadingPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/28.
 * P10_8_2加油卡
 */
@ContentView(R.layout.activity_add_gas_filling_card)
@PageName(R.string.s_add_gas_filling_card)
public class AddGasFillingCardActivity extends BaseActivity {
    private ArrayList<String> options1Items = new ArrayList<String>();
    OptionsPopupWindow pwOptions;

    @Bind(R.id.rl_choose_gas)
    protected RelativeLayout rl_choose_gas;
    @Bind(R.id.tv_bind)
    protected TextView tv_bind;

    @Bind(R.id.tv_confirm)
    protected TextView tv_confirm;

    @Bind(R.id.et_cardNo)
    protected EditText et_cardNo;

    @Bind(R.id.phone)
    protected EditText phone;

    private List<AddGasFillingCardBean> data;
    private Map<String,String> sunyardoils = new HashMap<String,String>();

    private AlertDialog tipDialog;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_add_gas_filling_card, R.drawable.return_arrow, true);
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        Request();
        pwOptions = new OptionsPopupWindow(this,R.layout.pw_options,true);
        rl_choose_gas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pwOptions.backgroundAlpha(0.7f);
                pwOptions.showAtLocation(rl_choose_gas, Gravity.BOTTOM, 0, 0);
            }
        });






        tipDialog = new AlertDialog(this)
                .setTitle(getString(R.string.global_tip))
                .setMessage(getString(R.string.s_gas_hint))
                .setNegativeButtonClickListener(getString(R.string.global_sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tipDialog.dismiss();
                    }
                });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInput()) {

                    AddOilCarRequest();

                }

            }
        });

      /*  et_cardNo.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             tipDialog.setCustomTextColor(R.color.common_blue, R.color.common_gray, R.color.common_blue);
                                             tipDialog.show();
                                         }
                                     }
        );*/
    }

    private boolean checkInput() {

        if (StringUtils.isEmpty(tv_bind.getText().toString())) {
            Toast.makeText(AddGasFillingCardActivity.this, "绑定加油站不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(et_cardNo.getText().toString())) {
            Toast.makeText(AddGasFillingCardActivity.this, "加油卡号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(AddGasFillingCardActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void AddOilCarRequest() {
        Utils.showProgressDialog(mContext);
        AddGasCardRequest request = new AddGasCardRequest
                (
                        new Response.Listener<AddGasCardResponse>() {
                            @Override
                            public void onResponse(AddGasCardResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    String deptName = tv_bind.getText().toString().trim();
                                    String str = sunyardoils.get(deptName);
                                    String[] split = str.split("/");
                                    String deptId = split[0];
                                    String deptCode = split[1];
                                    Intent intent = new Intent();
                                    intent.putExtra("deptName", deptName);
                                    intent.putExtra("deptId",deptId);
                                    intent.putExtra("deptCode",deptCode);
                                    intent.putExtra("PackageId",et_cardNo.getText().toString());
                                    intent.putExtra("timeLimit", phone.getText().toString());
                                    intent.setClass(mContext, ConfirmGasFillingCardActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);

        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setCardNumber(et_cardNo.getText().toString());//加油卡号
        request.setCellphone(phone.getText().toString());//手机号
        String deptName = tv_bind.getText().toString().trim();
        String str = sunyardoils.get(deptName);
        String[] split = str.split("/");
        String deptId = split[0];
        String deptCode = split[1];

        request.setGasStationCode(deptCode);//加油站编码
        request.setGasStationId(deptId);//加油站id
        request.setGasStationName(deptName);//加油站名称
        WebUtils.doPost(request);
    }

    private void Request() {

        Utils.showProgressDialog(mContext);
        AddGasFillingCardRequest request = new AddGasFillingCardRequest
                (
                        new Response.Listener<AddGasFillingCardResponse>() {
                            @Override
                            public void onResponse(AddGasFillingCardResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    data = response.getData();

                                    tv_bind.setText(data.get(0).getDeptName());
                                    if (options1Items != null && options1Items.size() > 0)
                                        options1Items.clear();
                                    if (data != null) {
                                        for (AddGasFillingCardBean dto : data) {
                                            if(dto.getDeptName() != null){
                                                sunyardoils.put( dto.getDeptName(), dto.getId()+ "/" + dto.getDeptCode());
                                                options1Items.add(dto.getDeptName());
                                            }
                                        }
                                    }
                                    pwOptions.setPicker(options1Items);
                                    pwOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                                        @Override
                                        public void onOptionsSelect(int options1, int option2, int options3) {
                                            String tx = options1Items.get(options1);
                                            tv_bind.setText(tx);
                                        }
                                    });
                                    pwOptions.setSelectOptions(0);
                                }
                            }
                        }, this);

        WebUtils.doPost(request);
    }

    @Override
    protected LoadingPage.LoadResult load() {

        return LoadingPage.LoadResult.success;

    }


}
