package service;

import model.Student;

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

        if (excluded) {
            report += "Status: EXCLUDED\n";
        } else {
            report += "Status: NOT EXCLUDED\n";
        }

        return report;
    }
}