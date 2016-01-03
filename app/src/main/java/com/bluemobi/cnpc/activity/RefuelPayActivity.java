package com.bluemobi.cnpc.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.model.OilPayTransBean;
import com.bluemobi.cnpc.network.response.ListGasStationDetailResponse;
import com.bluemobi.cnpc.network.response.UserCenterResponse;
import com.bluemobi.cnpc.util.Arith;
import com.bluemobi.cnpc.util.KeyBoardUtils;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.GasText;
import com.bluemobi.cnpc.view.LoadingPage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/7/23.
 * <p/>
 * p6-2 加油支付
 */

@ContentView(R.layout.activity_refuel_pay)
@PageName(R.string.come_pay)
public class RefuelPayActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.test_unit_price)
    TextView test_unit_price;

    @Bind(R.id.test_pay)
    TextView test_pay;

    @Bind(R.id.test_number)
    TextView test_number;

    @Bind(R.id.common_confirm)
    TextView common_confirm;

    @Bind(R.id.gridview)
    GridView gridView;

    @Bind(R.id.gridview_price)
    GridView gridview_price;

    @Bind(R.id.tv_gasdeptName)
    TextView tv_gasdeptName;

    @Bind(R.id.tv_order_time)
    TextView tv_order_time;

    private int lastSelected;
    private int countlastSelectPrice;

    private CommonAdapter<ListGasStationDetailResponse.GasStationDetailInfo> mAdapter;
    private TempPriceAdapter tempPriceAdapter;

    private View lastSelectView;
    private String text;
    private String gasName;

    private String customerPrice;

    private ArrayList<ListGasStationDetailResponse.GasStationDetailInfo> gasStationDetailInfos;

    private List<String> priceList = new ArrayList<String>();

    int index[] = new int[3];

    String valPrice = "100";


    private boolean isFristSelectPrice = true;
    private boolean isFristSelectCount = true;

    private String gasId;

    private double div;

    private String productID;

    private static final int MAX_VOLUME = 1000;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(RefuelPayActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.come_pay, R.drawable.return_arrow, true);
        gasName = getIntent().getStringExtra("GAS_NAME");
        gasId = getIntent().getStringExtra("GAS_ID");
        gasStationDetailInfos = CnpcApplication.getInstance().getmProductInfos();

        priceList.add("100");
        priceList.add("200");
        priceList.add("300");
        priceList.add("400");
        priceList.add("500");
        priceList.add("自定义");
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {

        ButterKnife.bind(this, successView);
        tv_gasdeptName.setText(gasName);

        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd hh:mm "); //格式化当前系统日期
        String dateTime = dateFm.format(new java.util.Date());
        tv_order_time.setText(dateTime.toString());
        if (gasStationDetailInfos == null) {
            return;
        }
        if (gasStationDetailInfos != null) {
            String temp = gasStationDetailInfos.get(0).customerPrice;
            oilPrice(Utils.StringTo2decimal(temp));
            addOilPrice(priceList.get(0));
            gasSeveral(gasStationDetailInfos.get(0).customerPrice);
            calOil(priceList.get(0), gasStationDetailInfos.get(0).customerPrice);
        }


        gridView.setAdapter(mAdapter = new CommonAdapter<ListGasStationDetailResponse.GasStationDetailInfo>(mContext, gasStationDetailInfos, R.layout.adapter_grid_oil) {
            @Override
            public void convert(com.bluemobi.cnpc.adapter.ViewHolder helper,
                                ListGasStationDetailResponse.GasStationDetailInfo item) {
                GasText gasText = helper.getView(R.id.val);
                //Log.e("tag--item.productName-->",item.productName+"");
                gasText.setText(item.productName);
                RelativeLayout rl = (RelativeLayout) helper.getView(R.id.item_bg);

                if (lastSelected == helper.getPosition()) {
                    rl.setBackgroundResource(R.drawable.item_sel);
                } else {
                    rl.setBackgroundResource(R.drawable.item_unsel);
                }

            }


        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (lastSelected != position) {
                    lastSelected = position;
                    mAdapter.notifyDataSetChanged();
                }
                customerPrice = gasStationDetailInfos.get(position).customerPrice;
                productID = gasStationDetailInfos.get(position).id;
                oilPrice(Utils.StringTo2decimal(customerPrice));
                isFristSelectPrice = false;

                if (isFristSelectCount) {
                    calOil(priceList.get(0), customerPrice);
                } else {
                    calOil(priceList.get(position), customerPrice);
                }


            }
        });

        tempPriceAdapter = new TempPriceAdapter();
        gridview_price.setAdapter(tempPriceAdapter);
        common_confirm.setOnClickListener(this);
    }

    public void oilPrice(String temp) {

        int size = temp.length();
        String text = String.format(getResources().getString(R.string.test_unit_price), temp);
        index[0] = text.indexOf(temp);
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_pink)), index[0], index[0] + size, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        test_unit_price.setText(style);
    }

    public void addOilPrice(String price) {
        int size_price = price.length();
        String text_price = String.format(getResources().getString(R.string.test_pay), price);
        index[1] = text_price.indexOf(price);
        SpannableStringBuilder style_price = new SpannableStringBuilder(text_price);
        style_price.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_pink)), index[1], index[1] + size_price, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        test_pay.setText(style_price);
    }

    public void gasSeveral(String number) {
        int size_number = number.length();
        String text_number = String.format(getResources().getString(R.string.test_number), number);
        index[2] = text_number.indexOf(number);
        SpannableStringBuilder style_number = new SpannableStringBuilder(text_number);
        style_number.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_pink)), index[2], index[2] + size_number, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        test_number.setText(style_number);

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_confirm:

                if ("".equals(valPrice) || valPrice.compareTo("0") <= 0) {
                    Utils.makeToastAndShow(mContext, "请输入加油金额");
                    return;
                }

               /* if(Double.parseDouble(valPrice) >= 1000){
                    return;
                }*/

                if (gasStationDetailInfos == null) {
                    Utils.makeToastAndShow(mContext, "该加油站无油品信息，无法进行加油支付");
                    return;
                }
                OilPayTransBean oilPayTransBean = new OilPayTransBean();
                oilPayTransBean.setGasID(gasId);
                oilPayTransBean.setGasName(gasName);
                oilPayTransBean.setCalOilNum(div + "");
                if (StringUtils.isEmpty(productID)) {
                    //TODO
                    oilPayTransBean.setProductID(gasStationDetailInfos.get(0).id);
                } else {
                    oilPayTransBean.setProductID(productID);
                }


                if (isFristSelectCount) {
                    oilPayTransBean.setSumPrice(priceList.get(0));

                } else {

                    oilPayTransBean.setSumPrice(valPrice);

//                    if (isCustom)
//                    {
//                        oilPayTransBean.setSumPrice(valPrice);
//                    }else
//                    {
//                        oilPayTransBean.setSumPrice(priceList.get(countlastSelectPrice));
//                    }

                }

                if (isFristSelectPrice) {
                    oilPayTransBean.setSinglePrice(gasStationDetailInfos.get(0).customerPrice);
                    oilPayTransBean.setGasType(gasStationDetailInfos.get(0).productName);
                } else {

                    oilPayTransBean.setSinglePrice(gasStationDetailInfos.get(lastSelected).customerPrice);
                    oilPayTransBean.setGasType(gasStationDetailInfos.get(lastSelected).productName);
                }

                Intent intent = new Intent(this, RefuelOrderSettlementActivity.class);
                intent.putExtra("oilPayTransBean", oilPayTransBean);
                startActivity(intent);
                break;
        }
    }

    private void calOil(String countPrice, String price) {
        Log.e("calOil-->", countPrice + "---[]---" + price);
        div = Arith.div(Double.parseDouble(countPrice), Double.parseDouble(price + ""), 2);
        gasSeveral(div + "");
    }

    private EditText editText;

    private boolean isCustom = false;

    class TempPriceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return priceList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(RefuelPayActivity.this).inflate(
                        R.layout.adapter_refuel_pay, parent, false);
                EditText val = (EditText) convertView.findViewById(R.id.val);
                TextView textVal = (TextView) convertView.findViewById(R.id.text_val);
                TextView text = (TextView) convertView.findViewById(R.id.text);
                RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.item_bg);
                holder.rl = rl;
                holder.val = val;
                holder.text_val = textVal;
                holder.text = text;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (countlastSelectPrice == position) {
                holder.rl.setBackgroundResource(R.drawable.item_sel);
                lastSelectView = holder.rl;
            } else {

                holder.rl.setBackgroundResource(R.drawable.item_unsel);
            }
            if (position < priceList.size() - 1) {
                holder.val.setVisibility(View.GONE);
                holder.text_val.setVisibility(View.VISIBLE);
                holder.text.setVisibility(View.VISIBLE);
                holder.text_val.setText(priceList.get(position));
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isCustom = false;

                        if (editText != null) {
                            editText.clearFocus();
                        }
                        countlastSelectPrice = position;
                        if (countlastSelectPrice != position) {
                            countlastSelectPrice = position;
                            tempPriceAdapter.notifyDataSetChanged();
                        }
                        valPrice = priceList.get(position);
                        addOilPrice(valPrice);
                        isFristSelectCount = false;
                        calOil(valPrice, gasStationDetailInfos.get(lastSelected).customerPrice);


                        TempPriceAdapter.this.notifyDataSetChanged();
                    }
                });
            } else {
                holder.val.setHint(priceList.get(position));
                holder.val.setVisibility(View.VISIBLE);
                holder.text_val.setVisibility(View.GONE);
                holder.text.setVisibility(View.GONE);
                editText = holder.val;
                holder.val.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        countlastSelectPrice = position;
                        valPrice = holder.val.getText().toString().trim();
                        if ("自定义".equals(valPrice) || valPrice == null) {
                            valPrice = "";
                        }
                        if (StringUtils.isNotEmpty(valPrice)) {
                            addOilPrice(valPrice);
                            isFristSelectCount = false;
                            calOil(valPrice, gasStationDetailInfos.get(lastSelected).customerPrice);
                        } else {
                            gasSeveral("0");
                            addOilPrice("0");
                        }
                        TempPriceAdapter.this.notifyDataSetChanged();
                        v.requestFocus();
                        InputMethodManager inputManager =

                                (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.showSoftInput(v, 0);
                        return true;
                    }
                });

                holder.val.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        isCustom = true;
                        if (countlastSelectPrice != position) {
                            countlastSelectPrice = position;
                            tempPriceAdapter.notifyDataSetChanged();
                        }

                        valPrice = holder.val.getText().toString().trim();
                        if (StringUtils.isNotEmpty(valPrice)) {
                            BigDecimal temp = new BigDecimal(valPrice);
                        }

                        if ("自定义".equals(valPrice) || valPrice == null) {
                            valPrice = "";
                        }

                        if (StringUtils.isNotEmpty(valPrice)) {
                            addOilPrice(valPrice);
                            isFristSelectCount = false;
                            calOil(valPrice, gasStationDetailInfos.get(lastSelected).customerPrice);
                        } else {
                            gasSeveral("0");
                            addOilPrice("0");
                        }

                    }
                });

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        countlastSelectPrice = position;


                        TempPriceAdapter.this.notifyDataSetChanged();

                    }
                });
            }
            return convertView;
        }
    }


    class ViewHolder {
        EditText val;
        TextView text_val;
        TextView text;
        RelativeLayout rl;
    }

}
