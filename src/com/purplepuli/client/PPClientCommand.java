package com.purplepuli.client;

import java.util.Map;

public abstract class PPClientCommand {
	
	protected String name;
	protected Map<String, Object> context;
	protected String parameters;
	
	
	
	public PPClientCommand(Map<String, Object> context) {
		super();
		this.context = context;
	}

	abstract public void execute();
	
	public boolean checkName(String command)  {
		if (command != null && command.toLowerCase().equals(name.toLowerCase())) {
			return true;
		}
		return false;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
		
	}
	
	abstract public void printHelp();

}
