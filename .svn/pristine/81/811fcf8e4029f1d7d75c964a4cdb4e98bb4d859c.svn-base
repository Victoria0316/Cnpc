package com.bluemobi.cnpc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.activity.VehicleEditActivity;
import com.bluemobi.cnpc.db.entity.VehicleType;
import com.bluemobi.cnpc.util.Utils;

import java.util.List;

/**
 * Created by wangzhijun on 2015/7/22.
 */
public class VehicleTypeAdapter extends BaseExpandableListAdapter implements SectionIndexer {
    private List<VehicleType> list = null;
    private List<List<VehicleType>> children = null;
    private Context mContext;

    public VehicleTypeAdapter(Context mContext, List<VehicleType> list, List<List<VehicleType>> children) {
        this.mContext = mContext;
        this.list = list;
        this.children = children;

    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<VehicleType> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public int getGroupCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children == null ? 0 : children.get(groupPosition) == null ? 0 :
                children.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list == null ? null : list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children == null ? null : children.get(groupPosition) == null ? null :
                children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View viewer = LayoutInflater.from(mContext).inflate(R.layout.adapter_vehicle_type, null);
        TextView tvTitle = (TextView) viewer.findViewById(R.id.title);
        TextView tvLetter = (TextView) viewer.findViewById(R.id.catalog);
        tvLetter.setVisibility(View.GONE);
        tvTitle.setText(this.list.get(groupPosition).getBrandName());
        return viewer;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        final String brandName= children.get(groupPosition).get(childPosition).getBrandName();
        textView.setText(brandName);
        int temp = Utils.dip2px(mContext, 10);
        textView.setPadding(temp, temp, temp, temp);
       /* textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VehicleEditActivity.class);
                intent.putExtra("brandName",brandName);

            }
        });*/

        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getGroupCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }


    @Override
    public Object[] getSections() {
        return null;
    }
}
