package com.bluemobi.cnpc.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.network.model.BonusItem;
import com.bluemobi.cnpc.network.model.CouponItem;

import java.util.List;

/**
 * Created by wangzhijun on 2015/9/13.
 */
public class CouponListAdapter extends CommonAdapter<CouponItem>{

    public CouponListAdapter(Context context, List<CouponItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, CouponItem item) {
        TextView tv_name = helper.getView(R.id.tv_name);//title
        TextView tv_money = helper.getView(R.id.tv_money);//title
        TextView activity = helper.getView(R.id.activity);//活动
        TextView time = helper.getView(R.id.time);//有效日期
        TextView oil_limit_value = helper.getView(R.id.oil_limit_value);//使用条件
        TextView min_buy = helper.getView(R.id.min_buy);//最低消费

        tv_name.setText(item.getDeptName());//TODO: to sure this right?
        tv_money.setText(item.getCouponDiscount() + "元");
        activity.setText(item.getCouponName());//TODO: to sure this right?
        min_buy.setText(item.getMinOrderAmount() + "元");
        time.setText(item.getCouponStartTime() + "至" +
            item.getCouponEndTime());
        oil_limit_value.setText(item.getDeptName());//TODO: to sure this right?
    }
}
