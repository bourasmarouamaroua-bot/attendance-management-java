package utils;

import enums.AttendanceStatus;
import enums.SessionStatus;
import model.Admin;
import model.AttendanceRecord;
import model.Group;
import model.Module;
import model.Session;
import model.Student;
import model.Teacher;
import service.AttendanceManager;
import service.AuthenticationService;

public class MockData {

    public static void loadMockData(
            AttendanceManager attendanceManager,
            AuthenticationService authenticationService
    ) {

        // =========================
        // STUDENTS
        // =========================

        Student sara =
                new Student(
                        1,
                        "Sara",
                        "sara01",
                        "1234",
                        "STUDENT",
                        "G1"
                );

        Student lina =
                new Student(
                        2,
                        "Lina",
                        "lina01",
                        "1234",
                        "STUDENT",
                        "G1"
                );

        Student amine =
                new Student(
                        3,
                        "Amine",
                        "amine01",
                        "1234",
                        "STUDENT",
                        "G2"
                );

        Student yacine =
                new Student(
                        4,
                        "Yacine",
                        "yacine01",
                        "1234",
                        "STUDENT",
                        "G2"
                );

        Student rayane =
                new Student(
                        5,
                        "Rayane",
                        "rayane01",
                        "1234",
                        "STUDENT",
                        "G1"
                );

        Student aya =
                new Student(
                        6,
                        "Aya",
                        "aya01",
                        "1234",
                        "STUDENT",
                        "G2"
                );

        // =========================
        // TEACHERS
        // =========================

        Teacher bouchachi =
                new Teacher(
                        10,
                        "Bouchachi",
                        "bouchachi",
                        "b2026",
                        "TEACHER",
                        "Optic"
                );

        Teacher mekki =
                new Teacher(
                        11,
                        "Mekki",
                        "mekki",
                        "m2026",
                        "TEACHER",
                        "Algebra"
                );

        Teacher nait =
                new Teacher(
                        12,
                        "Nait",
                        "nait",
                        "n2026",
                        "TEACHER",
                        "Database"
                );

        Teacher houadjeli =
                new Teacher(
                        13,
                        "Houadjeli",
                        "houadjeli",
                        "h2026",
                        "TEACHER",
                        "Information Systems"
                );

        // =========================
        // ADMINS
        // =========================

        Admin boughaled =
                new Admin(
                        20,
                        "Boughaled",
                        "boughaled",
                        "incub2026",
                        "ADMIN"
                );

        Admin riahla =
                new Admin(
                        21,
                        "Riahla",
                        "riahla",
                        "nscs2026",
                        "ADMIN"
                );

        Admin iddir =
                new Admin(
                        22,
                        "Iddir",
                        "iddir",
                        "nscs2026",
                        "ADMIN"
                );

        // =========================
        // AUTH USERS
        // =========================

        authenticationService.addUser(sara);
        authenticationService.addUser(lina);
        authenticationService.addUser(amine);
        authenticationService.addUser(yacine);
        authenticationService.addUser(rayane);
        authenticationService.addUser(aya);

        authenticationService.addUser(bouchachi);
        authenticationService.addUser(mekki);
        authenticationService.addUser(nait);
        authenticationService.addUser(houadjeli);

        authenticationService.addUser(boughaled);
        authenticationService.addUser(riahla);
        authenticationService.addUser(iddir);

        // =========================
        // GROUPS
        // =========================

        Group g1 = new Group(1, "G1");
        Group g2 = new Group(2, "G2");

        g1.addStudent(sara);
        g1.addStudent(lina);
        g1.addStudent(rayane);

        g2.addStudent(amine);
        g2.addStudent(yacine);
        g2.addStudent(aya);

        // =========================
        // MODULES
        // =========================

        Module optic =
                new Module(
                        1,
                        "Optic",
                        bouchachi
                );

        Module algebra =
                new Module(
                        2,
                        "Algebra",
                        mekki
                );

        Module database =
                new Module(
                        3,
                        "Database",
                        nait
                );

        Module informationSystems =
                new Module(
                        4,
                        "Information Systems",
                        houadjeli
                );

        // =========================
        // SESSIONS
        // =========================

        Session optic1 =
                attendanceManager.createSession(
                        1,
                        "18/05/2026",
                        "08:00",
                        optic,
                        g1
                );

        Session algebra1 =
                attendanceManager.createSession(
                        2,
                        "19/05/2026",
                        "10:00",
                        algebra,
                        g1
                );

        Session database1 =
                attendanceManager.createSession(
                        3,
                        "20/05/2026",
                        "09:30",
                        database,
                        g2
                );

        Session is1 =
                attendanceManager.createSession(
                        4,
                        "21/05/2026",
                        "13:00",
                        informationSystems,
                        g2
                );

        // =========================
        // SESSION STATES
        // =========================

        optic1.setStatus(SessionStatus.CLOSED);
        algebra1.setStatus(SessionStatus.OPEN);

        database1.setStatus(SessionStatus.CLOSED);
        is1.setStatus(SessionStatus.OPEN);

        // =========================
        // ADD STUDENTS TO MANAGER
        // =========================

        try {

            attendanceManager.addStudent(sara);
            attendanceManager.addStudent(lina);
            attendanceManager.addStudent(amine);
            attendanceManager.addStudent(yacine);
            attendanceManager.addStudent(rayane);
            attendanceManager.addStudent(aya);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // =========================
        // ATTENDANCE RECORDS
        // =========================

        // Sara -> excluded

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        sara,
                        optic1,
                        AttendanceStatus.ABSENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        sara,
                        algebra1,
                        AttendanceStatus.ABSENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        sara,
                        algebra1,
                        AttendanceStatus.ABSENT
                )
        );

        // Lina -> warning

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        lina,
                        optic1,
                        AttendanceStatus.JUSTIFIED
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        lina,
                        algebra1,
                        AttendanceStatus.JUSTIFIED
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        lina,
                        algebra1,
                        AttendanceStatus.JUSTIFIED
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        lina,
                        algebra1,
                        AttendanceStatus.JUSTIFIED
                )
        );

        // Rayane -> good student

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        rayane,
                        optic1,
                        AttendanceStatus.PRESENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        rayane,
                        algebra1,
                        AttendanceStatus.LATE
                )
        );

        // Amine

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        amine,
                        database1,
                        AttendanceStatus.PRESENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        amine,
                        is1,
                        AttendanceStatus.ABSENT
                )
        );

        // Yacine

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        yacine,
                        database1,
                        AttendanceStatus.LATE
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        yacine,
                        is1,
                        AttendanceStatus.PRESENT
                )
        );

        // Aya

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        aya,
                        database1,
                        AttendanceStatus.JUSTIFIED
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        aya,
                        is1,
                        AttendanceStatus.PRESENT
                )
        );
    }
}