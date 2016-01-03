package com.bluemobi.cnpc.activity;

import android.view.View;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/28.
 * P10_10_1优惠券详细
 */
@ContentView(R.layout.activity_invoice)
public class InvoiceInfoActivity extends BaseActivity
{

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_invoice_info, R.drawable.return_arrow, true);
        showLoadingPage(true);

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

    }

    @Override
    protected LoadingPage.LoadResult load() {

        return LoadingPage.LoadResult.success;

    }



}
