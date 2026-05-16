package service;

import enums.AttendanceStatus;
import enums.SessionStatus;
import exceptions.DuplicateStudentException;
import model.AttendanceRecord;
import model.Group;
import model.Module;
import model.Session;
import model.Student;
import model.Teacher;

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

    public void addStudent(Student student) throws DuplicateStudentException {

        if (findStudentById(student.getId()) != null) {
            throw new DuplicateStudentException(
                    "Student with ID " + student.getId() + " already exists."
            );
        }

        students.add(student);
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public Session createSession(int sessionId,
                                 String date,
                                 String time,
                                 Module module,
                                 Group group) {

        Session session = new Session(
                sessionId,
                date,
                time,
                module,
                group
        );

        session.openSession();
        sessions.add(session);

        return session;
    }

    public void openSession(Session session) {

        if (session == null) {
            return;
        }

        if (!session.isLocked()) {
            session.openSession();
        }
    }

    public void closeSession(Session session) {

        if (session == null) {
            return;
        }

        session.closeSession();
    }

    public void recordAttendance(AttendanceRecord record) {

        if (record == null) {
            return;
        }

        records.add(record);
    }

    public void updateOrCreateAttendance(Student student,
                                         Session session,
                                         AttendanceStatus status) {

        if (student == null || session == null || status == null) {
            return;
        }

        if (!session.canEditAttendance()) {
            return;
        }

        AttendanceRecord existingRecord =
                getStudentRecordInSession(student, session);

        if (existingRecord == null) {
            records.add(new AttendanceRecord(student, session, status));
        } else {
            existingRecord.setStatus(status);
        }
    }

    public AttendanceRecord getStudentRecordInSession(Student student,
                                                      Session session) {

        for (AttendanceRecord record : records) {

            if (record.getStudent().getId() == student.getId()
                    && record.getSession().getSessionId() == session.getSessionId()) {

                return record;
            }
        }

        return null;
    }

    public ArrayList<AttendanceRecord> getRecordsForStudent(Student student) {

        ArrayList<AttendanceRecord> result = new ArrayList<>();

        for (AttendanceRecord record : records) {

            if (record.getStudent().getId() == student.getId()) {
                result.add(record);
            }
        }

        return result;
    }

    public ArrayList<AttendanceRecord> getRecordsBySession(Session session) {

        ArrayList<AttendanceRecord> result = new ArrayList<>();

        for (AttendanceRecord record : records) {

            if (record.getSession().getSessionId() == session.getSessionId()) {
                result.add(record);
            }
        }

        return result;
    }

    public ArrayList<Session> getSessionsByTeacher(Teacher teacher) {

        ArrayList<Session> result = new ArrayList<>();

        for (Session session : sessions) {

            if (session.getModule()
                    .getTeacher()
                    .getUsername()
                    .equalsIgnoreCase(teacher.getUsername())) {

                result.add(session);
            }
        }

        return result;
    }

    public ArrayList<Session> getSessionsForStudent(Student student) {

        ArrayList<Session> result = new ArrayList<>();

        for (Session session : sessions) {

            for (Student groupStudent : session.getGroup().getStudents()) {

                if (groupStudent.getId() == student.getId()) {
                    result.add(session);
                    break;
                }
            }
        }

        return result;
    }

    public ArrayList<Teacher> getTeachersForStudent(Student student) {

        ArrayList<Teacher> result = new ArrayList<>();

        for (Session session : getSessionsForStudent(student)) {

            Teacher teacher = session.getModule().getTeacher();

            boolean alreadyExists = false;

            for (Teacher existingTeacher : result) {

                if (existingTeacher.getUsername()
                        .equalsIgnoreCase(teacher.getUsername())) {

                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                result.add(teacher);
            }
        }

        return result;
    }

    public ArrayList<Session> getSessionsForStudentByTeacher(Student student,
                                                             Teacher teacher) {

        ArrayList<Session> result = new ArrayList<>();

        for (Session session : getSessionsForStudent(student)) {

            if (session.getModule()
                    .getTeacher()
                    .getUsername()
                    .equalsIgnoreCase(teacher.getUsername())) {

                result.add(session);
            }
        }

        return result;
    }

    public String getStudentStatusInSession(Student student,
                                            Session session) {

        AttendanceRecord record =
                getStudentRecordInSession(student, session);

        if (record == null) {

            if (session.getStatus() == SessionStatus.OPEN) {
                return "Not marked yet";
            }

            return "No record";
        }

        return record.getStatus().toString();
    }

    public ArrayList<Student> getStudentsByGroup(String groupName) {

        ArrayList<Student> result = new ArrayList<>();

        for (Student student : students) {

            if (student.getGroup().equalsIgnoreCase(groupName)) {
                result.add(student);
            }
        }

        return result;
    }

    public int countUnjustifiedAbsences(Student student) {

        int count = 0;

        for (AttendanceRecord record : records) {

            if (record.getStudent().getId() == student.getId()
                    && record.getStatus() == AttendanceStatus.ABSENT) {

                count++;
            }
        }

        return count;
    }

    public int countJustifiedAbsences(Student student) {

        int count = 0;

        for (AttendanceRecord record : records) {

            if (record.getStudent().getId() == student.getId()
                    && record.getStatus() == AttendanceStatus.JUSTIFIED) {

                count++;
            }
        }

        return count;
    }

    public int countLate(Student student) {

        int count = 0;

        for (AttendanceRecord record : records) {

            if (record.getStudent().getId() == student.getId()
                    && record.getStatus() == AttendanceStatus.LATE) {

                count++;
            }
        }

        return count;
    }

    public int countPresent(Student student) {

        int count = 0;

        for (AttendanceRecord record : records) {

            if (record.getStudent().getId() == student.getId()
                    && record.getStatus() == AttendanceStatus.PRESENT) {

                count++;
            }
        }

        return count;
    }

    public boolean isExcluded(Student student) {

        int unjustified = countUnjustifiedAbsences(student);
        int justified = countJustifiedAbsences(student);

        return unjustified >= 3 || justified >= 5;
    }

    public boolean needsWarning(Student student) {

        int unjustified = countUnjustifiedAbsences(student);
        int justified = countJustifiedAbsences(student);

        return !isExcluded(student)
                && (unjustified >= 2 || justified >= 4);
    }

    public String getAdministrativeStatus(Student student) {

        if (isExcluded(student)) {
            return "EXCLUDED";
        }

        if (needsWarning(student)) {
            return "WARNING";
        }

        return "NORMAL";
    }

    public ArrayList<Student> getExcludedStudents() {

        ArrayList<Student> excludedStudents = new ArrayList<>();

        for (Student student : students) {

            if (isExcluded(student)) {
                excludedStudents.add(student);
            }
        }

        return excludedStudents;
    }

    public ArrayList<Student> getWarningStudents() {

        ArrayList<Student> warningStudents = new ArrayList<>();

        for (Student student : students) {

            if (needsWarning(student)) {
                warningStudents.add(student);
            }
        }

        return warningStudents;
    }

    public Student findStudentById(int id) {

        for (Student student : students) {

            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    public Student findStudentByUsername(String username) {

        for (Student student : students) {

            if (student.getUsername().equalsIgnoreCase(username)) {
                return student;
            }
        }

        return null;
    }

    public Session findSessionById(int sessionId) {

        for (Session session : sessions) {

            if (session.getSessionId() == sessionId) {
                return session;
            }
        }

        return null;
    }

    public int generateNextSessionId() {

        int max = 0;

        for (Session session : sessions) {

            if (session.getSessionId() > max) {
                max = session.getSessionId();
            }
        }

        return max + 1;
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
}