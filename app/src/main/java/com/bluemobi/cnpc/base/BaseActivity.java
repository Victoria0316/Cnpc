package com.bluemobi.cnpc.base;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.bluemobi.cnpc.activity.HomeActivity;
import com.bluemobi.cnpc.activity.LoginActivity;
import com.bluemobi.cnpc.activity.PayActivity;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.CnpcActivityManager;
import com.bluemobi.cnpc.app.CnpcApplication;
import com.bluemobi.cnpc.base.crop.Crop;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.callback.TitleBarCallBack;
import com.bluemobi.cnpc.eventbus.BaseEvent;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.exception.CustomResponseError;
import com.bluemobi.cnpc.network.exception.TokenInvalid;
import com.bluemobi.cnpc.network.request.LoginRequest;
import com.bluemobi.cnpc.network.response.LoginResponse;
import com.bluemobi.cnpc.util.Constants;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.ViewUtils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.CnpcAlertDialog;
import com.bluemobi.cnpc.view.LoadingPage;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tendcloud.tenddata.TCAgent;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements Response.ErrorListener, TitleBarCallBack, PullToRefreshListView.OnRefreshListener2 {

    /**
     * 上下文
     */
    public Context mContext = null;
    /**
     * 加载Loading页面
     */
    private LoadingPage loadingPage;


    private ImageView resultView;

    protected View contentView;

    private CnpcHttpJsonRequest request;


    private CnpcAlertDialog mDialog;

    public PullToRefreshListView plv_refresh = null;

    public static final int LOAD_MORE = 1;
    public static final int LOAD_REFRESH = 2;
    public static final int NUMBER_PER_PAGE = Constants.CURRENTNUMBASE;// 每页条数
    protected int curPage = -1;// 当前页
    protected long pageTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        request = initRequest();
        initView(inflater);
        initBase();
        invokeRequest();
        CnpcActivityManager.getInstance().pushOneActivity(this);
        TCAgent.setReportUncaughtExceptions(true);
        //TCAgent.init(this);
    }

    protected CnpcHttpJsonRequest initRequest() {
        return null;
    }

    private void invokeRequest() {
        if (request == null) {
            return;
        } else {
            request.setNetWorkResponseListener(loadingPage);
            pageTime = System.currentTimeMillis();
            CnpcApplication.getInstance().setPageTime(pageTime);
            request.setNetWorkResponseListener(loadingPage);
            WebUtils.doPost(request);
        }
    }

    /**
     * 初始化基础数据
     */
    protected abstract void initBase();

    /**
     * 初始化loadingpage
     */
    protected void initView(final LayoutInflater inflater) {

        if (loadingPage == null) {
            loadingPage = new LoadingPage(mContext) {

                @Override
                protected void successViewCompleted(View successView) {
                    BaseActivity.this.successViewCompleted(successView);
                }

                @Override
                public View createSuccessView() {
                    return BaseActivity.this.createSuccessView();
                }

                @Override
                protected LoadResult load() {
                    return BaseActivity.this.load();
                }
            };
        } else {
            ViewUtils.removeParent(loadingPage);
        }
        setContentView(loadingPage);

    }

    protected abstract void successViewCompleted(View successView);

    /**
     * 显示加载界面
     */
    public void showLoadingPage(boolean isShowLoad) {
        if (loadingPage != null) {
            loadingPage.show(isShowLoad);
        }
    }


    /**
     * 服务器返回数据校验
     *
     * @param datas
     * @return
     */
    public LoadingPage.LoadResult checkData(List datas) {
        if (datas == null) {
            return LoadingPage.LoadResult.error;//  请求服务器失败
        } else {
            if (datas.size() == 0) {
                return LoadingPage.LoadResult.empty;
            } else {
                return LoadingPage.LoadResult.success;
            }
        }
    }


    /***
     * 创建成功的界面 通过inflate加载界面
     *
     * @return
     */
    public View createSuccessView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        if (Constants.NO_ANNOTATION == getLayoutRes()) {
            contentView = createViewCustom(inflater);
        } else {
            View view = inflater.inflate(getLayoutRes(), null);
            contentView = view;
        }
        if (contentView == null) {
            //TODO:

        }
        return contentView;
    }

    public View getCurrentView() {
        return contentView;
    }

    protected View createViewCustom(LayoutInflater inflater) {
        return null;
    }


    @Override
    protected void onStart() {

        super.onStart();

        if(getPageName() != null){
            TCAgent.onPageStart(mContext, "android---" + String.valueOf(getPageName()));

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(getPageName() != null){
            TCAgent.onPageEnd(mContext, "android---" + String.valueOf(getPageName()));
        }
    }

    protected String getPageName() {
        Class<?> handlerType = this.getClass();
        PageName pageName = handlerType.getAnnotation(PageName.class);
        if (pageName == null) {
            return null;
        }
        return getString(pageName.value());
    }


    protected int getLayoutRes() {
        Class<?> handlerType = this.getClass();
        ContentView contentView = handlerType.getAnnotation(ContentView.class);
        if (contentView == null) {
            return Constants.NO_ANNOTATION;
        }
        return contentView.value();
    }

    /**
     * 请求服务器
     *
     * @return
     */
    protected abstract LoadingPage.LoadResult load();

    protected void myFinish() {
        CnpcActivityManager.getInstance().popOneActivity(this);
    }


    @Override
    protected void onDestroy() {
        CnpcActivityManager.getInstance().popOneActivity(this);
        super.onDestroy();

    }




    /**
     * 点击bar 右边按钮
     */
    @Override
    public void clickBarRight() {

    }

    /**
     * 点击bar左边按钮
     */
    @Override
    public void clickBarleft() {
        this.finish();
    }

    public void finishAll() {
        CnpcActivityManager.getInstance().popAllActivityExceptOne(HomeActivity.getInstance());
    }

    /**
     * 注销登录 清空栈
     */
    protected void finishSearch() {
        CnpcActivityManager.getInstance().popAllActivityExceptOne();
    }
    /**
     * 点击bar搜索按钮
     */
    @Override
    public void clickBarSearch() {

    }


    /**
     * 点击bar搜索按钮
     */
    @Override
    public void clickBarHint() {

    }

    @Override
    public void clickImageRight() {

    }

    @Override
    public void clickImageLeft() {

    }


    public void initPullToRefresh(PullToRefreshListView pullToRefresh) {
        this.plv_refresh = pullToRefresh;
        pullToRefresh.setOnRefreshListener(this);
        pullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabels = pullToRefresh
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("正在载入...");
        startLabels.setReleaseLabel("放开刷新...");
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        } else if (requestCode == Crop.REQUEST_CAMERA && resultCode == RESULT_OK) {
            beginCrop(Crop.outputFileUri);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            resultView.setImageURI(Crop.getOutput(result));
            UploadHeadImage();
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void UploadHeadImage() {
    }

    protected void getImage(ImageView iv) {
        this.resultView = iv;
        CharSequence[] items = {"相册", "相机"};
        new AlertDialog.Builder(this).setTitle("选择图片来源")
                .setItems(items, new MyDialogClickListener(1)).create()
                .show();
    }

    private class MyDialogClickListener implements
            DialogInterface.OnClickListener {
        private int n;

        MyDialogClickListener(int number) {
            this.n = number;
        }

        public void onClick(DialogInterface dialog, int which) {
            if (which == 0) {
                resultView.setImageDrawable(null);
                Crop.pickImage(BaseActivity.this);
            } else {
                resultView.setImageDrawable(null);
                Crop.pickImageFromCamera(BaseActivity.this);
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        requestError(error);
    }

    public void requestError(VolleyError error) {
        if (plv_refresh != null) {
            plv_refresh.onRefreshComplete();
        }
        Utils.closeDialog();
        if (error instanceof TimeoutError) {
            Utils.makeToastAndShow(this, "网络异常,请检查您的网络", Toast.LENGTH_SHORT);
        } else if (error instanceof ParseError) {
            Utils.makeToastAndShow(this, "解析出错", Toast.LENGTH_SHORT);
        } else if (error instanceof TokenInvalid) {
            if (mDialog == null || !mDialog.isShowing()) {
                mDialog = new CnpcAlertDialog(this);
                mDialog.setTitle("提示")
                        .setMessage("用户登录已失效,请重新登录")
                        .setNegativeButtonClickListener("取消",
                                new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        mDialog.dismiss();
                                    }
                                })
                        .setPositiveButtonClickListener("确定",
                                new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setClass(getBaseContext(),
                                                LoginActivity.class);
                                        intent.putExtra("tokeninvalid", true);
                                        getBaseContext().startActivity(intent);
                                        mDialog.dismiss();
                                    }
                                });
                mDialog.show();
            }

        } else if (error instanceof CustomResponseError) {
            CustomResponseError crs = (CustomResponseError) error;
            requestCustomErr(crs);
        } else if (error instanceof NoConnectionError) {//无网络连接
            Utils.makeToastAndShow(this, "网络异常,请检查您的网络");
        }
        error.printStackTrace();
    };


    public void requestCustomErr(CustomResponseError err) {
        if (err.isToast()) {
            Utils.makeToastAndShow(this, err.getErrMsg(), Toast.LENGTH_SHORT);
        }
    }


    //add getPage
    protected boolean getPage(int type) {
        boolean ret = true;
        switch (type) {
            case LOAD_MORE: {
                ListAdapter adapter = plv_refresh.getRefreshableView().getAdapter();
                int count =0;
                if (adapter!=null)
                {
                    count = adapter.getCount();
                }

                if(count == 0){
                    plv_refresh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            plv_refresh.onRefreshComplete();
                            Utils.makeToastAndShow(mContext, "已经没有更多记录", Toast.LENGTH_SHORT);
                        }
                    }, 2000);
                    return false;
                }
                int i = count - 2;
                if (i % NUMBER_PER_PAGE == 0) {
                    curPage = i / NUMBER_PER_PAGE;
                    Log.e("tag plv ->", "LOAD_MORE");
                } else {
                    ret = false;
                    Log.e("tag plv ELSE->", "LOAD_MORE");
                    plv_refresh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            plv_refresh.onRefreshComplete();
                            Utils.makeToastAndShow(mContext, "已经没有更多记录", Toast.LENGTH_SHORT);
                        }
                    }, 2000);
                }
            }
            break;
            case LOAD_REFRESH:
                Log.e("tag plv ->", "LOAD_REFRESH");
                pageTime = System.currentTimeMillis();
                CnpcApplication.getInstance().setPageTime(pageTime);
                curPage = 0;
                break;
        }


        return ret;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        Log.e("tag", "onPullDownToRefresh");
        getPage(LOAD_REFRESH);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        Log.e("tag", "onPullUpToRefresh");
        getPage(LOAD_MORE);

    }

    /**
     *
     * @return  true 需要登录，false 不需要登录
     */
    public boolean goToLogin() {

        boolean loginSucceed = PreferencesService.getInstance(mContext).getPreferencesBool("loginSucceed"); //frist TRUE

        if (loginSucceed) {
            //以前登录过
            String pwd = PreferencesService.getInstance(mContext).getPreferences("pwd");
            String phoneNum = PreferencesService.getInstance(mContext).getPreferences("phoneNum");
            connectToServerLogin(phoneNum,pwd);
            return false;
        } else {
            //以前没有登录过
            if (CnpcApplication.getInstance().getmData() == null) {
                finishAll();
                return true;
            }


        }
        return false;
    }




    private void connectToServerLogin(String phoneNum,String pwd) {

        LoginRequest request = new LoginRequest
                (
                        new Response.Listener<LoginResponse>() {
                            @Override
                            public void onResponse(LoginResponse response) {
                                if (response != null && response.getStatus() == 0)
                                {// success
                                    LoginResponse.LoginData data = response.data;
                                    CnpcApplication.getInstance().setmData(data);
                                    try {

                                        PreferencesService.getInstance(mContext).saveBool("loginSucceed", true);

                                        PreferencesService.getInstance(mContext).saveBool("firstloginsucceed", true);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if(response != null && response.getStatus() == 2)
                                {
                                    try {
                                        PreferencesService.getInstance(mContext).clear();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                                else
                                {
                                    Utils.makeToastAndShow(getBaseContext(), response.getMsg());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        request.setPassword(pwd);
        request.setHandleCustomErr(false);
        request.setUsername(phoneNum);
        WebUtils.doPost(request);
    }

    /**
     *
     * 选择日期 2015/03/15
     *
     * */
    public void pickDateDialog(final int viewId) {
        Calendar cd = Calendar.getInstance();
        new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                View v = findViewById(viewId);
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    tv.setText(String.format("%04d/%02d/%02d", year,
                            monthOfYear + 1, dayOfMonth));
                } else if (v instanceof EditText) {
                    EditText et = (EditText) v;
                    et.setText(String.format("%04d/%02d/%02d", year,
                            monthOfYear + 1, dayOfMonth));
                }
            }
        }, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH),
                cd.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     *
     * 选择时间 06:09
     *
     * */
    public void pickTimeDialog(final int viewId) {
        Calendar time = Calendar.getInstance();
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String s = String.format("%02d:%02d", hourOfDay, minute);
                View v = findViewById(viewId);
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    tv.setText(s.toString());
                } else if (v instanceof EditText) {
                    EditText et = (EditText) v;
                    et.setText(s.toString());
                }
            }
        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true).show();
    }

    public static String getDateTimeEx(int dayAdd) {
        Calendar calendar = new GregorianCalendar();
        calendar.add(calendar.DATE, dayAdd);
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);
        return String.format("%04d/%02d/%02d %02d:%02d", y, m, d, h, mi);
    }

}
