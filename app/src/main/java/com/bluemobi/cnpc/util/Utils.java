package com.bluemobi.cnpc.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.widget.TextView;
import android.widget.Toast;


import com.bluemobi.cnpc.base.BaseActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	static private Utils utils = null;

	static public Utils GetInstance() {
		if (null == utils) {
			utils = new Utils();
		}
		return utils;
	}

	private static AlertDialog mDialog;

	private static int deviceWidth;

	private static int deviceHeight;

	private static long lastShowTime;

	public static int getDeviceWidth() {
		return deviceWidth;
	}

	public static int getDeviceWidth(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		deviceWidth = dm.widthPixels;// 宽度
		deviceHeight = dm.heightPixels;// 高度
		return deviceWidth;
	}

	public static int getDeviceHeight() {
		return deviceHeight;
	}

	public static void initDevice(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		deviceWidth = dm.widthPixels;// 宽度
		deviceHeight = dm.heightPixels;// 高度
	}

	public static void moveTo(Context context, Class<? extends Activity> cls) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		context.startActivity(intent);
	}

	public static void makeToastAndShow(Context context, String text, int length) {
		if (new Date().getTime() - lastShowTime > 2000) {
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
			lastShowTime = new Date().getTime();
		}
	}

	public static void makeToastAndShow(Context context, String text) {
		makeToastAndShow(context, text, Toast.LENGTH_LONG);
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static void closeDialog() {
		try {
			if (mDialog != null && mDialog.isShowing()) {
				mDialog.dismiss();
				mDialog.cancel();
				mDialog = null;
			}
		} catch (Exception e) {
			mDialog = null;
		}

	}

	public static Dialog showProgressDialog(Context context) {
		if (mDialog == null || !mDialog.isShowing()) {
			mDialog = new ProgressDialog(context);
			mDialog.setMessage("加载中...");
			mDialog.setCancelable(false);
			mDialog.show();
		}
		return mDialog;
	}


	public static Dialog showProgressDialog(Context context,boolean isCancle) {
		if (mDialog == null || !mDialog.isShowing()) {
			mDialog = new ProgressDialog(context);
			mDialog.setMessage("加载中...");
			mDialog.setCancelable(isCancle);
			mDialog.show();
		}
		return mDialog;
	}

	public static Dialog showProgressDialog(Context context, String message) {
		if (mDialog == null || !mDialog.isShowing()) {
			mDialog = new ProgressDialog(context);
			mDialog.setMessage(message);
			mDialog.setCancelable(false);
			mDialog.show();
		}
		return mDialog;
	}

	public static boolean isShowing(Context context) {
		boolean flag = false;
		if (mDialog != null && mDialog.isShowing()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 检测手机号码是否合法
	 * 
	 * @return
	 */
	public static boolean checkPhoneNum(String value) {
		// String regExp = "^[1]([3][0-9]{1}|59|58|88|89|50|56)[0-9]{8}$";
		// regExp =
		// "^(130|131|132|133|134|135|136|137|138|139|145|147|150|151|152|153|155|157|156|158|159|176|177|178|180|181|188|182|183|184|185|186|189)\\d{8}$";
		String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[6-8])|145|147)\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(value);
		return m.find();
	}

	/**
	 * 检测密码是否合法
	 * 
	 * @param pwd
	 *            6-18位
	 * @return
	 */
	public static boolean checkPwd(String pwd) {
		String regExp = "^[a-zA-Z0-9]+$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(pwd);
		boolean temp = m.find();
		if (temp && (pwd.length() > 5 && pwd.length() < 19)) {
			return true;
		} else {
			return false;
		}
	}



	/**
	 * 检测密码强度是否合法
	 * 
	 * @param pwd
	 *            8位以上字母符号数字
	 * @return//[\u0000-\u00FF]
	 */
	public static boolean checkPwdStrength(String pwd, Context context) {
		String regExp = "^[a-zA-Z0-9\u0000-\u00FF]+$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(pwd);
		boolean temp = m.find();
		if (!temp) {
			showTipDialog(context, "密码必须为字母字符数字");
			return false;
		}
		if (temp && (pwd.length() >= 8 && pwd.length() < 19)) {
			return checkPwdStrengthContain(pwd, context);
		} else {
			showTipDialog(context, "密码长度为8到18位");
			return false;
		}
	}
	private static void showTipDialog(Context context, String tipMsg) {
		if(context instanceof BaseActivity){
			BaseActivity baseActivity = (BaseActivity)context;
			//baseActivity.showTipDialog(tipMsg);
		}
	}

	public static boolean checkPwdStrengthContain(String pwd, Context context) {
		int digitCount = 0;
		int letterCount = 0;
		for (int i = 0; i < pwd.length(); i++) { // 循环遍历字符串
			if (Character.isDigit(pwd.charAt(i))) { // 用char包装类中的判断数字的方法判断每一个字符
				digitCount++;
			}
			if (Character.isLetter(pwd.charAt(i))) { // 用char包装类中的判断字母的方法判断每一个字符
				letterCount++;
			}
		}
		if (digitCount == 0) {
			makeToastAndShow(context, "密码必须包含数字", Toast.LENGTH_SHORT);
			return false;
		}
		if (digitCount == pwd.length()) {
			makeToastAndShow(context, "密码不能全为数字", Toast.LENGTH_SHORT);
			return false;
		}
		return true;
	}

	public static boolean checkComponentEmpty(TextView... views) {
		for (TextView textView : views) {
			if (StringUtils.isEmpty(textView.getText().toString())) {
				textView.requestFocus();
				return true;
			}
		}
		return false;
	}

	public static int getCurrentHour() {
		int hour = 0;
		Calendar now = Calendar.getInstance();
		hour = now.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	// 获得10天后的日期
	public static String getTenDayAfterDate() {
		Date d = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + 10);
		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy/M/dd");
		return matter1.format(now.getTime());
	}

	// 获得当前日期
	public static String getCurrentDate() {
		Date dt = new Date();
		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy/MM/dd");
		return matter1.format(dt);
	}

	// 获得当前时间
	public static String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return df.format(new Date());

	}

	private Map<String, String> timeMaps = new HashMap<String, String>();

	{
		timeMaps.put("最近一周", "1");
		timeMaps.put("最近一月", "2");
		timeMaps.put("最近三月", "3");
	}

	public String getTimeType(String cTime) {
		return timeMaps.get(cTime);
	}

	/**
	 * charge_state支付状态：1未付款，2等待付款，3 已付款
	 */
	private SparseArray<String> chargeState = new SparseArray<String>();

	{
		chargeState.put(1, "未付款");
		chargeState.put(2, "等待付款");
		chargeState.put(3, "已付款");
	}

	public String getChargeState(int flag) {
		return chargeState.get(flag);
	}

	/**
	 * cancel_order_state 退单状态：1未退单，2退单申请中，3退单成功, 4退单失败
	 */
	private SparseArray<String> cancelOrderState = new SparseArray<String>();

	{
		cancelOrderState.put(1, "未退单");
		cancelOrderState.put(2, "退单申请中");
		cancelOrderState.put(3, "退单成功");
		cancelOrderState.put(4, "退单失败 ");
	}

	public String getCancelOrderState(int flag) {
		return cancelOrderState.get(flag);
	}

	public static String toDouble(String singleTime) {
		String doubleTime = "";
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date date = sd.parse(singleTime);
			doubleTime = sd.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doubleTime;
	}

	/**
	 * 得到星通付款状态
	 */
	public static String getStarChargeState(String id) {
		if (id.equals("1"))
			return "星通未付款";
		else if (id.equals("2"))
			return "等待星通付款";
		else if (id.equals("3"))
			return "交易已完成";
		return "";
	}

	/**
	 * 得到星通付款状态
	 */
	public static String getChargeTypeName(String id) {
		if (id.equals("1"))
			return "支付宝";
		else if (id.equals("2"))
			return "银联";
		return "";
	}

	public static boolean isX86CPU() {
		try {
			FileReader fileReader = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fileReader);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("Intel(R) Core(TM)")) {
					return true;
				}

			}
			fileReader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 
	 * @param scoreString
	 * @return
	 */
	public static String getCreditLevel(String scoreString) {
		int scoreInt;
		try {
			scoreInt = Integer.parseInt(scoreString);
		} catch (Exception e) {
			scoreInt = 0;
		}
		return getCreditLevel(scoreInt);
	}

	/**
	 * 
	 * @param scoreInt
	 * @return
	 */
	public static String getCreditLevel(int scoreInt) {
		int score = 0;
		if (0 <= scoreInt && scoreInt <= 10) {
			score = scoreInt;
		} else if (10 < scoreInt && scoreInt <= 30) {
			score = (scoreInt - 10) / 2 + 10;
		} else if (30 < scoreInt && scoreInt <= 70) {
			score = (scoreInt - 30) / 4 + 20;
		} else if (70 < scoreInt && scoreInt <= 150) {
			score = (scoreInt - 70) / 8 + 30;
		} else if (150 < scoreInt && scoreInt <= 310) {
			score = (scoreInt - 150) / 16 + 40;
		} else if (310 < scoreInt && scoreInt <= 1878) {
			score = (scoreInt - 310) / 32 + 50;
		} else if (scoreInt > 1878) {
			score = 99;
		}
		return String.valueOf("信用 LV" + score);
	}

	public static String hideIdCard(String str) {
		if (str == null) {
			return "";
		}
		if (str.length() < 18) {
			return str;
		}
		StringBuffer last = new StringBuffer();
		String a = str.substring(0, 10);
		String b = str.substring(16, 18);
		last.append(a);
		last.append("******");
		last.append(b);
		return last.toString();

	}

	/**
	 * 
	 * @param price
	 * @return
	 */
	public static String getPriceFormat(double price) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.setMaximumFractionDigits(2);
		df.applyPattern("0.00");
		return df.format(price);
	}

	/**
	 * 检查当前网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}


	/**
	 * 一位小数 变 二位小数
	 * @param str
	 * @return
	 */
	public static String StringTo2decimal(String str)
	{
//        str = "10.1";

		String[] a =str.split("\\.");
		if(a[1].length()==1){
			a[1]= a[1]+"0";
		}
		return a[0]+"."+a[1];

	}

	/**
	 * js正则表达式验证车牌号//
	 * 创建正则表达式var re=/^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
	 * if(window.document.getElementById("id").value.search(re)==-1)
	 * { alert("输入的车牌号格式不正确");
	 * return false;}
	 * 下面是对上面正则表达式的简单分析
	 * ^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$
	 * ^[\u4e00-\u9fa5]{1}
	 * 代表以汉字开头并且只有一个，这个汉字是车辆所在省的简称
	 * [A-Z]{1}代表A-Z的大写英文字母且只有一个，代表该车所在地的地市一级代码
	 * [A-Z_0-9]{5}代表后面五个数字是字母和数字的组合。
	 * ^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{3}$如果是这种格式的话，英文字母大小写都可以。但是最好在后台进行转换
	 */


	/**
	 * 检测车牌号码是否合法
	 *
	 * @return
	 */
	public static boolean checkCarNum(String value) {
		// String regExp = "^[1]([3][0-9]{1}|59|58|88|89|50|56)[0-9]{8}$";
		// regExp =
		// "^(130|131|132|133|134|135|136|137|138|139|145|147|150|151|152|153|155|157|156|158|159|176|177|178|180|181|188|182|183|184|185|186|189)\\d{8}$";
		String regExp = "^[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(value);
		return m.find();
	}

}