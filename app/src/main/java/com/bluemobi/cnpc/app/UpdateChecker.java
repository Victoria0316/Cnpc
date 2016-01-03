package com.bluemobi.cnpc.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.base.utils.Logger;
import com.bluemobi.cnpc.network.request.VersionUpdatingRequest;
import com.bluemobi.cnpc.network.response.LoginResponse;
import com.bluemobi.cnpc.network.response.VersionUpdatingResponse;
import com.bluemobi.cnpc.service.UpdateService;
import com.bluemobi.cnpc.util.PreferencesService;
import com.bluemobi.cnpc.util.Utils;
import com.bluemobi.cnpc.util.WebUtils;
import com.bluemobi.cnpc.view.CnpcAlertDialog;
import com.bluemobi.cnpc.view.CustomDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {

    private final static String tag = "UpdateChecker";
    private static BaseActivity mActivity;
//	private static BaseFragmentActivity mFragmentActivity;

    private static int localVersion = 0;

    private CustomDialog dialog;

    private UpdateChecker(BaseActivity activity) {
        mActivity = activity;
        try {
            localVersion = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

    }

//	private UpdateChecker(BaseFragmentActivity activity) {
//		mFragmentActivity = activity;
//		try {
//			localVersion = mFragmentActivity.getPackageManager().getPackageInfo(
//					mFragmentActivity.getPackageName(), 0).versionCode;
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}

//	public static UpdateChecker getInstance(BaseFragmentActivity activity) {
//		if (instance == null) {
//			instance = new UpdateChecker(activity);
//		}
//		mFragmentActivity = activity;
//		return instance;
//	}


    private static UpdateChecker instance;

    public static UpdateChecker getInstance(BaseActivity activity) {
        if (instance == null) {
            instance = new UpdateChecker(activity);
        }
        mActivity = activity;
        return instance;
    }

    /**
     * 是否提示检测中
     *
     * @param showLoading
     */
    //baseactivity
    public void check(final boolean showLoading) {
        Logger.d(tag, "check");

        VersionUpdatingRequest request = new VersionUpdatingRequest(new Response.Listener<VersionUpdatingResponse>() {

            @Override
            public void onResponse(final VersionUpdatingResponse response) {
                if (showLoading) {
                    Utils.closeDialog();
                }
                if (response != null && response.getStatus() == 0) {
                    Logger.d(tag, "版本更新请求成功");

                    Logger.d(tag, response.getData().value);


                    final boolean force = true;//response.getData().getForceUpdate();
                    String negative = !force ? "取消" : "退出";
                    final CnpcAlertDialog dialog = new CnpcAlertDialog(
                            mActivity);
                    dialog.setTitle("更新提示")
                            .setMessage("有新版本是否更新")//response.getData().getReleaseNote())
                            .setNegativeButtonClickListener(negative,
                                    new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            Logger.d(tag,"取消");
                                            dialog.dismiss();
//														if(force){
//															if(mActivity instanceof MainActivity){
//																LlptApplication.getInstance().myUserInfo = null;
//																mActivity.finish();
//															}
//														}
                                        }
                                    })
                            .setPositiveButtonClickListener("更新",
                                    new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            Logger.d(tag,"更新");

                                            Intent updateIntent = new Intent(mActivity, UpdateService.class);
                                            updateIntent.putExtra("downLoadUrl", response.getData().value);
                                            Log.e("update app", response.getData().value);
                                            updateIntent.putExtra("ifForce", force);
                                            mActivity.startService(updateIntent);
                                            dialog.dismiss();
                                        }
                                    }).show();
//										.show();


                }
            }


        }, mActivity);

//		checkUserId 	string 	是 	用户登录时返回的userId对应的值
//		ssid 	string 	是 	session ID
//		app 	string 	是 	app标识 默认为'android'
//		version 	string 	是 	app当前版本号

        request.setCheckUserId(PreferencesService.getInstance(mActivity).getPreferences("userId"));
        request.setSsid(PreferencesService.getInstance(mActivity).getPreferences("ssid"));
        request.setApp("android");
        request.setVersion(localVersion + "");

        if (showLoading) {
            Utils.showProgressDialog(mActivity);
        } else {
            request.setHandleCustomErr(false);
        }

        WebUtils.doPost(request);


//        CheckVersionRequest request = new CheckVersionRequest(
//                new Response.Listener<CheckVersionResponse>() {
//
//                    @Override
//                    public void onResponse(final CheckVersionResponse response) {
//                        if (showLoading) {
//                            Utils.closeDialog();
//                        }
//                        if (response != null && response.getStatus() == 0) {
//                            float serverVersion = response.getData().getVersion();
//                            Log.e("serverVersion", serverVersion + "");
//                            Log.e("localVersion", localVersion + "");
//                            if (serverVersion > localVersion) {
//                                final boolean force = response.getData().getForceUpdate();
//                                String negative = !force ? "取消" : "退出";
//                                final LlptAlertDialog dialog = new LlptAlertDialog(
//                                        mActivity);
//                                dialog.setTitle("更新提示")
//                                        .setMessage(
//                                                response.getData()
//                                                        .getReleaseNote())
//                                        .setNegativeButtonClickListener(negative,
//                                                new View.OnClickListener() {
//
//                                                    @Override
//                                                    public void onClick(View v) {
//                                                        dialog.dismiss();
////														if(force){
////															if(mActivity instanceof MainActivity){
////																LlptApplication.getInstance().myUserInfo = null;
////																mActivity.finish();
////															}
////														}
//                                                    }
//                                                })
//                                        .setPositiveButtonClickListener("更新",
//                                                new View.OnClickListener() {
//
//                                                    @Override
//                                                    public void onClick(View v) {
//                                                        Intent updateIntent = new Intent(
//                                                                mActivity,
//                                                                UpdateService.class);
//                                                        updateIntent.putExtra("downLoadUrl",
//                                                                response.getData().getLoadurl());
//                                                        Log.e("update app", response.getData().getLoadurl());
//                                                        updateIntent.putExtra("ifForce", force);
//                                                        mActivity
//                                                                .startService(updateIntent);
//                                                        dialog.dismiss();
//                                                    }
//                                                }).show();
////										.show();
//                            } else {
//                                if (showLoading) {
//                                    CustomDialog.Builder customBuilder = new CustomDialog.Builder(mActivity);
//                                    customBuilder
//                                            .setTitle("提示")
//                                            .setMessage("当前版本已为最新")
//                                            .setLineGONE(View.GONE)
//                                            .setNegativeButton("确定",
//                                                    new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialog,
//                                                                            int which) {
//                                                            dialog.dismiss();
//                                                        }
//                                                    });
//                                    dialog = customBuilder.create();
//                                    dialog.show();
//                                }
//                            }
//                        } else {// TODO:
//                            if (!showLoading) {
//                                return;
//                            }
//                            Utils.makeToastAndShow(
//                                    mActivity,
//                                    response == null ? mActivity.getString(R.string.global_unknown_err) : response
//                                            .getContent(), Toast.LENGTH_SHORT);
//                        }
//                    }
//                }, mActivity);
//        if (showLoading) {
//            Utils.showProgressDialog(mActivity);
//        } else {
//            request.setHandleCustomErr(false);
//        }
//        WebUtils.doPost(request);
    }







    /**
     * 是否提示检测中
     *
     * @param showLoading
     */
    //baseafragmentctivity
//	public void checkFragment(final boolean showLoading) {
//		CheckVersionRequest request = new CheckVersionRequest(
//				new Response.Listener<CheckVersionResponse>() {
//
//					@Override
//					public void onResponse(final CheckVersionResponse response) {
//						if (showLoading) {
//							Utils.closeDialog();
//						}
//						if (response != null && response.getStatus() == 0) {
//							float serverVersion = response.getData()
//									.getVersion();
//							Log.e("serverVersion", serverVersion+"");
//							Log.e("localVersion", localVersion+"");
//							if (serverVersion > localVersion) {
//								final boolean force =  response.getData().getForceUpdate();
//								String negative = !force? "取消":"退出";
//								final LlptAlertDialog dialog = new LlptAlertDialog(
//										mFragmentActivity);
//								dialog.setTitle("更新提示")
//										.setMessage(
//												response.getData()
//														.getReleaseNote())
//										.setNegativeButtonClickListener(negative,
//												new View.OnClickListener() {
//
//													@Override
//													public void onClick(View v) {
//														dialog.dismiss();
////														if(force){
////															if(mActivity instanceof MainActivity){
////																LlptApplication.getInstance().myUserInfo = null;
////																mActivity.finish();
////															}
////														}
//													}
//												})
//										.setPositiveButtonClickListener("更新",
//												new View.OnClickListener() {
//
//													@Override
//													public void onClick(View v) {
//														Intent updateIntent = new Intent(
//																mFragmentActivity,
//																UpdateService.class);
//														updateIntent.putExtra("downLoadUrl",
//																 response.getData().getLoadurl());
//														Log.e("update app", response.getData().getLoadurl());
//														updateIntent.putExtra("ifForce", force);
//														mFragmentActivity
//																.startService(updateIntent);
//														dialog.dismiss();
//													}
//												}).show();
////										.show();
//							} else {
//								if(showLoading){
//									CustomDialog.Builder customBuilder = new CustomDialog.Builder(mFragmentActivity);
//									customBuilder
//											.setTitle("提示")
//											.setMessage("当前版本已为最新")
//											.setLineGONE(View.GONE)
//											.setNegativeButton("确定",
//													new DialogInterface.OnClickListener() {
//														@Override
//														public void onClick(DialogInterface dialog,
//																int which) {
//															dialog.dismiss();
//														}
//													});
//									dialog = customBuilder.create();
//									dialog.show();
//								}
//							}
//						} else {// TODO:
//							if(!showLoading){
//								return;
//							}
//							Utils.makeToastAndShow(
//									mFragmentActivity,
//									response == null ? mFragmentActivity.getString(R.string.global_unknown_err) : response
//											.getContent(), Toast.LENGTH_SHORT);
//						}
//					}
//				}, mFragmentActivity);
//		if (showLoading) {
//			Utils.showProgressDialog(mFragmentActivity);
//		}else{
//			request.setHandleCustomErr(false);
//		}
//		WebUtils.doPost(request);
//	}
}
