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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
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
 * Command copy implements ShellComand.
 * 
 * @author antonija
 *
 */
public class CopyCommand implements ShellCommand {

	/**
	 * The copy command expects two arguments: source file name and destination file
	 * name (i.e. paths and names). If destination file exists, user is asked is it
	 * allowed to overwrite it. This copy command works only with files (no
	 * directories). If the second argument is directory, it is assumed that user
	 * wants to copy the original file into that directory using the original file
	 * name.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		arguments = arguments.trim();

		String[] line = Utility.separateArguments(arguments);

		if (line.length != 2) {
			env.writeln("Illegal input for command copy");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

		String input = Utility.getPath(line[0]);
		String output = Utility.getPath(line[1]);

		try {
			copy(input, output, env);
		} catch (IOException ex) {
			env.writeln("Illegal argument for input file!");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;

	}

	/**
	 * Method reads data from input and writes it to newFile.
	 * 
	 * @param origFile input string path to input data
	 * @param newFile  output file
	 * @param env
	 */
	private void copy(String origFile, String newFile, Environment env) throws IOException {

		Path file_input = new File(origFile).toPath();

		Path file_out = new File(newFile).toPath();

		// Check if original file is existing file
		if (!Files.isRegularFile(file_input)) {
			env.writeln("Illegal argument for input stream!");
			return;
		}
		// Check if new file is existing file
		if (Files.isRegularFile(file_out)) {
			env.write("Do you want to overwrite the existing file (yes/no): ");
			do {
				String answer = env.readLine();
				if (answer.equals("no")) {
					return;
				} else if (answer.equals("yes")) {
					break;
				}
				env.write("Type \"yes\" or \"no\" ");
			} while (true);

			// Check if the second argument is directory
		} else if (Files.isDirectory(file_out)) {
			if (file_out.equals(file_input.getParent())) {
				env.writeln("Illegal input. Input file cannot be copied in it's parents directory by the same name.");
				return;
			}
			newFile = newFile + File.separator + file_input.getFileName();
			// check again if file exists
			if (Files.isRegularFile(new File(newFile).toPath())) {
				env.write("File " + newFile + " already exists. Do you want to overwrite the existing file (Yes/No): ");
				env.write("Do you want to overwrite the existing file (yes/no): ");
				do {
					String answer = env.readLine();
					if (answer.equals("no")) {
						return;
					} else if (answer.equals("yes")) {
						break;
					}
					env.write("Type \"yes\" or \"no\" ");
				} while (true);
			}

			
			// check if the files has the same extension
			int index1 = newFile.lastIndexOf('.');
			int index2 = origFile.lastIndexOf('.');
			
			if (index1 > 0 && index2 > 0 && index1 < newFile.length() - 1 && index2 < origFile.length() - 1
					&& (newFile.substring(index1 + 1).equals(origFile.substring(index2 + 1)))) {
			} else {
				env.writeln("Illegal extension for output stream!");
				return;
			}

			try (InputStream is = new BufferedInputStream(new FileInputStream(origFile));) {
				try (OutputStream os = new BufferedOutputStream(new FileOutputStream(newFile));) {

					byte[] buff = new byte[512];
					do {
						int r = is.read(buff);
						if (r < 1)
							break;
						os.write(buff, 0, r);
					} while (true);
				}
			}

		}
	}

	/**
	 * Getter method for command name
	 */
	@Override
	public String getCommandName() {
		return "copy";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add(
				"The copy command expects two arguments: source file name and destination file name (i.e. paths and names).");
		Commandescritpion.add("If destination file exists, user is asked is it allowed to overwrite it.");
		Commandescritpion.add("This copy command works only with files (no directories).");
		Commandescritpion.add(
				"If the second argument is directory, it is assumed that user wants to copy the original file into that directory using the original file name.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
