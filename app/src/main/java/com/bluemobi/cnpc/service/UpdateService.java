package com.bluemobi.cnpc.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.activity.HomeActivity;
import com.bluemobi.cnpc.base.utils.Logger;

public class UpdateService extends Service {
	private final static String tag ="UpdateService";

	private File updateDir;
	private File updateFile;
	private NotificationManager updateNotificationManager;
	private Notification updateNotification;
	private Intent updateIntent;
	private PendingIntent updatePendingIntent;
	private static final int DOWNLOAD_COMPLETE = 0;
	private static final int DOWNLOAD_FAIL = -1;
	private String downLoadUrl;
	boolean isForceUpdate;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 获取传值
		// titleId = intent.getIntExtra("titleId",0);
		// 创建文件

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			updateDir = new File(Environment.getExternalStorageDirectory(),
					"Cnpc");
			updateFile = new File(updateDir.getPath(), "Cnpc" + ".apk");
		}
		//调试用 apk 下载地址
		this.downLoadUrl = intent.getStringExtra("downLoadUrl");
		this.isForceUpdate = intent.getBooleanExtra("ifForce", false);
		this.updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		this.updateNotification = new Notification();

		// 设置下载过程中，点击通知栏，回到主界面
		Log.e("UpdateService", "跳到mainactivity");
		updateIntent = new Intent(this, HomeActivity.class);
		updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent,
				0);
		// 设置通知栏显示内容
		updateNotification.icon = R.drawable.right_arrow_blue;
		updateNotification.tickerText = "开始下载";
		updateNotification.setLatestEventInfo(this, "油管家", "0%",
				updatePendingIntent);
		// 发出通知
		updateNotificationManager.notify(0, updateNotification);

		// 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
		new Thread(new updateRunnable()).start();// 这个是下载的重点，是下载的过程

		return super.onStartCommand(intent, flags, startId);
	}

	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_COMPLETE:
				// 点击安装PendingIntent
				if(!isForceUpdate){
					Uri uri = Uri.fromFile(updateFile);
					Intent installIntent = new Intent(Intent.ACTION_VIEW);
					installIntent.setDataAndType(uri,
							"application/vnd.android.package-archive");
					updatePendingIntent = PendingIntent.getActivity(
							UpdateService.this, 0, installIntent, 0);
				}

				updateNotification.defaults = Notification.DEFAULT_SOUND;// 铃声提醒
				updateNotification.setLatestEventInfo(UpdateService.this,
						"油管家", "下载完成,点击安装。", updatePendingIntent);
				updateNotificationManager.notify(0, updateNotification);

				installApk();
				// 停止服务
				stopSelf();
				break;
			case DOWNLOAD_FAIL:
				// 下载失败
				updateNotification.setLatestEventInfo(UpdateService.this,
						"油管家", "下载完成,点击安装。", updatePendingIntent);
				updateNotificationManager.notify(0, updateNotification);
				break;
			default:
				stopSelf();
				break;
			}
		}
	};

	public void installApk() {
		if (updateFile == null) {
			Toast.makeText(getApplicationContext().getApplicationContext(),
					"下载新版本失败", Toast.LENGTH_SHORT).show();
			if (isForceUpdate) {
//				LlptApplication.getInstance().myUserInfo = null;
//				LlptActivityManager.getInstance().finishAllActivity();
			}
		} else {
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// 执行动作
			intent.setAction(Intent.ACTION_VIEW);
			// 执行的数据类型
			intent.setDataAndType(Uri.fromFile(updateFile),
					"application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
			getApplicationContext().startActivity(intent);
			if (isForceUpdate) {
//				LlptApplication.getInstance().myUserInfo = null;
//				LlptActivityManager.getInstance().finishAllActivity();
			}
		}
	}

	class updateRunnable implements Runnable {
		Message message = updateHandler.obtainMessage();

		public void run() {
			message.what = DOWNLOAD_COMPLETE;
			try {
				// 增加权限;
				if (!updateDir.exists()) {
					updateDir.mkdirs();
				}
				if (!updateFile.exists()) {
					updateFile.createNewFile();
				}
				// 下载函数，以QQ为例子
				// 增加权限;
				long downloadSize = downloadUpdateFile(downLoadUrl, updateFile);
				if (downloadSize > 0) {
					// 下载成功
					updateHandler.sendMessage(message);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				message.what = DOWNLOAD_FAIL;
				// 下载失败
				updateHandler.sendMessage(message);
			}
		}
	}

	public long downloadUpdateFile(String downloadUrl, File saveFile)
			throws Exception {
		// 这样的下载代码很多，我就不做过多的说明
		int downloadCount = 0;
		int currentSize = 0;
		long totalSize = 0;
		int updateTotalSize = 0;

		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;

		try {
			URL url = new URL(downloadUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection
					.setRequestProperty("User-Agent", "PacificHttpClient");
			if (currentSize > 0) {
				httpConnection.setRequestProperty("RANGE", "bytes="
						+ currentSize + "-");
			}
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			updateTotalSize = httpConnection.getContentLength();
			if (httpConnection.getResponseCode() == 404) {
				throw new Exception("fail!");
			}
			is = httpConnection.getInputStream();
			fos = new FileOutputStream(saveFile, false);
			byte buffer[] = new byte[4096];
			int readsize = 0;
			while ((readsize = is.read(buffer)) > 0) {
				fos.write(buffer, 0, readsize);
				totalSize += readsize;
				// 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
				if ((downloadCount == 0)
						|| (int) (totalSize * 100 / updateTotalSize) - 5 > downloadCount) {
					downloadCount += 5;
					updateNotification.setLatestEventInfo(UpdateService.this,
							"正在下载", downloadCount + "%", updatePendingIntent);
					updateNotificationManager.notify(0, updateNotification);
				}
			}
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return totalSize;
	}

}
