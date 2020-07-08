package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

public class Utility {

	/**
	 * Public method getPath excepts one string argument (path to file or folder) in
	 * form: "C:/Program Files/Program1/info.txt" or C:/tmp/informacije.txt and
	 * returns path in form: C:/tmp/informacije.txt. Escaping \\ is allowed only for
	 * chars '"' and '\'.
	 * 
	 * @param text input path
	 * @return String path in format: C:/tmp/informacije.txt
	 */
	public static String getPath(String text) {
		text = text.trim();
		int currentIndex = 0;
		String next = "";
		char[] data = text.toCharArray();

		if (text.charAt(0) == '\"') {

			if (!text.matches("\"(.*)\"")) {
				throw new IllegalArgumentException("Unable to parse path -> " + text);
			}

			currentIndex++;
			while (currentIndex < data.length && data[currentIndex] != '\"') {
				if (data[currentIndex] == '\\') {
					if (currentIndex != data.length - 1 && data[currentIndex + 1] != '"'
							&& data[currentIndex + 1] != '\\') {

						next = next + data[currentIndex] + data[currentIndex + 1];
						++currentIndex;

					} else {
						next = next + data[currentIndex + 1];
						++currentIndex;
					}
				} else {
					next = next + data[currentIndex];
				}
				++currentIndex;
			}

			++currentIndex;
			return next;
		} else {
			return text;
		}
	}

	/**
	 * public method separateArguments excepts one string argument and returns array
	 * of strings. Strings can be in form \"*\" or just *. If the form is \"*\" then
	 * string is allowed to contain spaces otherwise strings are separated. Example:
	 * "\"C:/Program Files/Program1/info.txt\" bla novo" is separated in three
	 * strings: "\"C:/Program Files/Program1/info.txt\"", "bla" and "novo"
	 * 
	 * @param text
	 * @return String[] strings
	 */

	public static String[] separateArguments(String text) {

		List<String> strings = new ArrayList<>();

		text = text.trim();

		if (text.equals("")) {
			return strings.toArray(new String[0]);
		}

		String[] parts = text.split("\\s+");
		int index = 0;

		while (index < parts.length) {
			String current = "";
			if (parts[index].matches("\"(.*)\"")) {
				current = parts[index];
				index++;
				strings.add(current);
			}

			else if (parts[index].matches("\"(.*)")) {
				current = current + parts[index];
				index++;
				while (!parts[index].matches("(.*)\"")) {
					current = current + " " + parts[index];
					index++;
				}
				current = current + " " + parts[index];
				index++;
				strings.add(current);
			} else {
				current = parts[index];
				strings.add(current);
				index++;
			}
		}

		return strings.toArray(new String[0]);

	}

	/**
	 * Method hextobyte(keyText) takes hex-encoded String and returns appropriate
	 * byte[]. If string is not valid (odd-sized, has invalid characters, …) throws
	 * an IllegalArgumentException. For zero-length string, method returns
	 * zero-length byte array. Method hextobyte supports both uppercase letters and
	 * lowercase letters.
	 * 
	 * @param keyText input hex-encoded String
	 * @return byte[] array
	 */
	public static byte[] hextobyte(String keyText) {

		int length = keyText.length();

		if (length % 2 != 0) {
			throw new IllegalArgumentException("keyTexts length should not be odd-sized");
		}

		for (int i = 0; i < length; i++) {
			if (keyText.charAt(i) >= '0' && keyText.charAt(i) <= '9'
					|| keyText.charAt(i) >= 'a' && keyText.charAt(i) <= 'f'
					|| keyText.charAt(i) >= 'A' && keyText.charAt(i) <= 'F') {
				continue;
			} else {
				throw new IllegalArgumentException("keyTexts has illegal input character -> " + keyText.charAt(i));
			}
		}

		if (length == 0) {
			return new byte[0];
		}

		byte[] byteReturn = new byte[length / 2];

		for (int i = 0; i < byteReturn.length; i += 1) {
			byteReturn[i] = (byte) Integer.parseInt(keyText.substring(i * 2, i * 2 + 2), 16);
		}
		return byteReturn;
	}

	/**
	 * Method bytetohex(bytearray) takes a byte array and creates its hex-encoding:
	 * for each byte of given array, two characters are returned in string, in
	 * big-endian notation. For zero-length array an empty string is returned.
	 * Method uses lowercase letters for creating encoding.
	 * 
	 * @param bytearray byte[]
	 * @return hex-encoded String
	 */
	public static String bytetohex(byte bajt) {

		return String.format("%02x", bajt);

	}

}
