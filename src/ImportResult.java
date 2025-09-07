package com.luismonserratt.lms;

import java.util.ArrayList;
import java.util.List;

/** Tracks import totals and first errors. */
public class ImportResult {
    private int total, success;
    private final List<String> fails = new ArrayList<>();

    public void incrementTotal() { total++; }
    public void incrementSuccess() { success++; }
    public void failLine(String line, String reason) { fails.add(reason + ": " + line); }

    public String summary() {
        return "\nImported: " + success + "/" + total + " (" + fails.size() + " failed)" +
                (fails.isEmpty() ? "" : "\nErrors: " + String.join(", ", fails));
    }
}
