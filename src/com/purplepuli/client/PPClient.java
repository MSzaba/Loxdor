package com.purplepuli.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.purplepuli.PPMain;
import com.purplepuli.client.commands.PPClientCommandExit;
import com.purplepuli.client.commands.PPClientCommandHelp;
import com.purplepuli.client.commands.PPClientCommandJavaVersion;
import com.purplepuli.client.commands.PPClientCommandStart;
import com.purplepuli.client.commands.PPClientCommandStop;
import com.purplepuli.client.commands.PPClinetCommandUnknown;

public class PPClient {
	

	private static final long CONSOLE_INPUT_DELAY = 500;
	public static final String CONTEXT_EXIT = "CONTEXT_EXIT";
	public static final String COMMANDS = "COMMANDS";

	public static void main(String[] args) {
		System.out.println("Welcome to Purple Puli!\nVersion: " + PPMain.VERSION + "\nPlease enter your commands.");
		
		try(Scanner keyboard = new Scanner(System.in)) {
		
			String command;
			
			Map<String, Object> context = new HashMap<>();
			context.put(CONTEXT_EXIT, false);
			Map<String, PPClientCommand> commandList = fillCommandList(context);
			context.put(COMMANDS, commandList);
			
			while (!(boolean)context.get(CONTEXT_EXIT)) {
				command = keyboard.nextLine();
				PPClientCommand cc = processCommand(command, commandList );
				cc.execute();
				try {
					Thread.sleep(CONSOLE_INPUT_DELAY);
				} catch (InterruptedException e) {
					// There is no need to handle this
				}
				
			}
			
		}
		System.out.println("Exiting in process...");
		System.exit(0);
	}

	private static Map<String, PPClientCommand> fillCommandList(Map<String, Object> context) {
		Map<String,PPClientCommand> commands =  new HashMap<>();
		commands.put(PPClientCommandExit.EXIT, new PPClientCommandExit(context));
		commands.put(PPClinetCommandUnknown.UNKNOWN, new PPClinetCommandUnknown(context));
		commands.put(PPClientCommandStart.START, new PPClientCommandStart(context));
		commands.put(PPClientCommandStop.STOP, new PPClientCommandStop(context));
		commands.put(PPClientCommandJavaVersion.JAVA_VERSION, new PPClientCommandJavaVersion(context));
		commands.put(PPClientCommandHelp.HELP, new PPClientCommandHelp(context));
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
		if (st.hasMoreTokens()) {
			result.setParameters(command.substring(toCheck.length() + 1, command.length()));	
		} else {
			result.setParameters(null);
		}
		
		
		
		return result;
	}

}
