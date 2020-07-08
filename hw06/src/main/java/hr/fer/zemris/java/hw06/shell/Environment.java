package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * This is an abstraction which will be passed to each defined command. The each
 * implemented shell command communicates with user(reads user input and writes
 * response)only through this interface.
 * 
 * @author antonija
 *
 */

public interface Environment {

	String readLine() throws ShellIOException;

	/**
	 * Method write writes input text to console for the user.
	 * 
	 * @param text input
	 * @throws ShellIOException if communication is not possible
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method write writes input text to console for the user and after that puts
	 * cursor in next line.
	 * 
	 * @param text input
	 * @throws ShellIOException if communication is not possible
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Method commands() returns unmodifiable map,so that the client can not delete
	 * commands by clearing the map
	 * 
	 * @return SortedMap<String, ShellCommand> map of ShellCommands with key of
	 *         String name for each command.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Method returns current MULTILINE symbol
	 * 
	 * @return
	 */
	Character getMultilineSymbol();

	/**
	 * Method sets current MULTILINE symbol to input simbol
	 * 
	 * @param symbol new value of MULTILINE symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Method returns current PROMPT symbol
	 * 
	 * @return
	 */
	Character getPromptSymbol();

	/**
	 * Method sets current PROMPT symbol to input simbol
	 * 
	 * @param symbol new value of PROMPT symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Method returns current MORELINES symbol
	 * 
	 * @return
	 */
	Character getMorelinesSymbol();

	/**
	 * Method sets current MORELINES symbol to input simbol
	 * 
	 * @param symbol new value of MORELINES symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
