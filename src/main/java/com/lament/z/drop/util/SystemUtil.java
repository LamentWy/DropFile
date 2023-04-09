package com.lament.z.drop.util;


import org.apache.commons.lang3.SystemUtils;

public class SystemUtil {

	public static String OS_NAME = System.getProperty("os.name");
	public static boolean IS_MAC = OS_NAME.contains("Mac");
	public static boolean IS_WINDOWS = OS_NAME.contains("Windows");

	public static String USER_DIR = System.getProperty("user.home");
	/**
	 * @return Mac/Windows/Unknown
	 * */
	public static String getOSType(){
		String osType = "Unknown";
		if (IS_MAC){
			osType = "Mac";
		}
		if (IS_WINDOWS){
			osType = "Windows";
		}
		return osType;
	}




}
