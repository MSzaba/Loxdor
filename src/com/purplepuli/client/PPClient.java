package com.purplepuli.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.purplepuli.PPMain;
import com.purplepuli.client.commands.PPClientCommandExit;
import com.purplepuli.client.commands.PPClinetCommandUnknown;

public class PPClient {
	

	private static final long CONSOLE_INPUT_DELAY = 500;
	public static final String CONTEXT_EXIT = "CONTEXT_EXIT";

	public static void main(String[] args) {
		System.out.println("Welcome to Purple Puli!\nVersion: " + PPMain.VERSION + "\nPlease enter your commands.");
		
		try(Scanner keyboard = new Scanner(System.in)) {
		
			String command = keyboard.nextLine();
			
			Map<String, Object> context = new HashMap<>();
			context.put(CONTEXT_EXIT, false);
			Map<String, PPClientCommand> commandList = fillCommandList(context);
			
			while (!(boolean)context.get(CONTEXT_EXIT)) {
				PPClientCommand cc = processCommand(command, commandList );
				cc.execute();
				try {
					Thread.sleep(CONSOLE_INPUT_DELAY);
				} catch (InterruptedException e) {
					// There is no need to handle this
				}
				command = keyboard.nextLine();
			}
		}

	}

	private static Map<String, PPClientCommand> fillCommandList(Map<String, Object> context) {
		Map<String,PPClientCommand> commands =  new HashMap<>();
		commands.put(PPClientCommandExit.EXIT, new PPClientCommandExit(context));
		commands.put(PPClinetCommandUnknown.UNKNOWN, new PPClinetCommandUnknown(context));
		return commands;
	}

	private static PPClientCommand processCommand(String command, Map<String, PPClientCommand> commandList) {
		if (command == null  || command.isEmpty() ) {
			return commandList.get(PPClinetCommandUnknown.UNKNOWN);
		}
		StringTokenizer st = new StringTokenizer(command);
		String toCheck = st.nextToken();
		
		//using Lambda expression, streaming and filtering
		PPClientCommand result = commandList.values().stream().filter(c -> c.checkName(toCheck)).findAny().orElse(commandList.get(PPClinetCommandUnknown.UNKNOWN));
		
		
		
		return result;
	}

}
