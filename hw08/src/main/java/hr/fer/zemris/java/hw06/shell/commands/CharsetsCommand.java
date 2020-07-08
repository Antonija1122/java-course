package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command charsets implements ShellCommand
 * @author antonija
 *
 */
public class CharsetsCommand implements ShellCommand {

	/**
	 * Command charsets takes no arguments and lists names of supported charsets for
	 * your Java platform. A single charset name is written per line.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		SortedMap<String, Charset> charsets = Charset.availableCharsets();

		String[] line = Utility.separateArguments(arguments);

		if (line.length > 0) {
			env.writeln("Illegal input for CharsetCommand");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		} else {

			for (SortedMap.Entry<String, Charset> entry : charsets.entrySet()) {
				try {
					env.writeln(entry.getKey());
				} catch (Exception e) {
					throw new ShellIOException("Unable to write in Charset command");
				}
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

		return "charsets";
	}

	/**
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {

		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command charsets takes NO arguments.");
		Commandescritpion.add("Command lists names of supported charsets for your Java platform");
		Commandescritpion.add("A single charset name is written per line.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
