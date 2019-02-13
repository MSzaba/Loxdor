package com.purplepuli;

public class PPMain {
	public static String VERSION = "0.1"; 
	public static String JAVA_HOME = "PP_JAVA";
	public static String INSTANCE_HOME_PREFIX = "PP_HOME_";
	public static int INSTANCE_NAME_LENGTH_MIN = 3;
	public static int INSTANCE_NAME_LENGTH_MAX = 10;
	public static String LOG_FOLDER = "log";
	public static String STARTUP_LOG = "startupLog";

	public static void main(String[] args) {
		System.out.println("Starting Purple Puli version " + VERSION + " at " + Utils.getTimestamp(null));
		System.out.println("Incoming parameters:");
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				String parameter = args[i];
				System.out.println(parameter);
			}
		}
		PPMainThread mainThread =  new PPMainThread();
		mainThread.run();
	}

}
