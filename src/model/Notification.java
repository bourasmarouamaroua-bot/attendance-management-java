package model;

import java.io.Serializable;

public class Notification implements Serializable {

    private int id;
    private Student student;
    private String adminName;
    private String type;
    private String message;
    private String date;

    public Notification(int id,
                        Student student,
                        String adminName,
                        String type,
                        String message,
                        String date) {

        this.id = id;
        this.student = student;
        this.adminName = adminName;
        this.type = type;
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return type
                + " from "
                + adminName
                + " | "
                + date
                + "\n"
                + message;
    }
}