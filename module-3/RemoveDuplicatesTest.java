/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 3 Programming Assignment - Generic Remove Duplicates
 * Date: August 26th 2026
 *
 * Description:
 * Test program that demonstrates a generic static method for removing
 * duplicate values from an ArrayList. The method returns a new ArrayList
 * containing every original value with duplicates removed, preserving
 * the order in which values were first seen in the original list.
 */

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;

public class RemoveDuplicatesTest {

    // Number of random values placed in the original list
    private static final int LIST_SIZE = 50;

    // Random values are chosen between 1 and MAX_VALUE inclusive
    private static final int MAX_VALUE = 20;

    /**
     * Generic static method that returns a new ArrayList containing every
     * original value from the input list with duplicates removed.
     *
     * The <E> before the return type declares E as a type parameter scoped
     * to this method, so it can accept an ArrayList of any element type
     * while still preserving full type safety at compile time.
     *
     * A LinkedHashSet is used internally because it automatically rejects
     * duplicates and remembers the order in which elements were first added,
     * so the output list mirrors the ordering of the original.
     */
    public static <E> ArrayList<E> removeDuplicates(ArrayList<E> list) {
        LinkedHashSet<E> uniqueSet = new LinkedHashSet<>(list);
        return new ArrayList<>(uniqueSet);
    }

    public static void main(String[] args) {
        Random random = new Random();
        ArrayList<Integer> original = new ArrayList<>();

        // Populate the list with 50 random integers from 1 through 20
        for (int i = 0; i < LIST_SIZE; i++) {
            original.add(random.nextInt(MAX_VALUE) + 1);
        }

        // Call the generic method to build a duplicate-free copy
        ArrayList<Integer> deduplicated = removeDuplicates(original);

        System.out.println("Original list (" + original.size() + " values):");
        System.out.println(original);
        System.out.println();

        System.out.println("Deduplicated list (" + deduplicated.size() + " values):");
        System.out.println(deduplicated);
    }
}