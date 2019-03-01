package com.purplepuli;

import com.purplepuli.core.ConfigurationManager;
import com.purplepuli.core.PPContext;
import com.purplepuli.core.PPException;
import com.purplepuli.core.Status;
import com.purplepuli.logging.LogManager;
import com.purplepuli.logging.LogSeverity;
import com.purplepuli.logging.Logger;

public class PPMain {
	
	
	public static final String VERSION = "0.1"; 
	public static final String JAVA_HOME = "PP_JAVA";
	public static final String INSTANCE_HOME_PREFIX = "PP_HOME_";
	public static final int INSTANCE_NAME_LENGTH_MIN = 3;
	public static final int INSTANCE_NAME_LENGTH_MAX = 10;
	public static final String LOG_FOLDER = "log";
	
	
	private static PPContext context;

	public static void main(String[] args) {
		Logger startupLogger = null;
		if (args != null) {
			
			
			if (args.length != 1) {
				System.err.println("Exactly 1 argument is needed, the system id");
				System.exit(-1);
			}
			
			String sysId = args[0];
			if (!Utils.validateSystemId(sysId)) {
				 System.err.println("Ambigiuous system id. Minimum length: "+ INSTANCE_NAME_LENGTH_MIN +" maximum length: " + INSTANCE_NAME_LENGTH_MAX + " . It can contain only numbers and alphabetic characters.");
				
				 System.exit(-1);
			} else {
				System.err.println(sysId);
			}
			//System.err.println("ETest3");
			context = new PPContext(sysId);
			context.put(PPContext.STATUS, Status.STARTING);
			
			
			startupLogger = LogManager.getInstanceWithContext(context).getStartupLogger();
			startupLogger.log(LogSeverity.INFO, PPMain.class, "main", "Starting Purple Puli version " + VERSION + " at " + Utils.getTimestamp(null));
			
		} else {
			System.err.println("Unable to start the system. Invalid system id.");
			System.exit(-1);
		}
		ConfigurationManager cm = null;
		try {
			cm = ConfigurationManager.getInstance(context);
			cm.initialLoad();
		} catch (PPException e) {
			System.err.println(e.getMessage());
			System.err.println(e.getAdditionalInfo());
			e.printStackTrace();
			System.exit(-1);
		}
		
		startupLogger.log(LogSeverity.INFO, PPMain.class, "main", "Purple Puli is started at " + Utils.getTimestamp(null));
		context.put(PPContext.STATUS, Status.RUNNING);
		PPMainThread mainThread =  new PPMainThread();
		mainThread.run();
		startupLogger.log(LogSeverity.INFO, PPMain.class, "main", "Purple Puli is stopping at " + Utils.getTimestamp(null));
		context.put(PPContext.STATUS, Status.STOPPING);
		startupLogger.log(LogSeverity.INFO, PPMain.class, "main", "Purple Puli is stopped at " + Utils.getTimestamp(null));
		LogManager.getInstanceWithContext(context).stopStartupLogger();
	}

}
