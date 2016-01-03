package com.bluemobi.cnpc.network.request;

import com.android.volley.Response;
import com.bluemobi.cnpc.activity.RegisteredActivity;
import com.bluemobi.cnpc.network.CnpcHttpJsonRequest;
import com.bluemobi.cnpc.network.response.GetValResponse;
import com.bluemobi.cnpc.network.response.RegisterResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/19.
 * P2_1注册_用户注册
 */
public class RegisterRequest extends CnpcHttpJsonRequest<RegisterResponse> {

    private static final String APIPATH = "mobi/adminuser/register";
    private  String pass	;//	固定值“n”
    private  String cellphone	;//	用户手机号
    private  String password	;//	用户密码
    private  String type	;//	固定值 “register”
    private  String validationCode	;//	用户的验证码
    private  String oilId	;//	用户选择的关注油品ID

    private String longitude ;//	否 	当前用户坐标经度
    private String latitude ;//		否 	当前用户坐标纬度
    private String addressid ; // 	当前用户所在城市ID，如果定位不到传上海市ID

    public RegisterRequest(Response.Listener<RegisterResponse> listener,
                         Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("cellphone", cellphone);
        map.put("password", password);
        map.put("type", "register");
        map.put("validationCode", validationCode);
        map.put("oilId", oilId);
        map.put("pass", "n");
        map.put("longitude", longitude == null? "":longitude);
        map.put("latitude", latitude == null? "":latitude);
        map.put("addressid", "n");
        return map;
    }

    @Override
    public Class getResponseClass() {
        return RegisterResponse.class;
    }

    public static String getAPIPATH() {
        return APIPATH;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public String getOilId() {
        return oilId;
    }

    public void setOilId(String oilId) {
        this.oilId = oilId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }
}
