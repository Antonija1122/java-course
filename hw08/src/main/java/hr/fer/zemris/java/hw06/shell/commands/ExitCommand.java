package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command exit implements ShellComand.
 * 
 * @author antonija
 *
 */
public class ExitCommand implements ShellCommand {

	/**
	 * Exit command excepts zero arguments. If input is correct command sends
	 * ShellStatus.TERMINATE to shell as a sign that user is terminating the
	 * program.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		arguments = arguments.trim();
		if (arguments.length() != 0) {
			env.write("Invalid exit command. ->" + arguments);
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	/**
	 * Getter method for command name
	 */
	@Override
	public String getCommandName() {
		return "exit";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Exit command excepts zero arguments.");
		Commandescritpion.add(
				"If input is correct command sends ShellStatus.TERMINATE to shell as a sign that user is terminating the program.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
