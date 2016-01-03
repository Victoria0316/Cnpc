package com.bluemobi.cnpc.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.fragment.BonusFragment;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 红包
 * Created by wangzhijun on 2015/7/29.
 */
@ContentView(R.layout.activity_bonus)
@PageName(R.string.mine_bonus_title)
public class BonusActivity extends BaseActivity {
    @Bind(R.id.radio_group)
    protected RadioGroup radioGroup;
    private FragmentManager fragmentManager;

    protected BonusFragment validFragment;//未用
    protected BonusFragment invalidFragment;//失效
    protected BonusFragment usedFragment;//已用
    private BonusFragment curFragment;

    private static final int LEFT = 0;
    private static final int MIDDLE = 1;
    private static final int RIGHT = 2;

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
        titleBarManager.showCommonTitleBar(R.string.mine_bonus_title, R.drawable.return_arrow, true);
        fragmentManager = getFragmentManager();
        setSelection(LEFT);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_left:
                        setSelection(LEFT);
                        break;
                    case R.id.radio_middle:
                        setSelection(MIDDLE);

                        break;
                    case R.id.radio_right:
                        setSelection(RIGHT);

                        break;
                }
            }
        });
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    private void setSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (index) {
            case LEFT:
                if (validFragment == null) {
                    validFragment = new BonusFragment();
                    validFragment.setType(BonusFragment.VALID);
                    transaction.add(R.id.content, validFragment);
                }
                curFragment = validFragment;
                break;
            case MIDDLE:
                if (usedFragment == null) {
                    usedFragment = new BonusFragment();
                    usedFragment.setType(BonusFragment.UESD);
                    transaction.add(R.id.content, usedFragment);
                }
                curFragment = usedFragment;
                break;
            case RIGHT:
                if (invalidFragment == null) {
                    invalidFragment = new BonusFragment();
                    invalidFragment.setType(BonusFragment.INVALID);
                    transaction.add(R.id.content, invalidFragment);
                }
                curFragment = invalidFragment;
                break;
        }
        hidenAll(transaction);
        transaction.show(curFragment);
        transaction.commit();
    }

    private void hidenAll(FragmentTransaction transaction) {
        if (validFragment != null) {
            transaction.hide(validFragment);
        }
        if (usedFragment != null) {
            transaction.hide(usedFragment);
        }
        if (invalidFragment != null) {
            transaction.hide(invalidFragment);
        }

    }
}
