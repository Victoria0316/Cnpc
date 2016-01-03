package com.bluemobi.cnpc.network.exception;

import com.android.volley.VolleyError;

public class CustomResponseError extends VolleyError{

	private static final long serialVersionUID = 304282614767253174L;
	
	private boolean isToast = true;
	private String errMsg;
	private int errCode;
	
	public CustomResponseError(String errMsg){
		this.errMsg = errMsg;
	}
	public CustomResponseError(String errMsg, boolean isToast){
		this.errMsg = errMsg;
		this.isToast = isToast;
	}
	public CustomResponseError(String errMsg, boolean isToast, int errCode){
		this.errMsg = errMsg;
		this.isToast = isToast;
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public boolean isToast() {
		return isToast;
	}
	public void setToast(boolean isToast) {
		this.isToast = isToast;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	
}
