package com.luismonserratt.lms;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Manages patrons in memory: import, add, remove, list/print. */
public class LibraryManager {
    // Keeps insertion order for printing
    private final Map<String, Patron> patrons = new LinkedHashMap<>();

    /** Load file lines (id-name-address-fine). Skips invalid lines. */
    public ImportResult loadFromFile(Path path) {
        ImportResult result = new ImportResult();
        if (path == null || !Files.exists(path)) {
            result.failLine(String.valueOf(path), "File not found");
            return result;
        }
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                result.incrementTotal();
                String[] p = line.split("-", 4);
                if (p.length != 4) { result.failLine(line, "Bad format"); continue; }
                try {
                    double fine = Double.parseDouble(p[3].trim());
                    if (addPatron(p[0], p[1], p[2], fine)) result.incrementSuccess();
                    else result.failLine(line, "Invalid data");
                } catch (NumberFormatException e) {
                    result.failLine(line, "Bad fine");
                }
            }
        } catch (IOException e) {
            result.failLine(path.toString(), "I/O: " + e.getMessage());
        }
        return result;
    }

    /** Add if valid and not duplicated. */
    public boolean addPatron(String id, String name, String address, double fine) {
        if (id == null || !id.matches("\\d{7}")) return false;
        if (name == null || name.isBlank()) return false;
        if (address == null || address.isBlank()) return false;
        if (fine < 0 || fine > 250) return false;
        if (patrons.containsKey(id)) return false;

        patrons.put(id.trim(), new Patron(id.trim(), name.trim(), address.trim(), fine));
        return true;
    }

    /** Remove by ID. */
    public boolean removePatron(String id) {
        if (id == null) return false;
        return patrons.remove(id.trim()) != null;
    }

    /** Get all patrons. */
    public List<Patron> getAllPatrons() {
        return new ArrayList<>(patrons.values());
    }

    /** Print patrons as a simple table. */
    public void printPatrons() {
        if (patrons.isEmpty()) { System.out.println("No patrons"); return; }
        System.out.println("\nID       | Name              | Address              | Fine");
        for (Patron p : patrons.values()) {
            System.out.printf("%-8s | %-17s | %-20s | %.2f%n",
                    p.getId(), p.getName(), p.getAddress(), p.getFine());
        }
    }
}
