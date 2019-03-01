package com.purplepuli.logging;

public class LogItem {
	private String logId;
	private long timestamp;
	private LogSeverity severity;
	private String locationName;
	private String subloc;
	private String message;

	public LogItem(String logId, long timestamp, LogSeverity severity, Class location, String subloc, String message) {
		this.logId = logId;
		this.timestamp = timestamp;
		this.severity = severity;
		this.locationName = location.getName();
		this.subloc = subloc;
		this.message = message;
	}

	public String getLogId() {
		return logId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public LogSeverity getSeverity() {
		return severity;
	}

	public String getLocation() {
		return locationName;
	}

	public String getSubloc() {
		return subloc;
	}

	public String getMessage() {
		return message;
	}

	
}
