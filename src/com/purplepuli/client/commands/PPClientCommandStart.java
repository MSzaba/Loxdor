package com.purplepuli.client.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import com.purplepuli.PPMain;
import com.purplepuli.client.PPClient;
import com.purplepuli.client.PPClientCommand;

public class PPClientCommandStart extends PPClientCommand {

	public static String START = "Start";
	
	
	public PPClientCommandStart(Map<String, Object> context) {
		super(context);
		this.name = START;
	}

	@Override
	public void execute() {
		String actualJavaHome = System.getenv(PPMain.JAVA_HOME);
		if (actualJavaHome != null) {
			 URL[] urls = ((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs();
			 String url = urls[0].getFile();
//			 for (URL url1 : urls) {
//				 System.out.println(url1);
//				 System.out.println(url1.getPath());
//				 System.out.println(url1.getFile());
//			}
			 try {
				Process process = new ProcessBuilder(actualJavaHome + "\\bin\\java.exe", "-cp", url, "com.purplepuli.PPMain").start();
				CommandsUtils.printLines(process.getInputStream());
				CommandsUtils.printLines(process.getErrorStream());
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			//Process process = new ProcessBuilder(actualJavaHome + "\\bin\\java\\java.exe","-classpath",urls.join(":"), PPMain).start();
		} else {
			System.out.println("Environment variable " + PPMain.JAVA_HOME + " missing.");
			context.put(PPClient.CONTEXT_EXIT, true);
		}
		

	}
	


	
}
