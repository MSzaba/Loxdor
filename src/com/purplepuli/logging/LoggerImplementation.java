package com.purplepuli.logging;

public class LoggerImplementation implements Logger {
	
	private LogCollector startupCollector;

	public LoggerImplementation(LogCollector startupCollector) {
		this.startupCollector = startupCollector;
	}

	

	public void stopLogging() {
		startupCollector.stopLogging();
		
	}



	@Override
	public void log(LogSeverity severity, Class location, String subloc, String message) {
		startupCollector.log(severity, location, subloc, message);
		
	}
	
	
}
