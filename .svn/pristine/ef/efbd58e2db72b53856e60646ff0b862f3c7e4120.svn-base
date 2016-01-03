package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.model.AddGasFillingCardBean;
import com.bluemobi.cnpc.network.model.GasFillingCardInfo;
import com.bluemobi.cnpc.network.model.OilInBean;
import com.bluemobi.cnpc.network.request.GasFillingCardRequest;
import com.bluemobi.cnpc.network.request.OilInRequest;
import com.bluemobi.cnpc.network.response.GasFillingCardResponse;
import com.bluemobi.cnpc.network.response.OilInResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.KeyBoardUtils;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.AlertDialog;
import com.bluemobi.cnpc.view.LoadingPage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 转入油卡
 * Created by wangzhijun on 2015/7/24.
 */
@ContentView(R.layout.activity_oil_in)
@PageName(R.string.mine_oilwallet_in)
public class OilInActivity extends BaseActivity{

    /**
     * 实际支付
     */
    private String balance;

    @Bind(R.id.balance_val)
    TextView balance_val;

    @Bind(R.id.time)
    TextView time_start;

    @Bind(R.id.amount_val)
    EditText amount_val;

    @Bind(R.id.card_val)
    TextView card_val;

    @Bind(R.id.card_sel)
    RelativeLayout card_sel;

    private ArrayList<String> options1Items = new ArrayList<String>();
    private OptionsPopupWindow pwOptions;

    private Map<String,String> sunyardoils = new HashMap<String,String>();

    private String defaultcardid ;
    private  String defaultcardname ;

    private String str;

    private AlertDialog tipDialog;

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
        titleBarManager.showCommonTitleBar(R.string.mine_oilwallet_in, R.drawable.return_arrow, true);
        balance = getIntent().getStringExtra("balance");
        balance_val.setText(balance);
        defaultcardid = getIntent().getStringExtra("DEFAULTCARDID");
        defaultcardname =getIntent().getStringExtra("DEFAULTCARDNAME");
        pwOptions = new OptionsPopupWindow(this,R.layout.pw_options,true);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        String formatTime = df.format(time);
        time_start.setText(formatTime);
        if (StringUtils.isNotEmpty(defaultcardname))
        {
            card_val.setText(defaultcardname);
        }else
        {

            card_val.setText("");
        }


        amount_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edt) {

                String temp = edt.toString();

                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    str = temp;
                    return;
                }
                if (temp.length() - posDot - 1 > 2) {

                    edt.delete(posDot + 3, posDot + 4);
                }
                str = edt.toString();

            }
        });

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @OnClick(R.id.submit)
    public void submit(){

        if(checkUserPas()){

            if(Double.parseDouble(str) > Double.parseDouble(balance)){
                tipDialog = new AlertDialog(this)
                        .setTitle(getString(R.string.global_tip))
                        .setMessage("余额不足，请前去充值")
                        .setPositiveButtonClickListener(getString(R.string.crop__cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tipDialog.dismiss();
                            }
                        })
                        .setNegativeButtonClickListener(getString(R.string.global_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tipDialog.dismiss();
                                Utils.moveTo(mContext, PayPreferentialActivity.class);

                            }
                        });
                tipDialog.show();
            }else{
                Request();
            }

        }



    }

    private boolean checkUserPas() {

        if (StringUtils.isEmpty(str)) {
            Toast.makeText(OilInActivity.this, "输入金额不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.card_sel)
    public void cardAll()
    {
        KeyBoardUtils.closeKeybord(amount_val,mContext);
        pwOptions.backgroundAlpha(0.7f);
        pwOptions.showAtLocation(card_sel, Gravity.BOTTOM, 0, 0);
        getDataServer();

    }

    private void getDataServer() {

        Utils.showProgressDialog(mContext);
        GasFillingCardRequest request = new GasFillingCardRequest
                (
                        new Response.Listener<GasFillingCardResponse>() {
                            @Override
                            public void onResponse(GasFillingCardResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    final List<GasFillingCardInfo> info = response.getData().getInfo();

                                    if (options1Items != null && options1Items.size() > 0)
                                        options1Items.clear();
                                    if (info != null) {
                                        for (GasFillingCardInfo dto : info) {
                                                sunyardoils.put( dto.getGasStationName(), dto.getId());
                                                options1Items.add(dto.getGasStationName());
                                            }
                                        }
                                    }
                                    pwOptions.setPicker(options1Items);
                                    pwOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                                        @Override
                                        public void onOptionsSelect(int options1, int option2, int options3) {
                                            String tx = options1Items.get(options1);
                                            card_val.setText(tx);
                                        }
                                    });
                                    pwOptions.setSelectOptions(0);

                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setCurrentnum(1000+"");
        request.setCurrentpage(0+"");
        WebUtils.doPost(request);
    }

    private void Request() {
        OilInRequest request = new OilInRequest(new Response.Listener<OilInResponse>() {
            @Override
            public void onResponse(OilInResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    finish();
                    OilInBean data = response.getData();
                    Intent intent = new Intent(mContext, OilTransferInSuccessActivity.class);
                    intent.putExtra("OILINBEAN",data);
                    startActivity(intent);
                    finish();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        },this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setMoney(str);
        request.setOilCardId(sunyardoils.get(card_val.getText().toString().trim()));
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }
}
