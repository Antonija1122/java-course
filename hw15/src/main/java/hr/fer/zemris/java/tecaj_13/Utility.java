package hr.fer.zemris.java.tecaj_13;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * This is utility class. It offers methods for hashing ("SHA-256") strings. 
 * @author antonija
 *
 */
public class Utility {

	/**
	 * This method hashes ("SHA-256") input string 
	 * @param password input string
	 * @return hashes password
	 * @throws NoSuchAlgorithmException
	 */
	public static String getPaswordHash(String password) throws NoSuchAlgorithmException {
		
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		byte[] buff = password.getBytes();
		
		sha.update(buff, 0, buff.length);
		
		byte[] hash = sha.digest();

		String hashString = bytetohex(hash);

		return hashString;
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
