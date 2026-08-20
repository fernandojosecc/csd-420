/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 2 Programming Assignment - Binary I/O
 * Date: August 2026
 *
 * Description:
 * Reads the binary file created by DataWriter.java and displays every
 * integer and double stored in it. The file may contain data from
 * multiple runs of DataWriter since that program appends rather than
 * overwrites, so this program reads sessions of five ints followed by
 * five doubles in a loop until end of file is reached.
 */

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataReader {

    // Must match the file name used in DataWriter
    private static final String FILE_NAME = "contreras datafile.dat";

    // Same session size as the writer: 5 ints followed by 5 doubles
    private static final int ARRAY_SIZE = 5;

    public static void main(String[] args) {
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(FILE_NAME)))) {

            System.out.println("Reading data from " + FILE_NAME + ":\n");

            int sessionNumber = 1;

            // Each pass through the loop reads one full session written
            // by DataWriter. When there is no more data to read, an
            // EOFException is thrown and we exit the loop normally.
            while (true) {
                try {
                    System.out.println("Session " + sessionNumber + " integers:");
                    for (int i = 0; i < ARRAY_SIZE; i++) {
                        System.out.print(in.readInt() + " ");
                    }
                    System.out.println();

                    System.out.println("Session " + sessionNumber + " doubles:");
                    for (int i = 0; i < ARRAY_SIZE; i++) {
                        System.out.printf("%.4f ", in.readDouble());
                    }
                    System.out.println("\n");

                    sessionNumber++;
                } catch (EOFException eof) {
                    // Normal end of file reached, exit the read loop
                    break;
                }
            }

            System.out.println("Finished reading. Total sessions found: "
                    + (sessionNumber - 1));

        } catch (FileNotFoundException fnf) {
            System.err.println("File not found: " + FILE_NAME
                    + ". Run DataWriter first to create it.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}