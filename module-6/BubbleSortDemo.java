/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 6 Programming Assignment - Generic Bubble Sort
 * Date: September 9th 2026
 *
 * Description:
 * Demonstrates two generic implementations of bubble sort. The first
 * version uses the Comparable interface, so it can sort any array whose
 * element type defines its own natural ordering (Integer, String, etc.).
 * The second version uses the Comparator interface, so the caller can
 * pass in any ordering rule at call time without modifying the element
 * class itself.
 */

import java.util.Arrays;
import java.util.Comparator;

public class BubbleSortDemo {

    /**
     * Generic bubble sort using the Comparable interface. Works on any
     * array whose element type implements Comparable, meaning the type
     * already knows how to order itself.
     */
    public static <E extends Comparable<E>> void bubbleSort(E[] array) {
        boolean swapped;                                         // tracks whether the current pass changed anything
        int n = array.length;

        for (int pass = 0; pass < n - 1; pass++) {
            swapped = false;

            // Each pass shortens by one because the largest unsorted
            // element ends up at position (n - 1 - pass) after this pass
            for (int i = 0; i < n - 1 - pass; i++) {
                if (array[i].compareTo(array[i + 1]) > 0) {
                    // Adjacent elements are in the wrong order, swap them
                    E temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                }
            }

            // Optimization: if no swaps happened, the array is already sorted
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Generic bubble sort using the Comparator interface. Works on any
     * array of any type, since the ordering rule is provided by the
     * caller through the Comparator argument instead of being baked
     * into the element class.
     */
    public static <E> void bubbleSort(E[] array, Comparator<? super E> comparator) {
        boolean swapped;
        int n = array.length;

        for (int pass = 0; pass < n - 1; pass++) {
            swapped = false;

            for (int i = 0; i < n - 1 - pass; i++) {
                // Comparator.compare replaces compareTo here
                if (comparator.compare(array[i], array[i + 1]) > 0) {
                    E temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Simple item class used to demonstrate the Comparator version by
     * showing that the same array can be sorted multiple ways without
     * modifying the class itself.
     */
    static class Item {
        String name;
        int rarity;

        Item(String name, int rarity) {
            this.name = name;
            this.rarity = rarity;
        }

        @Override
        public String toString() {
            return name + "(" + rarity + ")";
        }
    }

    public static void main(String[] args) {
        // ---- Test 1: Comparable version with Integers ----
        Integer[] numbers = {42, 8, 15, 23, 4, 16, 108, 55};
        System.out.println("Before sort (Comparable, Integer): " + Arrays.toString(numbers));
        bubbleSort(numbers);
        System.out.println("After sort  (Comparable, Integer): " + Arrays.toString(numbers));
        testSorted(numbers, "Test 1 (Comparable / Integer ascending)");
        System.out.println();

        // ---- Test 2: Comparable version with Strings ----
        String[] words = {"cherry", "apple", "date", "banana", "elderberry"};
        System.out.println("Before sort (Comparable, String): " + Arrays.toString(words));
        bubbleSort(words);
        System.out.println("After sort  (Comparable, String): " + Arrays.toString(words));
        testSorted(words, "Test 2 (Comparable / String alphabetical)");
        System.out.println();

        // ---- Test 3: Comparator version, sorting Items by rarity ascending ----
        Item[] inventory = {
                new Item("Sword", 3),
                new Item("Potion", 1),
                new Item("Amulet", 5),
                new Item("Shield", 2)
        };
        System.out.println("Before sort (Comparator, by rarity): " + Arrays.toString(inventory));
        bubbleSort(inventory, Comparator.comparingInt(item -> item.rarity));
        System.out.println("After sort  (Comparator, by rarity): " + Arrays.toString(inventory));
        testSortedBy(inventory, Comparator.comparingInt(item -> item.rarity),
                "Test 3 (Comparator / Item by rarity ascending)");
        System.out.println();

        // ---- Test 4: Same Items, different Comparator (by name alphabetical) ----
        // Reuse the array from Test 3 to prove one class can be sorted many ways
        System.out.println("Before sort (Comparator, by name): " + Arrays.toString(inventory));
        bubbleSort(inventory, Comparator.comparing(item -> item.name));
        System.out.println("After sort  (Comparator, by name): " + Arrays.toString(inventory));
        testSortedBy(inventory, Comparator.comparing(item -> item.name),
                "Test 4 (Comparator / Item by name alphabetical)");
    }

    /**
     * Verifies that a Comparable array is sorted in ascending order.
     */
    private static <E extends Comparable<E>> void testSorted(E[] array, String label) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                System.out.println(label + ": FAILED at index " + i);
                return;
            }
        }
        System.out.println(label + ": PASSED");
    }

    /**
     * Verifies that an array is sorted according to the given Comparator.
     */
    private static <E> void testSortedBy(E[] array, Comparator<? super E> comparator, String label) {
        for (int i = 0; i < array.length - 1; i++) {
            if (comparator.compare(array[i], array[i + 1]) > 0) {
                System.out.println(label + ": FAILED at index " + i);
                return;
            }
        }
        System.out.println(label + ": PASSED");
    }
}