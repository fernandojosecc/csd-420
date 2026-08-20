/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 2 Programming Assignment - Binary I/O
 * Date: August 2026
 *
 * Description:
 * Generates two arrays, one of five random integers and one of five
 * random doubles, then writes them to a binary file. If the file does
 * not exist it is created. If the file already exists the new data is
 * appended to the end of it. Uses DataOutputStream wrapped around a
 * BufferedOutputStream and FileOutputStream to write primitive types
 * efficiently in binary format.
 */

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class DataWriter {

    // File name matches the format specified in the assignment
    private static final String FILE_NAME = "contreras datafile.dat";

    // Number of values in each array
    private static final int ARRAY_SIZE = 5;

    public static void main(String[] args) {
        Random random = new Random();
        int[] ints = new int[ARRAY_SIZE];
        double[] doubles = new double[ARRAY_SIZE];

        // Fill both arrays with random values in reasonable ranges
        for (int i = 0; i < ARRAY_SIZE; i++) {
            ints[i] = random.nextInt(100);          // integers 0 through 99
            doubles[i] = random.nextDouble() * 100; // doubles 0.0 through ~100.0
        }

        // Try-with-resources auto-closes the stream even if an error occurs.
        // Passing "true" as the second argument to FileOutputStream opens
        // the file in append mode so existing data is preserved.
        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(FILE_NAME, true)))) {

            // Write the five integers first, then the five doubles.
            // writeInt uses 4 bytes per value and writeDouble uses 8.
            System.out.println("Writing integers to " + FILE_NAME + ":");
            for (int value : ints) {
                out.writeInt(value);
                System.out.print(value + " ");
            }
            System.out.println();

            System.out.println("Writing doubles to " + FILE_NAME + ":");
            for (double value : doubles) {
                out.writeDouble(value);
                System.out.printf("%.4f ", value);
            }
            System.out.println("\n");

            System.out.println("Data successfully written.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}