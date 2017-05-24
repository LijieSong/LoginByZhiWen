package com.example.bjlz.loginbyzhiwen.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
//import android.support.multidex.MultiDex;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.tools.tools.PreferencesUtils;
import com.umeng.socialize.PlatformConfig;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：loginbyzhiwen
 * 类描述：MyApplication 全局变量
 * 创建人：slj
 * 创建时间：2016-6-27 20:06
 * 修改人：slj
 * 修改时间：2016-6-27 20:06
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class MyApplication extends Application {

    public static Map<String, Long> map;//用来存放倒计时的时间
    private List<Activity> activityList = new LinkedList<Activity>();//activity管理器
    private static Handler mainHandler = new Handler();
    private static MyApplication mApplication;//application
    public static RequestQueue queue;//请求队列
    private static Context mContext;//全局变量
    public static final String HAS_FINGERPRINT_API = "hasFingerPrintApi";

    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mContext = this;
        //分包
//        MultiDex.install(getBaseContext());
        //请求
        queue = Volley.newRequestQueue(this);
        //初始化友盟分享
        //各个平台的配置，建议放在全局Application或者程序入口
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        boolean containKey = PreferencesUtils.isContainKey(this, HAS_FINGERPRINT_API);
        if (containKey) { // 检查是否存在该值，不必每次都通过反射来检查
            return;
        }
        try {
            Class.forName("android.hardware.fingerprint.FingerprintManager"); // 通过反射判断是否存在该类
            PreferencesUtils.putBoolean(this,HAS_FINGERPRINT_API, true);
        } catch (ClassNotFoundException e) {
            PreferencesUtils.putBoolean(this,HAS_FINGERPRINT_API, false);
            e.printStackTrace();
        }
    }

    /**
     * 获取请求队列
     * @return
     */
    public static RequestQueue getHttpQueue() {
        return queue;
    }
    /**
     * 获取全局变量
     *
     * @return
     */
    public static MyApplication getInstance() {
        return mApplication;
    }

    /**
     * 获取上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取主线程的handler
     * @return
     */
    public static Handler getHandler() {
        return mainHandler;
    }

    /**
     * 无参跳转
     *
     * @param activity
     * @param clazz
     */
    public static <T> void startActivity(Activity activity, Class<T> clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.cuntomanim, R.anim.cunexitmanim);
    }
    /**
     * 无参跳转
     *
     * @param context
     * @param clazz
     */
    public static <T> void startActivity(Context context, Class<T> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    /**
     * 有参跳转  传递bundle
     *
     * @param activity
     * @param clazz
     * @param bundle
     */
    public static <T> void startActivity(Activity activity, Class<T> clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtra("bundle", bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.cuntomanim, R.anim.cunexitmanim);
    }
    /**
     * 有参跳转  传递bundle
     *
     * @param context
     * @param clazz
     * @param bundle
     */
    public static <T> void startActivity(Context context, Class<T> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    /**
     * 开启服务
     *
     * @param activity
     * @param serviceClazz
     */
    public static <T> void startService(Activity activity, Class<T> serviceClazz) {
        Intent intent = new Intent(activity, serviceClazz);
        activity.startService(intent);
    }

    /**
     * 停止服务
     *
     * @param activity
     * @param serviceClazz
     */
    public static <T> void stopService(Activity activity, Class<T> serviceClazz) {
        Intent intent = new Intent(activity, serviceClazz);
        activity.stopService(intent);

    }

    /**
     * 带请求码的跳转方式
     *
     * @param activity
     * @param clazz
     * @param requestCode
     */
    public static <T> void startActivityForResult(Activity activity, Class<T> clazz,
                                                  int requestCode) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.cuntomanim, R.anim.cunexitmanim);
    }

    /**
     * 添加Activity到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 关闭所有activity
     */
    public void close() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityList = new LinkedList<Activity>();
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        for (Activity ac : activityList) {
            if (ac.equals(activity)) {
                activityList.remove(ac);
                break;
            }
        }
    }

    /**
     * 获取app的名字
     * @param pID 进程ID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
