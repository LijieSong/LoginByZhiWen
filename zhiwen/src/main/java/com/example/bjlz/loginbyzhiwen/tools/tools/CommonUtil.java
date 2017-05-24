package com.example.bjlz.loginbyzhiwen.tools.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.bjlz.loginbyzhiwen.application.MyApplication;

/**
 * 封装一些零碎的工具方法
 * @author Administrator
 *
 */
public class CommonUtil {
	/**
	 * 在主线程执行Runnable
	 * @param r
	 */
	public static void runOnUIThread(Runnable r){
		MyApplication.getHandler().post(r);
	}
	/**
	 * 在主线程执行的吐司
	 * @param string
	 */
	public static void runOnUIThreadToast(final Context context, final String string){
		MyApplication.getHandler().post(new Runnable() {
			@Override
			public void run() {
				ToastUtil.shortDiyToast(context,string);
			}
		});
	}
	/**
	 * 在主线程执行的吐司
	 * @param StrResId
	 */
	public static void runOnUIThreadToast(final Context context, final int StrResId){
		MyApplication.getHandler().post(new Runnable() {
			@Override
			public void run() {
				ToastUtil.shortDiyToastByRec(context,StrResId);
			}
		});
	}
	/**
	 * 将自己从父view移除
	 * @param child
	 */
	public static void removeSelfFromParent(View child){
		if(child!=null){
			ViewParent parent = child.getParent();
			if(parent instanceof ViewGroup){
				ViewGroup group = (ViewGroup) parent;
				group.removeView(child);//移除子view
			}
		}
	}

	/**
	 * 获取Resource对象
	 * @return Resources
	 */
	public static Resources getResources(){
		return MyApplication.getInstance().getResources();
	}

	/**
	 * 获取字符串的资源
	 * @param resId
	 * @return String
	 */
	public static String getString(int resId){
		return getResources().getString(resId);
	}

	/**
	 * 获取字符串数组的资源
	 * @param resId
	 * @return String[]
	 */
	public static String[] getStringArray(int resId){
		return getResources().getStringArray(resId);
	}

	/**
	 * 获取图片资源
	 * @param resId
	 * @return Drawable
	 */
	public static Drawable getDrawable(int resId){
		return getResources().getDrawable(resId);
	}
	/**
	 * 获取dp资源
	 * @param resId
	 * @return float
	 */
	public static float getDimen(int resId){
		//会自动将dp值转为像素值
		return getResources().getDimension(resId);
	}

	/**
	 * 获取颜色资源
	 * @param resId
	 * @return 颜色
	 */
	public static int getColor(int resId){
		return getResources().getColor(resId);
	}

	/**
	 * 获取deviceId
	 * @param context
	 * @return deviceId
     */
	public static String getDeviceId(Context context) {
		// 获取系统管理者
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 获取手机型号
	 * @return 手机型号
     */
	public static String getBuildBrand(){
		 String MIUI = "Xiaomi";
		 String FLYME = "Meizu";
		 String HUAWEI = "HUAWEI";
		String QIHU = "360";
		String result = null;
		String brand = android.os.Build.BRAND;//手机品牌
		String model = android.os.Build.MANUFACTURER;// CPU厂商

		if (brand.equals(MIUI) || model.equals(MIUI)){
			result = MIUI;
		} else if (brand.equals(FLYME)|| model.equals(FLYME)){
			result = FLYME;
		} else if (brand.equals(HUAWEI)|| model.equals(HUAWEI)){
			result = HUAWEI;
		}else if (brand.equals(QIHU)|| model.equals(QIHU)){
			result = QIHU;
		}
		return result;
	}
}
