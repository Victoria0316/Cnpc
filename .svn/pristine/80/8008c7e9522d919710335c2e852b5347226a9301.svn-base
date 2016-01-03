package com.bluemobi.cnpc.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.activity.HomeActivity;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/20.
 * <p/>
 * p9-发现
 */
@ContentView(R.layout.fragment_home_found)
public class HomeFoundFragment extends BaseFragment implements  View.OnClickListener{

    private FragmentManager fragmentManager;
    private NewsInformationFragment newsInformationFragment;
    private SalesPromotionFragment salesPromotionFragment;
    @Bind(R.id.radio_left)
    RadioButton radio_left;

    @Bind(R.id.radio_right)
    RadioButton radio_right;

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        ((HomeActivity) mContext).isPage = true;
        radio_left.setOnClickListener(this);
        radio_right.setOnClickListener(this);
        fragmentManager = getFragmentManager();

        radio_right.setChecked(true);
        setTabSelection(1);


    }

    @Override
    public void initData(Bundle savedInstanceState) {
             isShowLoadPage = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_left:
                setTabSelection(0);
                break;
            case R.id.radio_right:
                setTabSelection(1);
                break;

        }
    }

    private void setTabSelection(int index) {
//        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (salesPromotionFragment == null) {
                    salesPromotionFragment = new SalesPromotionFragment();
                    transaction.add(R.id.fl_content, salesPromotionFragment);
                } else {
                    transaction.show(salesPromotionFragment);

                }
                break;
            case 1:
                if (newsInformationFragment == null) {
                    newsInformationFragment = new NewsInformationFragment();

                    transaction.add(R.id.fl_content, newsInformationFragment);
                } else {
                    transaction.show(newsInformationFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (salesPromotionFragment != null) {
            transaction.hide(salesPromotionFragment);
        }
        if (newsInformationFragment != null) {
            transaction.hide(newsInformationFragment);
        }
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
   /* @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) mContext).showActivityTitleBar(R.string.home_found);
    }*/
//    /**
//     * 清除掉所有的选中状态。
//     */
//    private void clearSelection() {
//        home_page.setImageResource(R.drawable.home_page_selected);
//
//        home_near.setImageResource(R.drawable.home_near_selected);
//
//        home_found.setImageResource(R.drawable.home_found_selected);
//
//        home_My.setImageResource(R.drawable.home_my_selected);
//
//    }
}
