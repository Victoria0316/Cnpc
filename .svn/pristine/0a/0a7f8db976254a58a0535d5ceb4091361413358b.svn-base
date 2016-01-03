package com.bluemobi.cnpc.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * LIUFY
 */
public class PreferencesService
{
    private Context mContext;

    static private PreferencesService instance;

    final private String PREFS_NAME = "CnpcAPP";

    static public PreferencesService getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new PreferencesService(context);
        }

        return instance;
    }

    private PreferencesService(Context context)
    {
        this.mContext = context;
    }

    public void clear()
    {
        SharedPreferences preferences = mContext.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear().commit();
    }

    public void save(String key, String value) throws Exception
    {
        SharedPreferences preferences = mContext.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void saveInt(String key, int value) throws Exception
    {
        SharedPreferences preferences = mContext.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void saveBool(String key, Boolean value) throws Exception
    {
        SharedPreferences preferences = mContext.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getPreferences(String key)
    {
        SharedPreferences preferences = mContext.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);

        String defValue = "";

        return preferences.getString(key, defValue);
    }

    public int getPreferencesInt(String key)
    {
        SharedPreferences preferences = mContext.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);

        int defValue = 0;
        return preferences.getInt(key, defValue);
    }

    public Boolean getPreferencesBool(String key)
    {
        SharedPreferences preferences = mContext.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);

        Boolean defValue = false;

        return preferences.getBoolean(key, defValue);
    }
}
