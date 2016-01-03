package com.bluemobi.cnpc.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.model.MakeOutInvoiceInfo;
import com.bluemobi.cnpc.network.request.CarDelRequest;
import com.bluemobi.cnpc.network.request.DelReceiptinfoRequest;
import com.bluemobi.cnpc.network.request.MakeOutInvoiceRequest;
import com.bluemobi.cnpc.network.response.CarDelResponse;
import com.bluemobi.cnpc.network.response.DelReceiptinfoResponse;
import com.bluemobi.cnpc.network.response.MakeOutInvoiceResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.InfoDialog;
import com.bluemobi.cnpc.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liufy on 2015/7/29.
 * P10_15 开票信息
 */
@ContentView(R.layout.activity_invoice_info)
@PageName(R.string.s_invoice_info)
public class MakeOutInvoiceActivity extends BaseActivity {
    private final static String tag = "MakeOutInvoiceActivity";

    @Bind(R.id.listView)
    protected SwipeMenuListView listView;

    private TempAdapter mAdapter;

    @Bind(R.id.btn_add)
    Button btn_add;

//    private CommonAdapter<MakeOutInvoiceInfo> adapter;

    private List<MakeOutInvoiceInfo> dataList = new ArrayList<MakeOutInvoiceInfo>();

    private String currentpage;

    private static final int ADD_AND_REFRESH = 0;


    @Override
    protected void initBase() {
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_invoice_info, R.drawable.return_arrow, true);
        showLoadingPage(false);
    }

    @OnClick(R.id.btn_add)
    public void addInvoice() {
        Intent intent = new Intent();
        intent.setClass(this, MakeOutAddInvoiceActivity.class);
        startActivityForResult(intent, ADD_AND_REFRESH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_AND_REFRESH) {
                getPage(LOAD_REFRESH);
            }
        }

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);


        initListSetting();
        getPage(LOAD_REFRESH);

    }

    @Override
    protected boolean getPage(int type) {
        if (!super.getPage(type)) return false;
        getDataServer(type);
        return true;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    public void getDataServer(final int type) {
        Utils.showProgressDialog(mContext);
        MakeOutInvoiceRequest request = new MakeOutInvoiceRequest
                (
                        new Response.Listener<MakeOutInvoiceResponse>() {
                            @Override
                            public void onResponse(MakeOutInvoiceResponse response) {
                                Utils.closeDialog();
                                if (type == LOAD_REFRESH) {
                                    dataList.clear();
                                }
                                if (response != null && response.getStatus() == 0) {
                                    final List<MakeOutInvoiceInfo> info = response.getData().getInfo();
                                    currentpage = response.getData().getCurrentpage();
                                    wrapListView(info);

                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setCurrentnum(1000+"");
        request.setCurrentpage(0 + "");
        request.setHandleCustomErr(false);
        WebUtils.doPost(request);

    }

    private void wrapListView(List<MakeOutInvoiceInfo> info) {
        if (info == null) {
            return;
        }
        if (info.size() == 0) {
            return;
        }

        if (currentpage.equals("0")) {
            dataList.clear();
        }

        for (MakeOutInvoiceInfo lineDto : info) {
            dataList.add(lineDto);
        }

//        if (adapter == null) {
//            listView.setAdapter(
//                    adapter = new CommonAdapter<MakeOutInvoiceInfo>(mContext, dataList, R.layout.lv_make_out_invoice) {
//                        @Override
//                        public void convert(ViewHolder helper, MakeOutInvoiceInfo item) {
//
//                            TextView companyName = helper.getView(R.id.companyName);
//                            if(item.getReceiptType().equals("1")){
//                                companyName.setText("个人");
//                            }else{
//                                companyName.setText(item.getCompanyName());
//                            }
//
//
//                        }
//                    });
//        } else {
//
//            adapter.notifyDataSetChanged();
//        }


        if (mAdapter == null) {
            mAdapter = new TempAdapter();
            listView.setAdapter(mAdapter);
        } else {
            listView.setAdapter(mAdapter);
        }
    }


    private void initListSetting() {

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        Logger.d(tag, "删除");
                        Utils.showProgressDialog(mContext);
                        DelReceiptinfoRequest request = new DelReceiptinfoRequest(new Response.Listener<DelReceiptinfoResponse>() {

                            @Override
                            public void onResponse(DelReceiptinfoResponse response) {
                                Utils.closeDialog();
                                if (response !=null && response.getStatus() ==0){
                                    Toast.makeText(MakeOutInvoiceActivity.this,"删除成功",Toast.LENGTH_SHORT).show();

                                    dataList.remove(position);
                                    if (mAdapter == null) {
                                        mAdapter = new TempAdapter();
                                        listView.setAdapter(mAdapter);
                                    } else {
                                        listView.setAdapter(mAdapter);
                                    }

                                }

                            }
                        }, MakeOutInvoiceActivity.this);


                        request.setCheckUserId(PreferencesService.getInstance(mContext).getPreferences("userId"));
                        request.setSsid(PreferencesService.getInstance(mContext).getPreferences("ssid"));
                        request.setId(dataList.get(position).getId());

                        WebUtils.doPost(request);
                        break;
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Logger.d(tag, "setOnItemClickListener");
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
                    case 1:
                        SwipeMenuItem openItem = new SwipeMenuItem(
                                getApplicationContext());
                        openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                                0xCE)));
                        openItem.setWidth(Utils.dip2px(MakeOutInvoiceActivity.this, 90));
                        openItem.setTitle("Open");
                        openItem.setTitleSize(18);
                        openItem.setTitleColor(Color.WHITE);
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getApplicationContext());
                        // set item background
                        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                                0x3F, 0x25)));
                        deleteItem.setWidth(Utils.dip2px(MakeOutInvoiceActivity.this, 60));
                        deleteItem.setIcon(R.drawable.home_badgeview);
                        menu.addMenuItem(deleteItem);
                        break;
                    case 2:
                        break;

                }
            }

        };

        listView.setMenuCreator(creator);
    }


    class TempAdapter extends BaseAdapter {


        public TempAdapter() {

        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
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
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (dataList.size() == 0 || position == dataList.size()) {
                return 2;
            } else {
                return 1;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(MakeOutInvoiceActivity.this).inflate(
                    R.layout.lv_make_out_invoice, parent, false);
            TextView companyName = (TextView) convertView.findViewById(R.id.companyName);

            if (dataList.get(position).getCompanyName() == null) {
                companyName.setText("个人");
            } else {

                companyName.setText(dataList.get(position).getCompanyName());
            }


            return convertView;
        }
    }
}
