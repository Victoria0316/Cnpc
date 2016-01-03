package com.bluemobi.cnpc.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.fragment.CouponFragment;
import com.bluemobi.cnpc.fragment.NoValidCouponFragment;
import com.bluemobi.cnpc.fragment.UsedCouponFragment;
import com.bluemobi.cnpc.fragment.ValidCouponFragment;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/28.
 * P10_10优惠券
 */
@ContentView(R.layout.activity_coupon)
@PageName(R.string.s_the_coupon)
public class CouponActivity extends BaseActivity implements  View.OnClickListener
{

    private FragmentManager fragmentManager;
    private CouponFragment usedCouponFragment;
    private CouponFragment validCouponFragment;
    private CouponFragment noValidCouponFragment;
    @Bind(R.id.radio_left)
    TextView radio_left;

    @Bind(R.id.radio_middle)
    TextView radio_middle;

    @Bind(R.id.radio_right)
    TextView radio_right;



    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_the_coupon, R.drawable.return_arrow, true);
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        radio_left.setOnClickListener(this);
        radio_right.setOnClickListener(this);
        radio_middle.setOnClickListener(this);
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_left:
                setTabSelection(0);
                break;
            case R.id.radio_middle:
                setTabSelection(1);
                break;

            case R.id.radio_right:
                setTabSelection(2);
                break;

        }
    }


    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (validCouponFragment == null) {
                    validCouponFragment = new CouponFragment();
                    validCouponFragment.setType(CouponFragment.VALID);
                    transaction.add(R.id.fl_content_three, validCouponFragment);
                } else {
                    transaction.show(validCouponFragment);

                }
                break;
            case 1:
                if (usedCouponFragment == null) {
                    usedCouponFragment = new CouponFragment();
                    usedCouponFragment.setType(CouponFragment.UESD);
                    transaction.add(R.id.fl_content_three, usedCouponFragment);
                } else {
                    transaction.show(usedCouponFragment);
                }
                break;

            case 2:
                if (noValidCouponFragment == null) {
                    noValidCouponFragment = new CouponFragment();
                    noValidCouponFragment.setType(CouponFragment.INVALID);
                    transaction.add(R.id.fl_content_three, noValidCouponFragment);
                } else {
                    transaction.show(noValidCouponFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (validCouponFragment != null) {
            transaction.hide(validCouponFragment);
        }
        if (usedCouponFragment != null) {
            transaction.hide(usedCouponFragment);
        }
        if (noValidCouponFragment != null) {
            transaction.hide(noValidCouponFragment);
        }
    }

    @Override
    protected LoadingPage.LoadResult load() {

        return LoadingPage.LoadResult.success;

    }



}
