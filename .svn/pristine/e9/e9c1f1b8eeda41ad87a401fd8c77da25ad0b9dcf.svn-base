package com.bluemobi.cnpc.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.navisdk.ui.routeguide.fsm.RouteGuideFSM;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.ExpandableSectionAdapter;
import com.bluemobi.cnpc.adapter.SectionAdapter;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.util.Utils;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by wangzhijun on 2015/7/23.
 */
public class CnpcStatementsView extends LinearLayout {
    /**
     * 当前选中
     */
    private TextView status;

    private LinearLayout titleLayout;

    private String[] titles;

    private Context mContext;

    private BaseAdapter mAdapter;

    private ListView mListView;

    private ExpandableListView mExpandListView;

    private SideMonthBar monthBar;

    private int monthBarWidth;

    private int padding;

    private int listMarginRight;

    private int statusHeight;

    private AbsListView.OnScrollListener scrollListener;

    private int listType;

    private SectionAdapter sectionAdapter;

    private ExpandableSectionAdapter expandableSectionAdapter;

    private SectionIndexer adapter = null;

    private int listSize;

    @BindString(R.string.global_month)

    String month;

    private static final String DEFAULT_STATUS_UNIT = "月";

    private String unit;

    public CnpcStatementsView(Context context) {
        super(context);
        this.mContext = context;
    }

    public CnpcStatementsView(Context context, String[] titles, BaseAdapter adapter) {
        super(context);
        this.mContext = context;
        this.titles = titles;
    }

    public CnpcStatementsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

    }

    public CnpcStatementsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

    }

    public void refreshView(String[] titles, SectionAdapter adapter) {
        listType = 1;
        sectionAdapter = adapter;
        refreshView(titles, adapter, DEFAULT_STATUS_UNIT);
    }

    public void refreshView(String[] titles, ExpandableSectionAdapter adapter) {
        listType =2;
        expandableSectionAdapter = adapter;
        refreshView(titles, adapter, DEFAULT_STATUS_UNIT);
    }

    public void refreshView(String[] titles, SectionAdapter adapter, String statusUnit) {
        this.titles = titles;
        this.unit = statusUnit;
        monthBarWidth = Utils.dip2px(mContext, 20);
        statusHeight = Utils.dip2px(mContext, 20);
        padding = Utils.dip2px(mContext, 10);
        listMarginRight = Utils.dip2px(mContext, 10);
        sectionAdapter = adapter;
        initView();
    }

    public void refreshView(String[] titles, ExpandableSectionAdapter adapter, String statusUnit) {
        this.titles = titles;
        this.unit = statusUnit;
        monthBarWidth = Utils.dip2px(mContext, 20);
        statusHeight = Utils.dip2px(mContext, 20);
        padding = Utils.dip2px(mContext, 10);
        listMarginRight = Utils.dip2px(mContext, 10);
        expandableSectionAdapter = adapter;
        initView();
    }


    private void initView() {
        this.removeAllViews();
        this.setOrientation(LinearLayout.VERTICAL);
        status = new TextView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                statusHeight);
        params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        status.setPadding(Utils.dip2px(mContext, 10), 0, 0, 0);
        status.setBackgroundResource(R.color.common_bg);
        this.addView(status, params);
        //line
        TextView textView = new TextView(mContext);
        textView.setBackgroundResource(R.color.common_divider);
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
        this.addView(textView);
        //title
        if (titles != null && titles.length > 0) {
            titleLayout = new LinearLayout(mContext);
            titleLayout.setOrientation(HORIZONTAL);
            titleLayout.setBackgroundResource(R.color.white);
            LayoutParams textParam = new LayoutParams(0,
                    LayoutParams.WRAP_CONTENT);
            textParam.weight = 1;
//            textParam.rightMargin = 10;
            for (int i = 0; i < titles.length; i++) {
                TextView textView1 = new TextView(mContext);
                textView1.setGravity(Gravity.CENTER);
                titleLayout.addView(textView1, textParam);
//                textView1.setBackgroundResource(R.color.common_red);
                textView1.setTextAppearance(mContext, R.style.common_text);
                textView1.setTextColor(Color.rgb(18, 156, 208));
                textView1.setText(titles[i]);
            }
            titleLayout.setPadding(Utils.dip2px(mContext, 10),
                    Utils.dip2px(mContext, 5), monthBarWidth + padding + listMarginRight
                    , Utils.dip2px(mContext, 5));
            if (this.getChildCount()!=0)
            this.addView(titleLayout, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            //line
            LayoutParams line2Param = new LayoutParams(LayoutParams.MATCH_PARENT,
                    Utils.dip2px(mContext, 1));
            TextView line2 = new TextView(mContext);
            line2.setBackgroundResource(R.color.common_divider);
            line2.setLayoutParams(line2Param);
            line2Param.leftMargin = Utils.dip2px(mContext, 10);
            line2Param.rightMargin = Utils.dip2px(mContext, 10);
            this.addView(line2);

        }
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setBackgroundResource(R.color.white);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        monthBar = new SideMonthBar(mContext);
        RelativeLayout.LayoutParams barParam = new RelativeLayout.LayoutParams(monthBarWidth,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        barParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        barParam.addRule(RelativeLayout.CENTER_VERTICAL);
        relativeLayout.setPadding(padding,
                0, padding, 0);
        monthBar.setId(10011);
        relativeLayout.addView(monthBar, barParam);

        if(listType == 1){
            mListView = new ListView(mContext);
            adapter = sectionAdapter;
            mListView.setAdapter(sectionAdapter);
            mListView.setDivider(new ColorDrawable(Color.rgb(204, 204, 204)));
            mListView.setDividerHeight(1);
            mListView.setCacheColorHint(mContext.getResources().getColor(R.color.common_transparent));
            mListView.setSelector(R.color.common_transparent);
            mListView.setVerticalScrollBarEnabled(false);
            listSize = sectionAdapter.getCount();
        }else{
            mExpandListView = new ExpandableListView(mContext);
            adapter = expandableSectionAdapter;
            ((ExpandableListView)mExpandListView).setAdapter(expandableSectionAdapter);
            ((ExpandableListView)mExpandListView).setGroupIndicator(null);
            mExpandListView.setDivider(new ColorDrawable(Color.rgb(204, 204, 204)));
            mExpandListView.setDividerHeight(1);
            mExpandListView.setCacheColorHint(mContext.getResources().getColor(R.color.common_transparent));
            mExpandListView.setSelector(R.color.common_transparent);
            mExpandListView.setVerticalScrollBarEnabled(false);
            listSize = mExpandListView.getCount();

        }




        RelativeLayout.LayoutParams listParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        listParam.addRule(RelativeLayout.LEFT_OF, 10011);
        listParam.rightMargin = listMarginRight;
        if(listType == 1)
        {
            relativeLayout.addView(mListView, listParam);
        }else
        {
            relativeLayout.addView(mExpandListView, listParam);
        }

        this.addView(relativeLayout);
        monthBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s, String dis) {
                int month= Integer.parseInt(dis);

                int positionSection = adapter.getPositionForSection(month);
                if (positionSection != -1) {

                    lastIndex = month;
                    monthBar.setSelection(month);
                    status.setText(month + unit);
                    mListView.setSelection(positionSection);
                }else{
                    Utils.makeToastAndShow(getContext(),"本月无流水");
                }

            }
        });

        String temp ;
        if(adapter.getSectionForPosition(0) == -1){
            temp = "";
            status.setText(temp);
        }else{
            temp = String.valueOf(adapter.getSectionForPosition(0));
            status.setText(temp + unit);
            monthBar.setSelection(adapter.getSectionForPosition(0));
        }
        scrollListener = new  AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem >= listSize){
                    firstVisibleItem = listSize-1;
                    if(firstVisibleItem == -1){
                        return;
                    }
                }
                if(lastIndex == -1){
                    String temp ;
                    if(adapter.getSectionForPosition(firstVisibleItem) == -1){
                        temp = "";
                        status.setText(temp);
                    }else{
                        temp = String.valueOf(adapter.getSectionForPosition(firstVisibleItem));
                        status.setText(temp + unit);
                    }
                    if(adapter.getSectionForPosition(firstVisibleItem) != -1){
                        monthBar.setSelection(adapter.getSectionForPosition(firstVisibleItem));
                    }
                }else{
                    status.setText(lastIndex + unit);
                    monthBar.setSelection(lastIndex);
                    lastIndex = -1;
                }




//                monthBar.setSelection(adapter.getSectionForPosition(firstVisibleItem));
            }
        };
        if(listType == 1)
        {
            mListView.setOnScrollListener(scrollListener);
        }else
        {
            mExpandListView.setOnScrollListener(scrollListener);
        }

    }
    private int lastIndex = 0;

//    /**
//     * 向外公开的方法
//     *
//     * @param onTouchingLetterChangedListener
//     */
//    public void setOnTouchingLetterChangedListener(
//            SideMonthBar.OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
//        monthBar.setOnTouchingLetterChangedListener(onTouchingLetterChangedListener);
//    }


    public CnpcStatementsView(Context context, String[] titles, View view) {
        super(context);
        this.mContext = context;
        this.titles = titles;

    }

    public void setOnItemOnClickListener(AdapterView.OnItemClickListener listener) {
        mListView.setOnItemClickListener(listener);

    }

    public void setOnChildClickListener(ExpandableListView.OnChildClickListener listener)
    {
        mExpandListView.setOnChildClickListener(listener);

    }

    public void setOnGroupClickListener(ExpandableListView.OnGroupClickListener listener)
    {
        mExpandListView.setOnGroupClickListener(listener);


    }

    enum Type {
        LIST, DETAIL;
    }

    class Options {

    }

}
