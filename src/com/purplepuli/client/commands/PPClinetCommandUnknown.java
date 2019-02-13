package com.purplepuli.client.commands;

import java.util.Map;

import com.purplepuli.client.PPClientCommand;

public class PPClinetCommandUnknown extends PPClientCommand {
	
	public static String UNKNOWN = "Unknown";

	public PPClinetCommandUnknown(Map<String, Object> context) {
		super(context);
		this.name = UNKNOWN;
	}

	@Override
	public void execute() {
		System.out.println("Unknown command. Please type HELP for command list.");

	}

	@Override
	public void printHelp() {
		System.out.println("Unable to print help for an unknown command");
		
	}

}
