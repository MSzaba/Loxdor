package com.purplepuli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Utils {

	public static String getTimestamp(LocalDateTime timestamp) {
		if (timestamp == null) {
			timestamp = LocalDateTime.now();
		}
		//
		return "" + timestamp.getYear() + timestamp.getMonthValue() + timestamp.getDayOfMonth() + "_" + timestamp.getHour() + timestamp.getMinute() + timestamp.getSecond(); //+ "_"+ timestamp.getNano();
	}
	
	public static boolean validateSystemId(String systemId) {
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
	
	public static boolean checkOrCreateFolders(String instanceHome) {
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
}
