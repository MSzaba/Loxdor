package com.purplepuli.logging;

import java.util.HashMap;

public class LogCollector {
	
	private HashMap<Integer, LogItem> logItems; 
	private LogWriter logWriter;
	private LogId logId;
	

	public LogCollector(LogWriter logWriter) {
		super();
		logItems = new HashMap<>();
		this.logWriter = logWriter;
		logId = new LogId(16);
	}
	
	public synchronized void log(LogSeverity severity, Class location, String subloc, String message) {
		LogItem item = new LogItem(logId.getNextId() , System.currentTimeMillis() ,severity, location, subloc, message);
		logWriter.writeLog(item);
	}

	public void stopLogging() {
		logWriter.close();
		
	}

}
