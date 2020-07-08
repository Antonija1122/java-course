package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * Command CdCommand implements ShellCommand
 * @author antonija
 *
 */
public class CdCommand implements ShellCommand {

	/**
	 * Command cd excepts one argument. This command sets current directory saved in environment to input path in arguments.
	 * @return ShellStatus.CONTINUE;
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] line = Utility.separateArguments(arguments);

		if (line.length != 1) {
			env.writeln("Illegal input for cdCommand");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		} else {
			Path path=Paths.get(Utility.getPath(line[0]));
			path=env.getCurrentDirectory().resolve(path);
			env.setCurrentDirectory(path);
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Getter method for command name
	 */
	@Override
	public String getCommandName() {
		return "cd";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command cd excepts one argument.");
		Commandescritpion.add("This command sets current directory saved in environment to input path in arguments");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
