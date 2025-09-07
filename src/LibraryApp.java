// =============================================
// LMS — Console App (Java 17+)
// Package: com.luismonserratt.lms
// Author: Luis Augusto Monserratt Alvarado
// =============================================
// Files:
// - LibraryApp.java (main + CLI)
// - LibraryManager.java (logic)
// - Patron.java (entity)
// - ImportResult.java (import summary)
// Data format (one per line): id-name-address-fine
// Example: 1245789-Sarah Jones-1136 Gorden Ave. Orlando, FL 32822-40.54
package com.luismonserratt.lms;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * LibraryApp: Command-Line Interface for the Library Management System.
 *
 * Responsibilities:
 * - Load initial patrons from the bundled resource file (patrons_sample.txt).
 * - Show a menu to the user and process their input.
 * - Delegate all data operations (import, add, remove, list) to LibraryManager.
 */
public class LibraryApp {
    // Scanner to read user input from console
    private final Scanner sc = new Scanner(System.in);

    // Business logic layer that manages patrons
    final LibraryManager manager = new LibraryManager();

    /**
     * Main entry point of the program.
     * Creates a new instance of LibraryApp and starts execution.
     */
    public static void main(String[] args) {
        new LibraryApp().run();
    }

    /**
     * Main workflow of the program.
     * 1. Loads the sample patrons file from resources at startup.
     * 2. Prints summary of the import result.
     * 3. Enters a loop to display the menu and process user choices.
     */
    private void run() {
        System.out.println("\n=== Library Management System ===");

        // --- Step 1: Load patrons_sample.txt from the resources folder ---
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("patrons_sample.txt")) {
            if (in != null) {
                // Copy the resource to a temporary file so it can be handled as a Path
                Path tempFile = Files.createTempFile("patrons_sample", ".txt");
                Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);

                // Ask LibraryManager to load patrons from this temp file
                ImportResult result = manager.loadFromFile(tempFile);

                // Print summary: how many lines loaded successfully, how many failed
                System.out.println(result.summary());
            } else {
                // If the file is missing from resources
                System.out.println("patrons_sample.txt not found in resources.");
            }
        } catch (Exception e) {
            // Catch and print any unexpected error during loading
            System.out.println("Error loading sample file: " + e.getMessage());
        }

        // --- Step 2: Show menu until user chooses to exit ---
        boolean running = true;
        while (running) {
            menu(); // print available options
            switch (sc.nextLine().trim()) {
                case "1" -> importFile();           // Manually import from a file path
                case "2" -> addPatron();            // Add a new patron manually
                case "3" -> removePatron();         // Remove an existing patron
                case "4" -> manager.printPatrons(); // Print all patrons
                case "5" -> running = false;        // Exit loop/program
                default -> System.out.println("Invalid option"); // Any wrong input
            }
        }
    }

    /**
     * Print the menu options to the console.
     */
    private void menu() {
        System.out.println("\n1) Import patrons from file");
        System.out.println("2) Add patron manually");
        System.out.println("3) Remove patron by ID");
        System.out.println("4) List all patrons");
        System.out.println("5) Exit");
        System.out.print("Choose: ");
    }

    /**
     * Ask user for a file path, load patrons from that file,
     * and print a summary of the import.
     */
    private void importFile() {
        System.out.print("File path: ");
        Path path = Path.of(sc.nextLine().trim());
        System.out.println(manager.loadFromFile(path).summary());
    }

    /**
     * Ask user for patron details (ID, name, address, fine).
     * Validates and adds the patron through LibraryManager.
     * Prints result (success or error).
     */
    private void addPatron() {
        System.out.print("ID (7 digits): ");
        String id = sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Address: ");
        String addr = sc.nextLine();

        System.out.print("Fine (0-250): ");
        try {
            double fine = Double.parseDouble(sc.nextLine());
            boolean added = manager.addPatron(id, name, addr, fine);
            System.out.println(added ? "Added" : "Invalid or duplicate");
        } catch (NumberFormatException e) {
            // Handles when user enters a non-numeric fine
            System.out.println("Invalid fine");
        }
    }

    /**
     * Ask user for patron ID to remove.
     * Removes the patron if exists, otherwise prints "Not found".
     */
    private void removePatron() {
        System.out.print("ID to remove: ");
        boolean removed = manager.removePatron(sc.nextLine());
        System.out.println(removed ? "Removed" : "Not found");
    }
}
