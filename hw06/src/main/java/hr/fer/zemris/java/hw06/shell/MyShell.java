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
			String commandName = extractCommandName(input);
			String arguments = extractArguments(input);
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
	 * This method removes first word(command name) from input string and returns
	 * it.
	 * 
	 * @param input string
	 * @param env   environment
	 * @return
	 */
	private static String extractArguments(String input) {

		input = input.trim();
		String[] command = input.split("\\s+");
		if (command.length == 0)
			return "";
		String arguments = input.replaceFirst(command[0], "");
		return arguments;

	}

	/**
	 * This method returns first word from input string. In this program that is
	 * command name.
	 * 
	 * @param input
	 * @return
	 */
	private static String extractCommandName(String input) {

		input = input.trim();
		String[] command = input.split("\\s+");
		if (command.length == 0)
			return "";
		String name = command[0];
		return name;
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

		while (argument.charAt(argument.length() - 1) == env.getMorelinesSymbol()) {
			argument = argument.substring(0, argument.length() - 1);
			env.write(env.getMultilineSymbol().toString() + " ");
			argument = argument + " " + env.readLine();
		}

		return argument;
	}

}
