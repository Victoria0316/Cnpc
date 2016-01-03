package com.bluemobi.cnpc.activity;

import android.view.View;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.view.LoadingPage;

/**
 * Created by liufy on 2015/7/23.
 * P5_3_2预借-提交订单
 */

@ContentView(R.layout.activity_fuel_consumption_detail)
public class FuelConsumptionDetailActivity extends BaseActivity {


    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_fuel_consumption_detail, R.drawable.return_arrow, true);

        showLoadingPage(true);

    }

    @Override
    protected void successViewCompleted(View successView) {

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
}
