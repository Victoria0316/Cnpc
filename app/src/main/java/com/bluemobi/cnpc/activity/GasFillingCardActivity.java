package com.bluemobi.cnpc.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.model.GasFillingCardInfo;
import com.bluemobi.cnpc.network.request.GasCardDefaultRequest;
import com.bluemobi.cnpc.network.request.GasCardDelRequest;
import com.bluemobi.cnpc.network.request.GasFillingCardRequest;
import com.bluemobi.cnpc.network.response.GasCardDefaultResponse;
import com.bluemobi.cnpc.network.response.GasCardDelResponse;
import com.bluemobi.cnpc.network.response.GasFillingCardResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/28.
 * P10_8加油卡
 */
@ContentView(R.layout.activity_gas_filling_car)
@PageName(R.string.s_gas_filling_card)
public class GasFillingCardActivity extends BaseActivity {

    @Bind(R.id.plv_refresh)
    protected PullToRefreshListView plv_refresh;

    private GasCardAdapter adapter;

    private List<GasFillingCardInfo> dataList = new ArrayList<GasFillingCardInfo>();

    private View view;

    private AlertDialog dialog;

    private boolean isEdit;

    private boolean isFristClick = true;

    private int lastIndex = 0;

    @Bind(R.id.tv_add_newCard)
    TextView tv_add_newCard;

    private String currentpage;

    private String Id;

    private int typeInt;


    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showShareBar(R.string.s_gas_filling_card, R.drawable.edit);
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);

        initPullToRefresh(plv_refresh);
        getPage(LOAD_REFRESH);
        tv_add_newCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.moveTo(mContext, AddGasFillingCardActivity.class);
            }
        });

        plv_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isEdit) {
                    return;
                }
                //TODO

                typeInt = i;
                int index = i - 1;
                if (lastIndex != (index)) {
                    lastIndex = (index);
                    isFrist = false;
                    for (GasFillingCardInfo info : dataList) {
                        if (info.getIsDefault().equals("1")) {
                            info.setIsDefault("0");
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                showDialog();
            }
        });
    }

    @Override
    protected boolean getPage(int type) {
        if (!super.getPage(type)) return false;
        getDataServer();
        return true;
    }

    private void getDataServer() {

        Utils.showProgressDialog(mContext);
        GasFillingCardRequest request = new GasFillingCardRequest
                (
                        new Response.Listener<GasFillingCardResponse>() {
                            @Override
                            public void onResponse(GasFillingCardResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    final List<GasFillingCardInfo> info = response.getData().getInfo();
                                    plv_refresh.onRefreshComplete();
                                    currentpage = response.getData().getCurrentpage();
                                    wrapListView(info);

                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(getCurPage() + "");
        WebUtils.doPost(request);
    }

    private void wrapListView(List<GasFillingCardInfo> info) {
        if (info == null) {
            return;
        }
        if (info.size() == 0) {
            return;
        }

        if (currentpage.equals("0")) {
            dataList.clear();
        }

        for (GasFillingCardInfo lineDto : info) {
            dataList.add(lineDto);
        }

        if (adapter == null) {
            adapter = new GasCardAdapter(mContext);
            plv_refresh.getRefreshableView().setAdapter(adapter);

        } else {

            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickImageRight() {
        if (isFristClick) {
            isEdit = true;

        } else {
            isEdit = false;
        }
        isFristClick = !isFristClick;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void showDialog() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .create();
            view = LayoutInflater.from(this).inflate(R.layout.dialog_gas_station_edit, null);

            view.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CardDelRequest();
                }
            });
            view.findViewById(R.id.default_card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CardDefaultRequest();
                }
            });
            view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
            window.setWindowAnimations(R.style.dialog_bottom_style);  //添加动画
            dialog.show();
            dialog.setContentView(view);
        } else {
            dialog.show();
        }

    }

    private void CardDefaultRequest() {
        Utils.showProgressDialog(mContext);
        GasCardDefaultRequest request = new GasCardDefaultRequest
                (
                        new Response.Listener<GasCardDefaultResponse>() {
                            @Override
                            public void onResponse(GasCardDefaultResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    dialog.dismiss();
                                    //TODO
                                    for (GasFillingCardInfo info:dataList)
                                    {
                                       if (info.getIsDefault().equals("1"))
                                       {
                                           info.setIsDefault("0");
                                       }
                                    }

                                    dataList.get(typeInt-1).setIsDefault("1");
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);

        request.setUserId(CnpcApplication.getInstance().getmData().userId);
        request.setId(Id);
        WebUtils.doPost(request);
    }

    private void CardDelRequest() {
        Utils.showProgressDialog(mContext);
        GasCardDelRequest request = new GasCardDelRequest
                (
                        new Response.Listener<GasCardDelResponse>() {
                            @Override
                            public void onResponse(GasCardDelResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    dialog.dismiss();
                                    //TODO
                                    dataList.remove(typeInt-1);
                                    adapter.notifyDataSetChanged();

                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);

        request.setId(Id);
        WebUtils.doPost(request);
    }

    private boolean isFrist = true;

    private class GasCardAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public GasCardAdapter(Context context) {
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList == null ? null : dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = inflater.inflate(R.layout.lv_item_gas_filling_card,
                    null);

            GasFillingCardInfo temp = dataList.get(position);

            ImageView textViewDel = (ImageView) view.findViewById(R.id.iv_check);
            if (isEdit)
                textViewDel.setVisibility(View.VISIBLE);
            else {
                textViewDel.setVisibility(View.GONE);
            }
            TextView default_text = (TextView) view.findViewById(R.id.default_text);

            if (lastIndex == position) {

                textViewDel.setBackgroundResource(R.drawable.common_elected);
                Id = temp.getId();
                if (position==0)
                {

                    if (isFrist)
                    {
                        Log.e("tag-->isFrist",isFrist+"");
                        textViewDel.setBackgroundResource(R.drawable.common_elected_destroy);
                    }else
                    {
                        Log.e("tag-else->isFrist",isFrist+"");
                        textViewDel.setBackgroundResource(R.drawable.common_elected);
                    }
                }

            } else {
                textViewDel.setBackgroundResource(R.drawable.common_elected_destroy);
            }
            //TODO
            if ("1".equals(temp.getIsDefault())) {
                default_text.setVisibility(View.VISIBLE);
                textViewDel.setBackgroundResource(R.drawable.common_elected);
                Id = temp.getId();
            } else {
                default_text.setVisibility(View.GONE);
            }
            TextView gasStationName = (TextView) view.findViewById(R.id.gasStationName);
            TextView headquartersNo = (TextView) view.findViewById(R.id.headquartersNo);
            TextView tv_cardNo_text = (TextView) view.findViewById(R.id.tv_cardNo_text);
            TextView tv_residual_text = (TextView) view.findViewById(R.id.tv_residual_text);
            //TextView tv_integration_text = (TextView) view.findViewById(R.id.tv_integration_text);

            gasStationName.setText(temp.getGasStationName());
            headquartersNo.setText(temp.getHeadquartersNo());
            tv_cardNo_text.setText(temp.getCardNumber());
            tv_residual_text.setText(temp.getCardBalance());

           // tv_integration_text.setText(new BigDecimal(temp.getCardIntegral()).intValue() + "分");


            return view;
        }
    }

    @Override
    public void clickBarleft() {
        finishAll();
        Intent intent  = new Intent();
        intent.putExtra("setTabSelection",3);
        intent.setClass(this, HomeActivity.class);
        startActivity(intent);

    }
}