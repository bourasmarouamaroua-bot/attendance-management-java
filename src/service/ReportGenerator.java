package service;

import model.Student;

import java.util.ArrayList;

public class ReportGenerator {

    private AttendanceManager attendanceManager;

    public ReportGenerator(AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    public String generateStudentReport(Student student) {

        int justified = attendanceManager.countJustifiedAbsences(student);
        int unjustified = attendanceManager.countUnjustifiedAbsences(student);
        boolean excluded = attendanceManager.isExcluded(student);

        String report = "Attendance Report\n";
        report += "Student: " + student.getName() + "\n";
        report += "Group: " + student.getGroup() + "\n";
        report += "Justified absences: " + justified + "\n";
        report += "Unjustified absences: " + unjustified + "\n";
        report += "Status: " + (excluded ? "EXCLUDED" : "NOT EXCLUDED") + "\n";

        return report;
    }

    public String generateExcludedStudentsReport() {

        ArrayList<Student> excludedStudents =
                attendanceManager.getExcludedStudents();

        String report = "Excluded Students Report\n";

        if (excludedStudents.isEmpty()) {
            report += "No excluded students.\n";
            return report;
        }

        for (Student student : excludedStudents) {
            report += "- " + student.getName()
                    + " | Group: " + student.getGroup()
                    + " | Unjustified: " + attendanceManager.countUnjustifiedAbsences(student)
                    + " | Justified: " + attendanceManager.countJustifiedAbsences(student)
                    + "\n";
        }

        return report;
    }

    public String generateGroupReport(String groupName) {

        String report = "Group Attendance Report: " + groupName + "\n";

        for (Student student : attendanceManager.getStudents()) {

            if (student.getGroup().equalsIgnoreCase(groupName)) {

                report += "- " + student.getName()
                        + " | Justified: " + attendanceManager.countJustifiedAbsences(student)
                        + " | Unjustified: " + attendanceManager.countUnjustifiedAbsences(student)
                        + " | Status: "
                        + (attendanceManager.isExcluded(student) ? "EXCLUDED" : "NOT EXCLUDED")
                        + "\n";
            }
        }

        return report;
    }
}