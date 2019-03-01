package com.purplepuli.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class LogWriter {
	
	private File logfile;
	private PrintWriter writer;
	private LogFormatter formatter;

	public LogWriter(File logfile, LogFormatter formatter) throws IOException {
		this.logfile =logfile;
		if (!logfile.exists()) {
			logfile.createNewFile();
		}
		writer = new PrintWriter(new FileOutputStream(logfile, true));
		this.formatter = formatter;
	}
	
	

	public void close() {
		writer.close();
		
	}



	public void writeLog(LogItem item) {
		String stringToWrite = formatter.formatLogItem(item);
		writer.println(stringToWrite);
		writer.flush();
	}

}
