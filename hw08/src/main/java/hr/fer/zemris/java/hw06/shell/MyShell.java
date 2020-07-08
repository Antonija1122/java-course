package hr.fer.zemris.java.hw06.shell;

/**
 * This program runs shell program. MyShellEnvironment is built. Program is
 * executing users commands until user enters "exit".
 * 
 * @author antonija
 *
 */
public class MyShell {

	public static void main(String[] args) {

		MyShellEnvironment env = new MyShellEnvironment();
		ShellStatus status = ShellStatus.CONTINUE;

		env.writeln("Welcome to MyShell v 1.0");
		env.write(env.getPromptSymbol().toString() + " ");

		while (status != ShellStatus.TERMINATE) {

			String input = readLineOrLines(env);
			String commandName = extractArguments(input)[0];
			String arguments = extractArguments(input)[1];
			ShellCommand command = env.commands().get(commandName);
			if (command == null) {
				env.writeln("Invalid input. Command is not recognized. ");
				env.write(env.getPromptSymbol().toString() + " ");
			} else {
				try {
					status = command.executeCommand(env, arguments);
				} catch (ShellIOException e) {
					env.writeln("Error. Unable to write or read in shell.");
					env.writeln(e.getMessage());
					env.write(env.getPromptSymbol().toString() + " ");
				}
			}
		}
	}

	/**
	 * This method returns first word(command name) and the rest of input returns
	 * as arguments.
	 * 
	 * @param input string 
	 * @param env  environment
	 * @return String[] of length 2. First string is command name, second are arguments
	 */
	private static String[] extractArguments(String input) {

		String[] parts = new String[2];
		input = input.trim();
		String[] command = input.split("\\s+");
		if (command.length == 0) {
			parts[0] = parts[1] = "";
			return parts;
		}
		parts[0] = command[0];
		parts[1] = input.replaceFirst(command[0], "");
		return parts;
	}


	/**
	 * This method reads multiple lines from user input, removes MORELINES symbols
	 * and concatenates them into one string
	 * 
	 * @param env
	 * @return
	 */
	private static String readLineOrLines(MyShellEnvironment env) {

		String argument = env.readLine();
		argument = argument.trim();

		while (!argument.equals("") && argument.charAt(argument.length() - 1) == env.getMorelinesSymbol()) {
			argument = argument.substring(0, argument.length() - 1);
			env.write(env.getMultilineSymbol().toString() + " ");
			argument = argument + " " + env.readLine();
		}

		return argument;
	}

}
