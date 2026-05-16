package service;

import enums.AttendanceStatus;
import enums.SessionStatus;
import exceptions.DuplicateStudentException;
import enums.AttendanceStatus;
import enums.SessionStatus;
import exceptions.DuplicateStudentException;

import model.AttendanceRecord;
import model.Group;
import model.Module;
import model.Notification;
import model.Session;
import model.Student;
import model.Teacher;

import java.util.ArrayList;

import java.util.ArrayList;

public class AttendanceManager {

    private ArrayList<Student> students;
    private ArrayList<AttendanceRecord> records;
    private ArrayList<Session> sessions;
    private ArrayList<Notification> notifications;
    private ArrayList<Runnable> listeners;

    public AttendanceManager() {
        students = new ArrayList<>();
        records = new ArrayList<>();
        sessions = new ArrayList<>();
        notifications = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    // =========================
    // LIVE UPDATE SYSTEM
    // =========================

    public void addChangeListener(Runnable listener) {
        listeners.add(listener);
    }

    public void notifyChange() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

    // =========================
    // STUDENTS
    // =========================

    public void addStudent(Student student) throws DuplicateStudentException {
        if (findStudentById(student.getId()) != null) {
            throw new DuplicateStudentException(
                    "Student with ID " + student.getId() + " already exists."
            );
        }

        students.add(student);
        notifyChange();
    }

    public Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
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

    // =========================
    // SESSIONS
    // =========================

    public Session createSessionFromTeacher(Teacher teacher,
                                            String date,
                                            String timeSlot,
                                            String groupName) {

        Group group = new Group(generateNextSessionId() + 100, groupName);

        for (Student student : students) {
            if (student.getGroup().equalsIgnoreCase(groupName)) {
                group.addStudent(student);
            }
        }

        Module module = new Module(
                generateNextSessionId(),
                teacher.getSpeciality(),
                teacher
        );

        Session session = new Session(
                generateNextSessionId(),
                date,
                timeSlot,
                module,
                group
        );

        session.openSession();
        sessions.add(session);

        notifyChange();

        return session;
    }

    public void addSession(Session session) {
        sessions.add(session);
        notifyChange();
    }

    public void closeSession(Session session) {
        if (session != null) {
            session.closeSession();
            notifyChange();
        }
    }

    public Session findSessionById(int id) {
        for (Session session : sessions) {
            if (session.getSessionId() == id) {
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
            for (Student s : session.getGroup().getStudents()) {
                if (s.getId() == student.getId()) {
                    result.add(session);
                    break;
                }
            }
        }

        return result;
    }

    public ArrayList<Session> getSessionsForStudentByDate(Student student,
                                                          String date) {

        ArrayList<Session> result = new ArrayList<>();

        for (Session session : getSessionsForStudent(student)) {
            if (session.getDate().equals(date)) {
                result.add(session);
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
    public ArrayList<Teacher> getTeachersForStudent(Student student) {

        ArrayList<Teacher> result = new ArrayList<>();

        for (Session session : getSessionsForStudent(student)) {

            Teacher teacher = session.getModule().getTeacher();

            boolean exists = false;

            for (Teacher t : result) {

                if (t.getUsername()
                        .equalsIgnoreCase(teacher.getUsername())) {

                    exists = true;
                    break;
                }
            }

            if (!exists) {
                result.add(teacher);
            }
        }

        return result;
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

        notifyChange();

        return session;
    }
    // =========================
    // ATTENDANCE
    // =========================

    public void recordAttendance(AttendanceRecord record) {
        records.add(record);
        notifyChange();
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

        AttendanceRecord existing =
                getStudentRecordInSession(student, session);

        if (existing == null) {
            records.add(new AttendanceRecord(student, session, status));
        } else {
            existing.setStatus(status);
        }

        notifyChange();
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

    // =========================
    // MODULE STATUS
    // =========================

    public ArrayList<String> getModulesForStudent(Student student) {
        ArrayList<String> modules = new ArrayList<>();

        for (Session session : getSessionsForStudent(student)) {
            String module = session.getModule().getModuleName();

            if (!modules.contains(module)) {
                modules.add(module);
            }
        }

        return modules;
    }

    public int countUnjustifiedAbsencesByModule(Student student,
                                                String moduleName) {

        int count = 0;

        for (AttendanceRecord record : records) {
            if (record.getStudent().getId() == student.getId()
                    && record.getSession().getModule().getModuleName().equalsIgnoreCase(moduleName)
                    && record.getStatus() == AttendanceStatus.ABSENT) {
                count++;
            }
        }

        return count;
    }

    public int countJustifiedAbsencesByModule(Student student,
                                              String moduleName) {

        int count = 0;

        for (AttendanceRecord record : records) {
            if (record.getStudent().getId() == student.getId()
                    && record.getSession().getModule().getModuleName().equalsIgnoreCase(moduleName)
                    && record.getStatus() == AttendanceStatus.JUSTIFIED) {
                count++;
            }
        }

        return count;
    }

    public String getStatusByModule(Student student,
                                    String moduleName) {

        int unjustified =
                countUnjustifiedAbsencesByModule(student, moduleName);

        int justified =
                countJustifiedAbsencesByModule(student, moduleName);

        if (unjustified >= 3 || justified >= 5) {
            return "EXCLUDED";
        }

        if (unjustified >= 2 || justified >= 4) {
            return "WARNING";
        }

        return "NORMAL";
    }

    // =========================
    // GLOBAL COUNTS
    // =========================

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

    public boolean isExcluded(Student student) {
        return countUnjustifiedAbsences(student) >= 3
                || countJustifiedAbsences(student) >= 5;
    }

    public boolean needsWarning(Student student) {
        return !isExcluded(student)
                && (countUnjustifiedAbsences(student) >= 2
                || countJustifiedAbsences(student) >= 4);
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

    // =========================
    // NOTIFICATIONS
    // =========================

    public void sendNotification(Student student,
                                 String adminName,
                                 String type,
                                 String message,
                                 String date) {

        Notification notification =
                new Notification(
                        notifications.size() + 1,
                        student,
                        adminName,
                        type,
                        message,
                        date
                );

        notifications.add(notification);
        notifyChange();
    }

    public ArrayList<Notification> getNotificationsForStudent(Student student) {
        ArrayList<Notification> result = new ArrayList<>();

        for (Notification notification : notifications) {
            if (notification.getStudent().getId() == student.getId()) {
                result.add(notification);
            }
        }

        return result;
    }

    // =========================
    // GETTERS
    // =========================

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<AttendanceRecord> getRecords() {
        return records;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
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
}