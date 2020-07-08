package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command PushdCommand implements ShellCommand
 * 
 * @author antonija
 *
 */
public class PushdCommand implements ShellCommand {
	/**
	 * Key for stack in Shared data in input Environment
	 */
	private static final String STACK_KEY = "cdstack";

	/**
	 * Command pushd excepts one argument. This command saves current directory in
	 * stack and pushes stack in map SharedData in environment. Command after saving
	 * current directory in SharedData, sets current directory to path given in
	 * arguments
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] line = Utility.separateArguments(arguments);

		if (line.length != 1) {
			env.writeln("Illegal input for PushdCommand");
		} else {
			Path path = Paths.get(Utility.getPath(line[0]));
			if (Files.exists(path) && Files.isDirectory(path)) {
				if (env.getSharedData(STACK_KEY) == null) {
					Stack<Path> stack = new Stack<>();
					stack.push(env.getCurrentDirectory());
					env.setSharedData(STACK_KEY, stack);
				} else {
					Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK_KEY);
					stack.push(env.getCurrentDirectory());
				}
				env.setCurrentDirectory(env.getCurrentDirectory().resolve(path));

			} else {
				env.writeln("Illegal input directory -> " + path.toString());
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
		return "pushd";
	}

	/**
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command pushd excepts one argument.");
		Commandescritpion.add(
				"This command saves current directory in stack and pushes stack in map SharedData in environment.");
		Commandescritpion.add(
				"Command after saving current directory in SharedData, sets current directory to path given in arguments");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
