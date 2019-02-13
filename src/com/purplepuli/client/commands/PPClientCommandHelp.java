package com.purplepuli.client.commands;

import java.util.Map;
import java.util.Set;

import com.purplepuli.client.PPClient;
import com.purplepuli.client.PPClientCommand;

public class PPClientCommandHelp extends PPClientCommand {

	public static String HELP = "Help";

	public PPClientCommandHelp(Map<String, Object> context) {
		super(context);
		this.name = HELP;
	}

	@Override
	public void execute() {
		if (parameters == null) {
			printBasicHelp();
		} else {
			if (parameters.contains(" ")) {
				 //the parameter contains more than one string
				System.out.println("Ambigiuous command name. Onle one parameter can be used with help command.");
				return;
			}
			callCommandSpecificHelp(parameters);
		}

	}

	private void callCommandSpecificHelp(String commandName) {
		Map<String, PPClientCommand>  commandList = (Map<String, PPClientCommand>) context.get(PPClient.COMMANDS);
		PPClientCommand result = commandList.values().stream().filter(c -> c.checkName(commandName)).findAny().orElse(commandList.get(PPClinetCommandUnknown.UNKNOWN));
		result.printHelp();
		
	}

	private void printBasicHelp() {
		System.out.println("The following commands can be used in the system:");
		Map<String, PPClientCommand>  commandList = (Map<String, PPClientCommand>) context.get(PPClient.COMMANDS);
		Set<String> keys = commandList.keySet();
		boolean first = true; 
		for (String commandName : keys) {
			if (!first) {
				System.out.print(", ");
				
			} else {
				first = false;
			}
				
			
			System.out.print(commandName);
			
		}
		System.out.println("\nFor further details type help <command name>");
		
	}

	@Override
	public void printHelp() {
		System.out.println("This command prints help about other commands");

	}

}
