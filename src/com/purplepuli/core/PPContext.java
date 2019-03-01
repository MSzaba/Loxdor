package com.purplepuli.core;

import java.util.HashMap;

public class PPContext extends HashMap<String, Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2932597524536479839L;
	public static final String SYSTEM_ID = "SYSTEM_ID";
	public static final String STATUS = "STATUS";
	public static final String INSTANCE_HOME = "INSTANCE_HOME";
	
	//private String systemId;
	
	public PPContext(String sysId) {
		put(SYSTEM_ID, sysId);
		
	}

	
	
}
