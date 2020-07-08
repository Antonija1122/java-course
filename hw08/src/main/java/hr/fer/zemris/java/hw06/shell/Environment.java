package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
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

	/**
	 * Method returns absolute path to current directory
	 * 
	 * @return current directory
	 */
	Path getCurrentDirectory();

	/**
	 * Method sets current directory to input directory
	 * 
	 * @param path to input directory
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Getter method for Shared data. Method returns Shared data linked with input
	 * key
	 * 
	 * @param key key for Shared data
	 * @return Shared data linked with input key
	 */
	Object getSharedData(String key);

	/**
	 * Method adds input data (Object value) to Shared data under the key of input
	 * key. Shared data is stored in a Map.
	 * 
	 * @param key   key for adder Shared data
	 * @param value new added Shared data
	 */
	void setSharedData(String key, Object value);

}
