package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command DropdCommand implements ShellCommand
 * 
 * @author antonija
 *
 */
public class DropdCommand implements ShellCommand {

	/**
	 * Key for stack in Shared data in input Environment
	 */
	private static final String STACK_KEY = "cdstack";

	/**
	 * Command dropd excepts no arguments. This command drops last directory stored
	 * in SharedData. Current Directory remains unchanged. If error occurs.
	 * Appropriate message is written to user.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] line = Utility.separateArguments(arguments);

		if (line.length > 0) {
			env.writeln("Illegal input for DropdCommand");
		} else {
			Stack<Path> stack = (Stack) env.getSharedData(STACK_KEY);
			if (stack != null && !stack.isEmpty()) {
				stack.pop();
			} else {
				env.writeln("Error in DropdCommand. Stack is empty.");
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
		return "dropd";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command dropd excepts no arguments.");
		Commandescritpion
				.add("This command drops last directory stored in SharedData. Current Directory remains unchanged.");
		Commandescritpion.add("If error occurs. Appropriate message is written to user.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
