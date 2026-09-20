package com.example.coursemap;

/**
 * Represents a student in the Firestore database.
 * This class follows the JavaBean convention required by Firestore
 * for automatic data serialization and deserialization.
 */
public class Student {
    private String usn;

    // A public no-argument constructor is required for Firestore deserialization.
    public Student() {
    }

    // Constructor to easily create a new student object.
    public Student(String usn) {
        this.usn = usn;
    }

    // Public getter for the 'usn' field, required by Firestore.
    public String getUsn() {
        return usn;
    }

    // Public setter for the 'usn' field. While not strictly required by
    // Firestore for reading data, it is good practice to include it.
    public void setUsn(String usn) {
        this.usn = usn;
    }
}
