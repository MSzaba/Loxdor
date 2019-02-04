package com.purplepuli;

public class PPMain {
	public static String VERSION = "0.1"; 
	public static String JAVA_HOME = "PP_JAVA";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PPMainThread mainThread =  new PPMainThread();
		mainThread.run();
	}

}
