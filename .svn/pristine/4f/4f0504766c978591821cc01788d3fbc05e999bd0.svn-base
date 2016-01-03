package com.bluemobi.cnpc.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.network.model.BonusItem;

import java.util.List;

/**
 * Created by wangzhijun on 2015/9/13.
 */
public class BonusListAdapter extends CommonAdapter<BonusItem>{

    public BonusListAdapter(Context context, List<BonusItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, BonusItem item) {
        TextView amount = helper.getView(R.id.img);
        TextView title = helper.getView(R.id.title);
        TextView time = helper.getView(R.id.time);//有效日期
        TextView limit = helper.getView(R.id.limit);//使用条件
        amount.setText("￥" + item.getBonusDiscount());
        title.setText(item.getBonusName());
        limit.setText("满" + item.getMinOrderAmount() + "元使用");
        time.setText(item.getBonusStartTime() + "---" +
        item.getBonusEndTime());
    }
}
