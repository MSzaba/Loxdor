package com.purplepuli.client.commands;

import java.util.Map;

import com.purplepuli.client.PPClient;
import com.purplepuli.client.PPClientCommand;

public class PPClientCommandExit extends PPClientCommand {
	
	public static String EXIT = "Exit";

	public PPClientCommandExit(Map<String, Object> context) {
		super(context);
		this.name = EXIT;
	}

	@Override
	public void execute() {
		System.out.println("Exiting Purple Puli");
		context.put(PPClient.CONTEXT_EXIT, true);
	}

}
