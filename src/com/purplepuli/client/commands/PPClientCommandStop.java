package com.purplepuli.client.commands;

import java.util.Map;

import com.purplepuli.client.PPClientCommand;

public class PPClientCommandStop extends PPClientCommand {
	
	public static String STOP = "Stop";

	public PPClientCommandStop(Map<String, Object> context) {
		super(context);
		this.name = STOP;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printHelp() {
		System.out.println("Stops a Purple Puli instance");
		System.out.println("Usage: stop <instance name>");
		
	}

}
