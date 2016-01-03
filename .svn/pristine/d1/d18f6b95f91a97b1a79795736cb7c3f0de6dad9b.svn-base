package com.bluemobi.cnpc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.db.entity.CarAllListDetail;
import com.bluemobi.cnpc.db.entity.CityListDetail;
import com.bluemobi.cnpc.db.entity.DbVersion;
import com.bluemobi.cnpc.db.entity.HotCityListDetail;
import com.bluemobi.cnpc.network.request.CarAllListRequest;
import com.bluemobi.cnpc.network.request.CityListRequest;
import com.bluemobi.cnpc.network.response.CarAllListResponse;
import com.bluemobi.cnpc.network.response.CityListResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.ThreadManager;
import com.bluemobi.cnpc.util.UiUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * 启动页面
 * Created by wangzhijun on 2015/7/17.
 */
public class LauncherActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RelativeLayout rl = new RelativeLayout(this);
        rl.setBackgroundResource(R.drawable.launcher);
        setContentView(rl);
        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                Utils.moveTo(LauncherActivity.this, HomeActivity.class);
                finish();
            }
        }.start();
    }



}
