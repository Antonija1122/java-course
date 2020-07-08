package searching.slagalica;

import java.util.Arrays;

/**
 * This class represent configuration of puzzle (state of puzzle). Private
 * variable int[] configuration stores state configuration and private variable
 * int indexOfZero represents index of blank space in puzzle.
 * 
 * @author antonija
 *
 */
public class KonfiguracijaSlagalice {

	/**
	 * configuration of current state of puzzle
	 */
	private int[] configuration;
	/**
	 * index of blank space in puzzle (in configuration)
	 */
	private int indexOfZero;

	/**
	 * Public constructor initializes configuration to input configuration and sets
	 * indexOfZero.
	 * 
	 * @param configuration input state configuration of puzzle
	 */
	public KonfiguracijaSlagalice(int[] configuration) {
		super();
		this.configuration = configuration;
		setIndexOfZero(configuration);
	}

	/**
	 * This method finds zero in configuration array and sets private variable
	 * indexOfZero to that found index.
	 * 
	 * @param elem configuration of current state of puzzle
	 */
	private void setIndexOfZero(int[] elem) {
		int index = 0;
		if (elem.length != 9)
			throw new IllegalArgumentException("Number of elements in given array must be 9.");
		for (int el : elem) {
			if (el > 8 || el < 0)
				throw new IllegalArgumentException("Arguments must be in range 0-8.");
			if (el == 0) {
				indexOfZero = index;
				return;
			}
			index++;
		}
		throw new IllegalArgumentException("There has to be one zero element in given array.");

	}

	/**
	 * String representation of KonfiguracijaSlagalice
	 */
	@Override
	public String toString() {
		return String.format("%d %d %d\n%d %d %d\n%d %d %d", configuration[0], configuration[1], configuration[2],
				configuration[3], configuration[4], configuration[5], configuration[6], configuration[7],
				configuration[8]).replaceFirst("0", "*");
	}

	/**
	 * Getter method for configuration
	 * @return configuration
	 */
	public int[] getPolje() {
		return configuration.clone();
	}

	/**
	 * Getter method for index of blank space
	 * @return indexOfZero
	 */
	public int indexOfSpace() {
		return indexOfZero;
	}

	/**
	 * Hash code for KonfiguracijaSlagalice
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(configuration);
		result = prime * result + indexOfZero;
		return result;
	}

	/**
	 * Equals method for KonfiguracijaSlagalice
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		if (!Arrays.equals(configuration, other.configuration))
			return false;
		if (indexOfZero != other.indexOfZero)
			return false;
		return true;
	}

}
