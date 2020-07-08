package hr.fer.zemris.java.hw17;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
/**
 * This class implements query app for searching similar text files to input string. 
 * @author antonija
 *
 */
public class Konzola {

	/**
	 * path to folder with text files
	 */
	public static String path;

	/**
	 * List of result from query operation
	 */
	private static List<Result> results;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Morate unijeti put do clanaka u argumente.");
			System.exit(-1);
		}

		path = args[0];

		Scanner sc = new Scanner(System.in);
		System.out.println("Veličina riječnika je " + Database.getVocabular().size() + " riječi.\n");

		while (true) {
			System.out.printf("Enter command > ");
			if (sc.hasNextLine()) {
				String nextQuery = sc.nextLine().trim();

				if (nextQuery.equals("exit")) {
					break;

				} else {
					try {
						parseQuery(nextQuery);
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		sc.close();

	}

	/**
	 * This method parses order that user gave
	 * @param nextQuery command line 
	 */
	private static void parseQuery(String nextQuery) {

		if (nextQuery.startsWith("query")) {
			queryAction(nextQuery.replaceFirst("query", "").trim());
		} else if (nextQuery.startsWith("type")) {
			typeAction(nextQuery.replaceFirst("type", "").trim());
		} else if (nextQuery.startsWith("results")) {
			resultsAction(nextQuery.replaceFirst("results", "").trim());
		} else {
			throw new IllegalArgumentException("Nepoznata naredba.\n");
		}

	}

	/**
	 * This parameter looks for similar texts to input string
	 * @param text input string
	 */
	private static void queryAction(String text) {

		int[] tfQuery = Database.createTfVector(text);
		List<String> words = createWordList(text);

		double[] idf = Database.getIdf();
		double[] vectorQuery = new double[tfQuery.length];
		for (int i = 0; i < vectorQuery.length; i++) {
			vectorQuery[i] = tfQuery[i] * idf[i];
		}
		results = new ArrayList<>();
		for (Entry<String, int[]> entry : Database.getMap().entrySet()) {
			double sim = calculateSimilarity(vectorQuery, entry.getValue());
			if (sim > 0) {
				Result r = new Result();
				r.path = entry.getKey();
				r.sim = sim;
				results.add(r);
			}
		}
		System.out.println("Query is: " + words);
		System.out.println("Najboljih 10 rezultata:");

		Collections.sort(results, new SortResult());
		printResults();

	}

	/**
	 * This method prints results
	 */
	private static void printResults() {
		if (results == null) {
			System.out.println("Još nije unešena query naredba.\n");
		} else {
			int br = 0;
			for (Result r : results) {
				if (br < 10) {
					System.out.println("[" + br + "]" + " (" + round(r.sim, 4) + ") " + r.path);
					br++;
				}
			}
		}
		System.out.println();
	}

	/**
	 * This method saves words from input command line 
	 * @param text
	 * @return
	 */
	private static List<String> createWordList(String text) {
		List<String> words = new ArrayList<>();

		String word = "";
		int currentIndex = 0;

		while (currentIndex < text.length()) {
			if (Character.isAlphabetic(text.charAt(currentIndex))) {
				while (currentIndex < text.length() && Character.isAlphabetic(text.charAt(currentIndex))) {
					word += text.charAt(currentIndex);
					currentIndex++;
				}
				if (!words.contains(word.toLowerCase()) && Database.getVocabular().contains(word.toLowerCase())) {
					words.add(word.toLowerCase());
				}
				word = "";
			} else {
				currentIndex++;
			}
		}
		return words;
	}

	/**
	 * This method calculates similarities to results files 
	 * @param vectorQuery
	 * @param tfDoc
	 * @return
	 */
	private static double calculateSimilarity(double[] vectorQuery, int[] tfDoc) {

		double[] idf = Database.getIdf();
		double[] vectorDoc = new double[tfDoc.length];
		for (int i = 0; i < vectorDoc.length; i++) {
			vectorDoc[i] = tfDoc[i] * idf[i];
		}
		return sim(vectorQuery, vectorDoc);
	}

	/**
	 * This method calculates similarity
	 * @param vectorQuery
	 * @param vectorDoc
	 * @return
	 */
	private static double sim(double[] vectorQuery, double[] vectorDoc) {
		double sim = dotProduct(vectorQuery, vectorDoc)
				/ (Math.sqrt(dotProduct(vectorQuery, vectorQuery)) * Math.sqrt(dotProduct(vectorDoc, vectorDoc)));
		return sim;
	}

	/**
	 * This method rounds result 
	 * @param value input value
	 * @param decPos number of dec spots
	 * @return
	 */
	public static double round(double value, int decPos) {
		BigDecimal big = new BigDecimal(value);
		big = big.setScale(decPos, RoundingMode.HALF_UP);
		return big.doubleValue();
	}

	/**
	 * This method does type action. It gives result at index index.
	 * @param command
	 */
	private static void typeAction(String command) {
		int index;
		try {
			index = Integer.parseInt(command);
			Result r=results.get(index);
			printDocument(r);
		} catch (NumberFormatException e) {
			System.out.println("Neispravan unos naredbe type - >" + command +"\n");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Ilegalan unos indexa za naredbu type - >" + command +"\n");
		}

	}

	private static void printDocument(Result r) {
		System.out.println("Dokument: " + r.path);
		System.out.println("------------------------------------------------------------------- ");
		System.out.println(Database.readTextFromFile(r.path));
		System.out.println("------------------------------------------------------------------- \n");
	}

	private static void resultsAction(String command) {
		if (!command.isEmpty()) {
			System.out.println("Neispravan unos naredbe results - >" + command +"\n");
		}
		printResults();
	}

	public static double dotProduct(double[] a, double[] b) {
		double sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i] * b[i];
		}
		return sum;
	}

	/**
	 * THis class represents result object 
	 * @author antonija
	 *
	 */
	private static class Result {

		private double sim;
		private String path;

	}

	/**
	 * This method sorts results
	 * @author antonija
	 *
	 */
	static class SortResult implements Comparator<Result> {
		public int compare(Result a, Result b) {
			if (a.sim - b.sim > 0) {
				return -1;
			}
			return 1;
		}
	}

}
