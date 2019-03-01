package com.purplepuli.core;

public class PPException extends Exception {

	private String additionalInfo;

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public PPException(String arg0, Throwable arg1, String additionalInfo) {
		super(arg0, arg1);
		this.additionalInfo = additionalInfo;
	}

	public PPException(String arg0, String additionalInfo) {
		super(arg0);
		this.additionalInfo = additionalInfo;
	}
	
	
}
