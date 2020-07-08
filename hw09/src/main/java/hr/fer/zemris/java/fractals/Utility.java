package hr.fer.zemris.java.fractals;

/**
 * This class is used for parsing users input for complex numbers. It has two
 * methods for parsing real and imaginary parts of complex number.
 * 
 * @author antonija
 *
 */
public class Utility {

	/**
	 * Method is used to help convert input string in Newton class to complex number.
	 * 
	 * @param s input string
	 * @return String[] string array with string values of real and imaginary parts
	 *         of complex number
	 */
	public static String[] parsing(String s) {
		s = s.trim().replace(" ", "");
		String realString = "";
		String imagString = "";

		if (s.equals("i")) {
			imagString = "1";
			realString = "0";
		} else if (s.equals("-i")) {
			imagString = "-1";
			realString = "0";
		} else {
			try {
				Double.parseDouble(s);
				imagString = "0";
				realString += s;
			} catch (NumberFormatException e) {
				try {
					Double.parseDouble(s.replaceFirst("i", ""));
					imagString = s.replaceFirst("i", "");
					realString += "0";
				} catch (NumberFormatException e2) {
					String[] tokens = s.split("i");
					int index=tokens[0].length() - 1;
					realString += tokens[0].substring(0, index);
					imagString += tokens[0].charAt(index) + tokens[1];
				}
			}
		}
		String[] realAndImaginary = new String[2];
		realAndImaginary[0] = realString;
		realAndImaginary[1] = imagString;
		return realAndImaginary;
	}

}
