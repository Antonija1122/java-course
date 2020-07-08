package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This is program program Crypto that allows the user to encrypt/decrypt given
 * file using the AES cryptoalgorithm and the 128-bit encryption key or
 * calculate and check the SHA-256 file digest depending on users input. The
 * algorithms used here for encryption and decryption are block-based. They must
 * get a block of bytes in order to create new encrypted/decrypted block of
 * bytes. This is the reason for using methods update and doFinal.
 * 
 * 
 * @author antonija
 *
 */
public class Crypto {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, IOException,
			IllegalAccessException {

		Scanner sc = new Scanner(System.in);

		if (args[0].equals("checksha")) {

			if (args.length != 2) {
				throw new IllegalArgumentException("Wrong input");
			}

			System.out.println("Please provide expected sha-256 digest for hw06test.bin:");
			System.out.printf("> ");
			String check = sc.next();
			try {
				String hash = checksha(args);

				if (hash.equals(check)) {
					System.out.println("Digesting completed. Digest of " + args[1] + " matches expected digest.");
				} else {
					System.out.println("Digesting completed. Digest of " + args[1]
							+ " does not match the expected digest. Digest was: " + hash);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());

			}

		} else if (args[0].equals("decrypt")) {

			if (args.length != 3) {
				throw new IllegalArgumentException("Wrong input");
			}

			try {
				String[] keyAndVector = scannKeyandVector(sc);
				decrypt(keyAndVector[0], keyAndVector[1], args);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} else if (args[0].equals("encrypt")) {

			if (args.length != 3) {
				throw new IllegalArgumentException("Wrong input");
			}

			try {
				String[] keyAndVector = scannKeyandVector(sc);
				encrypt(keyAndVector[0], keyAndVector[1], args);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} else {
			sc.close();
			throw new IllegalArgumentException(
					"Invalid input: expected  \"checksha\", \"encrypt\" or \"decrypt\" not ->" + args[0]);
		}

		sc.close();

	}

	/**
	 * If the first word from user input is checksha then method checks if digesting
	 * of second argument is equal to next users input. 
	 * 
	 * @param args
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private static String checksha(String[] args) throws NoSuchAlgorithmException, IOException {

		MessageDigest sha = MessageDigest.getInstance("SHA-256");

		Path input = Paths.get(args[1]);

		try (InputStream is = Files.newInputStream(input)) {
			byte[] buff = new byte[1024];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				sha.update(buff, 0, r);
			}
			byte[] hash = sha.digest();

			String hashString = Util.bytetohex(hash);

			return hashString;

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		return null;

	}

	/**
	 * This method encrypts second argument file and writes encrypted data in output file (third user argument).
	 * @param key        input password
	 * @param initVector input initialization vector
	 * @param args       paths to files
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	private static void encrypt(String key, String initVector, String[] args)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IOException, BadPaddingException, IllegalBlockSizeException {

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(key), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(initVector));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);

		Path input = Paths.get(args[1]);

		Path output = Paths.get(args[2]);

		OutputStream os = Files.newOutputStream(output);

		try (InputStream is = Files.newInputStream(input)) {

			byte[] buff = new byte[1024];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				os.write(cipher.update(buff, 0, r));

			}
			os.write(cipher.doFinal());

			System.out.println("Encryption completed. Generated file " + args[2] + " based on file " + args[1]);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * This method decrypts second argument file and writes decrypted data in output file (third user argument).
	 * @param key        input password
	 * @param initVector input initialization vector
	 * @param args       paths to files
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static void decrypt(String key, String initVector, String[] args)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(key), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(initVector));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);

		Path input = Paths.get(args[1]);

		Path output = Paths.get(args[2]);

		OutputStream os = Files.newOutputStream(output);

		try (InputStream is = Files.newInputStream(input)) {
			byte[] buff = new byte[1024];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				os.write(cipher.update(buff, 0, r));
			}
			os.write(cipher.doFinal());

			System.out.println("Decryption completed. Generated file " + args[2] + " based on file " + args[1]);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Method sends message to user to input password and initialization vector and
	 * returns users input.
	 * 
	 * @param sc
	 * @return
	 */
	private static String[] scannKeyandVector(Scanner sc) {

		String[] array = new String[2];

		while (true) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.printf("> ");
			array[0] = sc.next();

			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.printf("> ");
			array[1] = sc.next();
			break;
		}

		return array;

	}

}
