import enums.AttendanceStatus;

import model.Admin;
import model.AttendanceRecord;
import model.Group;
import model.Session;
import model.Student;
import model.Teacher;
import model.User;

import service.AttendanceManager;
import service.AuthenticationService;
import service.FileManager;
import service.ReportGenerator;

public class Main {

    public static void main(String[] args) {

        AttendanceManager attendanceManager =
                new AttendanceManager();

        AuthenticationService authenticationService =
                new AuthenticationService();

        Student student1 =
                new Student(
                        1,
                        "Sara",
                        "sara01",
                        "1234",
                        "STUDENT",
                        "G1"
                );

        Teacher teacher1 =
                new Teacher(
                        2,
                        "Ahmed",
                        "ahmed01",
                        "1234",
                        "TEACHER",
                        "OOP"
                );

        Admin admin1 =
                new Admin(
                        3,
                        "Admin",
                        "admin01",
                        "1234",
                        "ADMIN"
                );

        authenticationService.addUser(student1);
        authenticationService.addUser(teacher1);
        authenticationService.addUser(admin1);

        try {

            User loggedUser =
                    authenticationService.login(
                            "admin01",
                            "1234"
                    );

            System.out.println(
                    "Login successful: "
                            + loggedUser.getName()
            );

        } catch (exceptions.InvalidLoginException e) {

            System.out.println(e.getMessage());
        }

        Group group1 =
                new Group(
                        1,
                        "G1"
                );

        model.Module module1 =
                new model.Module(
                        1,
                        "OOP",
                        teacher1
                );

        Session session1 =
                new Session(
                        1,
                        "15/05/2026",
                        "08:00",
                        module1,
                        group1
                );

        try {

            attendanceManager.addStudent(student1);

        } catch (exceptions.DuplicateStudentException e) {

            System.out.println(e.getMessage());
        }

        attendanceManager.addSession(session1);

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        student1,
                        session1,
                        AttendanceStatus.ABSENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        student1,
                        session1,
                        AttendanceStatus.ABSENT
                )
        );

        attendanceManager.recordAttendance(
                new AttendanceRecord(
                        student1,
                        session1,
                        AttendanceStatus.ABSENT
                )
        );

        FileManager fileManager =
                new FileManager(
                        attendanceManager.getStudents(),
                        attendanceManager.getRecords()
                );

        fileManager.saveToFile();

        fileManager.loadFromFile();

        ReportGenerator reportGenerator =
                new ReportGenerator(attendanceManager);

        System.out.println(
                reportGenerator.generateStudentReport(student1)
        );

        System.out.println(
                reportGenerator.generateExcludedStudentsReport()
        );

        System.out.println(
                reportGenerator.generateGroupReport("G1")
        );
    }
}