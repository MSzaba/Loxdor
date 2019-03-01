package com.purplepuli.logging;

public interface Logger {

	public void log(LogSeverity severity, Class location, String subloc, String message);
}
