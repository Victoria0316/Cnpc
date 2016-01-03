package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.model.OrderListDTO;
import com.bluemobi.cnpc.network.request.BorrowGasBillRequest;
import com.bluemobi.cnpc.network.request.BorrowGasRequest;
import com.bluemobi.cnpc.network.response.BorrowGasBillResponse;
import com.bluemobi.cnpc.network.response.BorrowGasResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.AlertDialog;
import com.bluemobi.cnpc.view.CnpcStatementsView;
import com.bluemobi.cnpc.view.CustomSpinnerBase;
import com.bluemobi.cnpc.view.CustomSpinnerOption;
import com.bluemobi.cnpc.view.InfoDialog;
import com.bluemobi.cnpc.view.LoadingPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 借用油首页
 * Created by wangzhijun on 2015/7/28.
 */
@ContentView(R.layout.activity_borrow_gas)
@PageName(R.string.mine_borrow_gas_title)
public class BorrowGasActivity extends BaseActivity{

    @Bind(R.id.statements)
    protected CnpcStatementsView statementsView;
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


    private TempAdapter mAdapter;
    private List<OrderListDTO> lists = new ArrayList<>();
    private AlertDialog tipDialog;

    private ArrayList<String> oilsList = new ArrayList<String>();
    private Map<String,String> sunyardoils = new HashMap<String,String>();


    public BorrowGasResponse.OilOrderData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBase() {
        showLoadingPage(false);
    }

    @Override
    protected CnpcHttpJsonRequest initRequest() {

        BorrowGasRequest request = new BorrowGasRequest(new Response.Listener<BorrowGasResponse>() {
            @Override
            public void onResponse(BorrowGasResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    data = response.data;
                    ArrayList<BorrowGasResponse.CheckBoxDTO> checkBoxDTOs = data.checkBoxDTOs;
                    final ArrayList<String> years = data.years;

                    lists.clear();
                    ArrayList<BorrowGasResponse.OrderListDTOS> cardTradeLogDTOs = response.data.oilOrderDTOs;
                    for (BorrowGasResponse.OrderListDTOS bean :cardTradeLogDTOs)
                    {
                        ArrayList<OrderListDTO> oilCardTradeDetailLogDTOs = bean.orderListDTOs;
                        lists.addAll(oilCardTradeDetailLogDTOs);
                    }
                    mAdapter.notifyDataSetChanged();
                    statementsView.refreshView(getResources().getStringArray(R.array.mine_pre_title), mAdapter);

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

                    for (BorrowGasResponse.CheckBoxDTO dto : checkBoxDTOs)
                    {
                        if(!dto.name.contains("#")){
                            dto.name = dto.name + "#";
                        }
                        sunyardoils.put(dto.name,dto.id);
                        oilsList.add(dto.name);
                    }

                    if(oilsList != null && oilsList.size()>0){
                        query_val.setText(oilsList.get(0));
                    }

                    pwOptionTypes.setPicker(oilsList);
                    pwOptionTypes.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3) {
                            String tx = oilsList.get(options1);

                            query_val.setText(tx);
                            Request();
                        }
                    });
                    pwOptionTypes.setSelectOptions(0);

                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("2");//初始化类型 1预购油 2借用油
        Utils.showProgressDialog(this);
        return request;
    }





    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showLRBar(R.string.mine_borrow_gas_title, R.drawable.return_arrow, R.drawable.attention);

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
        Intent intent = new Intent();
        intent.putExtra("key", "BorrowGasActivity");
        intent.setClass(this, InstructionsDetailsActivity.class);
        startActivity(intent);
    }

    private void initListener(){
        statementsView.setOnItemOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderListDTO item = lists.get(position);
                Intent intent = new Intent();
                intent.putExtra("id",item.id);
                intent.setClass(mContext, BorrowGasDetailActivity.class);
                startActivity(intent);
            }
        });
        tipDialog = new AlertDialog(this)
                .setTitle(getString(R.string.global_tip))
                .setMessage(getString(R.string.mine_borrow_gas_item_tip))
                .setNegativeButtonClickListener(getString(R.string.global_sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tipDialog.dismiss();
                    }
                });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.show();
            }
        }, 500);

    }

    private void Request() {
        BorrowGasBillRequest request = new BorrowGasBillRequest(new Response.Listener<BorrowGasBillResponse>() {
            @Override
            public void onResponse(BorrowGasBillResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success

                    lists.clear();
                    ArrayList<BorrowGasBillResponse.BorrowGasBillBean> temp = response.data;
                    if(temp!= null) {
                        for (BorrowGasBillResponse.BorrowGasBillBean dto : temp) {
                            ArrayList<OrderListDTO> oilOrderDTOs = dto.orderListDTOs;

                            for (OrderListDTO item : oilOrderDTOs) {
                                lists.add(item);
                            }

                        }
                    }
                    /*Collections.sort(lists, orderListDtoComparator);*/
                    mAdapter.notifyDataSetChanged();

                } else {// failed
                    lists.clear();
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(mContext,response.getMsg()==null ?"网络异常":response.getMsg(),Toast.LENGTH_SHORT).show();
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setHandleCustomErr(false);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setType("2");//初始化类型 1预购油 2借用油
        request.setrYear(year_val.getText().toString().trim());

        String temp = sunyardoils.get(query_val.getText().toString().trim());
        request.setOilId(temp);
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
            convertView = LayoutInflater.from(BorrowGasActivity.this).inflate(
                    R.layout.adapter_pre_gas_blue, parent, false);
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView gas = (TextView) convertView.findViewById(R.id.gas);
            TextView volume = (TextView) convertView.findViewById(R.id.volume);
            TextView amount = (TextView) convertView.findViewById(R.id.amount);

            time.setText(lists.get(position).tradeTime);
            gas.setText(lists.get(position).oilName);
            volume.setText(lists.get(position).oilVolume);
            amount.setText(lists.get(position).oilMoney);

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

    class OrderListDtoComparator implements Comparator<OrderListDTO> {
        public int compare(OrderListDTO o1, OrderListDTO o2) {
            //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
            if (o2.tradeTime == null) {
                return -1;
            }
//            else if (o1.tradeTime.charAt(0) > o2.tradeTime.charAt(0)) {
//                return 1;
//            }
            else {
                return o1.tradeTime.compareTo(o2.tradeTime);
            }
        }
    }
}
