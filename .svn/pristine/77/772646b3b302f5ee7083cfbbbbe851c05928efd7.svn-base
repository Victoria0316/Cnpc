package com.bluemobi.cnpc.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.util.Constants;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);

        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	/**
	 * 0	成功	展示成功页面
	 -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
	 -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
	 * @param resp
	 */
	@Override
	public void onResp(BaseResp resp) {
//		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, resp.errStr +";code=" + String.valueOf(resp.errCode)));
//			builder.show();
//		}
		String code = "";
		if(resp instanceof SendAuth.Resp){//AUTH
			code = ((SendAuth.Resp) resp).code;
			Log.e("wangzhijun", "resp.code --》" +  code);
		}

		Log.e("wangzhijun", "resp.errStr--》" + resp.errStr);
		Intent intent = new Intent();
		intent.setAction("cn.abel.action.broadcast");
		//要发送的内容
		intent.putExtra("errCode", resp.errCode);

		intent.putExtra("code", code);
		//发送 一个无序广播
		sendBroadcast(intent);
		finish();

	}
}