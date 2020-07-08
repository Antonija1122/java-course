package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command PopdCommand implements ShellCommand
 * 
 * @author antonija
 *
 */

public class PopdCommand implements ShellCommand {

	/**
	 * Key for stack in Shared data in input Environment
	 */
	private static final String STACK_KEY = "cdstack";

	/**
	 * Command popd excepts one argument. This command sets current directory to
	 * path from the top of stack saved in SharedData. If error occurs. Appropriate
	 * message is written to user.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] line = Utility.separateArguments(arguments);

		if (line.length > 0) {
			env.writeln("Illegal input for PopdCommand. Popd command excepts no arguments.");
		} else {
			Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK_KEY);
			if (stack != null && !stack.isEmpty()) {
				Path path = stack.pop();
				if (Files.exists(path) && Files.isDirectory(path)) {
					env.setCurrentDirectory(path);
				} else {
					env.writeln("Illegal input for path in PopdCommand -> " + path.toString());
				}
			} else {
				env.writeln("Error in PopdCommand. Stack is empty.");
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
		return "popd";
	}

	/**
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command popd excepts one argument.");
		Commandescritpion.add("This command sets current directory to path from the top of stack saved in SharedData");
		Commandescritpion.add("If error occurs. Appropriate message is written to user.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
