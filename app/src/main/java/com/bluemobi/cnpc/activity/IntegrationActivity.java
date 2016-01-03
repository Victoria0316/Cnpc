package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bluemobi.cnpc.network.model.OilCardTradeDetailLogDTO;
import com.bluemobi.cnpc.network.request.IntegrationBillRequest;
import com.bluemobi.cnpc.network.request.IntegrationRequest;
import com.bluemobi.cnpc.network.response.IntegrationBillResponse;
import com.bluemobi.cnpc.network.response.IntegrationResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.CnpcStatementsView;
import com.bluemobi.cnpc.view.CustomSpinnerBase;
import com.bluemobi.cnpc.view.CustomSpinnerOption;
import com.bluemobi.cnpc.view.LoadingPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/27.
 * 冻结款首页
 */
@ContentView(R.layout.activity_frozen_section)
@PageName(R.string.s_frozen_money)
public class IntegrationActivity extends BaseActivity {

    private TitleBarManager titleBarManager;

    @Bind(R.id.balance_val)
    TextView balance_val;

    @Bind(R.id.combined)
    TextView combined;

    @Bind(R.id.combined_price)
    TextView combined_price;

    @Bind(R.id.yeal_sel)
    RelativeLayout yeal_sel;

    @Bind(R.id.year_val)
    protected TextView year_val;

    @Bind(R.id.query_sel)
    RelativeLayout query_sel;

    @Bind(R.id.query_val)
    TextView query_val;

    private OptionsPopupWindow pwOptions;
    private OptionsPopupWindow pwOptionTypes;

    @Bind(R.id.statements)
    protected CnpcStatementsView statementsView;

    @Bind(R.id.balance_label)
    TextView balance_label;

    private TempAdapter mAdapter;
    private List<OilCardTradeDetailLogDTO> lists = new ArrayList<>();

    private ArrayList<String> oilsList = new ArrayList<String>();
    private Map<String,String> sunyardoils = new HashMap<String,String>();

    public IntegrationResponse.IntegrationBean data;

    private ArrayList<IntegrationBillResponse.IntegrationBillBean> temp;

    @Override
    protected void initBase() {
        titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showLRBar(R.string.s_frozen_money, R.drawable.return_arrow, R.drawable.attention);

        showLoadingPage(false);
    }

    @Override
    public void clickImageRight() {
        Intent intent = new Intent();
        intent.putExtra("key", "IntegrationActivity");
        intent.setClass(this, InstructionsDetailsActivity.class);
        startActivity(intent);
    }


    @Override
    protected CnpcHttpJsonRequest initRequest() {


        IntegrationRequest request = new IntegrationRequest(new Response.Listener<IntegrationResponse>() {
            @Override
            public void onResponse(IntegrationResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    data = response.data;
                    ArrayList<IntegrationResponse.CheckBoxDTO> checkBoxDTOs = data.checkBoxDTOs;
                    final ArrayList<String> years = data.years;
                    balance_val.setText(data.frozenBalance);
                    lists.clear();
                    ArrayList<IntegrationResponse.CardTradeLogDTOs> cardTradeLogDTOs = response.data.cardTradeLogDTOs;
                    if(cardTradeLogDTOs != null && cardTradeLogDTOs.size()>0){
                        if(cardTradeLogDTOs.get(0).yearAmount !=null){
                            combined_price.setText(cardTradeLogDTOs.get(0).yearAmount);
                        }else{
                            combined_price.setText("0.00");
                        }
                    }


                    for (IntegrationResponse.CardTradeLogDTOs bean :cardTradeLogDTOs)
                    {
                        ArrayList<OilCardTradeDetailLogDTO> oilCardTradeDetailLogDTOs = bean.oilCardTradeDetailLogDTOs;
                        if(oilCardTradeDetailLogDTOs != null){
                            lists.addAll(oilCardTradeDetailLogDTOs);
                        }

                    }
                    mAdapter.notifyDataSetChanged();
                    statementsView.refreshView(new String[]{"待转日期", "待转金额", "到账日期"}, mAdapter);

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

                    for (IntegrationResponse.CheckBoxDTO dto : checkBoxDTOs)
                    {
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
        Utils.showProgressDialog(this);
        return request;
    }




    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        balance_label.setText("冻结款");

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

    private void Request() {
        IntegrationBillRequest request = new IntegrationBillRequest(new Response.Listener<IntegrationBillResponse>() {
            @Override
            public void onResponse(IntegrationBillResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 2)
                {
                    if("1".equals(sunyardoils.get(query_val.getText().toString().trim()))){
                        statementsView.refreshView(new String[]{"待转日期", "待转金额", "到账日期"}, mAdapter);
                        combined.setText("合计待转金额：");
                        combined_price.setText("0.00");
                    }else{
                        statementsView.refreshView(new String[]{"已转日期", "已转金额", "到账地址"}, mAdapter);
                        combined.setText("合计已转金额：");
                        combined_price.setText("0.00");

                    }
                }

                if (response != null && response.getStatus() == 0) {// success

                    if("1".equals(sunyardoils.get(query_val.getText().toString().trim()))){
                        statementsView.refreshView(new String[]{"待转日期", "待转金额", "到账日期"}, mAdapter);
                        combined.setText("合计待转金额：");
                    }else{
                        statementsView.refreshView(new String[]{"已转日期", "已转金额", "到账地址"}, mAdapter);
                        combined.setText("合计已转金额：");
                    }

                    lists.clear();
                    if (temp!=null&&temp.size()>0)
                        temp.clear();
                     temp= response.data;

                    if(temp!= null) {

                        if(temp.get(0).yearAmount !=null){
                            combined_price.setText(temp.get(0).yearAmount);
                        }else{
                            combined_price.setText("0.00");
                        }


                        for (IntegrationBillResponse.IntegrationBillBean dto : temp) {
                            ArrayList<OilCardTradeDetailLogDTO> oilOrderDTOs = dto.oilCardTradeDetailLogDTOs;

                            for (OilCardTradeDetailLogDTO item : oilOrderDTOs) {
                                lists.add(item);
                            }

                        }
                    }
                    mAdapter.notifyDataSetChanged();

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
        request.setrYear(year_val.getText().toString().trim());
        String temp = sunyardoils.get(query_val.getText().toString().trim());
        request.setrType(temp);
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
            convertView = LayoutInflater.from(IntegrationActivity.this).inflate(
                    R.layout.adapter_oil_wallet_red, parent, false
            );
            TextView time =(TextView) convertView.findViewById(R.id.time);
            TextView type =(TextView) convertView.findViewById(R.id.type);
            TextView amount =(TextView) convertView.findViewById(R.id.amount);

            time.setText(lists.get(position).tradeTime);

            if(!"null".equals(lists.get(position).tradeMoney)){
                type.setText(lists.get(position).tradeMoney);
            }else{
                type.setText("0.00");
            }

            if("1".equals(sunyardoils.get(query_val.getText().toString().trim()))){
                amount.setText(lists.get(position).transferTime);
            }else{
                amount.setText(lists.get(position).transferAddress);
            }
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
}
