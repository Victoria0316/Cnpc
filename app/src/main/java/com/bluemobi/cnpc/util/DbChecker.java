package com.bluemobi.cnpc.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * DB check
 */
public class DbChecker extends AsyncTask<Void, Void, Void> {

	public static String dbName = "cnpcdb.db";// 数据库的名字

	private static String DATABASE_PATH = "/data/data/com.bluemobi.cnpc/databases/";

	private Context mContext;

	public DbChecker(Context context) {
		mContext = context;
	}

	@Override
	protected Void doInBackground(Void... params) {
		boolean dbExist = isDbExist();
		if (!dbExist) {
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
		return null;
	}



	/**
	 * 判断数据库是否存在
	 * 
	 * @return false or true
	 */
	public boolean isDbExist() {

		File dbFile = mContext.getDatabasePath(dbName);
		return dbFile.exists();
	}

	/**
	 * 复制数据库到手机指定文件夹下
	 * 
	 * @throws IOException
	 */
	public void copyDataBase() throws IOException {
		String databaseFilenames = DATABASE_PATH + dbName;
		File dir = new File(DATABASE_PATH);
		if (!dir.exists())// 判断文件夹是否存在，不存在就新建一个
			dir.mkdir();
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(databaseFilenames);// 得到数据库文件的写入流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		AssetManager mAssetManager = mContext.getAssets();
		InputStream is = mAssetManager.open("cnpcdb.db");
		byte[] buffer = new byte[8192];
		int count = 0;
		try {

			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (IOException e) {

		}
		try {
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
