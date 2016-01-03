package com.bluemobi.cnpc.activity;

import android.view.View;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/28.
 * P10_8加油卡
 */
@ContentView(R.layout.activity_confirm_gas_filling_card)
@PageName(R.string.s_confirm_gas_filling_card)
public class ConfirmGasFillingCardActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.tv_cardNo_text)
    TextView tv_cardNo_text;

    @Bind(R.id.deptName)
    TextView deptName1;

    @Bind(R.id.deptCode)
    TextView deptCode1;

    @Bind(R.id.tv_integration_text)
    TextView tv_integration_text;

//    @Bind(R.id.tv_residual_text)
//    TextView tv_residual_text;
    /**
     * 页面传递
     */
    private String PackageId;

    /**
     * 页面传递
     */
    private String deptName;

    /**
     * 页面传递
     */
    private String deptCode;

    /**
     * 页面传递
     */
    private String timeLimit;

    @Bind(R.id.tv_confirm)
    TextView tv_confirm;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_confirm_gas_filling_card, R.drawable.return_arrow,true);
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        PackageId = getIntent().getStringExtra("PackageId");
        deptName = getIntent().getStringExtra("deptName");
        //deptCode = getIntent().getStringExtra("deptCode");
        timeLimit = getIntent().getStringExtra("timeLimit");

        tv_cardNo_text.setText(PackageId);
        deptName1.setText(deptName);
        //deptCode1.setText(deptCode);
        tv_integration_text.setText(timeLimit);
//        tv_residual_text.setText(CnpcApplication.getInstance().getmData().nickName);

        tv_confirm.setOnClickListener(this);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_confirm:
                Utils.moveTo(this,GasFillingCardActivity.class);
                break;
        }
    }
}
