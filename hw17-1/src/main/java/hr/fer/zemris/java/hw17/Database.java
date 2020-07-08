package hr.fer.zemris.java.hw17;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * This class implements database for text searcher implemented as Konzola 
 * @author antonija
 *
 */
public class Database {

	/**
	 * list of stop words in croatian
	 */
	private static HashSet<String> stopWords = createStopWordsDB();

	/**
	 * list of all vocabulary words from text files 
	 */
	private static List<String> vocabular = createVocabularDB();

	/**
	 * idf numbers vector for this vocabulary
	 */
	private static double[] idfNumber = new double[vocabular.size()];

	/**
	 * hash map connects vectors to text file paths
	 */
	private static HashMap<String, int[]> map = createVectors();

	/**
	 * idf vector for this vocabulary
	 */
	private static double[] idf = createIDF();

	/**
	 * number of text files
	 */
	private static int D;

	/**
	 * This method creates stop words list
	 * @return HashSet<String> words
	 */
	private static HashSet<String> createStopWordsDB() {
		String fileName = "./src/main/resources/hrvatski_stoprijeci.txt";
		Charset charset = Charset.forName("UTF-8");
		List<String> result = null;
		try {
			result = Files.readAllLines(Paths.get(fileName), charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashSet<String> set = new HashSet<String>(result);
		return set;
	}

	/**
	 * This method creates idf vector
	 * @return double[] vector
	 */
	private static double[] createIDF() {
		double[] idf = new double[vocabular.size()];
		for (int i = 0; i < vocabular.size(); i++) {
			idf[i] = Math.log((double) D / idfNumber[i]);
		}
		return idf;
	}

	/**
	 * This method creates HashMap<String, int[]> map that conects paths and vectors
	 * @return double[] vector
	 */
	private static HashMap<String, int[]> createVectors() {

		// File folder = new File("./src/main/resources/clanci");
		File folder = new File(Konzola.path);

		HashMap<String, int[]> vectorsMap = new HashMap<String, int[]>();

		for (final File fileEntry : folder.listFiles()) {
			int[] vector = buildVector(fileEntry.getPath());
			vectorsMap.put(fileEntry.getPath(), vector);
		}

		return vectorsMap;
	}

	/**
	 * This method builds vectors for input path file
	 * @param path path to file
	 * @return vectors
	 */
	private static int[] buildVector(String path) {
		String text = readTextFromFile(path);

		int[] vector = createTfVector(text);

		for (int i = 0; i < vocabular.size(); i++) {
			if (vector[i] > 0)
				idfNumber[i]++;
		}

		return vector;
	}

	/**
	 * This method creates tf vector for given text
	 * @param text input text
	 * @return tf vector 
	 */
	public static int[] createTfVector(String text) {
		int[] vector = new int[vocabular.size()];

		String word = "";
		int currentIndex = 0;
		int wordIndex;

		while (currentIndex < text.length()) {
			if (Character.isAlphabetic(text.charAt(currentIndex))) {
				while (currentIndex < text.length() && Character.isAlphabetic(text.charAt(currentIndex))) {
					word += text.charAt(currentIndex);
					currentIndex++;
				}
				wordIndex = vocabular.indexOf(word.toLowerCase());
				if (wordIndex != -1) {
					vector[wordIndex]++;
				}
				word = "";
			} else {
				currentIndex++;
			}
		}
		return vector;
	}

	/**
	 * This method creates vocabulary 
	 * @return
	 */
	private static List<String> createVocabularDB() {

		File folder = new File(Konzola.path);

		List<String> words = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			D++;
			addToVocabulary(fileEntry.getAbsolutePath(), words);
		}
		return words;
	}

	/**
	 * This method reads text from file and parses it
	 * @param path path to file
	 * @param words list for building vocabulary
	 */
	private static void addToVocabulary(String path, List<String> words) {

		String text = readTextFromFile(path);

		parseText(text, words);
	}

	/**
	 * This method reads file in a string
	 * @param path path to file
	 * @return String
	 */
	public static String readTextFromFile(String path) {
		StringBuilder sb = new StringBuilder();
		try (Reader r = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(new FileInputStream(path)), Charset.defaultCharset()))) {

			char[] buff = new char[1024];
			while (true) {
				int procitano = r.read(buff);
				if (procitano < 1)
					break;
				sb.append(buff, 0, procitano);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * This method looks for words from text
	 * @param text input text
	 * @param words list of words
	 */
	private static void parseText(String text, List<String> words) {

		String word = "";
		int currentIndex = 0;

		while (currentIndex < text.length()) {
			if (Character.isAlphabetic(text.charAt(currentIndex))) {
				while (currentIndex < text.length() && Character.isAlphabetic(text.charAt(currentIndex))) {
					word += text.charAt(currentIndex);
					currentIndex++;
				}
				if (!words.contains(word.toLowerCase()) && !stopWords.contains(word.toLowerCase())) {
					words.add(word.toLowerCase());
				}
				word = "";
			} else {
				currentIndex++;
			}
		}
	}
	
	/**
	 * Getter for stopWords
	 * @return stopWords
	 */
	public static HashSet<String> getStopwords() {
		return stopWords;
	}

	/**
	 * Getter for vocabulary
	 * @return stopWords
	 */
	public static List<String> getVocabular() {
		return vocabular;
	}

	/**
	 * Getter for stop words
	 * @return stopWords
	 */
	public static HashSet<String> getStopWords() {
		return stopWords;
	}

	/**
	 * Getter for idf
	 * @return idf
	 */
	public static double[] getIdf() {
		return idf;
	}

	/**
	 * Getter for map
	 * @return map
	 */
	public static HashMap<String, int[]> getMap() {
		return map;
	}

	/**
	 * Getter for D
	 * @return D
	 */
	public static int getD() {
		return D;
	}

}
