package com.bluemobi.cnpc.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.adapter.ChangeCityAdapter;
import com.bluemobi.cnpc.adapter.CommonAdapter;
import com.bluemobi.cnpc.adapter.ViewHolder;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.db.entity.AllCity;
import com.bluemobi.cnpc.db.entity.CarAllListDetail;
import com.bluemobi.cnpc.db.entity.CityListDetail;
import com.bluemobi.cnpc.db.entity.CityPinyinComparator;
import com.bluemobi.cnpc.db.entity.HotCityListDetail;
import com.bluemobi.cnpc.util.CharacterParser;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.view.CustomListView;
import com.bluemobi.cnpc.view.LoadingPage;
import com.bluemobi.cnpc.view.OnTouchingLetterChangedListener;
import com.bluemobi.cnpc.view.SideBar;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/23.
 * P7城市切换
 */
@ContentView(R.layout.activity_change_city)
public class CityChangeActivity extends BaseActivity {

    @Bind(R.id.lv_all_city)
    ListView lv_all_city;

    private List<String> mDatas = new ArrayList<String>();

    private CommonAdapter<String> commonAdapter = null;

    @Bind(R.id.sideBar)
    protected SideBar sideBar;

    private ChangeCityAdapter adapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    private List<AllCity> SourceDateList;

    private List<AllCity> favorDataList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private CityPinyinComparator pinyinComparator;

    private String change_city;

    private List<AllCity> allCitys = new ArrayList<AllCity>();

    @Override
    protected void initBase() {
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_city_change, R.drawable.return_arrow, true);
        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
/*        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) sideBar.getLayoutParams();
        layoutParams.setMargins(0, 500, 0, 0);*/
        //实例化汉字转拼音类

        change_city = getIntent().getStringExtra("change_city");
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new CityPinyinComparator();
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s, String dis) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lv_all_city.setSelection(position);
                }
            }
        });

        lv_all_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                AllCity singleCity = allCitys.get(position);
                if ("change_city".equals(change_city))
                {
                    Intent intent = new Intent();
                    intent.putExtra("BACK_CITY_TYPE","BACK_CITY_TYPE");
                    intent.putExtra("backcity",singleCity.getName());
                    intent.putExtra("backcityID",singleCity.getAddressid());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else
                {

                    CnpcApplication.getInstance().setSingleCity(singleCity);
                    Utils.moveTo(mContext, HomeActivity.class);
                }



            }
        });
        //
        List<CityListDetail> all = DataSupport.where("divisiontype = ?", "2").find(CityListDetail.class);
        List<HotCityListDetail> hotCitys = DataSupport.findAll(HotCityListDetail.class);
        List<AllCity> hotCityList = new ArrayList<AllCity>();
        for (HotCityListDetail hotCity :hotCitys)
        {
            AllCity hotSingleCity = new AllCity();
            hotSingleCity.setName(hotCity.getDivisionName());
            hotSingleCity.setAddressid(hotCity.getSid());
            hotCityList.add(hotSingleCity);
        }
        SourceDateList = filledData(all);
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        allCitys.addAll(0,hotCityList);
        allCitys.addAll(hotCitys.size(), SourceDateList);
        adapter = new ChangeCityAdapter(this, SourceDateList,hotCityList);
        //adapter = new ChangeCityAdapter(this, SourceDateList);
        lv_all_city.setAdapter(adapter);


    }


    private List<AllCity> filledData(List<CityListDetail> data) {

        List<AllCity> mSortList = new ArrayList<AllCity>();

        for (int i = 0; i < data.size(); i++)
        {
            AllCity sortModel = new AllCity();
            sortModel.setName(data.get(i).getDivisionName());
            sortModel.setAddressid(data.get(i).getSid());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(data.get(i).getDivisionName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
}
