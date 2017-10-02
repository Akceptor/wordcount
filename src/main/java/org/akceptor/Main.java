package org.akceptor;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	/**
	 * Characters used to separate words
	 */
	private static final String SEPARATOR_REGEX = "[, ;]";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Insufficient arguments");
		} else {
			Path path = Paths.get(args[0]);
			int size = Integer.parseInt(args[1]);
			try (Stream<String> lines = Files.lines(path)) { // Auto close
				// WordCount
				Map<String, Long> limitedSortedWordCount = limitSortedMap(size,
						sortByValues(countWordsInSteram(lines)));
				// Print
				limitedSortedWordCount.forEach((k, v) -> System.out.println(k + " - " + v));
			} catch (NoSuchFileException nsfe) {
				System.err.println("File not found: " + path.toString());
			} catch (AccessDeniedException ade) {
				System.err.println("Access denied or resource is directory: " + path.toString());
			} catch (IOException e) {
				System.err.println("I/O Exception while accessing: " + path.toString());
			}
		}
	}

	/**
	 * Implements word counting in string stream
	 * @param stringStream
	 * @return map where each word is a key and word quantity is a value
	 */
	protected static Map<String, Long> countWordsInSteram(Stream<String> stringStream) {
		return stringStream.flatMap(line -> Arrays.stream(line.trim().split(SEPARATOR_REGEX)))
				.map(word -> word.toLowerCase().trim()).filter(word -> word.length() > 0)
				.map(word -> new SimpleEntry<>(word, 1))
				.collect(Collectors.groupingBy(SimpleEntry::getKey, Collectors.counting()));
	}

	/**
    * Returns the first N elements from sorted map
    * @param n number of elements to return
    * @param wholeMap initial map
    * @return
    */
	protected static Map<String, Long> limitSortedMap(int n, Map<String, Long> wholeMap) {
		return wholeMap.entrySet().stream().limit(n).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
				(newValue, oldValue) -> newValue, LinkedHashMap::new));
	}

	/**
    * Sorts a map by values (descending)
    * @param unsortedMap map to sort
    * @return sorted LinkedHashMap
    */
	protected static Map<String, Long> sortByValues(Map<String, Long> unsortedMap) {
		return unsortedMap.entrySet().stream().sorted(Map.Entry.<String, Long> comparingByValue().reversed())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (newValue, oldValue) -> newValue,
						LinkedHashMap::new));
	}
}
