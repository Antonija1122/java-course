package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command help implements ShellComand.
 * 
 * @author antonija
 *
 */
public class HelpCommand implements ShellCommand {

	/**
	 * Command help can start with no arguments or only one element. If command
	 * starts with no arguments, list of names of all supported commands is printed. If
	 * started with single argument, it prints name and the description of
	 * selected command (or prints appropriate error message if no such command
	 * exists).
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();

		String[] line = Utility.separateArguments(arguments);
		if (line.length > 1) {
			env.writeln("Illegal input for help command");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

		SortedMap<String, ShellCommand> commands = env.commands();

		if (line.length == 0) {
			for (String name : commands.keySet()) {
				env.writeln(name);
			}
		} else if (line.length == 1) {
			if (commands.containsKey(line[0])) {
				env.writeln(line[0]);
				List<String> description = commands.get(line[0]).getCommandDescription();
				for (String descriptLine : description) {
					env.writeln(descriptLine);
				}
			} else {
				env.writeln("Unrecognized command -> " + line[0]);
			}
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Getter method for command name
	 */
	@Override
	public String getCommandName() {
		return "help";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command help can start with no arguments or only one element.");
		Commandescritpion.add("If command starts with no arguments, list of names of all supported commands is printed.");
		Commandescritpion.add("If started with single argument, it prints name and the description of selected command");
		Commandescritpion.add("If input command does not exist apropriate message is printed.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
