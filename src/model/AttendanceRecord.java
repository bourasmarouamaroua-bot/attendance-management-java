package model;

public class AttendanceRecord {

    private Student student;
    private String date;
    private boolean present;
    private boolean justified;

    public AttendanceRecord(Student student,
                            String date,
                            boolean present,
                            boolean justified) {

        this.student = student;
        this.date = date;
        this.present = present;
        this.justified = justified;
    }

    public Student getStudent() {
        return student;
    }

    public String getDate() {
        return date;
    }

    public boolean isPresent() {
        return present;
    }

    public boolean isJustified() {
        return justified;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public void setJustified(boolean justified) {
        this.justified = justified;
    }

    @Override
    public String toString() {

        return student.getName()
                + " | Date: " + date
                + " | Present: " + present
                + " | Justified: " + justified;
    }
}