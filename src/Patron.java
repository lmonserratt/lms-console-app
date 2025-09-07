package com.luismonserratt.lms;

/** Simple data model for a patron. */
public class Patron {
    private final String id;      // 7 digits
    private final String name;    // full name
    private final String address; // mailing address
    private final double fine;    // 0..250

    public Patron(String id, String name, String address, double fine) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.fine = fine;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getFine() { return fine; }
}
