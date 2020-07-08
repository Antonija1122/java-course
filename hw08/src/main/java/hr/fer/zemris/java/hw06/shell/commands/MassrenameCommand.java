package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.Utility;
import hr.fer.zemris.java.hw06.shell.commands.massrename.FilterResult;
import hr.fer.zemris.java.hw06.shell.commands.massrename.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.massrename.NameBuilderParser;

/**
 * Command MassrenameCommand implements ShellCommand
 * 
 * @author antonija
 *
 */
public class MassrenameCommand implements ShellCommand {

	/**
	 * This command has four types of commands: "filter", "groups", "show" and
	 * "execute". Commands "filter" and "groups" take four arguments, and "show" and
	 * "execute" take five arguments. First two arguments are source and destination
	 * paths. Third argument is command, fourth argument is a pattern Mask and fifth
	 * argument (for show and execute) is expression for building new name. Filter
	 * command filters files in directory that fit the pattern. Groups command
	 * writes to user groups found in file name using pattern. Command show writes
	 * old and new names for files. Finally command execute renames files from
	 * source directory and moves them in destination directory.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		arguments = arguments.trim();
		String[] line = Utility.separateArguments(arguments);

		if (line.length == 4 || line.length == 5) {

			Path dir1 = Paths.get(Utility.getPath(line[0]));
			dir1 = env.getCurrentDirectory().resolve(dir1);
			Path dir2 = Paths.get(Utility.getPath(line[1]));
			dir2 = env.getCurrentDirectory().resolve(dir2);

			if (!Files.isDirectory(dir1) || !Files.isDirectory(dir2)) {
				throw new ShellIOException("Illegal input for directory in MassrenameCommand");
			}
			String maska = Utility.getPath(line[3]);

			if (line[2].equals("filter") || line[2].equals("groups")) {
				if (line.length != 4)
					throw new ShellIOException("Illegal input for " + line[2] + " command in MassrenameCommand");
				try {
					List<FilterResult> filterList = filter(dir1, maska);
					executeFilterOrGroup(filterList, line[2], env);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else if (line[2].equals("show") || line[2].equals("execute")) {
				if (line.length != 5)
					throw new ShellIOException("Illegal input for show command in MassrenameCommand");
				String IZRAZ = Utility.getPath(line[4]);
				try {
					List<FilterResult> filterList = filter(dir1, maska);
					executeShowOrExecute(filterList, IZRAZ, line, dir1, dir2, env);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				env.writeln("Illegal input for CMD in MassrenameCommand");
			}

		} else {
			env.writeln("Illegal input for MassrenameCommand");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method executes commands show and execute
	 * 
	 * @param filterList list of instances of class FilterResult which represent all
	 *                   files in source directory
	 * @param IZRAZ      expression for renaming the files from source directory
	 * @param line       input arguments
	 * @param dir1       source directory path
	 * @param dir2       destination directory path
	 * @param env        Environment for communication with user
	 * @throws IOException IOException is thrown if paths to files are not found
	 */
	private void executeShowOrExecute(List<FilterResult> filterList, String IZRAZ, String[] line, Path dir1, Path dir2,
			Environment env) throws IOException {
		try {
			NameBuilderParser parser = new NameBuilderParser(IZRAZ);
			NameBuilder builder = parser.getNameBuilder();
			for (FilterResult file : filterList) {
				StringBuilder sb = new StringBuilder();
				builder.execute(file, sb);
				String novoIme = sb.toString();
				if (line[2].equals("show")) {
					env.writeln(file.toString() + " => " + novoIme);
				} else {
					Path oldPath = dir1.resolve(file.toString());
					Path newPath = dir2.resolve(novoIme);
					env.writeln(oldPath + " => " + newPath);
					Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
		}
	}

	/**
	 * This method executes methods filter and group
	 * 
	 * @param filterList list of instances of class FilterResult which represent all
	 *                   files in source directory
	 * @param string     input command
	 * @param env        Environment for communication with user
	 */
	private void executeFilterOrGroup(List<FilterResult> filterList, String string, Environment env) {

		if (string.equals("filter")) {
			for (FilterResult f : filterList) {
				env.writeln(f.toString());
			}
		} else {
			for (FilterResult f : filterList) {
				env.write(f.toString() + " ");
				for (int i = 0; i <= f.numberOfGroups(); i++) {
					env.write(i + ": " + f.group(i) + " ");
				}
				env.write("\n");
			}
		}
	}

	/**
	 * This method creates List<FilterResult> with pattern groups informations of
	 * all files in source directory that fir the pattern
	 * 
	 * @param dir     input source directory
	 * @param pattern Mask for finding fitted files
	 * @return List<FilterResult> with all files that fit the input pattern for
	 *         names
	 * @throws IOException IOException is thrown if paths to files are not found
	 */
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		List<FilterResult> list = new ArrayList<>();
		List<Path> files = Files.list(dir).filter(f -> Files.isRegularFile(f)).collect(Collectors.toList());
		for (Path f : files) {
			FilterResult current = new FilterResult(f, pattern);

			Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(current.toString());

			if (m.matches()) {
				list.add(current);
			}
		}
		return list;
	}

	/**
	 * Getter method for command name
	 */
	@Override
	public String getCommandName() {
		return "massrename";
	}

	/**
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion
				.add("This command has four types of commands: \"filter\", \"groups\", \"show\" and \"execute\".");
		Commandescritpion.add(
				" Commands \"filter\" and \"groups\" take four arguments, and \"show\" and \"execute\" take five arguments.");
		Commandescritpion.add("First two arguments are source and destination paths.");
		Commandescritpion.add(
				"Third argument is command, fourth argument is a pattern Mask and fifth argument (for show and execute) is expression for building new name.");
		Commandescritpion.add("Filter command filters files in directory that fit the pattern.");
		Commandescritpion.add("Groups command writes to user groups found in file name using pattern.");
		Commandescritpion.add("Command show writes old and new names for files.");
		Commandescritpion.add(
				"Finally command execute renames files from source directory and moves them in destination directory.");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
