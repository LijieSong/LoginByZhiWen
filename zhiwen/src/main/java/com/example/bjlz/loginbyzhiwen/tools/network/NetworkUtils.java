package com.example.bjlz.loginbyzhiwen.tools.network;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络判断工具类
 */
public class NetworkUtils
{
  /**
   * 判断网络是否连接
   *
   * @param context
   * @return
   */
  public static boolean isConnected(Context context)
  {
    ConnectivityManager connectivity = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);

    if (null != connectivity)
    {
      NetworkInfo info = connectivity.getActiveNetworkInfo();
      if (null != info && info.isConnected())
      {
        if (info.getState() == NetworkInfo.State.CONNECTED)
        {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 是否是 wap方式连接
   * @param paramContext
     * @return
     */
  public static boolean isMobileConnected(Context paramContext)
  {
    boolean bool = false;
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(0);
      bool = false;
      if (localNetworkInfo != null)
        bool = localNetworkInfo.isAvailable();
    }
    return bool;
  }

  /**
   * 是否是net方式连接
   * @param paramContext
   * @return
     */
  public static boolean isNetworkConnected(Context paramContext)
  {
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
      if (localNetworkInfo != null)
        return localNetworkInfo.isAvailable();
    }
    return false;
  }

  /**
   * 是否是wifi连接
   * @param paramContext
     * @return
     */
  public static boolean isWifiConnected(Context paramContext)
  {
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(1);
      if (localNetworkInfo != null)
        return localNetworkInfo.isAvailable();
    }
    return false;
  }

  /**
   * 判断是否是wifi连接
   * @param context
   * @return
     */
  public static boolean isWifi(Context context)
  {
    ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (cm == null)
      return false;
    return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
  }

  /**
   * 是否是wap方式连接
   * @param context
   * @return
     */
  public static boolean isMobile(Context context)
  {
    ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (cm == null)
      return false;
    return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;
  }

  /**
   * 打开wifi设置界面
   * @param activity
     */
  public static void openSetting(Activity activity)
  {
    Intent intent = new Intent("/");
    ComponentName cm = new ComponentName("com.android.settings",
            "com.android.settings.WirelessSettings");
    intent.setComponent(cm);
    intent.setAction("android.intent.action.VIEW");
    activity.startActivityForResult(intent, 0);
  }
}