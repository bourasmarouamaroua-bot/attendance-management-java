package utils;

import enums.AttendanceStatus;

import model.Admin;
import model.AttendanceRecord;
import model.Group;
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

        Student sara =
                new Student(1, "Sara",
                        "sara01",
                        "1234",
                        "STUDENT",
                        "G1");

        Student lina =
                new Student(2, "Lina",
                        "lina01",
                        "1234",
                        "STUDENT",
                        "G1");

        Student yacine =
                new Student(3, "Yacine",
                        "yacine01",
                        "1234",
                        "STUDENT",
                        "G2");

        Student amine =
                new Student(4, "Amine",
                        "amine01",
                        "1234",
                        "STUDENT",
                        "G2");

        Student lylia =
                new Student(5,
                        "Lylia Boubchir",
                        "lylia01",
                        "1234",
                        "STUDENT",
                        "G1");

        Student maroua =
                new Student(6,
                        "Maroua Bouras",
                        "maroua01",
                        "1234",
                        "STUDENT",
                        "G1");

        Student wissal =
                new Student(7,
                        "Wissal Bouras",
                        "wissal01",
                        "1234",
                        "STUDENT",
                        "G1");

        Student aya =
                new Student(8,
                        "Aya Lillia Bourzak",
                        "aya01",
                        "1234",
                        "STUDENT",
                        "G2");

        Student rayane =
                new Student(9,
                        "Rayane Bouazouni",
                        "rayane01",
                        "1234",
                        "STUDENT",
                        "G2");

        Student daline =
                new Student(10,
                        "Daline Boubchir",
                        "daline01",
                        "1234",
                        "STUDENT",
                        "G2");

        Student houria =
                new Student(11,
                        "Houria Mounira Nacib",
                        "houria01",
                        "1234",
                        "STUDENT",
                        "G2");

        Teacher ahmed =
                new Teacher(12,
                        "Ahmed",
                        "ahmed01",
                        "1234",
                        "TEACHER",
                        "OOP");

        Teacher samir =
                new Teacher(13,
                        "Samir",
                        "samir01",
                        "1234",
                        "TEACHER",
                        "Database");

        Admin admin =
                new Admin(14,
                        "Admin",
                        "admin01",
                        "1234",
                        "ADMIN");

        authenticationService.addUser(sara);
        authenticationService.addUser(lina);
        authenticationService.addUser(yacine);
        authenticationService.addUser(amine);

        authenticationService.addUser(lylia);
        authenticationService.addUser(maroua);
        authenticationService.addUser(wissal);
        authenticationService.addUser(aya);
        authenticationService.addUser(rayane);
        authenticationService.addUser(daline);
        authenticationService.addUser(houria);

        authenticationService.addUser(ahmed);
        authenticationService.addUser(samir);
        authenticationService.addUser(admin);

        Group group1 =
                new Group(1, "G1");

        Group group2 =
                new Group(2, "G2");

        group1.addStudent(sara);
        group1.addStudent(lina);
        group1.addStudent(lylia);
        group1.addStudent(maroua);
        group1.addStudent(wissal);

        group2.addStudent(yacine);
        group2.addStudent(amine);
        group2.addStudent(aya);
        group2.addStudent(rayane);
        group2.addStudent(daline);
        group2.addStudent(houria);

        model.Module oop =
                new model.Module(1,
                        "OOP",
                        ahmed);

        model.Module database =
                new model.Module(2,
                        "Database",
                        samir);

        Session oopSession =
                new Session(
                        1,
                        "15/05/2026",
                        "08:00",
                        oop,
                        group1
                );

        Session dbSession =
                new Session(
                        2,
                        "16/05/2026",
                        "10:00",
                        database,
                        group2
                );

        try {

            attendanceManager.addStudent(sara);
            attendanceManager.addStudent(lina);
            attendanceManager.addStudent(yacine);
            attendanceManager.addStudent(amine);

            attendanceManager.addStudent(lylia);
            attendanceManager.addStudent(maroua);
            attendanceManager.addStudent(wissal);
            attendanceManager.addStudent(aya);
            attendanceManager.addStudent(rayane);
            attendanceManager.addStudent(daline);
            attendanceManager.addStudent(houria);

        } catch (exceptions.DuplicateStudentException e) {

            System.out.println(e.getMessage());
        }

        attendanceManager.addSession(oopSession);
        attendanceManager.addSession(dbSession);

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        sara,
                        oopSession,
                        AttendanceStatus.ABSENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        sara,
                        oopSession,
                        AttendanceStatus.ABSENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        sara,
                        oopSession,
                        AttendanceStatus.ABSENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        lina,
                        oopSession,
                        AttendanceStatus.JUSTIFIED
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        yacine,
                        dbSession,
                        AttendanceStatus.PRESENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        amine,
                        dbSession,
                        AttendanceStatus.ABSENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        amine,
                        dbSession,
                        AttendanceStatus.ABSENT
                )
        );
    }
}