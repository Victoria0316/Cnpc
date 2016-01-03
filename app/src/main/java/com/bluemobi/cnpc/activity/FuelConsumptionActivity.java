package com.bluemobi.cnpc.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.ExpandableSectionAdapter;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.db.entity.OilWalletModel;
import com.bluemobi.cnpc.network.model.FindOilCLogDetail;
import com.bluemobi.cnpc.network.model.IndexOilConsumeBean;
import com.bluemobi.cnpc.network.model.OrderListDTOsBean;
import com.bluemobi.cnpc.network.request.FindOilCLogDetailRequest;
import com.bluemobi.cnpc.network.request.IndexOilConsumeRequest;
import com.bluemobi.cnpc.network.response.FindOilCLogDetailResponse;
import com.bluemobi.cnpc.network.response.IndexOilConsumeResponse;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.CnpcStatementsView;
import com.bluemobi.cnpc.view.CustomSpinnerOption;
import com.bluemobi.cnpc.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/27.
 * P10_13加油消费
 */
@ContentView(R.layout.activity_fuel_consumption)
@PageName(R.string.s_fuel_consumption)
public class FuelConsumptionActivity extends BaseActivity {
    private final static String tag = "FuelConsumptionActivity";

    private TitleBarManager titleBarManager;

    private IndexOilConsumeBean data;

    @Bind(R.id.cso_spinner)
    CustomSpinnerOption customSpinnerOption;


    @Bind(R.id.statements)
    protected CnpcStatementsView statementsView;

    private TempAdapter mAdapter;

    private List<OilWalletModel> lists = new ArrayList<>();
    private boolean flag = false;
    List<OrderListDTOsBean> listdata = new ArrayList<OrderListDTOsBean>();
    FindOilCLogDetail datas;

    @Override
    protected void initBase() {
        titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_fuel_consumption, R.drawable.return_arrow, true);

        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        request();
    }

    private void request() {
        IndexOilConsumeRequest request = new IndexOilConsumeRequest(new Response.Listener<IndexOilConsumeResponse>() {
            @Override
            public void onResponse(IndexOilConsumeResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {
                    data = response.getData();
                    List<String> datas = new ArrayList<String>();
                    for (int i = 0; i < response.getData().getYears().size(); i++) {
                        datas.add(response.getData().getYears().get(i));
                    }
                    customSpinnerOption.setDatas(datas);
                    listdata.clear();

                    if (response.getData().getOilOrderDTOs() != null) {

                        for (int i = 0; i < response.getData().getOilOrderDTOs().size(); i++) {
                            for (int j = 0; j < response.getData().getOilOrderDTOs().get(i).getOrderListDTOs().size(); j++) {
                                listdata.add(response.getData().getOilOrderDTOs().get(i).getOrderListDTOs().get(j));
                            }
                        }
                    }
                    if(mAdapter == null){
                        mAdapter = new TempAdapter(listdata);
                    }
                    statementsView.refreshView(new String[]{"时间", "加油站", "油品", "加油金额"}, mAdapter);
                    statementsView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            flag = true;
                            connectToServer(v, groupPosition);
                            return false;
                        }
                    });


                }

            }
        }, FuelConsumptionActivity.this);

        request.setUserId(PreferencesService.getInstance(FuelConsumptionActivity.this).getPreferences("userId"));
        request.setCheckUserId(PreferencesService.getInstance(FuelConsumptionActivity.this).getPreferences("userId"));
        request.setSsid(PreferencesService.getInstance(FuelConsumptionActivity.this).getPreferences("ssid"));
        Utils.showProgressDialog(FuelConsumptionActivity.this);
        WebUtils.doPost(request);


    }

    private void connectToServer(final View v, int groupPosition) {
        FindOilCLogDetailRequest request = new FindOilCLogDetailRequest(new Response.Listener<FindOilCLogDetailResponse>() {
            @Override
            public void onResponse(FindOilCLogDetailResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {
                    datas = response.getData();
                    flag = false;
                    mAdapter.notifyDataSetChanged();
                }

            }
        }, FuelConsumptionActivity.this);

        Logger.d(tag, "id  : " + listdata.get(groupPosition).getId());
        request.setId(listdata.get(groupPosition).getId()); //加油消费订单主键id
        request.setCheckUserId(PreferencesService.getInstance(FuelConsumptionActivity.this).getPreferences("userId"));//用户登录时返回的userId对应的值
        request.setSsid(PreferencesService.getInstance(FuelConsumptionActivity.this).getPreferences("ssid"));//session ID
        Utils.showProgressDialog(FuelConsumptionActivity.this);
        WebUtils.doPost(request);


    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    class TempAdapter extends ExpandableSectionAdapter {

        private List<OrderListDTOsBean> data;

        public TempAdapter(List<OrderListDTOsBean> data) {
            this.data = data;

        }

        @Override
        public int getGroupCount() {

            return data == null ? 0 : data.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }


        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            HolderViewGroup holderGroup = null;
            if (convertView == null) {
                holderGroup = new HolderViewGroup();
                convertView = LayoutInflater.from(FuelConsumptionActivity.this).inflate(R.layout.adapter_pre_gas_red, parent, false);
                holderGroup.tv_time = (TextView) convertView.findViewById(R.id.tv_time);//时间
                holderGroup.tv_gas = (TextView) convertView.findViewById(R.id.tv_gas);//加油站
                holderGroup.tv_volume = (TextView) convertView.findViewById(R.id.tv_volume);//油品
                holderGroup.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);//加油金额


                convertView.setTag(holderGroup);
            } else {
                holderGroup = (HolderViewGroup) convertView.getTag();
            }


            holderGroup.tv_time.setText(data.get(groupPosition).getTradeTime());
            holderGroup.tv_gas.setText(data.get(groupPosition).getDeptName());
            holderGroup.tv_volume.setText(data.get(groupPosition).getOilName());
            holderGroup.tv_amount.setText(data.get(groupPosition).getOilMoney());


            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            HolderViewChild holderChild = null;
            if (convertView == null) {
                holderChild = new HolderViewChild();
                convertView = LayoutInflater.from(FuelConsumptionActivity.this).inflate(R.layout.eplv_item_all, parent, false);
                holderChild.tv_time = (TextView) convertView.findViewById(R.id.tv_time);//  2015-05-05  14:00:00
                holderChild.tv_order_num = (TextView) convertView.findViewById(R.id.tv_order_num);//            订单编号：
                holderChild.tv_oils = (TextView) convertView.findViewById(R.id.tv_oils);//            油品 %1$s#
                holderChild.tv_oils_price = (TextView) convertView.findViewById(R.id.tv_oils_price);//6.10
                holderChild.tv_litre = (TextView) convertView.findViewById(R.id.tv_litre);//升
                holderChild.tv_favorable_price = (TextView) convertView.findViewById(R.id.tv_favorable_price);//5000元
                holderChild.tv_refuel_price = (TextView) convertView.findViewById(R.id.tv_refuel_price);//200元
                holderChild.tv_practical_price = (TextView) convertView.findViewById(R.id.tv_practical_price);//100元
                holderChild.tv_discount_coupon = (TextView) convertView.findViewById(R.id.tv_discount_coupon);//优惠券：
                holderChild.tv_oil_wallet = (TextView) convertView.findViewById(R.id.tv_oil_wallet);//油钱包
                holderChild.tv_loose_change = (TextView) convertView.findViewById(R.id.tv_loose_change);//零钱包
                holderChild.tv_other_pay = (TextView) convertView.findViewById(R.id.tv_other_pay);//其他支付
                holderChild.tv_practical_pay = (TextView) convertView.findViewById(R.id.tv_practical_pay);//实际支付
                holderChild.tv_integral = (TextView) convertView.findViewById(R.id.tv_integral);//赠积分

                convertView.setTag(holderChild);
            } else {
                holderChild = (HolderViewChild) convertView.getTag();
            }
            if (datas != null) {
                holderChild.tv_time.setText(datas.getTransactionTime());//  2015-05-05  14:00:00
                holderChild.tv_order_num.setText(datas.getOrderNo());//            订单编号：
                holderChild.tv_oils.setText("油品:" + datas.getProductName() + "#");//            油品 %1$s#
                holderChild.tv_oils_price.setText(datas.getProductPrice());//6.10
                holderChild.tv_litre.setText(datas.getProductNum() + "升");//升
                holderChild.tv_favorable_price.setText(datas.getPreferentialPrice() + "元");//5000元
                holderChild.tv_refuel_price.setText(datas.getPayAmount() + "元");//200元
                holderChild.tv_practical_price.setText(datas.getPreferentialAmount() + "元");//100元
                holderChild.tv_discount_coupon.setText(datas.getCouponAmount() + "元");//优惠券：
                holderChild.tv_oil_wallet.setText(datas.getOilAmount() + "元");//油钱包
                holderChild.tv_loose_change.setText(datas.getChangeAmount() + "元");//零钱包
                if (datas.getActualAmount()==null){

                    holderChild.tv_other_pay.setText("0.00" + "元");//其他支付
                }else{
                    holderChild.tv_other_pay.setText(datas.getActualAmount() + "元");//其他支付
                }
                holderChild.tv_practical_pay.setText(datas.getActualPayMoney() + "元");//实际支付
                holderChild.tv_integral.setText(datas.getAwardedIntegral());//赠积分
            }

            return convertView;
        }

        @Override
        public Object[] getSections() {
            return null;
        }

        @Override
        public int getPositionForSection(int sectionIndex) {
            /*for (int j = 0; j < getGroupCount(); j++) {
                String sortStr = listdata.get(j).getSortLetters();
                char firstChar = sortStr.charAt(0);
                if (firstChar == sectionIndex) {
                    return j;
                }
            }*/
            return -1;
        }

        @Override
        public int getSectionForPosition(int position) {
            int section;
            section = data.size() == 0 ?
                    -1 : data.get(position) == null ?
                    -1 : Integer.parseInt(data.get(position).getTradeTime().split("-")[0]);
            return section;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }


        class HolderViewGroup {

            TextView tv_time, tv_gas, tv_volume, tv_amount;
        }

        class HolderViewChild {

            TextView tv_time;//  2015-05-05  14:00:00
            TextView tv_order_num;//            订单编号：
            TextView tv_oils;//            油品 %1$s#
            TextView tv_oils_price;//6.10
            TextView tv_litre;//升
            TextView tv_favorable_price;//5000元
            TextView tv_refuel_price;//200元
            TextView tv_practical_price;//100元
            TextView tv_discount_coupon;//优惠券：
            TextView tv_oil_wallet;//油钱包
            TextView tv_loose_change;//零钱包
            TextView tv_other_pay;//其他支付
            TextView tv_practical_pay;//实际支付
            TextView tv_integral;//赠积分
        }


    }
}
