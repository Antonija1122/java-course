package hr.fer.zemris.java.hw06.shell.commands.massrename;
/**
 * This class builds name for file part by part. Parser for name building uses instances of this class to create a new name for files.
 * @author antonija
 *
 */
public interface NameBuilder {

	/**
	 * This command executes NameBuilder by appending parts of file name to input StringBuilder
	 * @param result represents information about file whose new name is building with StringBuilder
	 * @param sb input StringBuilder for building name 
	 */
	 void execute(FilterResult result, StringBuilder sb);

}
