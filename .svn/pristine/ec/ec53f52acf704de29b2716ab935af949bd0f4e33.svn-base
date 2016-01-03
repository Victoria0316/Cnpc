package com.bluemobi.cnpc.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.admin.DeviceAdminInfo;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.activity.BonusActivity;
import com.bluemobi.cnpc.activity.BorrowGasActivity;
import com.bluemobi.cnpc.activity.CoinPurseActivity;
import com.bluemobi.cnpc.activity.CouponActivity;
import com.bluemobi.cnpc.activity.FrozenSectionActivity;
import com.bluemobi.cnpc.activity.FuelConsumptionActivity;
import com.bluemobi.cnpc.activity.GasFillingCardActivity;
import com.bluemobi.cnpc.activity.HomeActivity;
import com.bluemobi.cnpc.activity.IntegrationActivity;
import com.bluemobi.cnpc.activity.MakeOutInvoiceActivity;
import com.bluemobi.cnpc.activity.OilWalletActivity;
import com.bluemobi.cnpc.activity.PersonInfoActivity;
import com.bluemobi.cnpc.activity.CollectionActivity;
import com.bluemobi.cnpc.activity.PreGasActivity;
import com.bluemobi.cnpc.activity.SettingActivity;
import com.bluemobi.cnpc.activity.ShakeActivity;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.db.entity.Position;
import com.bluemobi.cnpc.network.request.NearnestGasStationRequest;
import com.bluemobi.cnpc.network.request.UserCenterRequest;
import com.bluemobi.cnpc.network.response.NearnestGasStationResponse;
import com.bluemobi.cnpc.network.response.UserCenterResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.GalleryFlow;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gaoyn on 2015/7/20.
 * <p/>
 * p10-个人中心
 */
@ContentView(R.layout.fragment_personal_center)
@PageName(R.string.home_my)
public class HomeMyFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    protected
    @Bind(R.id.user_phone)
    TextView user_phone;
    protected
    @Bind(R.id.user_score_value)
    TextView user_score_value;

    protected
    @Bind(R.id.user_avatar)
    ImageView user_avatar;

    protected
    @Bind(R.id.edit)
    ImageView edit;
    /**
     * 红包
     */
    protected
    @Bind(R.id.mine_packet)
    ImageView mine_packet;
    /**
     * 优惠券
     */
    protected
    @Bind(R.id.mine_coupon)
    ImageView mine_coupon;
    /**
     * 加油卡
     */
    protected
    @Bind(R.id.mine_card)
    ImageView mine_card;
    /**
     * 开票信息
     */
    protected
    @Bind(R.id.mine_info)
    ImageView mine_info;
    /**
     * 摇积分
     */
    protected
    @Bind(R.id.mine_shake)
    ImageView mine_shake;
    /**
     * 收藏
     */
    protected
    @Bind(R.id.mine_coll)
    ImageView mine_coll;
    /**
     * 加油消费
     */
    protected
    @Bind(R.id.mine_consume)
    ImageView mine_consume;
    /**
     * 设置
     */
    protected
    @Bind(R.id.mine_setting)
    ImageView mine_setting;

    @Bind(R.id.user_vehicle)
    TextView user_vehicle;

    /**
     * 是否第一次进入我的界面
     */
    private boolean isFrist = true;

    /**
     * 是否第一次进入我的界面
     */
    private boolean isFristEdit ;

    private ArrayList<UserCenterResponse.CheckBoxDTO> checkBoxDTOs;

    private  ArrayList<UserCenterResponse.BusinessDictionaryInfoDTO> businessDictionaryInfoDTOs;

    private UserCenterResponse.UserCenterData mData;
    protected
    @Bind(R.id.gallery_flow)
    GalleryFlow mGallery;
    protected GalleryAdapter mAdapter;
    private String[] array = new String[]{"油钱包","零钱包","冻结款","预购油","借用油","积分"};
    private String oilMoneyBalance;
    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        ((HomeActivity) mContext).isPage = true;
        mAdapter = new GalleryAdapter();
        mGallery.setBackgroundColor(Color.WHITE);
        mGallery.setFadingEdgeLength(0);
        mGallery.setGravity(Gravity.CENTER_VERTICAL);
        mGallery.setMaxRotationAngle(0);
        mGallery.setMaxZoom(-40);
        mGallery.setSpacing(40);
        mGallery.setSelection(0);

        mGallery.setOnItemSelectedListener(this);

        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //油钱包
                        Utils.moveTo(getActivity(), OilWalletActivity.class);
                        break;
                    case 1:
                        Utils.moveTo(getActivity(), CoinPurseActivity.class);
                        break;
                    case 2:

                        // 冻结款 不看类名
                        Utils.moveTo(getActivity(), IntegrationActivity.class);
                        break;
                    case 3:
                        //预购油
                        Utils.moveTo(getActivity(), PreGasActivity.class);
                        break;
                    case 4:
                        //借用油
                        Utils.moveTo(getActivity(), BorrowGasActivity.class);
                        break;
                    case 5:
                        // 积分 不看类名
                        Utils.moveTo(getActivity(), FrozenSectionActivity.class);
                        break;
                }
            }
        });
    }



    @Override
    public void initData(Bundle savedInstanceState) {
        isShowLoadPage = false;

    }

    @Override
    public void onResume() {
        super.onResume();
        getDataServer();
    }

    public void getDataServer() {
        Utils.showProgressDialog(mContext);
        UserCenterRequest request = new UserCenterRequest
                (
                        new Response.Listener<UserCenterResponse>() {
                            @Override
                            public void onResponse(final UserCenterResponse response) {
                                Utils.closeDialog();
                                mData = response.data;
                                user_phone.setText(mData.nickName);
                                user_score_value.setText(mData.availableAmount);
                                if (StringUtils.isNotEmpty(mData.carPlateNO)){
                                    user_vehicle.setText(mData.carPlateNO);
                                }else{
                                    user_vehicle.setText("请填写车辆信息");
                                }
                                if (StringUtils.isNotEmpty(mData.headPicUrl))
                                Glide.with(mContext).load(mData.headPicUrl).into(user_avatar);
                                oilMoneyBalance = mData.oilMoneyBalance;
                                checkBoxDTOs = mData.checkBoxDTOs;
                                businessDictionaryInfoDTOs = mData.businessDictionaryInfoDTOs;
                                mGallery.setAdapter(mAdapter);
                                CnpcApplication.getInstance().setmCheckBoxDTO(checkBoxDTOs);
                                CnpcApplication.getInstance().setmBusinessDictionaryInfoDTOs(businessDictionaryInfoDTOs);
                            }
                        }, (Response.ErrorListener) getActivity());

       // if (CnpcApplication.getInstance().getmData()==null)

        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        WebUtils.doPost(request);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @OnClick(R.id.edit)
    public void editPersonInfo() {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mData", mData);
        intent.putExtras(bundle);
        intent.setClass(mContext, PersonInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.mine_coll)
    public void collection(){
        Utils.moveTo(getActivity(), CollectionActivity.class);
    }

    @OnClick(R.id.mine_shake)
    public void shake(){
        Utils.moveTo(getActivity(), ShakeActivity.class);
    }


    @OnClick(R.id.mine_card)
    public void gas(){
        Utils.moveTo(getActivity(), GasFillingCardActivity.class);
    }

    @OnClick(R.id.mine_packet)
    public void goBonus(){
        Utils.moveTo(getActivity(), BonusActivity.class);
    }


    @OnClick(R.id.mine_coupon)
    public void coupon(){
        Utils.moveTo(getActivity(), CouponActivity.class);
    }
    @OnClick(R.id.mine_consume)
    public void consume(){
        Utils.moveTo(getActivity(), FuelConsumptionActivity.class);
    }
    @OnClick(R.id.mine_setting)
    public void set(){
        Utils.moveTo(getActivity(), SettingActivity.class);
    }

    @OnClick(R.id.mine_info)
    public void info(){
        Utils.moveTo(getActivity(), MakeOutInvoiceActivity.class);
    }


    private int size = 6;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              mAdapter.setSelectItem(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class GalleryAdapter extends BaseAdapter {

        /**
         * 选中的条目
         */
        private int selectItem;
        public void setSelectItem(int selectItem) {

            if (this.selectItem != selectItem) {
                this.selectItem = selectItem;
                notifyDataSetChanged();
            }
        }
        @Override
        public int getCount() {
            return size;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }



        public void propertyValuesHolder(View view)
        {
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                    0.7f);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                    0.7f);
            PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                    0.7f);
            ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY,pvhZ).setDuration(20).start();
        }

        public void rePropertyValuesHolder(View view)
        {
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0.7f,
                    1f);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0.7f,
                    1f);
            PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0.7f,
                   1f);
            ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY,pvhZ).setDuration(20).start();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_person_gallery
            , parent, false);
            LinearLayout item_ll = (LinearLayout)convertView.findViewById(R.id.item_ll);

            if(selectItem==position){

                rePropertyValuesHolder(item_ll);
            }
            else{
                //item_ll.setLayoutParams(new RelativeLayout.LayoutParams(100, 100));
                //rotateyAnimRun(item_ll);
                propertyValuesHolder(item_ll);
//未选中
            }
            TextView textView = (TextView)convertView.findViewById(R.id.text1);
            TextView tv_money = (TextView)convertView.findViewById(R.id.tv_money);
            TextView common_text = (TextView)convertView.findViewById(R.id.tv_common_text);
            textView.setText(array[position]);
            common_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            common_text.getPaint().setAntiAlias(true);
            if (position==0)
            {
                tv_money.setText("¥"+oilMoneyBalance);
            }

            if (position==1)
            {
                tv_money.setText("¥"+mData.changeBalance);
            }

            if (position==2)
            {
                tv_money.setText("¥"+mData.frozenPayment);
            }

            if (position==3)
            {
                tv_money.setText("¥"+mData.oilFuturesAmount);
            }
            if (position==4)
            {
                tv_money.setText("¥"+mData.oilBorrowAmount);
            }
            if (position==5)
            {
                tv_money.setText("¥"+mData.availableAmount);
            }
            return convertView;
        }
    }


   /* @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) mContext).showActivityTitleBar(R.string.home_my);
    }*/
}
