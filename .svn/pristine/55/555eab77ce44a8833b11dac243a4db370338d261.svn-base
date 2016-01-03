package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.model.PayPreferentialInfo;
import com.bluemobi.cnpc.network.model.SalesPromotionInfo;
import com.bluemobi.cnpc.network.model.oilInfoDTOInfo;
import com.bluemobi.cnpc.network.request.PayPreferentialRequest;
import com.bluemobi.cnpc.network.request.ProductSelectionRequest;
import com.bluemobi.cnpc.network.response.PayPreferentialResponse;
import com.bluemobi.cnpc.network.response.ProductSelectionResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * P5油品选择
 */

@ContentView(R.layout.activity_product_selection)
@PageName(R.string.s_product_selection)
public class ProductSelectionActivity extends BaseActivity implements View.OnClickListener {


    //油品预购
    @Bind(R.id.tv_oil_pre)
    Button tv_oil_pre;

    @Bind(R.id.gridview)
    GridView gridView;

    //油品借用
    @Bind(R.id.tv_oil_bow)
    Button tv_oil_bow;


    @Bind(R.id.tv_explain1)
    TextView tv_explain1;

    @Bind(R.id.tv_explain2)
    TextView tv_explain2;

    @Bind(R.id.time)
    TextView timeDate;

    private TempAdapter mAdapter;
    private int lastSelected;

    final List<oilInfoDTOInfo> info = new ArrayList<oilInfoDTOInfo>();


    @Override
    protected void initBase() {
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(ProductSelectionActivity.this, getSupportActionBar());
        titleBarManager.showLRBar(R.string.s_product_selection, R.drawable.return_arrow, R.drawable.attention);
        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long time = System.currentTimeMillis();
        String formatTime = df.format(time);

        timeDate.setText(formatTime + "   今日可预购或借用");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lastSelected != position) {
                    lastSelected = position;
                    mAdapter.notifyDataSetChanged();
                }
                if (info.get(position).getProductStore() == null && info.get(position).getProductBorrow() == null) {
                    tv_explain1.setVisibility(View.INVISIBLE);
                    tv_explain2.setVisibility(View.INVISIBLE);
                } else if (info.get(position).getProductStore() == null) {
                    tv_explain2.setText("2. 借用费率:" + Utils.StringTo2decimal(info.get(position).getProductBorrow()) + "%/天");
                } else if (info.get(position).getProductBorrow() == null) {
                    tv_explain2.setText("2. 代储费率:" + Utils.StringTo2decimal(info.get(position).getProductStore()) + "%/天");
                } else {
                    tv_explain2.setText("2. 代储费率:" + Utils.StringTo2decimal(info.get(position).getProductStore()) + "%/天,借用费率:" + Utils.StringTo2decimal(info.get(position).getProductBorrow()) + "%/天");
                }

                if (info.get(position).getIsPostage().equals("0")) {
                    tv_oil_bow.setBackgroundColor(getResources().getColor(R.color.common_gray));
                    tv_oil_bow.setEnabled(false);
                }
                if (info.get(position).getIsShelves().equals("0")) {
                    tv_oil_pre.setBackgroundColor(getResources().getColor(R.color.common_gray));
                    tv_oil_pre.setEnabled(false);
                }

            }
        });

        tv_oil_pre.setOnClickListener(this);
        tv_oil_bow.setOnClickListener(this);
        getPage(LOAD_REFRESH);

    }

    @Override
    protected boolean getPage(int type)
    {
        if(!super.getPage(type)) return false;
        connectToSercer();
        return true;
    }


    private void connectToSercer()
    {
        Utils.showProgressDialog(mContext);
        ProductSelectionRequest request = new ProductSelectionRequest
                (
                        new Response.Listener<ProductSelectionResponse>() {
                            @Override
                            public void onResponse(ProductSelectionResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    info.addAll(response.getData().getInfo());
                                    mAdapter = new TempAdapter();
                                    gridView.setAdapter(mAdapter);
                                    if (info.get(0).getProductStore() == null && info.get(0).getProductBorrow() == null) {
                                        tv_explain1.setVisibility(View.INVISIBLE);
                                        tv_explain2.setVisibility(View.INVISIBLE);
                                    } else if (info.get(0).getProductStore() == null) {
                                        tv_explain2.setText("2. 借用费率:" + Utils.StringTo2decimal(info.get(0).getProductBorrow()) + "%/天");
                                    } else if (info.get(0).getProductBorrow() == null) {
                                        tv_explain2.setText("2. 代储费率:" + Utils.StringTo2decimal(info.get(0).getProductStore()) + "%/天");
                                    }else{
                                        tv_explain2.setText("2. 代储费率:" + Utils.StringTo2decimal(info.get(0).getProductStore()) + "%/天,借用费率:" + Utils.StringTo2decimal(info.get(0).getProductBorrow()) + "%/天");
                                    }
                                    if (info.get(0).getIsPostage().equals("0")) {
                                        tv_oil_bow.setBackgroundColor(getResources().getColor(R.color.common_gray));
                                        tv_oil_bow.setEnabled(false);
                                    }
                                    if (info.get(0).getIsShelves().equals("0")) {
                                        tv_oil_pre.setBackgroundColor(getResources().getColor(R.color.common_gray));
                                        tv_oil_pre.setEnabled(false);
                                    }
                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(getCurPage() + "");
        WebUtils.doPost(request);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_oil_bow:
                oilInfoDTOInfo oilId = info.get(lastSelected);
                Intent Int = new Intent();
                Int.putExtra("oilId", oilId.getId());
                Int.setClass(this, BorrowOrdersForSureActivity.class);
                startActivity(Int);
                break;
            case R.id.tv_oil_pre:
                oilInfoDTOInfo item = info.get(lastSelected);
                Intent intent = new Intent();
                intent.putExtra("oilId", item.getId());
                intent.setClass(this, PreOrdersForSureActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void clickImageRight() {
        Utils.moveTo(mContext, OilWebViewDetailActivity.class);
    }

    class TempAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return info.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            oilInfoDTOInfo item = info.get(position);
            convertView = LayoutInflater.from(ProductSelectionActivity.this).inflate(
                    R.layout.adapter_product_selection, parent, false);
            RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.item_bg);
            TextView tv_oil_type1 = (TextView)convertView.findViewById(R.id.tv_oil_type1);
            TextView tv_oil_price1 = (TextView)convertView.findViewById(R.id.tv_oil_price1);

            tv_oil_type1.setText(item.getProductName());
            tv_oil_price1.setText(item.getCustomerPrice()+"元/吨");

            if (lastSelected == position) {
                rl.setBackgroundResource(R.drawable.oil_type_select);
            } else {
                rl.setBackgroundResource(R.drawable.oil_type_unselect);

            }
            return convertView;
        }
    }
}
