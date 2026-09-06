/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 4 Programming Assignment - LinkedList Performance
 * Date: 29 August 2026
 *
 * Description:
 * Test program that measures how long it takes to traverse a LinkedList
 * using two different approaches: an Iterator (which walks node to node
 * sequentially) and the get(index) method (which walks from the head of
 * the list for every single call). The test runs against two list sizes,
 * 50,000 and 500,000 integers, so the performance difference between the
 * two approaches becomes obvious as the list grows.
 
 *
 */

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListPerformanceTest {

    public static void main(String[] args) {
        // Run the test with both required sizes so the scaling behavior
        // of each traversal method becomes visible in the output
        runTest(50_000);
        runTest(500_000);
    }

    /**
     * Builds a LinkedList of the given size filled with sequential integers,
     * then times how long it takes to traverse the list using an Iterator
     * versus using the get(index) method.
     */
    private static void runTest(int size) {
        System.out.println("=== Testing with " + String.format("%,d", size) + " integers ===");

        // Populate the list with values 0 through size-1
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        // Sanity check: confirm the list contains the expected number of elements
        System.out.println("List size: " + String.format("%,d", list.size()));

        // ---- Iterator traversal ----
        // Walks node to node in a single pass, O(n) total time
        long iteratorStart = System.currentTimeMillis();
        long iteratorSum = 0;
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            iteratorSum += iterator.next();
        }
        long iteratorTime = System.currentTimeMillis() - iteratorStart;

        System.out.println("Iterator traversal: " + iteratorTime + " ms"
                + " (checksum " + iteratorSum + ")");

        // ---- get(index) traversal ----
        // Every get call restarts the walk from the head, O(n^2) total time
        long getStart = System.currentTimeMillis();
        long getSum = 0;
        for (int i = 0; i < list.size(); i++) {
            getSum += list.get(i);
        }
        long getTime = System.currentTimeMillis() - getStart;

        System.out.println("get(index) traversal: " + getTime + " ms"
                + " (checksum " + getSum + ")");

        // Verify both approaches produced the same sum. If the checksums
        // match, both traversals visited every element in the correct order.
        if (iteratorSum == getSum) {
            System.out.println("Test PASSED: both traversals produced the same sum");
        } else {
            System.out.println("Test FAILED: checksums do not match");
        }

        System.out.println();
    }
}