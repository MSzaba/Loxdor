package com.purplepuli;

import java.time.LocalDateTime;

public class Utils {

	public static String getTimestamp(LocalDateTime timestamp) {
		if (timestamp == null) {
			timestamp = LocalDateTime.now();
		}
		//
		return "" + timestamp.getYear() + timestamp.getMonth() + timestamp.getDayOfMonth() + "_" + timestamp.getHour() + timestamp.getMinute() + timestamp.getSecond() + "_"+ timestamp.getNano();
	}
}
