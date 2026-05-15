package model;

public class Student extends User {

    private String group;
    private int absenceCount;

    public Student(int id,
                   String name,
                   String username,
                   String password,
                   String role,
                   String group) {

        super(id, name, username, password, role);

        this.group = group;
        this.absenceCount = 0;
    }

    public String getGroup() {
        return group;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void addAbsence() {
        absenceCount++;
    }

    @Override
    public String toString() {
        return super.toString() + " - Group: " + group;
    }
}