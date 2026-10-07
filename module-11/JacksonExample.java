/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 11 Programming Assignment - Jackson JSON Example
 * Date: October 2026
 *
 * Description:
 * Demonstrates the Jackson JSON library's data binding approach by
 * converting a Java object to a JSON string (serialization) and then
 * back into a Java object (deserialization) using ObjectMapper. This
 * example was adapted from the Baeldung Jackson tutorial and uses a
 * custom Book class with multiple fields to show how Jackson handles
 * typed properties automatically.
 *
 * To compile and run, place the three Jackson JARs (jackson-core,
 * jackson-annotations, jackson-databind) in the same folder, then:
 *   javac -cp "jackson-core-2.17.0.jar:jackson-annotations-2.17.0.jar:jackson-databind-2.17.0.jar:." JacksonExample.java
 *   java  -cp "jackson-core-2.17.0.jar:jackson-annotations-2.17.0.jar:jackson-databind-2.17.0.jar:." JacksonExample
 *
 * On Windows, use semicolons (;) instead of colons (:) in the classpath.
 *
 * Source: Adapted from https://www.baeldung.com/jackson-object-mapper-tutorial
 */

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonExample {

    // Simple POJO used to demonstrate data binding. Jackson maps JSON
    // fields to these Java fields automatically based on their names.
    public static class Book {
        private String title;
        private String author;
        private int year;
        private boolean inStock;

        // Default constructor is required for Jackson deserialization
        public Book() {}

        public Book(String title, String author, int year, boolean inStock) {
            this.title = title;
            this.author = author;
            this.year = year;
            this.inStock = inStock;
        }

        // Getters and setters are what Jackson uses to read and write
        // field values when it is converting objects to and from JSON
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }

        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }

        public boolean isInStock() { return inStock; }
        public void setInStock(boolean inStock) { this.inStock = inStock; }

        @Override
        public String toString() {
            return "Book{title='" + title + "', author='" + author
                    + "', year=" + year + ", inStock=" + inStock + "}";
        }
    }

    public static void main(String[] args) throws Exception {
        // ObjectMapper is the central Jackson class for data binding.
        // One instance can serialize and deserialize any number of objects.
        ObjectMapper mapper = new ObjectMapper();

        // Enable pretty printing so the JSON output is readable
        mapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);

        // --- Serialization: Java object to JSON string ---
        Book book = new Book("Introduction to Java", "Y. Daniel Liang", 2019, true);
        String json = mapper.writeValueAsString(book);

        System.out.println("--- Serialization (Java -> JSON) ---");
        System.out.println(json);
        System.out.println();

        // --- Deserialization: JSON string to Java object ---
        String incomingJson = """
                {
                    "title": "Clean Code",
                    "author": "Robert C. Martin",
                    "year": 2008,
                    "inStock": false
                }
                """;

        Book parsedBook = mapper.readValue(incomingJson, Book.class);

        System.out.println("--- Deserialization (JSON -> Java) ---");
        System.out.println(parsedBook);
        System.out.println("Title field accessed directly: " + parsedBook.getTitle());
        System.out.println();

        // Quick verification test confirming the round trip worked
        boolean passed = parsedBook.getTitle().equals("Clean Code")
                && parsedBook.getAuthor().equals("Robert C. Martin")
                && parsedBook.getYear() == 2008
                && !parsedBook.isInStock();

        System.out.println("Round-trip test: " + (passed ? "PASSED" : "FAILED"));
    }
}