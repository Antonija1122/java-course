package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command PwdCommand implements ShellCommand
 * 
 * @author antonija
 *
 */
public class PwdCommand implements ShellCommand{

	/**
	 * Command pwd excepts no arguments. This method writes absolute path to current directory saved in environment
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] line = Utility.separateArguments(arguments);

		if (line.length > 0) {
			env.writeln("Illegal input for PwdCommand");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		} else {
			env.writeln(env.getCurrentDirectory().toString());
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
		
	}

	/**
	 * Getter method for command name
	 */
	@Override
	public String getCommandName() {
		return "pwd";
	}

	/**
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command pwd excepts no arguments.");
		Commandescritpion.add("This command writes absolute path to current directory saved in environment");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
