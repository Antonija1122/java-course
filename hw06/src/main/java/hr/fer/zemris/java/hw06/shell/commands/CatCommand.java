package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command CatCommand implements ShellCommand
 * 
 * @author antonija
 *
 */
public class CatCommand implements ShellCommand {

	/**
	 * Command cat takes one or two arguments. The first argument is path to some
	 * file and is mandatory. The second argument is charset name that is used to
	 * interpret chars from bytes. If not provided, a default platform charset is
	 * used. This command opens given file and writes its content to console.
	 * 
	 * @param Environment env, String arguments - one path string and charset
	 * 
	 * @return ShellStatus.CONTINUE
	 * @throws ShellIOException() if communication with user is not possible
	 */

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		arguments = arguments.trim();
		String[] line = Utility.separateArguments(arguments);

		try {
			String path = Utility.getPath(line[0]);
			Path p=Paths.get(path);
			if(Files.isDirectory(p)) {
				env.writeln("Input for command cat can not be directory.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
			
			Charset charset = Charset.defaultCharset();

			if (line.length == 1 || line.length == 2) {

				if (line.length == 2) {

					try {
						charset = Charset.forName(line[1]);
					} catch (Exception e) {
						env.writeln("Invalid charset name -> " + line[1]);
						env.write(env.getPromptSymbol() + " ");
						return ShellStatus.CONTINUE;
					}
				}

				StringBuilder sb = new StringBuilder();
				try (Reader r = new BufferedReader(
						new InputStreamReader(new BufferedInputStream(new FileInputStream(path)), charset))) {

					char[] buff = new char[1024];
					while (true) {
						int procitano = r.read(buff);
						if (procitano < 1)
							break;
						sb.append(buff, 0, procitano);
					}

					sb.append("%n");
					try {
						env.write(sb.toString());
					} catch (Exception e) {
						throw new ShellIOException("Unable to write to shell");
					}

				} catch (FileNotFoundException e) {
					env.writeln("Error ocurred. File does not exist or you don't have acces to it. - >" + path);
				} catch (IOException e) {
					env.writeln("Error ocured during opening file -> " + path);
				}

			} else {
				env.writeln("Illegal input for cat command");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
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
		return "cat";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command cat takes one or two arguments.");
		Commandescritpion.add(" The first argument is path to some file and is mandatory.");
		Commandescritpion.add("The second argument is charset name that should be used to interpret chars from bytes.");
		Commandescritpion.add("If not provided, a default platform charset should be used.");
		Commandescritpion.add("This command opens given file and writes its content to console.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
