package com.purplepuli.client.commands;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.purplepuli.PPMain;
import com.purplepuli.Utils;
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
			 if (parameters == null) {
				System.out.println("System id is missing. Enter a maximum 10 character long system id. It should start with a letter, and is can contain letters and numbers, but not special characters");
			
				return;
			}
			 if (parameters.contains(" ")) {
				 //the parameter contains more than one string
				System.out.println("Ambigiuous system id. Onle one parameter can be used with start command.");
				
				return;
			}
			 if (!validateSystemId(parameters)) {
				 System.out.println("Ambigiuous system id. Minimum length: "+ PPMain.INSTANCE_NAME_LENGTH_MIN +" maximum length: " + PPMain.INSTANCE_NAME_LENGTH_MAX + " . It can contain only numbers and alphabetic characters.");
				
				return;
			}
			 //getting environment value
			 String instanceHome = System.getenv(PPMain.INSTANCE_HOME_PREFIX + parameters.toUpperCase());
			if (instanceHome == null) {
				System.out.println("Environmental variable "+ PPMain.INSTANCE_HOME_PREFIX + parameters.toUpperCase()  +" is missing. Create the variable, it should contain the root of the data folder of the instance");
				
				return;
			}
			if(!checkOrCreateFolders(instanceHome)) {
				//prerequisites are not ready for starting the instance
				System.out.println("uable to start the server");
				return;
			}
			 try {
				//Process process = new ProcessBuilder(actualJavaHome + "\\bin\\java.exe", "-cp", url,  "com.purplepuli.PPMain" , parameters).start();
				ProcessBuilder pb = new ProcessBuilder(actualJavaHome + "\\bin\\java.exe", "-cp", url,  "com.purplepuli.PPMain" , parameters);
				//System.out.println("redirecting output");
				redirectInputStream(pb, instanceHome);
				System.out.println("start process");
				pb.start();
				//CommandsUtils.printLines(process.getInputStream());
				//CommandsUtils.printLines(process.getErrorStream());
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			//Process process = new ProcessBuilder(actualJavaHome + "\\bin\\java\\java.exe","-classpath",urls.join(":"), PPMain).start();
		} else {
			System.out.println("Environment variable " + PPMain.JAVA_HOME + " missing.");
			context.put(PPClient.CONTEXT_EXIT, true);
		}
		

	}
	

	private void redirectInputStream(ProcessBuilder pb, String instanceHome) throws IOException {
		Path logPath = Paths.get(instanceHome + File.separator + PPMain.LOG_FOLDER);
		
		File logfile = new File(logPath.toString() + File.separator + PPMain.STARTUP_LOG + "_" + Utils.getTimestamp(null) + ".log");
		System.out.println("create logfile: " + logfile.getPath());
		logfile.createNewFile();
		PrintStream o = new PrintStream(logfile);
		pb.redirectError(logfile);
		pb.redirectOutput(logfile);
		
	}

	private boolean checkOrCreateFolders(String instanceHome) {
		Path instanceHomePath = Paths.get(instanceHome);
		if (Files.notExists(instanceHomePath)) {
			//create the root directory of the instance, if it's missing
			try {
				Files.createDirectory(instanceHomePath);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		Path logPath = Paths.get(instanceHome + File.separator + PPMain.LOG_FOLDER);
		if (Files.notExists(logPath)) {
			//create the root directory of the instance, if it's missing
			try {
				Files.createDirectory(logPath);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
		
	}

	private boolean validateSystemId(String systemId) {
		if (systemId == null || systemId.length() < PPMain.INSTANCE_NAME_LENGTH_MIN || systemId.length() > PPMain.INSTANCE_NAME_LENGTH_MAX) {
			//the id is toos hort or too long
			return false;
		}
		if (!systemId.matches("^(.*[a-zA-Z0-9])$")) {
			//the id contains special characters
			return false;
		}
		return true;
	}

	@Override
	public void printHelp() {
		System.out.println("Starts a Purple Puli instance");
		System.out.println("Usage: start <instance name>");
	}

	
}
