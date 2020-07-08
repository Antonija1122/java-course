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
 * Command ListdCommand implements ShellCommand
 * 
 * @author antonija
 *
 */
public class ListdCommand implements ShellCommand {

	/**
	 * Key for stack in Shared data in input Environment
	 */
	private static final String STACK_KEY = "cdstack";

	/**
	 * Command lisd excepts no arguments. This command writes all directories stored
	 * in SharedData. If error occurs. Appropriate message is written to user.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] line = Utility.separateArguments(arguments);

		if (line.length > 0) {
			env.writeln("Illegal input for ListdCommand");
		} else {
			Stack<Path> stack = (Stack) env.getSharedData(STACK_KEY);
			List<Path> listSaved = new ArrayList<>(stack);
			
			if (stack != null && !stack.isEmpty()) {
				Path path;
				while (!stack.isEmpty()) {
					path = stack.pop();
					env.writeln(path.toString());
				}
			for(Path p: listSaved) {
				stack.push(p);
			}

			} else {
				env.writeln("There are no saved directories");
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
		return "listd";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command lisd excepts no arguments.");
		Commandescritpion.add("This command writes all directories stored in SharedData.");
		Commandescritpion.add("If error occurs. Appropriate message is written to user.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
