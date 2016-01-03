package com.bluemobi.cnpc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.model.PayPreferentialInfo;
import com.bluemobi.cnpc.network.model.SalesPromotionInfo;
import com.bluemobi.cnpc.network.request.PayPreferentialRequest;
import com.bluemobi.cnpc.network.response.PayPreferentialResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.DensityUtil;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/21.
 *
 * p4 充值优惠
 */

@ContentView(R.layout.activity_pay_preferential)
@PageName(R.string.top_up)
public class PayPreferentialActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.confirm)
    TextView confirm;

    @Bind(R.id.plv_refresh)
    PullToRefreshListView plv_refresh;

    @Bind(R.id.tv_explain1)
    TextView tv_explain1;

    @Bind(R.id.ll_text)
    LinearLayout ll_text;

    @Bind(R.id.rl_parent)
    RelativeLayout rl_parent;
    private CommonAdapter<PayPreferentialInfo> adapter;

    private List<PayPreferentialInfo> lv = new ArrayList<PayPreferentialInfo>();

    private List<PayPreferentialInfo> datas = new ArrayList<PayPreferentialInfo>();
    private int lastIndex = 0;

    private String  currentpage;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(PayPreferentialActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.top_up, R.drawable.return_arrow, true);
        showLoadingPage(false);

    }


    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        initPullToRefresh(plv_refresh);
        confirm.setOnClickListener(this);
        getPage(LOAD_REFRESH);
        plv_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int index = i - 1;
                if (lastIndex != (index)) {
                    lastIndex = (index);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        WindowManager wm = this.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();

        //int actionBarHeight = getSupportActionBar().getHeight();
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }


        ll_text.measure(0, 0);
        int height1 = ll_text.getMeasuredHeight();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_text.getLayoutParams();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) plv_refresh.getLayoutParams();
        lp.height = height-height1-actionBarHeight-rl_parent.getPaddingBottom()-layoutParams.topMargin-60;

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
        PayPreferentialRequest  request = new PayPreferentialRequest
                (
                        new Response.Listener<PayPreferentialResponse>() {
                            @Override
                            public void onResponse(PayPreferentialResponse response) {
                                Utils.closeDialog();
                                plv_refresh.onRefreshComplete();
                                if (response != null && response.getStatus() == 0) {
                                    List<PayPreferentialInfo> info = response.getData().getInfo();
                                    currentpage = response.getData().getCurrentpage();
                                    tv_explain1.setText("1.充值优惠按套餐购买，每份"+Utils.StringTo2decimal(info.get(0).getBuyPrice())+"元");
                                    wrapLvData(info);
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

    private void wrapLvData(List<PayPreferentialInfo> info) {
        if (info == null)
        {
            return;
        }
        if (info.size() == 0)
        {
            return;
        }
        if (currentpage.equals("0"))
        {
            datas.clear();
        }

        for (PayPreferentialInfo lineDto : info)
        {
            datas.add(lineDto);
        }

        if (adapter == null)
        {
            plv_refresh.setAdapter(
                    adapter = new CommonAdapter<PayPreferentialInfo>(mContext, datas, R.layout.lv_pay_preferential) {
                        @Override
                        public void convert(ViewHolder helper, PayPreferentialInfo item) {
                            Log.e("tag--->", "CommonAdapter");
                            RelativeLayout item_bg = (RelativeLayout) helper.getView(R.id.item_bg);
                            if (lastIndex == datas.indexOf(item)) {
                                item_bg.setBackgroundResource(R.drawable.pay_time_bg_select);
                            } else {
                                item_bg.setBackgroundResource(R.drawable.pay_time_bg);
                            }

                            TextView time = (TextView) helper.getView(R.id.time);
                            TextView discount = (TextView) helper.getView(R.id.discount);
                            TextView instructions = (TextView) helper.getView(R.id.instructions);
                            time.setText(item.getComboName());
                            discount.setText(item.getDepositRate());
                            if (item.getComboType().equals("1")) {
                                instructions.setText("第一个月到账" + item.getFirstArrive() + "%" + "," + "其他月份到账" + item.getOtherArrive() + "%");
                            }
                        }

                    });
        }else
        {
            adapter.notifyDataSetChanged();


        }

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                PayPreferentialInfo item = datas.get(lastIndex);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                intent.putExtras(bundle);
                intent.setClass(this, PayPackageActivity.class);
                startActivity(intent);
                break;
        }
    }




    @Override
    public void clickBarleft() {
        finish();
    }
}
