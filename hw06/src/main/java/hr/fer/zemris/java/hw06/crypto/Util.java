package hr.fer.zemris.java.hw06.crypto;

/**
 * Class Util has two public static methods: hextobyte(keyText) and
 * bytetohex(bytearray)
 * 
 * @author antonija
 *
 */
public class Util {

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
	public static String bytetohex(byte[] bytearray) {

		if (bytearray.length == 0)
			return "";

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < bytearray.length; i += 1) {

			sb.append(String.format("%02x", bytearray[i]));

		}

		return sb.toString();
	}

}
