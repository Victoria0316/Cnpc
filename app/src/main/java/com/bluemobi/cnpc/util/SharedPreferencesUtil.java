package com.bluemobi.cnpc.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil
{
    public static final String SETTING_FILE = "setting_file";

    /**
     * 保存设置项到文件
     */
    public static void saveToFile(Context context, String key, String value)
    {
        SharedPreferences pref = context.getSharedPreferences(SETTING_FILE,
                Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取设置项
     */
    public static String getFromFile(Context context, String key)
    {
        SharedPreferences pref = context.getSharedPreferences(SETTING_FILE,
                Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    /**
     * 
     * @param context
     * @param key
     * @param defValue 默认值
     * @return
     */
    public static String getFromFileByDefault(Context context, String key,
            String defValue)
    {
        SharedPreferences pref = context.getSharedPreferences(SETTING_FILE,
                Context.MODE_PRIVATE);
        return pref.getString(key, defValue);
    }
}
