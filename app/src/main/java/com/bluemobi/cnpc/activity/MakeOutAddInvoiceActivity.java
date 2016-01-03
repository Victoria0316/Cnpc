package com.bluemobi.cnpc.activity;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.request.MakeOutAddInvoiceRequest;
import com.bluemobi.cnpc.network.response.MakeOutAddInvoiceResponse;
import com.bluemobi.cnpc.util.KeyBoardUtils;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liufy on 2015/7/29.
 * P10_15开票信息-添加
 */
@ContentView(R.layout.activity_paper_edit)
@PageName(R.string.s_invoice_info)
public class MakeOutAddInvoiceActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.save)
    TextView save;

    @Bind(R.id.edit)
    EditText edit;

    @Bind(R.id.personal_company)
    Button personal_company;

    @Bind(R.id.personal_radio)
    Button personal_radio;

    protected
    @Bind(R.id.rg_select)
    RadioGroup rg_select;

    private boolean enabled = false;

    @Override
    protected void initBase() {
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_invoice_info, R.drawable.return_arrow, true);
        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        personal_company.setOnClickListener(this);
        personal_radio.setOnClickListener(this);
        save.setOnClickListener(this);

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_company:
                enabled = true;
                edit.setEnabled(true);
                edit.setFocusableInTouchMode(true);
                edit.setFocusable(true);
                KeyBoardUtils.openKeybord(edit, mContext);
                break;
            case R.id.personal_radio:
                enabled = false;
                edit.setEnabled(false);
                edit.setFocusableInTouchMode(false);
                edit.setFocusable(false);
                KeyBoardUtils.closeKeybord(edit, mContext);
                break;
            case R.id.save:
                if(enabled == true){
                    if (StringUtils.isEmpty(edit.getText().toString())) {
                        Toast.makeText(MakeOutAddInvoiceActivity.this, "公司名称不能为空", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Request();
                    }
                }else{
                    Request();
                }
                break;
        }
    }

    private void Request() {
        MakeOutAddInvoiceRequest request = new MakeOutAddInvoiceRequest(new Response.Listener<MakeOutAddInvoiceResponse>() {
            @Override
            public void onResponse(MakeOutAddInvoiceResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success

//                    Utils.moveTo(mContext, MakeOutInvoiceActivity.class);
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        int id = rg_select.getCheckedRadioButtonId();
        String selectID = "";
        String selectName = "";
        if (personal_radio.getId() == id) {
            selectID = "1";
            selectName = null;
        } else if (personal_company.getId() == id) {
            selectID = "2";
            selectName = edit.getText().toString().trim();
        }
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setReceiptType(selectID); //发票类型 1个人 2公司
        request.setCompanyName(selectName); //公司名称(发票类型为2时填写)
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }
}
