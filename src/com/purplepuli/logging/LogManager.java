package com.purplepuli.logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.purplepuli.PPMain;
import com.purplepuli.Utils;
import com.purplepuli.core.PPContext;
import com.purplepuli.core.PPException;

public class LogManager {

	private static final String LOGFILE_EXTENSION = ".log";
	public static final String SYSTEM_LOG = "systemLog";
	public static final String STARTUP_LOG = "startupLog";
	private static LogManager instance;
	private static boolean startupLoggerIsProvided = false;
	private Map<String, Object> context;
	private Map<String, Object> parameters;
	private LoggerImplementation startupLogger;
	
	public static LogManager getInstanceWithContext(Map<String, Object> context)  {
		if (instance == null) {
			instance = new LogManager(context);
		}
		return instance;
	}
	
	public static LogManager getInstanceWithParameters(Map<String, Object> parameters)  {
		//the instance should be already created, when the startup logger was created.
		instance.setParameters(parameters);
		return instance;
	}
	
	private void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
		
	}

	public static LogManager getInstance()  {
		//the instance should be already created, when the startup logger was created.
		return instance;
	}

	private LogManager(Map<String, Object> context) {
		super();
		this.context = context;
	}
	
	//can be called only once during an execution
	public Logger getStartupLogger()  {
		if (!startupLoggerIsProvided) {
			startupLoggerIsProvided = true;
			return createStartupLogger();
		}
		return null;
	}
	
	public void stopStartupLogger() {
		startupLogger.stopLogging();
	}

	private Logger createStartupLogger() {
		String instanceHome = System.getenv(PPMain.INSTANCE_HOME_PREFIX + context.get(PPContext.SYSTEM_ID));
		context.put(PPContext.INSTANCE_HOME, instanceHome);
		if(!Utils.checkOrCreateFolders(instanceHome)) {
			//prerequisites are not ready for starting the instance
			System.err.println("unable to start the server");
			System.exit(-1);
			
			
		}
		Path logPath = Paths.get(instanceHome + File.separator + PPMain.LOG_FOLDER);
		File logfile = new File(logPath.toString() + File.separator + STARTUP_LOG + "_" + Utils.getTimestamp(null) + LOGFILE_EXTENSION);
		String logItemPrefix = "##";
		String logItemSeparator = " <##> ";
		String logItemFormat = LogFormatter.TIMESTAMP + LogFormatter.SEPARATOR + LogFormatter.MESSAGE;
		LogFormatter formatter =  new LogFormatter(logItemPrefix, logItemSeparator, logItemFormat);
		LogWriter startupWriter = null;
		try {
			startupWriter = new LogWriter(logfile, formatter);
		} catch (IOException e) {
			System.err.println("Unable to create logfile: " + e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		LogCollector startupCollector = new LogCollector(startupWriter);
		startupLogger = new LoggerImplementation(startupCollector);
		return startupLogger;
	}

	public Logger getLogger(Object configurationManager) throws PPException {
		String instanceHome = (String) context.get(PPContext.INSTANCE_HOME);
		Path logPath = Paths.get(instanceHome + File.separator + PPMain.LOG_FOLDER);
		File logfile = getActualLogfile(logPath);
		return null;
	}

	private File getActualLogfile(Path logPath) throws PPException {
		List<File> filesInFolder = null;
		try {
			filesInFolder = Files.walk(logPath).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
		} catch (IOException e) {
			throw new PPException("Unable to get the lost of logfiles", e, "log path: " + logPath.getFileName());
		}
		if (filesInFolder != null && !filesInFolder.isEmpty()) {
			File latest = null;
			long lastModifiedTime = 0;
			for (File file : filesInFolder) {
				String name = file.getName();
				if (name.startsWith(SYSTEM_LOG) && name.endsWith(LOGFILE_EXTENSION)) {
					if (latest == null) {
						latest = file;
						Path filePath = Paths.get(file.getAbsolutePath());
						try {
							BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
							lastModifiedTime = attr.lastModifiedTime().toMillis();
						} catch (IOException e) {
							throw new PPException("Unable to get attribute of the file", e, "File name: " + file.getName());
						}
					} else {
						Path filePath = Paths.get(file.getAbsolutePath());
						try {
							BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
							FileTime actualLastModifiedTime = attr.lastModifiedTime();
							if (actualLastModifiedTime.toMillis() > lastModifiedTime) {
								lastModifiedTime = actualLastModifiedTime.toMillis();
								latest = file;
							}
						} catch (IOException e) {
							throw new PPException("Unable to get attribute of the file", e, "File name: " + file.getName());
						}
					}
					
				}
			}
			return latest;
		
		}
		File logfile = new File(logPath.toString() + File.separator + SYSTEM_LOG + "_" + Utils.getTimestamp(null)+ LOGFILE_EXTENSION);
		try {
			logfile.createNewFile();
		} catch (IOException e) {
			throw new PPException("Unable to get create the file", e, "File name: " + logfile.getName());
		}
		return logfile;
	}
}
