package model;

import java.util.ArrayList;

public class Group implements java.io.Serializable {

    private int groupId;
    private String groupName;
    private ArrayList<Student> students;

    public Group(int groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.students = new ArrayList<>();
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    @Override
    public String toString() {
        return groupName + " - Students: " + students.size();
    }
}