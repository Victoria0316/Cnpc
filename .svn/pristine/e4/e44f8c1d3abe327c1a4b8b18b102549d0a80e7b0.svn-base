package com.bluemobi.cnpc.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.SectionAdapter;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.OptionsPopupWindow;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.db.entity.OilWalletModel;
import com.bluemobi.cnpc.eventbus.BaseEvent;
import com.bluemobi.cnpc.eventbus.SuccessRefreshEvent;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.model.OilCardTradeDetailLogDTOs;
import com.bluemobi.cnpc.network.request.ConsumptionDetailedRequest;
import com.bluemobi.cnpc.network.request.OilWalletBillRequest;
import com.bluemobi.cnpc.network.request.OilWalletRequest;
import com.bluemobi.cnpc.network.response.BorrowGasResponse;
import com.bluemobi.cnpc.network.response.ConsumptionDetailedResponse;
import com.bluemobi.cnpc.network.response.OilWalletBillResponse;
import com.bluemobi.cnpc.network.response.OilWalletResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.AlertDialog;
import com.bluemobi.cnpc.view.CnpcStatementsView;
import com.bluemobi.cnpc.view.CustomSpinnerBase;
import com.bluemobi.cnpc.view.CustomSpinnerOption;
import com.bluemobi.cnpc.view.InfoDialog;
import com.bluemobi.cnpc.view.LoadingPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 油钱包
 * Created by wangzhijun on 2015/7/23.
 */
@ContentView(R.layout.activity_oil_wallet)
@PageName(R.string.order_oil_wallet)
public class OilWalletActivity extends BaseActivity {
    @Bind(R.id.statements)
    protected CnpcStatementsView statementsView;
    /**
     * 余额
     */
    @Bind(R.id.balance_val)
    TextView balance_val;
    /**
     * 年份
     */
    @Bind(R.id.yeal_sel)
    RelativeLayout yeal_sel;

    @Bind(R.id.year_val)
    protected TextView year_val;

    private OptionsPopupWindow pwOptions;

    /**
     * 油品
     */
    @Bind(R.id.query_sel)
    RelativeLayout query_sel;

    @Bind(R.id.query_val)
    TextView query_val;

    private OptionsPopupWindow pwOptionTypes;



    private String bindOilCard;// "bindOilCard": "是否绑定油卡0已绑定，1未绑定（只有初始化油钱包时才有）",
    private TempAdapter mAdapter;
    private List<OilCardTradeDetailLogDTOs> lists = new ArrayList<OilCardTradeDetailLogDTOs>();
    private InfoDialog dialog;

    private ArrayList<String> oilDataList = new ArrayList<String>();
    private Map<String, String> sunyardoils = new HashMap<String, String>();
    private AlertDialog tipDialog;
    public OilWalletResponse.OilWalletBean data;

    private int dialogType = 0;
    private String defaultCardId;
    private String defaultCardName;

    private RefreshBroadcastReceiver receiver;

    /**
     * 是否从加油支付界面返回 true 为 加油支付返回
     */
    private boolean isBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        receiver = new RefreshBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.REFRESH);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void initBase() {
        showLoadingPage(false);
    }

    @Override
    protected CnpcHttpJsonRequest initRequest() {

        OilWalletRequest request = new OilWalletRequest(new Response.Listener<OilWalletResponse>() {
            @Override
            public void onResponse(OilWalletResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    data = response.data;
                    bindOilCard = data.bindOilCard;
                    defaultCardId = data.defaultCardId;
                    defaultCardName = data.defaultCardName;

                    ArrayList<OilWalletResponse.checkBoxDTOs> checkBoxDTOs = data.checkBoxDTOs;
                    final ArrayList<String> years = data.years;
                    balance_val.setText(data.oilBalance);
                    lists.clear();
                    ArrayList<OilWalletResponse.cardTradeLogDTOs> cardTradeLogDTOs = response.data.cardTradeLogDTOs;
                    for (OilWalletResponse.cardTradeLogDTOs bean : cardTradeLogDTOs) {
                        ArrayList<OilCardTradeDetailLogDTOs> oilCardTradeDetailLogDTOs = bean.oilCardTradeDetailLogDTOs;
                        lists.addAll(oilCardTradeDetailLogDTOs);
                    }
                    mAdapter.notifyDataSetChanged();
                    statementsView.refreshView(getResources().getStringArray(
                            R.array.mine_gas_wallet_title), mAdapter);
                    initListener();

                    if(years != null && years.size()>0){
                        year_val.setText(years.get(0));
                    }

                    pwOptions.setPicker(years);
                    pwOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3) {
                            String tx = years.get(options1);
                            year_val.setText(tx);
                            Request();
                        }
                    });
                    pwOptions.setSelectOptions(0);

                    /*sunyardoils.put("全部", "");
                    oilDataList.add("全部");*/
                    for (OilWalletResponse.checkBoxDTOs dto : checkBoxDTOs) {
                        sunyardoils.put(dto.name, dto.id);
                        Log.e("tag--->", dto.id);
                        oilDataList.add(dto.name);
                    }

                    if(oilDataList != null && oilDataList.size()>0){
                        query_val.setText(oilDataList.get(0));
                    }

                    pwOptionTypes.setPicker(oilDataList);
                    pwOptionTypes.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3) {
                            String tx = oilDataList.get(options1);

                            query_val.setText(tx);
                            Request();
                        }
                    });
                    pwOptionTypes.setSelectOptions(0);

                    if (isBack)
                    Request();

                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("1");

        Utils.showProgressDialog(this);
        return request;
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showLRBar(R.string.order_oil_wallet, R.drawable.return_arrow, R.drawable.attention);


        pwOptions = new OptionsPopupWindow(this, R.layout.pw_options, true);
        pwOptions.setOutsideTouchable(true);
        pwOptions.setFocusable(true);

        yeal_sel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pwOptions.backgroundAlpha(0.7f);
                pwOptions.showAtLocation(yeal_sel, Gravity.BOTTOM, 0, 0);

            }
        });

        pwOptionTypes = new OptionsPopupWindow(this, R.layout.pw_options, true);
        pwOptionTypes.setOutsideTouchable(true);
        pwOptionTypes.setFocusable(true);
        query_sel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pwOptionTypes.backgroundAlpha(0.7f);
                pwOptionTypes.showAtLocation(query_sel, Gravity.BOTTOM, 0, 0);

            }
        });
        mAdapter = new TempAdapter();
    }

    @Override
    public void clickImageRight() {
        //EventBus.getDefault().post("OilWalletActivity");
        Intent intent = new Intent();
        intent.putExtra("key","OilWalletActivity");
        intent.setClass(this, InstructionsDetailsActivity.class);
        startActivity(intent);
        //Utils.moveTo(mContext, InstructionsDetailsActivity.class);
    }

    private void initListener() {
        statementsView.setOnItemOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog = new InfoDialog(OilWalletActivity.this);
                dialog.setTitle(lists.get(position).tradeType, getResources().getColor(R.color.common_blue));
                dialog.setNegagiveTextColor(getResources().getColor(R.color.common_blue));
                View messageView = null;
                //充值转入
                if ("充值转入".equals(lists.get(position).tradeType)) {
                    messageView = LayoutInflater.from(OilWalletActivity.this).inflate(
                            R.layout.dialog_oil_wallet, null);
                    dialogType = 1;
                } else if ("冻结款转入".equals(lists.get(position).tradeType)) //冻结款转入
                {
                    messageView = LayoutInflater.from(OilWalletActivity.this).inflate(
                            R.layout.dialog_frozen_section, null);
                    dialogType = 2;

                } else if ("预购油转入".equals(lists.get(position).tradeType))//预购油转入
                {
                    messageView = LayoutInflater.from(OilWalletActivity.this).inflate(
                            R.layout.dialog_preoil, null);
                    dialogType = 3;
                    //TODO
                } else if ("借用油转入".equals(lists.get(position).tradeType))//借用油转入
                {
                    messageView = LayoutInflater.from(OilWalletActivity.this).inflate(
                            R.layout.dialog_bow_section, null);
                    dialogType = 4;
                } else if ("转入油卡".equals(lists.get(position).tradeType))//转入油卡
                {
                    messageView = LayoutInflater.from(OilWalletActivity.this).inflate(
                            R.layout.dialog_into_oil, null);
                    dialogType = 5;
                } else if ("加油消费".equals(lists.get(position).tradeType))//加油消费
                {
                    messageView = LayoutInflater.from(OilWalletActivity.this).inflate(
                            R.layout.dialog_fuel_consumption, null);
                    dialogType = 6;
                }
                getDetailFromServer(messageView, lists.get(position).id, dialogType);

                dialog.setMessage(messageView).setNegativeButtonClickListener("确定",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                dialog.show();
            }
        });
    }

    /**
     * 获取单条详细信息
     *
     * @param messageView
     */
    private void getDetailFromServer(final View messageView, String detailID, final int dialogTypeParm) {
        ConsumptionDetailedRequest request = new ConsumptionDetailedRequest(new Response.Listener<ConsumptionDetailedResponse>() {
            @Override
            public void onResponse(ConsumptionDetailedResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success

                    ConsumptionDetailedResponse.ConsumptionDetailedData data = response.data;
                    wrapDetailData(messageView, data, dialogTypeParm);
                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setId(detailID);
        request.setType("1");
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }

    private void wrapDetailData(View messageView, ConsumptionDetailedResponse.ConsumptionDetailedData data, int dialogTypeParam) {
        if (data == null)
            return;
        switch (dialogTypeParam) {
            case 1:
                TextView mine_order_date = (TextView) messageView.findViewById(R.id.mine_order_date);
                TextView mine_order_num = (TextView) messageView.findViewById(R.id.mine_order_num);
                TextView mine_order_pay_amount = (TextView) messageView.findViewById(R.id.mine_order_pay_amount);
                TextView mine_order_preferential = (TextView) messageView.findViewById(R.id.mine_order_preferential);
                TextView mine_order_transfer_in = (TextView) messageView.findViewById(R.id.mine_order_transfer_in);
                mine_order_date.setText(data.tradeTime);
                mine_order_num.setText(data.orderNO);
                mine_order_pay_amount.setText(data.actualPayMoney);
                mine_order_preferential.setText(data.preferential);
                mine_order_transfer_in.setText(data.transferInMoney);
                break;
            case 2:
                TextView mine_frozen_order_date = (TextView) messageView.findViewById(R.id.mine_frozen_order_date);
                TextView mine_frozen_order_num = (TextView) messageView.findViewById(R.id.mine_frozen_order_num);
                TextView mine_frozen_order_transfer_in = (TextView) messageView.findViewById(R.id.mine_frozen_order_transfer_in);
                mine_frozen_order_date.setText(data.tradeTime);
                mine_frozen_order_num.setText(data.orderNO);
                mine_frozen_order_transfer_in.setText(data.transferInMoney);


                break;
            case 3:

                TextView tv_preoil_order_date = (TextView) messageView.findViewById(R.id.tv_preoil_order_date);
                TextView tv_preoil_order_num = (TextView) messageView.findViewById(R.id.tv_preoil_order_num);
                TextView tv_oil_type = (TextView) messageView.findViewById(R.id.tv_oil_type);
                TextView tv_preoil_price = (TextView) messageView.findViewById(R.id.tv_preoil_price);
                TextView tv_preoil_liter = (TextView) messageView.findViewById(R.id.tv_preoil_liter);
                TextView tv_preoil_proce = (TextView) messageView.findViewById(R.id.tv_preoil_proce);
                TextView tv_pro_oil_storage = (TextView) messageView.findViewById(R.id.tv_pro_oil_storage);
                TextView tv_pro_oil_into = (TextView) messageView.findViewById(R.id.tv_pro_oil_into);
                tv_preoil_order_date.setText(data.tradeTime);
                tv_preoil_order_num.setText(data.orderNO);
                tv_oil_type.setText(data.oilName);
                tv_preoil_price.setText(data.unitPrice);
                tv_preoil_liter.setText(data.val);
                tv_preoil_proce.setText(data.money);
                tv_pro_oil_storage.setText(data.storageMoney);
                tv_pro_oil_into.setText(data.transferInMoney);

                break;
            case 4:
                TextView tv_browoil_order_date = (TextView) messageView.findViewById(R.id.tv_browoil_order_date);
                TextView tv_browoil_order_num = (TextView) messageView.findViewById(R.id.tv_browoil_order_num);
                TextView tv_browoil_type = (TextView) messageView.findViewById(R.id.tv_browoil_type);
                TextView tv_browoil_price = (TextView) messageView.findViewById(R.id.tv_browoil_price);
                TextView tv_browoil_liter = (TextView) messageView.findViewById(R.id.tv_browoil_liter);
                TextView tv_browoil_proce = (TextView) messageView.findViewById(R.id.tv_browoil_proce);
                TextView tv_browoil_storage = (TextView) messageView.findViewById(R.id.tv_browoil_storage);
                TextView tv_browoil_into = (TextView) messageView.findViewById(R.id.tv_browoil_into);
                tv_browoil_order_date.setText(data.tradeTime);
                tv_browoil_order_num.setText(data.orderNO);
                tv_browoil_type.setText(data.oilName);
                tv_browoil_price.setText(data.unitPrice);
                tv_browoil_liter.setText(data.val);
                tv_browoil_proce.setText(data.money);
                tv_browoil_storage.setText(data.storageMoney);
                tv_browoil_into.setText(data.transferInMoney);
                break;
            case 5:
                TextView tv_intooil_order_date = (TextView) messageView.findViewById(R.id.tv_intooil_order_date);
                TextView tv_intooil_order_num = (TextView) messageView.findViewById(R.id.tv_intooil_order_num);
                TextView tv_intooil_type = (TextView) messageView.findViewById(R.id.tv_intooil_type);
                TextView tv_intooil_price = (TextView) messageView.findViewById(R.id.tv_intooil_price);
                TextView tv_intooil_into = (TextView) messageView.findViewById(R.id.tv_intooil_into);
                tv_intooil_order_date.setText(data.tradeTime);
                tv_intooil_order_num.setText(data.orderNO);
                tv_intooil_type.setText(data.deptName);
                tv_intooil_price.setText(data.cardNo);
                tv_intooil_into.setText(data.transferOutMoney);
                break;
            case 6:
                TextView tv_fueloil_order_num = (TextView) messageView.findViewById(R.id.tv_fueloil_order_num);
                TextView tv_fuel_consum_order_num = (TextView) messageView.findViewById(R.id.tv_fuel_consum_order_num);
                TextView tv_fuel_consum_name = (TextView) messageView.findViewById(R.id.tv_fuel_consum_name);
                TextView tv_fuel_consum_type = (TextView) messageView.findViewById(R.id.tv_fuel_consum_type);
                TextView tv_fuel_consum_price = (TextView) messageView.findViewById(R.id.tv_fuel_consum_price);
                TextView tv_fuel_consum_liter = (TextView) messageView.findViewById(R.id.tv_fuel_consum_liter);
                TextView tv_fuel_consum_preprice = (TextView) messageView.findViewById(R.id.tv_fuel_consum_preprice);
                TextView tv_fuel_consum_actprice = (TextView) messageView.findViewById(R.id.tv_fuel_consum_actprice);
                TextView tv_fuel_consum_outprice = (TextView) messageView.findViewById(R.id.tv_fuel_consum_outprice);
                tv_fueloil_order_num.setText(data.tradeTime);
                tv_fuel_consum_order_num.setText(data.orderNO);
                tv_fuel_consum_name.setText(data.deptName);
                tv_fuel_consum_type.setText(data.oilName);
                tv_fuel_consum_price.setText(data.unitPrice);
                tv_fuel_consum_liter.setText(data.val);
                tv_fuel_consum_preprice.setText(data.preferential);
                tv_fuel_consum_actprice.setText(data.actualPayMoney);
                tv_fuel_consum_outprice.setText(data.transferOutMoney);
                break;

        }
    }


    private void Request() {
        //下拉选择
        OilWalletBillRequest request = new OilWalletBillRequest(new Response.Listener<OilWalletBillResponse>() {
            @Override
            public void onResponse(OilWalletBillResponse response) {
                Utils.closeDialog();
                if (response.getStatus() == 2) {
                    lists.clear();
                }
                if (response != null && response.getStatus() == 0) {// success

                    if (lists != null && lists.size() > 0){
                        lists.clear();
                    }

                    ArrayList<OilWalletBillResponse.OilWalletBillBean> temp = response.data;
                    if (temp != null) {
                        for (OilWalletBillResponse.OilWalletBillBean item : temp) {
                            lists.addAll(item.oilCardTradeDetailLogDTOs);
                        }
                    }

                    mAdapter.notifyDataSetChanged();
                    statementsView.refreshView(getResources().getStringArray(
                            R.array.mine_gas_wallet_title), mAdapter);
                    initListener();

                } else {// failed
                    lists.clear();
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(mContext, response.getMsg() == null ? "网络异常" : response.getMsg(), Toast.LENGTH_SHORT).show();
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setHandleCustomErr(false);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        String text = year_val.getText().toString().trim();
        request.setrYear(text);
        String temp = sunyardoils.get(query_val.getText().toString().trim());
        request.setrType(temp);
        request.setType("1");
        if (!isBack)
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);

    }



    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    class TempAdapter extends SectionAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(OilWalletActivity.this).inflate(
                    R.layout.adapter_oil_wallet, parent, false
            );
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView type = (TextView) convertView.findViewById(R.id.type);
            TextView amount = (TextView) convertView.findViewById(R.id.amount);

            time.setText(lists.get(position).tradeTime);
            type.setText(lists.get(position).tradeType);
            amount.setText(lists.get(position).tradeMoney);
            return convertView;
        }

        @Override
        public Object[] getSections() {
            return null;
        }

        @Override
        public int getPositionForSection(int sectionIndex) {
            for (int j = 0; j < getCount(); j++) {
                String sortStr = lists.get(j).tradeTime;
                int firstChar = Integer.parseInt(sortStr.split("-")[0]);
                if (firstChar == sectionIndex) {
                    return j;
                }
            }
            return -1;
        }

        @Override
        public int getSectionForPosition(int position) {
            int section;
            section = lists.size() == 0 ?
                    -1 : lists.get(position) == null ?
                    -1 : Integer.parseInt(lists.get(position).tradeTime.split("-")[0]);
            return section;
        }
    }

    @OnClick(R.id.transfer)
    public void goTransfer() {
        if ("1".equals(bindOilCard)) {
            tipDialog = new AlertDialog(this)
                    .setTitle(getString(R.string.global_tip))
                    .setMessage("请先绑定一张油卡")
                    .setNegativeButtonClickListener(getString(R.string.global_sure), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tipDialog.dismiss();
                            Utils.moveTo(mContext,AddGasFillingCardActivity.class);
                        }
                    });
            tipDialog.show();
        } else {
            Intent intent = new Intent();
            if (StringUtils.isNotEmpty(defaultCardId))
                intent.putExtra("DEFAULTCARDID", defaultCardId);
            if (StringUtils.isNotEmpty(defaultCardName))
                intent.putExtra("DEFAULTCARDNAME", defaultCardName);
            intent.putExtra("balance", balance_val.getText().toString());
            intent.setClass(this, OilInActivity.class);
            startActivity(intent);
        }

    }

//    public void onEventPostThread(SuccessRefreshEvent event)
//    {
//
//        Log.e("wangzhijun", "BaseEvent" + "----");
//        reload();
//    }



    private void reload() {

        isBack = true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
        unregisterReceiver(receiver);
    }
    class RefreshBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            reload();
        }
    }
}
