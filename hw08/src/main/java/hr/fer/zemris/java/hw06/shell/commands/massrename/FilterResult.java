package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.ShellIOException;

/**
 * Instances of this class represent one file with the information of it's group
 * count and method for getting those groups.
 * 
 * @author antonija
 *
 */
public class FilterResult {

	/**
	 * this classes file
	 */
	private Path file;
	/**
	 * Mask defines pattern in which groups are found in file name
	 */
	private String MASK;
	/**
	 * Number of groups found in file's name
	 */
	private int numOfGroups;
	/**
	 * List of found groups in file name
	 */
	private List<String> groups = new ArrayList<>();

	/**
	 * Public constructor initializes file and mask. It also counts number of groups
	 * and with that also makes list groups.
	 * @param file input file
	 * @param MASK input pattern mask
	 */
	public FilterResult(Path file, String MASK) {
		this.file = file;
		this.MASK = MASK;
		numOfGroups = numberOfGroups();
	}

	/**
	 * Thid method returns name of the file without the whole path.
	 * @return File's name
	 */
	public String toString() {
		if (Files.isRegularFile(file)) {
			return file.getFileName().toString();
		} else {
			throw new ShellIOException("Error in FilterResult. Given path is not a file.");
		}
	}

	/**
	 * This method counts how many groups are found in file name
	 * @return group count
	 */
	public int numberOfGroups() {
		Pattern p = Pattern.compile(MASK, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(toString());
		int groupCount = m.groupCount();
		while (m.find()) {
			for (int i = 0; i <= groupCount; i++) {
				groups.add(m.group(i));
			}
		}
		return m.groupCount();
	}

	/**
	 * This method returns group in list groups with index of input index
	 * @param index index of wanted group in private list
	 * @return group in index index
	 */
	public String group(int index) {
		if (index < 0 || index > numOfGroups) {
			throw new ShellIOException("Error in FilterResult.group. Index out of bound.");
		}
		return groups.get(index);
	}

}
