package com.bluemobi.cnpc.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.fragment.NewsInformationFragment;
import com.bluemobi.cnpc.fragment.SalesPromotionFragment;
import com.bluemobi.cnpc.network.request.CollectRequest;
import com.bluemobi.cnpc.network.request.DelCollectRequest;
import com.bluemobi.cnpc.network.request.DelPraiseRequest;
import com.bluemobi.cnpc.network.request.ListGasStationDetailRequest;
import com.bluemobi.cnpc.network.request.NearnestListGasStationRequest;
import com.bluemobi.cnpc.network.request.PraiseRequest;
import com.bluemobi.cnpc.network.response.ListGasStationDetailResponse;
import com.bluemobi.cnpc.network.response.NearNotBdGasStationResponse;
import com.bluemobi.cnpc.network.response.PraiseResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.SharedPreferencesUtil;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.CustomListView;
import com.bluemobi.cnpc.view.GasText;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/22.
 * <p/>
 * p6-1 加油站详情
 */

@ContentView(R.layout.activity_gas_station_details)
@PageName(R.string.oil_details)
public class GasStationDetailsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.cb_collect)
    CheckBox cb_collect;

    @Bind(R.id.cb_praise)
    CheckBox cb_praise;

    @Bind(R.id.tv_collectNum)
    TextView tv_collectNum;

    @Bind(R.id.tv_praiseNum)
    TextView tv_praiseNum;

    @Bind(R.id.introduce)
    TextView introduce;

    @Bind(R.id.tv_gas_content)
    TextView tv_gas_content;

    @Bind(R.id.come_pay)
    Button come_pay;

    @Bind(R.id.lv_oils)
    CustomListView lv_oils;

    @Bind(R.id.iv_bg)
    ImageView iv_bg;

    @Bind(R.id.tv_promotion)
    WebView tv_promotion;

    private CommonAdapter<ListGasStationDetailResponse.GasStationDetailInfo> adapter;
    private FragmentManager fragmentManager;
    private NewsInformationFragment newsInformationFragment;
    private SalesPromotionFragment salesPromotionFragment;
    private String gas_id;
    private String gas_name;
    private String collectid;
    private String praiseid;
    private boolean isCollect = false;
    private boolean isPraise = false;
    private ArrayList<ListGasStationDetailResponse.GasStationDetailInfo> mProductInfos;


    private int collectionNum = 0;    //收藏数
    private int praiseNum = 0;   //已赞数

    TitleBarManager titleBarManager;
    @Override
    protected void initBase() {

        titleBarManager = new TitleBarManager();
        titleBarManager.init(GasStationDetailsActivity.this, getSupportActionBar());

        gas_id = getIntent().getStringExtra("GAS_ID");
        gas_name = getIntent().getStringExtra("GAS_NAME");

        if (gas_id == null) {
            gas_id = SharedPreferencesUtil.getFromFile(this, "GAS_ID");
        }
        if (gas_name==null)
        {
            gas_name = SharedPreferencesUtil.getFromFile(this,"GAS_NAME");
        }

        SharedPreferencesUtil.saveToFile(this, "GAS_ID", gas_id);
        SharedPreferencesUtil.saveToFile(this,"GAS_NAME",gas_name);
        titleBarManager.showCommonTextTitleBar(gas_name, R.drawable.return_arrow, true);
        showLoadingPage(false);
    }


    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        getDetailDataServer();
        come_pay.setOnClickListener(this);
        cb_collect.setOnClickListener(this);
        cb_praise.setOnClickListener(this);

    }

    public void getDetailDataServer() {
        Utils.showProgressDialog(mContext);
        ListGasStationDetailRequest request = new ListGasStationDetailRequest
                (
                        new Response.Listener<ListGasStationDetailResponse>() {
                            @Override
                            public void onResponse(final ListGasStationDetailResponse response) {
                                Utils.closeDialog();
                                ListGasStationDetailResponse.GasStationListDetail data = response.data;
                                wrapData(data);

                            }
                        }, this);
        request.setId(gas_id);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        WebUtils.doPost(request);
    }

    private void wrapData(ListGasStationDetailResponse.GasStationListDetail data) {


        if (StringUtils.isNotEmpty(data.deptContent)) {
            tv_gas_content.setText(data.deptContent);
        }
        if (StringUtils.isNotEmpty(data.gCoverImgPath)) {
            Glide.with(mContext).load(data.gCoverImgPath).into(iv_bg);
        }
        if (StringUtils.isNotEmpty(data.articleContent)) {

            tv_promotion.loadData(data.articleContent, "text/html; charset=UTF-8", null);
        }

        if (StringUtils.isNotEmpty(data.praiseNum)) {

            tv_praiseNum.setText("已赞" + "(" + data.praiseNum + ")");
            praiseNum = Integer.parseInt(data.praiseNum);
        }

        if (StringUtils.isNotEmpty(data.collectionNum)) {
            tv_collectNum.setText("收藏" + "(" + data.collectionNum + ")");
            collectionNum = Integer.parseInt(data.collectionNum);
        }

        String collectStatus = data.collectStatus;
        String praiseStatus = data.praiseStatus;
        if ("0".equals(praiseStatus)) {
            praiseid = data.praiseid;
            cb_praise.setChecked(true);
            isPraise = true;
        } else if ("1".equals(praiseStatus)) {
            cb_praise.setChecked(false);
            isPraise = false;

        }
        if ("0".equals(collectStatus)) {
            cb_collect.setChecked(true);
            collectid = data.collectid;
            isCollect = true;
        } else if ("1".equals(collectStatus)) {
            cb_collect.setChecked(false);
            isCollect = false;
        }

        mProductInfos = CnpcApplication.getInstance().getmProductInfos();
        if (mProductInfos != null && mProductInfos.size() > 0) {
            mProductInfos.clear();

        }
        mProductInfos = data.productInfos;
        if (mProductInfos != null && mProductInfos.size() > 0) {

            CnpcApplication.getInstance().setmProductInfos(mProductInfos);
            for (int i = 0; i < mProductInfos.size(); i++) {
            }
            wrapLvData(mProductInfos);
        }

    }

    private void wrapLvData(ArrayList<ListGasStationDetailResponse.GasStationDetailInfo> productInfoDTOs) {

        for (int i = 0; i < productInfoDTOs.size(); i++) {

        }

       /* MyAdapter adapter = new MyAdapter();
        lv_oils.setAdapter(adapter);
*/
       lv_oils.setAdapter(
                adapter = new CommonAdapter<ListGasStationDetailResponse.GasStationDetailInfo>(mContext, productInfoDTOs, R.layout.lv_oil_price) {

                    @Override
                    public void convert(ViewHolder helper, ListGasStationDetailResponse.GasStationDetailInfo item) {

                        helper.setText(R.id.tv_gas, item.productName);

                        helper.setText(R.id.tv_sale_price, Utils.StringTo2decimal(item.customerPrice));
                        helper.setText(R.id.tv_vip_price, Utils.StringTo2decimal(item.vipCustomerPrice));
                    }


                });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mProductInfos.size() ;
        }

        @Override
        public Object getItem(int position) {
            return mProductInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            convertView = getLayoutInflater().inflate(R.layout.lv_oil_price, null);

            TextView tv_gas = (TextView) convertView.findViewById(R.id.tv_gas);
            TextView tv_sale_price = (TextView) convertView.findViewById(R.id.tv_sale_price);
            TextView tv_vip_price = (TextView) convertView.findViewById(R.id.tv_vip_price);


            tv_gas.setText(mProductInfos.get(position).productName);
            tv_sale_price.setText(Utils.StringTo2decimal(mProductInfos.get(position).customerPrice));
            tv_vip_price.setText(Utils.StringTo2decimal(mProductInfos.get(position).vipCustomerPrice));

            return convertView;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.come_pay:
                if (mProductInfos == null || mProductInfos.size() == 0) {
                    Utils.makeToastAndShow(mContext, "该加油站无相关油品信息，无法进行加油支付");
                    return;
                }
                Intent intent = new Intent(mContext, RefuelPayActivity.class);
                intent.putExtra("GAS_NAME", gas_name);
                intent.putExtra("GAS_ID", gas_id);
                startActivity(intent);
                break;
            case R.id.cb_collect:
                if (isCollect) {
                    isCollect = false;
                    delCollect(collectid);
                    cb_collect.setChecked(false);
                    tv_collectNum.setText("收藏" + "(" + (collectionNum - 1) + ")");
                    collectionNum -= 1;
                } else {
                    isCollect = true;
                    setCollect();
                    cb_collect.setChecked(true);
                    tv_collectNum.setText("收藏" + "(" + (collectionNum + 1) + ")");
                    collectionNum += 1;
                }
                break;
            case R.id.cb_praise:

                if (isPraise) {
                    isPraise = false;
                    delPraise(praiseid);
                    cb_praise.setChecked(false);
                    tv_praiseNum.setText("已赞" + "(" + (praiseNum - 1) + ")");
                    praiseNum -= 1;
                } else {
                    isPraise = true;
                    setPraise();
                    cb_praise.setChecked(true);
                    tv_praiseNum.setText("已赞" + "(" + (praiseNum + 1) + ")");
                    praiseNum += 1;
                }
                break;
        }
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    public void setPraise() {
        Utils.showProgressDialog(mContext);
        PraiseRequest request = new PraiseRequest
                (
                        new Response.Listener<PraiseResponse>() {
                            @Override
                            public void onResponse(final PraiseResponse response) {
                                Utils.closeDialog();
                                Utils.makeToastAndShow(mContext, "点赞成功");
                                praiseid = response.getMsg();
                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setId(gas_id);
        WebUtils.doPost(request);
    }

    public void setCollect() {
        Utils.showProgressDialog(mContext);
        CollectRequest request = new CollectRequest
                (
                        new Response.Listener<PraiseResponse>() {
                            @Override
                            public void onResponse(final PraiseResponse response) {
                                Utils.closeDialog();
                                Utils.makeToastAndShow(mContext, "收藏成功");
                                collectid = response.getMsg();
                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setId(gas_id);
        WebUtils.doPost(request);
    }

    public void delPraise(String priseID) {
        Utils.showProgressDialog(mContext);
        DelPraiseRequest request = new DelPraiseRequest
                (
                        new Response.Listener<PraiseResponse>() {
                            @Override
                            public void onResponse(final PraiseResponse response) {
                                Utils.closeDialog();
                                Utils.makeToastAndShow(mContext, response.getMsg());
                            }
                        }, this);
        request.setId(priseID);
        WebUtils.doPost(request);
    }

    public void delCollect(String collectID) {
        Utils.showProgressDialog(mContext);
        DelCollectRequest request = new DelCollectRequest
                (
                        new Response.Listener<PraiseResponse>() {
                            @Override
                            public void onResponse(final PraiseResponse response) {
                                Utils.closeDialog();
                                Utils.makeToastAndShow(mContext, response.getMsg());
                            }
                        }, this);
        request.setId(collectID);
        WebUtils.doPost(request);
    }

}