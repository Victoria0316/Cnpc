package com.bluemobi.cnpc.base.utils;

import android.util.Log;

import java.lang.reflect.Array;

/**
 * Log包装类
 * 
 * 
 */
public class Logger {

    /**
     * 开发阶段将DEBUGABLE设置为true，便于调试。
     */
    private static final boolean DEBUGABLE = true;

    private static final String SPLITTER = " ";

    /**
     * 构造器
     */
    private Logger() {
    }

    /**
     * 生成打印的消息
     * 
     * @param msgs
     * @return
     */
    private static String makeMsg(Object... msgs) {
        StringBuilder msg = new StringBuilder();

        for (Object m : msgs) {
            if (m != null) {
                if (m.getClass().isArray()) {
                    int length = Array.getLength(m);
                    for (int i = 0; i < length; i++) {
                        msg.append(Array.get(m, i));
                    }
                } else {
                    msg.append(m.toString());
                }
                msg.append(SPLITTER);
            }
        }
        return msg.toString();
    }

    /**
     * 打印调试级别的log
     * 
     * @param tag
     * @param msgs
     */
    public static void d(String tag, Object... msgs) {

        if (DEBUGABLE) {
            Log.d(tag, makeMsg(msgs));
        }
    }

    /**
     * 打印调试级别的log
     * 
     * @param tag
     * @param msgs
     */
    public static void dl(String tag, Object... msgs) {

        if (DEBUGABLE) {
            String s = makeMsg(msgs);
            int length = 1024*3;
            for (int i = 0; i <= s.length() / length; i++) {
                int start = i * length;
                int end = (i + 1) * length;
                end = end > s.length() ? s.length() : end;
                Log.d(tag, s.substring(start, end));
            }
        }
    }

    /**
     * 打印错误级别的log
     * 
     * @param tag
     * @param msgs
     */
    public static void e(String tag, Object... msgs) {

        Log.e(tag, makeMsg(msgs));

    }

    /**
     * 打印信息级别的log
     * 
     * @param tag
     * @param msgs
     */
    public static void v(String tag, Object... msgs) {
        if (DEBUGABLE) {
            Log.v(tag, makeMsg(msgs));
        }
    }

    /**
     * 打印调试级别的log
     * 
     * @param tag
     * @param t
     * @param msgs
     */
    public static void d(String tag, Throwable t, Object... msgs) {
        if (DEBUGABLE) {
            Log.d(tag, makeMsg(msgs), t);
        }
    }

    /**
     * 打印错误级别的log
     * 
     * @param tag
     * @param t
     * @param msgs
     */
    public static void e(String tag, Throwable t, Object... msgs) {
        Log.e(tag, makeMsg(msgs), t);
    }

    /**
     * 打印消息级别的log
     * 
     * @param tag
     * @param t
     * @param msgs
     */
    public static void v(String tag, Throwable t, Object... msgs) {
        if (DEBUGABLE) {
            Log.v(tag, makeMsg(msgs), t);
        }
    }
}
