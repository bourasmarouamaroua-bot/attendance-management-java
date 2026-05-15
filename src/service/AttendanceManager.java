package service;

import enums.AttendanceStatus;
import exceptions.DuplicateStudentException;
import model.AttendanceRecord;
import model.Session;
import model.Student;

import java.util.ArrayList;

public class AttendanceManager {

    private ArrayList<Student> students;
    private ArrayList<AttendanceRecord> records;
    private ArrayList<Session> sessions;

    public AttendanceManager() {

        students = new ArrayList<>();
        records = new ArrayList<>();
        sessions = new ArrayList<>();
    }

    public void addStudent(Student student)
            throws DuplicateStudentException {

        if (findStudentById(student.getId()) != null) {

            throw new DuplicateStudentException(
                    "Student with ID "
                            + student.getId()
                            + " already exists."
            );
        }

        students.add(student);
    }

    public void addSession(Session session) {

        sessions.add(session);
    }

    public void recordAttendance(AttendanceRecord record) {

        records.add(record);
    }

    public ArrayList<Student> getStudents() {

        return students;
    }

    public ArrayList<AttendanceRecord> getRecords() {

        return records;
    }

    public ArrayList<Session> getSessions() {

        return sessions;
    }

    public int countUnjustifiedAbsences(Student student) {

        int count = 0;

        for (AttendanceRecord record : records) {

            if (record.getStudent().equals(student)
                    && record.getStatus() == AttendanceStatus.ABSENT) {

                count++;
            }
        }

        return count;
    }

    public int countJustifiedAbsences(Student student) {

        int count = 0;

        for (AttendanceRecord record : records) {

            if (record.getStudent().equals(student)
                    && record.getStatus() == AttendanceStatus.JUSTIFIED) {

                count++;
            }
        }

        return count;
    }

    public boolean isExcluded(Student student) {

        int unjustified =
                countUnjustifiedAbsences(student);

        int justified =
                countJustifiedAbsences(student);

        return unjustified >= 3
                || justified >= 5;
    }

    public Student findStudentById(int id) {

        for (Student student : students) {

            if (student.getId() == id) {

                return student;
            }
        }

        return null;
    }

    public ArrayList<Student> getExcludedStudents() {

        ArrayList<Student> excludedStudents =
                new ArrayList<>();

        for (Student student : students) {

            if (isExcluded(student)) {

                excludedStudents.add(student);
            }
        }

        return excludedStudents;
    }
}