package service;

import interfaces.Storable;
import model.AttendanceRecord;
import model.Student;

import java.io.*;
import java.util.ArrayList;

public class FileManager implements Storable {

    private ArrayList<Student> students;
    private ArrayList<AttendanceRecord> records;

    public FileManager(ArrayList<Student> students,
                       ArrayList<AttendanceRecord> records) {
        this.students = students;
        this.records = records;
    }

    @Override
    public void saveToFile() {
        saveStudents();
        saveAttendanceRecords();
    }

    @Override
    public void loadFromFile() {
        loadStudents();
        loadAttendanceRecords();
    }

    public void saveStudents() {
        try {
            ObjectOutputStream output =
                    new ObjectOutputStream(new FileOutputStream("students.dat"));

            output.writeObject(students);
            output.close();

            System.out.println("Students saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public void loadStudents() {
        try {
            ObjectInputStream input =
                    new ObjectInputStream(new FileInputStream("students.dat"));

            students = (ArrayList<Student>) input.readObject();
            input.close();

            System.out.println("Students loaded successfully.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    public void saveAttendanceRecords() {
        try {
            ObjectOutputStream output =
                    new ObjectOutputStream(new FileOutputStream("attendance.dat"));

            output.writeObject(records);
            output.close();

            System.out.println("Attendance records saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving attendance: " + e.getMessage());
        }
    }

    public void loadAttendanceRecords() {
        try {
            ObjectInputStream input =
                    new ObjectInputStream(new FileInputStream("attendance.dat"));

            records = (ArrayList<AttendanceRecord>) input.readObject();
            input.close();

            System.out.println("Attendance records loaded successfully.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading attendance: " + e.getMessage());
        }
    }
}