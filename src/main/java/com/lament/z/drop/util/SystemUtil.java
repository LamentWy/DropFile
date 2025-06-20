package com.lament.z.drop.util;



public class SystemUtil {

	private SystemUtil() {
	}

	public static final String OS_NAME = System.getProperty("os.name");
	public static final boolean IS_MAC = OS_NAME.contains("Mac");
	public static final boolean IS_WINDOWS = OS_NAME.contains("Windows");

	public static final String USER_DIR = System.getProperty("user.home");
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
