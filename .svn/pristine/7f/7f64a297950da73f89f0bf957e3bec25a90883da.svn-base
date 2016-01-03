package com.bluemobi.cnpc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.db.entity.AllCity;
import com.bluemobi.cnpc.db.entity.VehicleType;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhijun on 2015/7/22.
 */
public class ChangeCityAdapter extends SectionAdapter{
    private List<AllCity> list = null;
    private List<AllCity> hotList = null;
    private Context mContext;
    private List<AllCity> allCitys = new ArrayList<AllCity>();


    public ChangeCityAdapter(Context mContext, List<AllCity> list,List<AllCity> hotList) {
        this.mContext = mContext;
        this.list = list;
        this.hotList = hotList;
        allCitys.addAll(0,hotList);
        allCitys.addAll(hotList.size(),list);

    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     * @param list
     */
    public void updateListView(List<AllCity> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.allCitys.size();
    }

    public Object getItem(int position) {
        return allCitys.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {

        final AllCity allCity = allCitys.get(position);
        view = LayoutInflater.from(mContext).inflate(R.layout.lv_item_city, null);
        TextView  tv_city = (TextView) view.findViewById(R.id.tv_city);
        TextView  tv_all_city = (TextView) view.findViewById(R.id.tv_all_city);

        if (position==0)
        {
            tv_all_city.setVisibility(View.VISIBLE);
            tv_all_city.setText("热门城市");
        }
        else if (position==hotList.size())
        {
            tv_all_city.setVisibility(View.VISIBLE);
            tv_all_city.setText("所有城市");
        }


        tv_city.setText(this.allCitys.get(position).getName());

        return view;

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
        for (int i = 4; i < getCount(); i++) {
            String sortStr = allCitys.get(i).getSortLetters();
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
