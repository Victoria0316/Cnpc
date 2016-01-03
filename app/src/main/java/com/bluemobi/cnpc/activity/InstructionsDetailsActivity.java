package com.bluemobi.cnpc.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by gaoyn on 2015/10/20.
 *
 * 我的六个圈的功能介绍
 */

@ContentView(R.layout.activity_instructions_details)
@PageName(R.string.s_function_introduction)
public class InstructionsDetailsActivity extends BaseActivity{

    @Bind(R.id.instructions)
    TextView instructions;

    /**
     * 页面传递
     */
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
    }

   /* public void onEvent(String str) {


        switch(str){
            case "OilWalletActivity":
                instructions.setText("油钱包能用于加油支付或转入绑定的加油卡，不能提现。");
                break;
            case "CoinPurseActivity":
                instructions.setText("零钱包能随时转入或转出（提现）。可用于各种业务的支付。");
                break;
        }
    }*/

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(InstructionsDetailsActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_function_introduction, R.drawable.return_arrow, true);
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        key = getIntent().getStringExtra("key");

        switch(key){
            case "OilWalletActivity":
                instructions.setText(R.string.instructions1);
                break;
            case "CoinPurseActivity":
                instructions.setText(R.string.instructions2);
                break;
            case "IntegrationActivity":
                instructions.setText(R.string.instructions3);
                break;
            case "PreGasActivity":
                instructions.setText(R.string.instructions4);
                break;
            case "BorrowGasActivity":
                instructions.setText(R.string.instructions5);
                break;
            case "FrozenSectionActivity":
                instructions.setText(R.string.instructions6);
                break;
        }

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
}
