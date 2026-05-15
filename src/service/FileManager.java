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
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream("students.dat"));

            outputStream.writeObject(students);
            outputStream.close();

            System.out.println("Students saved successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadStudents() {
        try {
            ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("students.dat"));

            students = (ArrayList<Student>) inputStream.readObject();
            inputStream.close();

            System.out.println("Students loaded successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveAttendanceRecords() {
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream("attendance.dat"));

            outputStream.writeObject(records);
            outputStream.close();

            System.out.println("Attendance records saved successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadAttendanceRecords() {
        try {
            ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("attendance.dat"));

            records = (ArrayList<AttendanceRecord>) inputStream.readObject();
            inputStream.close();

            System.out.println("Attendance records loaded successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void exportStudentsToTextFile() {
        try {
            PrintWriter writer = new PrintWriter("students.txt");

            for (Student student : students) {
                writer.println("ID: " + student.getId());
                writer.println("Name: " + student.getName());
                writer.println("Username: " + student.getUsername());
                writer.println("Group: " + student.getGroup());
                writer.println("---------------------");
            }

            writer.close();

            System.out.println("Students exported to students.txt");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void exportAttendanceToTextFile() {
        try {
            PrintWriter writer = new PrintWriter("attendance.txt");

            for (AttendanceRecord record : records) {
                writer.println("Student: " + record.getStudent().getName());
                writer.println("Session: " + record.getSession().getModule().getModuleName());
                writer.println("Date: " + record.getSession().getDate());
                writer.println("Status: " + record.getStatus());
                writer.println("---------------------");
            }

            writer.close();

            System.out.println("Attendance exported to attendance.txt");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void exportReportToTextFile(String reportContent) {
        try {
            PrintWriter writer = new PrintWriter("report.txt");

            writer.println(reportContent);
            writer.close();

            System.out.println("Report exported to report.txt");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}