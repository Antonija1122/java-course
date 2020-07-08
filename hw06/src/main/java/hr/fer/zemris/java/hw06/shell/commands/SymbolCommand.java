package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command symbol implements ShellComand.
 * 
 * @author antonija
 *
 */
public class SymbolCommand implements ShellCommand {

	/**
	 * Command symbol excepts one or two arguments. If only one argument is excepted
	 * argument has to be name of shell symbol (PROMPT, MORELINES, MULTILINE) and
	 * symbol that represents it is printed. If two arguments are excepted then
	 * first argument again must be name of shell symbol and second argument has to
	 * be a character, then old character representing shell symbol is replaced with
	 * input character.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		arguments = arguments.trim();
		String[] line = arguments.split("\\s+");

		if (line.length == 1) {
			if (line[0].equals("PROMPT")) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");

			} else if (line[0].equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");

			} else if (line[0].equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");

			} else {
				env.writeln("Unrecognized sybmol name - > " + line[0]);
			}

		} else if (line.length == 2) {

			if (line[1].length() != 1) {
				env.writeln("Sybmol must be one character - > " + line[0]);
			} else {

				char symbol = line[1].charAt(0);

				if (line[0].equals("PROMPT")) {

					env.writeln(
							"Symbol for PROMPT changed from '" + env.getPromptSymbol() + "'" + " to '" + symbol + "'");
					env.setPromptSymbol(symbol);

				} else if (line[0].equals("MORELINES")) {

					env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "'" + " to '" + symbol
							+ "'");
					env.setMorelinesSymbol(symbol);

				} else if (line[0].equals("MULTILINE")) {

					env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "'" + " to '" + symbol
							+ "'");
					env.setMultilineSymbol(symbol);

				} else {
					env.writeln("Unrecognized sybmol name - > " + line[0]);
				}
			}
		} else {
			env.writeln("Illegal input for command symbol.");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Getter method for command name
	 */
	@Override
	public String getCommandName() {
		return "symbol";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command symbol excepts one or two arguments.");
		Commandescritpion.add(
				"If only one argument is excepted argument has to be name of shell symbol (PROMPT, MORELINES, MULTILINE) and symbol that represents it is printed.");
		Commandescritpion.add(
				"If two arguments are excepted then first argument again must be name of shell symbol and second argument has to be a character, then old character representing shell symbol is replaced with input character. ");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
