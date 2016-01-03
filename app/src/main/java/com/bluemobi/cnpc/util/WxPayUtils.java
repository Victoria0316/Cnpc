package com.bluemobi.cnpc.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.bluemobi.cnpc.network.model.WeChatPayBean;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by wangzhijun on 2015/8/20.
 */
public class WxPayUtils {
    private static IWXAPI api ;
    private static Activity mActivity;
    public static final String WX_APP_ID = "wxa8444c1a9f98d5f2";

    public static final String WX_APP_SECRER = "226ac566652934322c59160da0567ee5";

    public static final String WX_PARTNER_ID = "1272088301";

    private static final String API_KEY = "zokWcUBrXtBaYRR3KUbLqymFg8m2Up2E"; // wxa8444c1a9f98d5f2 对应的支付密钥

    public static void pay(Activity activity, WeChatPayBean obj) {
        mActivity = activity;
        api = WXAPIFactory.createWXAPI(activity, WX_APP_ID);

        api.registerApp(WX_APP_ID);
        if (doCheck()) {
            doPay(obj);
        }
    }


    private static boolean doCheck() {
        //TODO：api.isWXAppSupportAPI()
        if (!api.isWXAppInstalled()) {
            Toast.makeText(mActivity, "未安装微信", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private static void doPay(WeChatPayBean obj) {
        PayReq req = new PayReq();
//        req.appId = WX_APP_ID;
//        req.partnerId = WX_PARTNER_ID;
//        req.prepayId = obj.getPrepayid();
//        req.nonceStr = obj.getNoncestr();
//        req.timeStamp = String.valueOf(obj.getTimestamp());
//        req.packageValue = "Sign=Wxpay";//"Sign=" + packageValue;
//        req.sign = obj.getSign();
//		req.extData = "app data";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("appid", req.appId);
//        params.put("appkey", APP_KEY);
//        params.put("noncestr", req.nonceStr);
//        params.put("package", req.packageValue);
//        params.put("partnerid", req.partnerId);
//        params.put("prepayid", req.prepayId);
//        params.put("timestamp", req.timeStamp);
//        req.sign = buildSign(params);
//        Log.d("d", "调起支付的package串："+req.packageValue);
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//        req.sign = obj.getSign();
        req.appId = WX_APP_ID;
        req.partnerId = WX_PARTNER_ID;
        req.prepayId = obj.getPrepay_id();
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        req.packageValue = "SignWXPay";

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);
        req.extData = "app data"; // optional

        api.registerApp(WX_APP_ID);
        api.sendReq(req);

    }

    private static String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);
        Log.e("wangzhijun", "appSign--> " + sb.toString());

//        this.sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("wangzhijun", appSign);
        return appSign;
    }

    private static String genNonceStr() {
        Random random = new Random();
        return getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    public static String buildSign(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append(value);
            }
        }
        String sha1 = StringUtils.wxSha1(query.toString());
        return sha1;
    }

    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    private static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

}
