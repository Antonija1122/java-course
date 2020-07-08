package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/**
 * MyShellEnvironment implements interface Environment. Instances of this class
 * have private variables: private SortedMap<String, ShellCommand> commands,
 * private Character multilineSymbol, private Character promptSymbol, private
 * Character morelinesSymbol, private Scanner sc. When instance is created
 * private variables are set to default value.
 * 
 * @author antonija
 *
 */
public class MyShellEnvironment implements Environment {

	/**
	 * Private sorted map of commands that is given to every instance of this
	 * Environment.
	 */
	private SortedMap<String, ShellCommand> commands;
	/**
	 * Character for multiline simbol
	 */
	private Character multilineSymbol;
	/**
	 * Character for prompt simbol
	 */
	private Character promptSymbol;
	/**
	 * Character for morelines simbol
	 */
	private Character morelinesSymbol;
	/**
	 * Private Scanner for communication with user
	 */
	private Scanner sc;

	/**
	 * Current directory
	 */
	private Path currentDirectory;

	/**
	 * 
	 */
	private Map<String, Object> SharedData;

	/**
	 * Public constructor sets private values to default values. Private Scanner for
	 * communication with user is opened.
	 */
	public MyShellEnvironment() {
		super();
		this.commands = getCommands();
		this.multilineSymbol = '|';
		this.promptSymbol = '>';
		this.morelinesSymbol = '\\';
		setCurrentDirectory(Paths.get("."));
		SharedData = new HashMap<String, Object>();
		sc = new Scanner(System.in);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	private SortedMap<String, ShellCommand> getCommands() {
		SortedMap<String, ShellCommand> map = new TreeMap<>();
		map.put("cat", new CatCommand());
		map.put("charsets", new CharsetsCommand());
		map.put("copy", new CopyCommand());
		map.put("exit", new ExitCommand());
		map.put("help", new HelpCommand());
		map.put("hexdump", new HexdumpCommand());
		map.put("ls", new LsCommand());
		map.put("symbol", new SymbolCommand());	
		//new
		map.put("pwd", new PwdCommand());
		map.put("cd", new CdCommand());
		map.put("popd", new PopdCommand());
		map.put("massrename", new MassrenameCommand());
		map.put("pushd", new PushdCommand());
		map.put("listd", new ListdCommand());
		map.put("dropd", new DropdCommand());
		
		map.put("tree", new TreeCommand());
		map.put("mkdir", new MkdirCommand());
		return Collections.unmodifiableSortedMap(map);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String readLine() throws ShellIOException {
		try {
			return sc.nextLine();
		} catch (Exception e) {
			throw new ShellIOException();

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentDirectory(Path path) {
		if (Files.exists(path) && Files.isDirectory(path)) {
			currentDirectory = path.toAbsolutePath().normalize();
		} else {
			throw new ShellIOException("Given path is not an existing directory -> " + path);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getSharedData(String key) {
		return SharedData.get(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSharedData(String key, Object value) {
		SharedData.put(key, value);
	}

}
