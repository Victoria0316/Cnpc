package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by gaoyn on 2015/7/22.
 *
 * p4-3 选择支付方式
 */
@ContentView(R.layout.activity_payment_mode_other)
@PageName(R.string.mode_payment)
public class PaymentModeOtherActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.pay)
    RadioButton pay;

    @Bind(R.id.WeChat)
    RadioButton WeChat;

    @Bind(R.id.Internet)
    RadioButton Internet;

    private String payId;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(PaymentModeOtherActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.mode_payment, R.drawable.return_arrow, true);

        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);

        pay.setOnClickListener(this);
        WeChat.setOnClickListener(this);
        Internet.setOnClickListener(this);


    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){//1银联 2支付宝 3微信
            case R.id.pay:
                payId = "2";
                Intent intent = new Intent();
                intent.putExtra("payId", payId);
                setResult(RESULT_OK, intent);
                Toast.makeText(mContext, "已选择支付宝", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.WeChat:
                payId = "3";
                Intent inten = new Intent();
                inten.putExtra("payId", payId);
                setResult(RESULT_OK, inten);
                Toast.makeText(mContext, "已选择微信", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.Internet:
                payId = "1";
                Intent inte = new Intent();
                inte.putExtra("payId", payId);
                setResult(RESULT_OK, inte);
                Toast.makeText(mContext,"已选择银联",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

    }
}
