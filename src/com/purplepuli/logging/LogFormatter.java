package com.purplepuli.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LogFormatter {
	
	//private static String LOG_ITEM_PREFIX = "##";
	public static final String TIMESTAMP = "<TIMESTAMP>";
	public static final String MESSAGE = "<MESSAGE>";
	public static final String SEPARATOR = "<SEPARATOR>";
	
	private List<String> format;
	private String logItemPrefix;
	private String logItemSeparator;
	
	public LogFormatter(String logItemPrefix, String logItemSeparator, String logItemFormat) {
		super();
		this.format = createElementOrder(logItemFormat);
		this.logItemPrefix = logItemPrefix;
		this.logItemSeparator = logItemSeparator;
	}

	private List<String> createElementOrder(String logItemFormat) {
		ArrayList<String> retVal = new ArrayList<>();
		StringTokenizer st =  new StringTokenizer(logItemFormat, (">"));
		while (st.hasMoreElements()) {
			String token = st.nextToken();
			if (TIMESTAMP.startsWith(token)) {
				retVal.add(TIMESTAMP);
				continue;
			}
			if (MESSAGE.startsWith(token)) {
				retVal.add(MESSAGE);
				continue;
			}
			if (SEPARATOR.startsWith(token)) {
				retVal.add(SEPARATOR);
				continue;
			}
		}
		
		return retVal;
	}

	public String formatLogItem(LogItem logItem) {
		StringBuffer logItemAsString = new StringBuffer();
		logItemAsString.append(logItemPrefix);
		 for (String element : format) {
			switch (element) {
			case TIMESTAMP:
				logItemAsString.append(logItem.getTimestamp());
				break;
			case MESSAGE:
				logItemAsString.append(logItem.getMessage());
				break;
			case SEPARATOR:
				logItemAsString.append(logItemSeparator);
				break;
			default:
				break;
			}
		}
		return logItemAsString.toString();
	}
	
	public LogItem convertToLogItem(String text) {
		StringTokenizer st =  new StringTokenizer(text, logItemSeparator.substring(logItemPrefix.length()));
		long timestamp = 0;
		String message = "";
		String logId = "";
		LogSeverity severity = LogSeverity.DEBUG;
		Class location = null;
		String subloc = "";
		for (int i = 0; i < format.size(); i++) {
			String actualElement = format.get(i);
			switch (actualElement) {
			case TIMESTAMP:
				String token = st.nextToken();
				if (token != null && token.length() > 0) {
					timestamp = Long.parseLong(token);
				}
				
				break;
			case MESSAGE:
				message = st.nextToken();
				break;
			case SEPARATOR:
				
				break;
			default:
				break;
			}
		}
		
		LogItem retVal = new LogItem(logId, timestamp, severity, location, subloc, message);
		return retVal;
	}
	

}
