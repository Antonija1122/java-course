package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command mkdir implements ShellComand.
 * 
 * @author antonija
 *
 */
public class MkdirCommand implements ShellCommand {

	/**
	 * The mkdir command takes a single argument: directory name, and creates the
	 * appropriate directory structure.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();

		String[] line = Utility.separateArguments(arguments);
		if (line.length != 1) {
			env.writeln("Illegal input for mkdir command");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		try {
			Path path = Paths.get(Utility.getPath(arguments));
			path=env.getCurrentDirectory().resolve(path);

			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				env.writeln("Unable to create directory.");

			}
		} catch (Exception e) {
			env.writeln(e.getMessage());
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Getter method for command name
	 */
	@Override
	public String getCommandName() {
		return "mkdir";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add(
				"	 * The mkdir command takes a single argument: directory name, and creates the appropriate directory structure.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
