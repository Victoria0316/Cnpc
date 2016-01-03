package com.bluemobi.cnpc.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.model.AddGasFillingCardBean;
import com.bluemobi.cnpc.network.model.OilCardTradeDetailLogDTOs;
import com.bluemobi.cnpc.network.request.ConsumptionDetailedRequest;
import com.bluemobi.cnpc.network.request.OilWalletBillRequest;
import com.bluemobi.cnpc.network.request.OilWalletRequest;
import com.bluemobi.cnpc.network.response.ConsumptionDetailedResponse;
import com.bluemobi.cnpc.network.response.OilWalletBillResponse;
import com.bluemobi.cnpc.network.response.OilWalletResponse;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.CnpcStatementsView;
import com.bluemobi.cnpc.view.CustomSpinnerBase;
import com.bluemobi.cnpc.view.CustomSpinnerOption;
import com.bluemobi.cnpc.view.InfoDialog;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.view.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by gaoyn on 2015/7/24.
 * <p/>
 * p10-5 零钱
 */
@ContentView(R.layout.activity_coin_purse)
@PageName(R.string.order_change_wallet)
public class CoinPurseActivity extends BaseActivity implements View.OnClickListener {
    private final static String tag = "CoinPurseActivity";

    @Bind(R.id.query_sel)
    RelativeLayout query_sel;

    @Bind(R.id.yeal_sel)
    RelativeLayout yeal_sel;

    @Bind(R.id.transfer_come)
    Button transfer_come;

    @Bind(R.id.transfer_go)
    Button transfer_go;

    @Bind(R.id.query_val)
    TextView query_val;

    @Bind(R.id.statements)
    protected CnpcStatementsView statementsView;
    /**
     * 余额
     */
    @Bind(R.id.balance_val)
    TextView balance_val;

    @Bind(R.id.year_val)
    protected TextView year_val;

    private TempAdapter mAdapter;
    private List<OilCardTradeDetailLogDTOs> lists = new ArrayList<OilCardTradeDetailLogDTOs>();
    private InfoDialog dialog;

    private ArrayList<String> oilDataList = new ArrayList<String>();
    private Map<String, String> sunyardoils = new HashMap<String, String>();
    private com.bluemobi.cnpc.view.AlertDialog tipDialog;
    public OilWalletResponse.OilWalletBean data;

    private String mChageBalance;

    private int dialogType = 0;

    private OptionsPopupWindow pwOptions;

    private OptionsPopupWindow pwOptionTypes;


    private Map<String, String> mapKey = new HashMap<String, String>();


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
                    ArrayList<OilWalletResponse.checkBoxDTOs> checkBoxDTOs = data.checkBoxDTOs;
                    final ArrayList<String> years = data.years;
                    mChageBalance = data.changeBalance;
                    balance_val.setText(mChageBalance);
                    if (lists != null && lists.size() > 0) {
                        lists.clear();
                    }
                    ArrayList<OilWalletResponse.cardTradeLogDTOs> cardTradeLogDTOs = response.data.cardTradeLogDTOs;
                    for (OilWalletResponse.cardTradeLogDTOs bean : cardTradeLogDTOs) {
                        ArrayList<OilCardTradeDetailLogDTOs> oilCardTradeDetailLogDTOs = bean.oilCardTradeDetailLogDTOs;
                        lists.addAll(oilCardTradeDetailLogDTOs);
                    }
                    mAdapter = new TempAdapter();
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
                    for (OilWalletResponse.checkBoxDTOs dto : checkBoxDTOs) {
                        if("全部".equals(dto.name)){
                            dto.id = "";
                        }
                        sunyardoils.put(dto.name, dto.id);
                        oilDataList.add(dto.name);
                    }
                    if(oilDataList == null && oilDataList.size() == 0){
                        return;
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

                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("2");
        Utils.showProgressDialog(this);
        return request;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
//        initRequest();


        request();
    }

    private void request() {

        OilWalletRequest request = new OilWalletRequest(new Response.Listener<OilWalletResponse>() {
            @Override
            public void onResponse(OilWalletResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    data = response.data;
                    ArrayList<OilWalletResponse.checkBoxDTOs> checkBoxDTOs = data.checkBoxDTOs;
                    final ArrayList<String> years = data.years;
                    mChageBalance = data.changeBalance;


                    balance_val.setText(mChageBalance);
                    if (lists != null && lists.size() > 0) {
                        lists.clear();
                    }
                    ArrayList<OilWalletResponse.cardTradeLogDTOs> cardTradeLogDTOs = response.data.cardTradeLogDTOs;
                    for (OilWalletResponse.cardTradeLogDTOs bean : cardTradeLogDTOs) {
                        ArrayList<OilCardTradeDetailLogDTOs> oilCardTradeDetailLogDTOs = bean.oilCardTradeDetailLogDTOs;
                        lists.addAll(oilCardTradeDetailLogDTOs);
                    }
                    mAdapter = new TempAdapter();
                    statementsView.refreshView(getResources().getStringArray(
                            R.array.mine_gas_wallet_title), mAdapter);
                    initListener();
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
                    for (OilWalletResponse.checkBoxDTOs dto : checkBoxDTOs) {
                        sunyardoils.put(dto.name, dto.id);
                        oilDataList.add(dto.name);
                    }

                    if(oilDataList == null && oilDataList.size() == 0){
                        return;
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

                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("2");
        Utils.showProgressDialog(this);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebUtils.doPost(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showLRBar(R.string.order_change_wallet, R.drawable.return_arrow, R.drawable.attention);
        mapKey.put("1", "现金");
        mapKey.put("2", "银行卡转账");
        mapKey.put("3", "支付宝");
        mapKey.put("4", "微信");
        mapKey.put("5", "银联");
        mapKey.put("6", "油钱包余额");
        pwOptions = new OptionsPopupWindow(this, R.layout.pw_options, true);
        pwOptions.setOutsideTouchable(true);
        pwOptions.setFocusable(true);

        pwOptionTypes = new OptionsPopupWindow(this, R.layout.pw_options, true);

        yeal_sel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pwOptions.backgroundAlpha(0.7f);
                pwOptions.showAtLocation(yeal_sel, Gravity.BOTTOM, 0, 0);
                if (lists != null && lists.size() > 0)
                    lists.clear();

            }
        });
        pwOptionTypes.setOutsideTouchable(true);
        pwOptionTypes.setFocusable(true);
        query_sel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pwOptionTypes.backgroundAlpha(0.7f);
                pwOptionTypes.showAtLocation(yeal_sel, Gravity.BOTTOM, 0, 0);
                if (lists != null && lists.size() > 0)
                    lists.clear();

            }
        });

        mAdapter = new TempAdapter();
        transfer_come.setOnClickListener(this);
        transfer_go.setOnClickListener(this);


    }

    @Override
    public void clickImageRight() {
       /* EventBus.getDefault().post("CoinPurseActivity");
        Utils.moveTo(mContext, InstructionsDetailsActivity.class);*/

        Intent intent = new Intent();
        intent.putExtra("key","CoinPurseActivity");
        intent.setClass(this, InstructionsDetailsActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.transfer_come:
                Intent intent = new Intent(this, CoinTransferIntoActivity.class);
                intent.putExtra("MCHAGEBALANCE", mChageBalance);
                startActivity(intent);

                break;
            case R.id.transfer_go:
                Intent inten = new Intent(this, CoinTransferOutActivity.class);
                inten.putExtra("balance_val",mChageBalance);
                startActivity(inten);
                break;
        }
    }

    private void initListener() {

        statementsView.setOnItemOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog = new InfoDialog(CoinPurseActivity.this);
                dialog.setTitle(lists.get(position).tradeType, getResources().getColor(R.color.common_blue));
                dialog.setNegagiveTextColor(getResources().getColor(R.color.common_blue));
                View messageView = null;
                if ("资金转入".equals(lists.get(position).tradeType)) {
                    messageView = LayoutInflater.from(CoinPurseActivity.this).inflate(
                            R.layout.dialog_coin_fundin, null);
                    dialogType = 1;
                } else if ("资金转出".equals(lists.get(position).tradeType)) //冻结款转入
                {
                    messageView = LayoutInflater.from(CoinPurseActivity.this).inflate(
                            R.layout.dialog_coin_fundout, null);
                    dialogType = 2;

                } else if ("油品退货转入".equals(lists.get(position).tradeType)) {
                    messageView = LayoutInflater.from(CoinPurseActivity.this).inflate(
                            R.layout.dialog_coin_bow_pre_consum_oil, null);
                    dialogType = 3;
                } else if ("油品归还转入".equals(lists.get(position).tradeType)) {
                    messageView = LayoutInflater.from(CoinPurseActivity.this).inflate(
                            R.layout.dialog_coin_bow_pre_consum_oil, null);
                    dialogType = 4;

                } else if ("油品预购转出".equals(lists.get(position).tradeType)) {
                    messageView = LayoutInflater.from(CoinPurseActivity.this).inflate(
                            R.layout.dialog_coin_bow_pre_consum_oil, null);
                    dialogType = 6;
                } else if ("油品借用转出".equals(lists.get(position).tradeType))//油品借用转出
                {
                    messageView = LayoutInflater.from(CoinPurseActivity.this).inflate(
                            R.layout.dialog_coin_bow_pre_consum_oil, null);
                    dialogType = 7;
                } else if ("加油消费".equals(lists.get(position).tradeType))//加油消费
                {
                    messageView = LayoutInflater.from(CoinPurseActivity.this).inflate(
                            R.layout.dialog_fuel_consumption, null);
                    dialogType = 8;
                }else if ( ("充值消费".equals(lists.get(position).tradeType)))
                {
                    dialogType = 9;
                    //TODO
                    messageView = LayoutInflater.from(CoinPurseActivity.this).inflate(
                            R.layout.dialog_coin_intransfer, null);
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
        request.setType("2");
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }

    private void wrapDetailData(View messageView, ConsumptionDetailedResponse.ConsumptionDetailedData data, int dialogTypeParam) {
        if (data == null)
            return;

        switch (dialogTypeParam) {
            case 1:
                TextView coin_fundin_order_date = (TextView) messageView.findViewById(R.id.coin_fundin_order_date);
                TextView coin_fundin_order_num = (TextView) messageView.findViewById(R.id.coin_fundin_order_num);
                TextView coin_fundin_order_pay_amount = (TextView) messageView.findViewById(R.id.coin_fundin_order_pay_amount);
                TextView coin_fundin_transfer_in = (TextView) messageView.findViewById(R.id.coin_fundin_transfer_in);
                coin_fundin_order_date.setText(data.tradeTime);
                coin_fundin_order_num.setText(data.orderNO);
                //String mPayType = mapKey.get(data.payType);
                coin_fundin_order_pay_amount.setText(data.payType);
                coin_fundin_transfer_in.setText(data.transferInMoney);
                break;
            case 2:
                TextView coin_fundout_order_date = (TextView) messageView.findViewById(R.id.coin_fundout_order_date);
                TextView coin_fundout_order_num = (TextView) messageView.findViewById(R.id.coin_fundout_order_num);
                TextView coin_fundout_order_pay_amount = (TextView) messageView.findViewById(R.id.coin_fundout_order_pay_amount);
               // TextView coin_fundout_order_preferential = (TextView) messageView.findViewById(R.id.coin_fundout_order_preferential);
                TextView coin_fundout_order_transfer_in = (TextView) messageView.findViewById(R.id.coin_fundout_order_transfer_in);
                coin_fundout_order_date.setText(data.tradeTime);
                coin_fundout_order_num.setText(data.orderNO);
                coin_fundout_order_pay_amount.setText(data.payType);
                /*coin_fundout_order_preferential.setText(data.preferential);*/
                coin_fundout_order_transfer_in.setText(data.transferOutMoney);
                break;
            case 3:
                TextView oil_type2 = (TextView) messageView.findViewById(R.id.tv_coin_oil_type);
                oil_type2.setText("预购油品：");
                TextView tv_coin_browoil_order_date1 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_order_date);
                TextView tv_coin_browoil_order_num1 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_order_num);
                TextView tv_coin_browoil_type1 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_type);
                TextView tv_coin_browoil_price1 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_price);
                TextView tv_coin_browoil_liter1 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_liter);
                TextView tv_coin_browoil_proce1 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_proce);
                TextView tv_coin_browoil_storage1 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_storage);
                TextView tv_coin_browoil_into1 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_into);
                TextView tv_type_consum1 = (TextView) messageView.findViewById(R.id.tv_type_consum);
                tv_type_consum1.setText("代储费：");
                tv_coin_browoil_order_date1.setText(data.tradeTime);
                tv_coin_browoil_order_num1.setText(data.orderNO);
                tv_coin_browoil_type1.setText(data.oilName);
                tv_coin_browoil_price1.setText(data.unitPrice);
                tv_coin_browoil_liter1.setText(data.val);
                tv_coin_browoil_proce1.setText(data.money);
                tv_coin_browoil_storage1.setText(data.storageMoney);
                tv_coin_browoil_into1.setText(data.transferInMoney);
                break;

            case 4:
                TextView oil_type1 = (TextView) messageView.findViewById(R.id.tv_coin_oil_type);
                oil_type1.setText("预购油品：");
                TextView tv_coin_browoil_order_date11 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_order_date);
                TextView tv_coin_browoil_order_num11 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_order_num);
                TextView tv_coin_browoil_type11 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_type);
                TextView tv_coin_browoil_price11 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_price);
                TextView tv_coin_browoil_liter11 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_liter);
                TextView tv_coin_browoil_proce11 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_proce);
                TextView tv_coin_browoil_storage11 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_storage);
                TextView tv_coin_browoil_into11 = (TextView) messageView.findViewById(R.id.tv_coin_browoil_into);
                TextView tv_type_consum = (TextView) messageView.findViewById(R.id.tv_type_consum);
                tv_type_consum.setText("借用费：");
                tv_coin_browoil_order_date11.setText(data.tradeTime);
                tv_coin_browoil_order_num11.setText(data.orderNO);
                tv_coin_browoil_type11.setText(data.oilName);
                tv_coin_browoil_price11.setText(data.unitPrice);
                tv_coin_browoil_liter11.setText(data.val);
                tv_coin_browoil_proce11.setText(data.money);
                tv_coin_browoil_storage11.setText(data.storageMoney);
                tv_coin_browoil_into11.setText(data.transferInMoney);
                break;
            case 6:
                //TODO
                TextView oil_type = (TextView) messageView.findViewById(R.id.tv_coin_oil_type);
                oil_type.setText("预购油品：");
                TextView tv_coin_browoil_order_date = (TextView) messageView.findViewById(R.id.tv_coin_browoil_order_date);
                TextView tv_coin_browoil_order_num = (TextView) messageView.findViewById(R.id.tv_coin_browoil_order_num);
                TextView tv_coin_browoil_type = (TextView) messageView.findViewById(R.id.tv_coin_browoil_type);
                TextView tv_coin_browoil_price = (TextView) messageView.findViewById(R.id.tv_coin_browoil_price);
                TextView tv_coin_browoil_liter = (TextView) messageView.findViewById(R.id.tv_coin_browoil_liter);
                TextView tv_coin_browoil_proce = (TextView) messageView.findViewById(R.id.tv_coin_browoil_proce);
                TextView tv_coin_browoil_storage = (TextView) messageView.findViewById(R.id.tv_coin_browoil_storage);
                TextView tv_coin_browoil_into = (TextView) messageView.findViewById(R.id.tv_coin_browoil_into);
                tv_coin_browoil_order_date.setText(data.tradeTime);
                tv_coin_browoil_order_num.setText(data.orderNO);
                tv_coin_browoil_type.setText(data.oilName);
                tv_coin_browoil_price.setText(data.unitPrice);
                tv_coin_browoil_liter.setText(data.val);
                tv_coin_browoil_proce.setText(data.money);
                tv_coin_browoil_storage.setText(data.storageMoney);
                tv_coin_browoil_into.setText(data.transferOutMoney);
                break;
            case 7:

                TextView tv_coin_browoil_order_dated = (TextView) messageView.findViewById(R.id.tv_coin_browoil_order_date);
                TextView tv_coin_browoil_order_numd = (TextView) messageView.findViewById(R.id.tv_coin_browoil_order_num);
                TextView tv_coin_browoil_typed = (TextView) messageView.findViewById(R.id.tv_coin_browoil_type);
                TextView tv_coin_browoil_priced = (TextView) messageView.findViewById(R.id.tv_coin_browoil_price);
                TextView tv_coin_browoil_literd = (TextView) messageView.findViewById(R.id.tv_coin_browoil_liter);
                TextView tv_coin_browoil_proced = (TextView) messageView.findViewById(R.id.tv_coin_browoil_proce);
                TextView tv_coin_browoil_storaged = (TextView) messageView.findViewById(R.id.tv_coin_browoil_storage);
                TextView tv_coin_browoil_intod = (TextView) messageView.findViewById(R.id.tv_coin_browoil_into);

                tv_coin_browoil_order_dated.setText(data.tradeTime);
                tv_coin_browoil_order_numd.setText(data.orderNO);
                tv_coin_browoil_typed.setText(data.oilName);
                tv_coin_browoil_priced.setText(data.unitPrice);
                tv_coin_browoil_literd.setText(data.val);
                tv_coin_browoil_proced.setText(data.money);
                tv_coin_browoil_storaged.setText(data.storageMoney);
                tv_coin_browoil_intod.setText(data.transferOutMoney);
                break;

            case 8:
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
                tv_fuel_consum_type.setText(data.oilName);//油品
                tv_fuel_consum_price.setText(data.unitPrice);//单价
                tv_fuel_consum_liter.setText(data.val);
                tv_fuel_consum_preprice.setText(data.preferential);
                tv_fuel_consum_actprice.setText(data.actualPayMoney);
                tv_fuel_consum_outprice.setText(data.transferOutMoney);

                break;

            case 9:
                TextView coin_date1 = (TextView) messageView.findViewById(R.id.coin_date1);
                TextView coin_data_num = (TextView) messageView.findViewById(R.id.coin_data_num);
                TextView coin_1 = (TextView) messageView.findViewById(R.id.coin_1);
                TextView coin_2 = (TextView) messageView.findViewById(R.id.coin_2);
                TextView coin_3 = (TextView) messageView.findViewById(R.id.coin_3);

                coin_date1.setText(data.tradeTime);
                coin_data_num.setText(data.orderNO);

                coin_1.setText(data.actualPayMoney); //充值金额
                coin_2.setText(data.preferential);// 优惠金额,);
                coin_3.setText(data.transferOutMoney);
                break;


        }
    }


    private void Request() {

        OilWalletBillRequest request = new OilWalletBillRequest(new Response.Listener<OilWalletBillResponse>() {
            @Override
            public void onResponse(OilWalletBillResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    if (lists != null && lists.size() > 0)
                        lists.clear();
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

                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setrYear(year_val.getText().toString().trim());
        String temp = sunyardoils.get(query_val.getText().toString().trim());
        request.setrType(temp);
        request.setType("2");
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
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
            convertView = LayoutInflater.from(CoinPurseActivity.this).inflate(
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

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


}
