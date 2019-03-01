package com.purplepuli.client.commands;

import java.util.Map;

import com.purplepuli.PPMain;
import com.purplepuli.client.PPClient;
import com.purplepuli.client.PPClientCommand;

public class PPClientCommandJavaVersion extends PPClientCommand {
	
	public static String JAVA_VERSION = "JavaVersion";

	public PPClientCommandJavaVersion(Map<String, Object> context) {
		super(context);
		this.name = JAVA_VERSION;
	}

	@Override
	public void execute() {
		String actualJavaHome = System.getenv(PPMain.JAVA_HOME);
		if (actualJavaHome != null) {
			 
			 try {
				Process process = new ProcessBuilder(actualJavaHome + "\\bin\\java.exe", "-version").start();
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

	@Override
	public void printHelp() {
		System.out.println("Prints java version used to execute the client");
		System.out.println("Usage: JavaVersion");
		
	}
	
	


}
