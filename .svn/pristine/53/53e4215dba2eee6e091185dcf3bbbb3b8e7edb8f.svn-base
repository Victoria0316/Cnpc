package com.bluemobi.cnpc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.model.BonusItem;
import com.bluemobi.cnpc.network.model.CollectionItem;
import com.bluemobi.cnpc.network.request.CollectionDelRequest;
import com.bluemobi.cnpc.network.request.CollectionListRequest;
import com.bluemobi.cnpc.network.response.CollectionDelResponse;
import com.bluemobi.cnpc.network.response.CollectionListResponse;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;

import java.util.List;

/**
 * Created by wangzhijun on 2015/9/13.
 */
public class CollectionListAdapter extends CommonAdapter<CollectionItem>{

    private boolean isDelete;

    private List<CollectionItem> dataLists;

    private BaseActivity baseActivity;

    public CollectionListAdapter(Context context, List<CollectionItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.dataLists = mDatas;
        baseActivity = (BaseActivity)context;
    }


    @Override
    public void convert(ViewHolder helper, final CollectionItem item) {
        TextView title = helper.getView(R.id.title);
        ImageView details = helper.getView(R.id.details);
        title.setText(item.getCollectionName());
        if(isDelete){
            details.setImageResource(R.drawable.delete_image);
        }else{
            details.setImageResource(R.drawable.arrow_gray_right);
        }
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDelete){
                    deleteItem(item);
                }
            }
        });
    }

    private void deleteItem(CollectionItem item) {
        final int index = dataLists.indexOf(item);
        CollectionDelRequest request = new CollectionDelRequest(new Response.Listener<CollectionDelResponse>() {
            @Override
            public void onResponse(CollectionDelResponse response) {
                Utils.closeDialog();
                if(response != null && response.getStatus() == 0){
                    Utils.makeToastAndShow(baseActivity, "删除成功！");
                    dataLists.remove(index);
                    CollectionListAdapter.this.notifyDataSetChanged();
                }else{
                    Utils.makeToastAndShow(baseActivity, response.getContent());
                }
            }
        }, baseActivity);
        request.setId(item.getId());
        request.setHandleCustomErr(false);
        WebUtils.doPost(request);
        Utils.showProgressDialog(baseActivity);

    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}
