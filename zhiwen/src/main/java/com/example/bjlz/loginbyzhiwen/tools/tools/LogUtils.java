package com.example.bjlz.loginbyzhiwen.tools.tools;

import android.util.Log;

/**
 * Created by slj  on 2016/5/11.
 */
public class LogUtils {

    private static final boolean isLog = true;
    private static final String TAG = "slj";
    public static void debug(Object paramObject, String paramString)
    {
        if (isLog == true){
            Log.d(paramObject.getClass().getSimpleName(), paramString);
        }
    }

    public static void debug(String paramString)
    {
        if (isLog == true){
            Log.d(TAG, paramString);
        }
    }

    /**
     * 自定义tag,打印debug
     * @param tag
     * @param paramString
     */
    public static void debug(String tag , String paramString)
    {
        if (isLog == true){
            Log.d(tag, paramString);
        }
    }

    public static void debug(String paramString, Throwable paramThrowable)
    {
        if (isLog == true){
            Log.d(TAG, paramString, paramThrowable);
        }
    }

    public static void error(Object paramObject, String paramString)
    {
        if (isLog == true){
            Log.e(paramObject.getClass().getSimpleName(), paramString);
        }
    }

    public static void error(String paramString)
    {
        if (isLog == true){
            Log.e(TAG, paramString);
        }
    }

    /**
     * 自定义tag 打印error
     * @param tag
     * @param paramString
     */
    public static void error(String tag , String paramString)
    {
        if (isLog == true){
            Log.e(tag, paramString);
        }
    }

    public static void error(String paramString, Throwable paramThrowable)
    {
        if (isLog == true){
            Log.e(TAG, paramString, paramThrowable);
        }
    }

    public static void info(Object paramObject, String paramString)
    {
        if (isLog == true){
            Log.i(paramObject.getClass().getSimpleName(), paramString);
        }
    }
    public static void info( String paramString)
    {
        if (isLog == true){
            Log.i(TAG, paramString);
        }
    }

    /**
     * 自定义tag 打印info
     * @param tag
     * @param paramString
     */
    public static void info(String tag , String paramString )
    {
        if (isLog == true){
            Log.i(tag, paramString);
        }
    }
}
