package com.bluemobi.cnpc.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;

import com.bluemobi.cnpc.R;

/**
 * Created by gaoyn on 2015/7/27.
 *
 * p10-11 摇积分
 */
public class ShakeLitenerTestActivity extends Activity
{
    private ShakeListener mShaker;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake()
            {
                vibe.vibrate(100);
                new AlertDialog.Builder(ShakeLitenerTestActivity.this)
                        .setPositiveButton(android.R.string.ok, null)
                        .setMessage("Shooken!")
                        .show();
            }
        });
    }

    @Override
    public void onResume()
    {
        mShaker.resume();
        super.onResume();
    }
    @Override
    public void onPause()
    {
        mShaker.pause();
        super.onPause();
    }
}