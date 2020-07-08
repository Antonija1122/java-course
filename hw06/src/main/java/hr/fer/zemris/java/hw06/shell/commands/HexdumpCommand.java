package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command hexdump implements ShellComand.
 * 
 * @author antonija
 *
 */
public class HexdumpCommand implements ShellCommand {

	/**
	 * The hexdump command expects a single argument: file name, and produces
	 * hex-output as illustrated below.
	 * 
	 * 00000000: 3C 3F 78 6D 6C 20 76 65|65 72 73 69 6F 6E 3D 22 31 | <?xml
	 * version="1 00000010: 2E 30 22 20 65 6E 63 6F|6F 64 69 6E 67 3D 22 55 54 | .0"
	 * encoding="UT 00000020: 46 2D 38 22 3F 3E 0D 0A|0A 3C 70 72 6F 6A 65 63 74 |
	 * F-8"?>..<project
	 * 
	 * On the right side of the image only a standard subset of characters is shown;
	 * for all other characters a '.' is printed instead .
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();

		String[] line = Utility.separateArguments(arguments);

		if (line.length != 1) {
			env.writeln("Illegal input for command hexdump");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

		try {

			Path input = Paths.get(Utility.getPath(line[0]));

			if (Files.isDirectory(input)) {
				env.writeln("Input for hexdump command can not be directory -> " + input);
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}

			if (!Files.isReadable(input)) {
				env.writeln("Cannot read from file -> " + input);
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;

			} else {

				int hex = 0;
				String bytes;

				try (InputStream is = Files.newInputStream(input)) {
					byte[] buff = new byte[16];
					while (true) {
						int r = is.read(buff);
						if (r < 1)
							break;

						StringBuilder sb = new StringBuilder();
						env.write(String.format("%08X", hex) + ":");

						for (int i = 0; i < 8; i++) {
							if (r < i) {
								env.write(" " + "  ");
							} else {
								bytes = Utility.bytetohex(buff[i]);
								env.write(" " + bytes.toUpperCase());
							}
						}
						env.write("|");
						for (int i = 7; i < 16; i++) {
							if (r < i) {
								env.write("  " + " ");
							} else {
								bytes = Utility.bytetohex(buff[i]);
								env.write(bytes.toUpperCase() + " ");
							}
						}
						env.write("| ");
						for (int i = 0; i < 16; i++) {
							if (r < i) {
								sb.append(" ");
							} else {
								if ((int) buff[i] < 32 || (int) buff[i] > 127) {
									sb.append(".");
								} else {
									sb.append((char) buff[i]);
								}
							}
						}
						try {
							env.write(sb.toString() + "%n");
						} catch (Exception e) {
							throw new ShellIOException();
						}
						hex = hex + 16;
					}

				} catch (Exception ex) {
					env.writeln(ex.getMessage());
				}

			}
		} catch (

		Exception e) {
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
		return "hexdump";
	}

	/**
	 * 
	 * Getter method for description of command execution.
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> Commandescritpion = new ArrayList<>();

		Commandescritpion.add("The hexdump command expects a single argument: file name");
		Commandescritpion.add("Command produces hex-output as illustrated below. ");
		Commandescritpion.add("00000000: 3C 3F 78 6D 6C 20 76 65|65 72 73 69 6F 6E 3D 22 31 | <?xml version=\"1");
		Commandescritpion.add("00000010: 2E 30 22 20 65 6E 63 6F|6F 64 69 6E 67 3D 22 55 54 | .0\" encoding=\"UT");
		Commandescritpion.add("00000020: 46 2D 38 22 3F 3E 0D 0A|0A 3C 70 72 6F 6A 65 63 74 | F-8\"?>..<project");
		Commandescritpion.add(
				"On the right side of the example only a standard subset of characters is shown; for all other characters a '.' is printed instead .");

		return Collections.unmodifiableList(Commandescritpion);
	}

}
