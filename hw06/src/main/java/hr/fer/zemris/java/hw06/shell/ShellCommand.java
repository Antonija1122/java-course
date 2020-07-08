package hr.fer.zemris.java.hw06.shell;

import java.io.IOException;
import java.util.List;

/**
 * This inteface has tree methods: executeCommand, getCommandName, getCommandDescription. 
 * @author antonija
 *
 */
public interface ShellCommand {

	/**
	 * The second argument of method executeCommand is a single string which
	 * represents everything that user entered AFTER the command name.
	 * 
	 * @param env       instance of Environment
	 * @param arguments users input
	 * @return ShellStatus
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Getter method for command name
	 * 
	 * @return command name
	 */
	String getCommandName();

	/**
	 * Getter method for command explanation.
	 * 
	 * @return List of strings that explains what this command does.
	 */
	List<String> getCommandDescription();

}
