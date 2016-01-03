package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.eventbus.BaseEvent;
import com.bluemobi.cnpc.eventbus.SuccessRefreshEvent;
import com.bluemobi.cnpc.network.model.OilInBean;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by wangzhijun on 2015/7/24.
 * 油卡转入成功
 */
@ContentView(R.layout.activity_oil_transfer_success)
@PageName(R.string.mine_oilwallet_transfer_success)
public class OilTransferInSuccessActivity extends BaseActivity {

    private OilInBean oilInBean;


    @Bind(R.id.order_code)
    TextView order_code;
    @Bind(R.id.transfer_time)
    TextView transfer_time;
    @Bind(R.id.mine_gas_station)
    TextView mine_gas_station;
    @Bind(R.id.mine_gas_card)
    TextView mine_gas_card;

    @Bind(R.id.mine_gas_crad_transfer_int)
    TextView mine_gas_crad_transfer_int;

    @Bind(R.id.mine_gas_card_transfer_out)
    TextView mine_gas_card_transfer_out;

    @Bind(R.id.mine_cur_gas_wallet)
    TextView mine_cur_gas_wallet;


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
        titleBarManager.showCommonTitleBar(R.string.mine_oilwallet_transfer_success, R.drawable.return_arrow, true);
        oilInBean = (OilInBean) getIntent().getSerializableExtra("OILINBEAN");
        if (oilInBean == null) {
            return;
        }

        order_code.setText(oilInBean.getOrderno());
        transfer_time.setText(oilInBean.getTradeTime());
        mine_gas_station.setText(oilInBean.getOilstation());
        mine_gas_card.setText(oilInBean.getCardno());
        mine_gas_crad_transfer_int.setText(oilInBean.getCardin()+"元");
        mine_gas_card_transfer_out.setText(oilInBean.getPurseout()+"元");
        mine_cur_gas_wallet.setText(oilInBean.getBalance()+"元");
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        Utils.moveTo(this, OilWalletActivity.class);
        EventBus.getDefault().post(new SuccessRefreshEvent(1));
        finishAll();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new SuccessRefreshEvent(1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setAction(Constants.REFRESH);
        sendBroadcast(intent);
    }
}
