package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command ls implements ShellComand.
 * 
 * @author antonija
 *
 */
public class LsCommand implements ShellCommand {

	/**
	 * Command ls takes a single argument – directory – and writes a directory
	 * listing. Output is formatted. The output consists of 4 columns. First column
	 * indicates if current object is directory (d), readable (r), writable (w) and
	 * executable (x). Second column contains object size in bytes that is right
	 * aligned and occupies 10 characters. Follows file creation date/time and
	 * finally file name.
	 * 
	 * @param Environment env, String arguments-one path string
	 * @return ShellStatus.CONTINUE
	 * @throws ShellIOException() if communication with user is not possible
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		arguments = arguments.trim();

		String[] line = Utility.separateArguments(arguments);
		if (line.length != 1) {
			env.writeln("Illegal input for LsCommand");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

		try {
			String pathString = Utility.getPath(arguments);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Path path = Paths.get(pathString);

			if (!Files.isDirectory(path)) {
				env.writeln("Given path is not a directory");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}

			try {
				Files.list(path).forEach(f -> {

					BasicFileAttributeView faView = Files.getFileAttributeView(f, BasicFileAttributeView.class,
							LinkOption.NOFOLLOW_LINKS);
					BasicFileAttributes attributes = null;

					try {
						attributes = faView.readAttributes();
					} catch (IOException e2) {
						env.writeln("Unable to find arguments for file.");
						return; // provjeri
					}

					FileTime fileTime = attributes.creationTime();
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

					StringBuilder print = new StringBuilder();
					@SuppressWarnings("resource")
					Formatter fmt = new Formatter(print);
					if (Files.isDirectory(f)) {
						print.append("d");
					} else {
						print.append("-");
					}
					if (Files.isReadable(f)) {
						print.append("r");
					} else {
						print.append("-");
					}
					if (Files.isWritable(f)) {
						print.append("w");
					} else {
						print.append("-");
					}
					if (Files.isExecutable(f)) {
						print.append("x");
					} else {
						print.append("-");
					}

					try {
						fmt.format("%10d %s %s %n", Files.size(f), formattedDateTime, f.getFileName());
					} catch (IOException e1) {
						env.write("unadble to get size from file");
						return;
					}

					try {
						env.write(print.toString());
					} catch (Exception e) {
						throw new ShellIOException("Unable to print to user from LsCommand");
					}

				});

			} catch (Exception e) {
				env.writeln("Unable to read files from input path.");
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
		return "ls";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("Command ls takes a single argument – directory – and writes a directory listing.");
		Commandescritpion.add("The output consists of 4 columns.");
		Commandescritpion.add(
				"First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).");
		Commandescritpion
				.add("Second column contains object size in bytes that is right aligned and occupies 10 characters.");
		Commandescritpion.add("Follows file creation date/time and finally file name.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
