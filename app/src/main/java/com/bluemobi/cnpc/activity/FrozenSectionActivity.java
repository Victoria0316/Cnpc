package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.bluemobi.cnpc.db.entity.OilWalletModel;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.model.DetailLogDTO;
import com.bluemobi.cnpc.network.request.FrozenSectionBillRequest;
import com.bluemobi.cnpc.network.request.FrozenSectionRequest;
import com.bluemobi.cnpc.network.response.FrozenSectionBillResponse;
import com.bluemobi.cnpc.network.response.FrozenSectionResponse;
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
 * <p/>
 * P10_7 积分
 */
@ContentView(R.layout.activity_frozen_section)
@PageName(R.string.integral_tar)
public class FrozenSectionActivity extends BaseActivity {

    private TitleBarManager titleBarManager;

    @Bind(R.id.balance_val)
    TextView balance_val;

    @Bind(R.id.btn_ll)
    LinearLayout btn_ll;

    @Bind(R.id.yeal_sel)
    RelativeLayout yeal_sel;

    @Bind(R.id.year_val)
    protected TextView year_val;

    @Bind(R.id.query_sel)
    RelativeLayout query_sel;

    @Bind(R.id.query_val)
    TextView query_val;

    @Bind(R.id.statements)
    protected CnpcStatementsView statementsView;

    private TempAdapter mAdapter;
    private List<DetailLogDTO> lists = new ArrayList<>();

    private ArrayList<String> oilsList = new ArrayList<String>();
    private Map<String, String> sunyardoils = new HashMap<String, String>();

    public FrozenSectionResponse.FrozenSectionBean data;

    private OptionsPopupWindow pwOptions;
    private OptionsPopupWindow pwOptionTypes;

    @Override
    protected void initBase() {
        titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showLRBar(R.string.integral_tar, R.drawable.return_arrow, R.drawable.attention);

        showLoadingPage(false);
    }

    @Override
    public void clickImageRight() {
        Intent intent = new Intent();
        intent.putExtra("key", "FrozenSectionActivity");
        intent.setClass(this, InstructionsDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    protected CnpcHttpJsonRequest initRequest() {

        FrozenSectionRequest request = new FrozenSectionRequest(new Response.Listener<FrozenSectionResponse>() {
            @Override
            public void onResponse(FrozenSectionResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    data = response.data;
                    ArrayList<FrozenSectionResponse.CheckBoxDTO> checkBoxDTOs = data.checkBoxDTOs;
                    final ArrayList<String> years = data.years;
                    balance_val.setText(data.pointsBalance);
                    if (lists != null && lists.size() > 0) {
                        lists.clear();
                    }
                    ArrayList<FrozenSectionResponse.CardTradeLogDTOs> cardTradeLogDTOs = response.data.cardTradeLogDTOs;
                    for (FrozenSectionResponse.CardTradeLogDTOs bean : cardTradeLogDTOs) {
                        ArrayList<DetailLogDTO> oilCardTradeDetailLogDTOs = bean.oilCardTradeDetailLogDTOs;
                        lists.addAll(oilCardTradeDetailLogDTOs);
                    }

                    mAdapter.notifyDataSetChanged();
                    statementsView.refreshView(new String[]{"时间", "类别", "名称", "积分"}, mAdapter);

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

                    for (FrozenSectionResponse.CheckBoxDTO dto : checkBoxDTOs) {
                        sunyardoils.put(dto.name, dto.id);
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
        btn_ll.setVisibility(View.GONE);

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

        FrozenSectionBillRequest request = new FrozenSectionBillRequest(new Response.Listener<FrozenSectionBillResponse>() {
            @Override
            public void onResponse(FrozenSectionBillResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success

                    lists.clear();
                    ArrayList<FrozenSectionBillResponse.FrozenSectionBillBean> temp = response.data;
                    if (temp != null) {
                        for (FrozenSectionBillResponse.FrozenSectionBillBean dto : temp) {
                            ArrayList<DetailLogDTO> oilOrderDTOs = dto.oilCardTradeDetailLogDTOs;

                            for (DetailLogDTO item : oilOrderDTOs) {
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
            convertView = LayoutInflater.from(FrozenSectionActivity.this).inflate(
                    R.layout.adapter_oil_integ, parent, false
            );
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView type = (TextView) convertView.findViewById(R.id.type);
            TextView amount = (TextView) convertView.findViewById(R.id.amount);
            TextView last = (TextView) convertView.findViewById(R.id.last);

            time.setText(lists.get(position).tradeTime);
            type.setText(lists.get(position).tradeType);
            amount.setText(lists.get(position).changeType);
            last.setText(lists.get(position).points);

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

    class OrderListDtoComparator implements Comparator<DetailLogDTO> {
        public int compare(DetailLogDTO o1, DetailLogDTO o2) {
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
