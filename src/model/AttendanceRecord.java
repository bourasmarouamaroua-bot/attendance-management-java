package model;

import enums.AttendanceStatus;

public class AttendanceRecord implements java.io.Serializable {

    private Student student;
    private Session session;
    private AttendanceStatus status;

    public AttendanceRecord(Student student, Session session, AttendanceStatus status) {
        this.student = student;
        this.session = session;
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public Session getSession() {
        return session;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return student.getName()
                + " | "
                + session.toString()
                + " | Status: "
                + status;
    }
}