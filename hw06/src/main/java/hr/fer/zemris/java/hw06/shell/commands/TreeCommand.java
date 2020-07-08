package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
public class TreeCommand implements ShellCommand {

	/**
	 * The tree command expects a single argument: directory name and prints a tree
	 * (each directory level shifts output two charatcers to the right).
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
			env.writeln("Illegal input for tree command");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

		try {
			Path path = Paths.get(Utility.getPath(arguments));

			if (!Files.isDirectory(path)) {
				env.writeln("Given path is not a directory");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}

			try {
				Files.walkFileTree(path, new Visitor(env));
			} catch (IOException e) {
				env.writeln("unable to list all files from folder");
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
	 * Public static class Visitor implements interface FileVisitor. Class
	 * implements an easy way of walking through input tree structure
	 * 
	 * @author antonija
	 *
	 */
	public static class Visitor implements FileVisitor<Path> {

		int level = 1;
		Environment env;

		public Visitor(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			if (level == 0) {
				env.writeln(dir.getFileName().toString());
			} else {
				env.writeln(" ".repeat(level * 2) + dir.getFileName().toString());
			}
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(" ".repeat(level * 2) + file.getFileName().toString());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

	}

	/**
	 * Getter method for command name
	 */
	@Override
	public String getCommandName() {
		return "tree";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("The tree command expects a single argument: directory name.");
		Commandescritpion
				.add("Command prints a tree (each directory level shifts output two charatcers to the right).");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
