/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 5 Programming Assignment - Sets, Sorting, and File I/O
 * Date: September 6th 2026
 *
 * Description:
 * Reads words from a text file named collection_of_words.txt and displays
 * all non-duplicate words in both ascending and descending order. The
 * file name is referenced directly in the program rather than passed as
 * a command-line argument, as required by the assignment.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;

public class WordSorter {

    // File name referenced directly in the program, not from the command line
    private static final String FILE_NAME = "collection_of_words.txt";

    public static void main(String[] args) {
        TreeSet<String> words = readWords(FILE_NAME);

        if (words == null) {
            return;                                              // read failed, message already printed
        }

        System.out.println("Total unique words: " + words.size());
        System.out.println();

        // TreeSet iterates in natural (ascending) order by default
        System.out.println("Ascending order:");
        System.out.println(words);
        System.out.println();

        // descendingSet returns a reverse-order view of the same data
        NavigableSet<String> descending = words.descendingSet();
        System.out.println("Descending order:");
        System.out.println(descending);
        System.out.println();

        // Simple test to verify correctness
        runTests(words);
    }

    /**
     * Reads all whitespace-separated words from the given file into a
     * TreeSet. Returns null if the file cannot be opened, after printing
     * an error message.
     */
    private static TreeSet<String> readWords(String fileName) {
        TreeSet<String> words = new TreeSet<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                // Lowercase everything so "Apple" and "apple" are treated as duplicates
                words.add(scanner.next().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file: " + fileName);
            return null;
        }

        return words;
    }

    /**
     * Basic tests that verify the collection behaves as expected:
     * duplicates are rejected, the set is sorted, and the descending
     * view reverses the order correctly.
     */
    private static void runTests(TreeSet<String> words) {
        System.out.println("--- Running tests ---");

        // Test 1: adding a duplicate word must not increase the set size
        int sizeBefore = words.size();
        boolean wasAdded = words.add("apple");                   // apple is already in the set
        boolean duplicateRejected = !wasAdded && words.size() == sizeBefore;
        System.out.println("Test 1 (duplicates rejected): "
                + (duplicateRejected ? "PASSED" : "FAILED"));

        // Test 2: the set's first element should equal the alphabetical minimum
        boolean firstIsSmallest = words.first().equals(words.iterator().next());
        System.out.println("Test 2 (ascending order): "
                + (firstIsSmallest ? "PASSED" : "FAILED"));

        // Test 3: descendingSet.first should equal the set's last (largest) element
        boolean descendingIsReversed = words.descendingSet().first().equals(words.last());
        System.out.println("Test 3 (descending order): "
                + (descendingIsReversed ? "PASSED" : "FAILED"));
    }
}