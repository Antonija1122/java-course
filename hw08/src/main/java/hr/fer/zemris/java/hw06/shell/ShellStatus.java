package hr.fer.zemris.java.hw06.shell;

/**
 * ShellStatus is simple enum that has two values CONTINUE and TERMINATE. Status
 * CONTINUE is returned by every command by shell except command exit. Command
 * exit is called when user decides to terminate shell program and command exit
 * changes ShellStatus to TERMINATE.
 * 
 * @author antonija
 *
 */
public enum ShellStatus {

	/**
	 * Program continues
	 */
	CONTINUE,
	/**
	 * Program is terminated
	 */
	TERMINATE
}
