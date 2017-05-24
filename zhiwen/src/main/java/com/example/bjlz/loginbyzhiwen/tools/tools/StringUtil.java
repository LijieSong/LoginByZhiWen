package com.example.bjlz.loginbyzhiwen.tools.tools;

import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 是否是电子邮箱
	 * @param paramString
	 * @return
	 */
	public static boolean isEmail(String paramString) {
		return Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
				.matcher(paramString).matches();
	}
	/**
	 * 是否是身份证号
	 * @param paramString
	 * @return
	 */
	public static boolean isIDCard(String paramString) {
		return Pattern.compile("(\\d{14}[0-9xX])|(\\d{17}[0-9xX])")
				.matcher(paramString).matches();
	}
	/**
	 * 是否符合手机格式
	 * @param paramString
	 * @return
	 */
	public static boolean isMobileNO(String paramString) {
		return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher(paramString).matches();
	}
	/**
	 * 是否为数字
	 * @param paramString
	 * @return
	 */
	public static boolean isNumber(String paramString) {
		try {
			Float.parseFloat(paramString);
			return true;
		} catch (Exception localException) {
		}
		return false;
	}
	/**
	 * 是否是符合要求的密码格式
	 * @param paramString
	 * @return
	 */
	public static boolean isValidPassword(String paramString) {
		return Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$")
				.matcher(paramString).matches();
	}
	/**
	 * 是否是闰年
	 * @param year
	 * @return
	 */
	public static boolean isLeap(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
			return true;
		else
			return false;
	}
}