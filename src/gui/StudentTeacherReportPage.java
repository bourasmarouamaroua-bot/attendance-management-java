package gui;

import model.AttendanceRecord;
import model.Session;
import model.Student;
import model.Teacher;
import service.AttendanceManager;
import service.FileManager;

import javax.swing.*;
import java.awt.*;

public class StudentTeacherReportPage extends JFrame {

    private Student student;
    private Teacher teacher;
    private AttendanceManager attendanceManager;
    private JTextArea area;

    public StudentTeacherReportPage(Student student,
                                    Teacher teacher,
                                    AttendanceManager attendanceManager) {

        this.student = student;
        this.teacher = teacher;
        this.attendanceManager = attendanceManager;

        setTitle("My Teacher Report");
        setSize(1050, 680);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("My Report With " + teacher.getName());
        title.setBounds(250, 30, 650, 50);

        area = new JTextArea();
        area.setFont(new Font("Arial", Font.PLAIN, 16));
        area.setEditable(false);
        area.setBorder(BorderFactory.createLineBorder(LamayaTheme.PINK, 2));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(90, 110, 860, 400);

        JButton export = LamayaTheme.button("Export My Report");
        export.setBounds(365, 545, 320, 60);

        export.addActionListener(e -> {
            FileManager fileManager =
                    new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

            fileManager.exportReportToTextFile(area.getText());
            JOptionPane.showMessageDialog(this, "Report exported.");
        });

        bg.add(title);
        bg.add(scroll);
        bg.add(export);

        add(bg);
        generate();
        setVisible(true);
    }

    private void generate() {

        StringBuilder report = new StringBuilder();

        int present = 0;
        int late = 0;
        int absent = 0;
        int justified = 0;

        report.append("STUDENT PERSONAL REPORT\n");
        report.append("Student: ").append(student.getName()).append("\n");
        report.append("Teacher: ").append(teacher.getName()).append("\n\n");

        report.append("SESSIONS:\n");

        for (Session session : attendanceManager.getSessionsForStudentByTeacher(student, teacher)) {

            String status = attendanceManager.getStudentStatusInSession(student, session);

            report.append("- ")
                    .append(session.getDate())
                    .append(" ")
                    .append(session.getTime())
                    .append(" | Module: ")
                    .append(session.getModule().getModuleName())
                    .append(" | Session: ")
                    .append(session.getStatus())
                    .append(" | My status: ")
                    .append(status)
                    .append("\n");

            AttendanceRecord record =
                    attendanceManager.getStudentRecordInSession(student, session);

            if (record != null) {
                switch (record.getStatus()) {
                    case PRESENT: present++; break;
                    case LATE: late++; break;
                    case ABSENT: absent++; break;
                    case JUSTIFIED: justified++; break;
                }
            }
        }

        report.append("\nSUMMARY:\n");
        report.append("Present: ").append(present).append("\n");
        report.append("Late: ").append(late).append("\n");
        report.append("Absent: ").append(absent).append("\n");
        report.append("Justified: ").append(justified).append("\n");

        report.append("\nAdministrative status: ")
                .append(attendanceManager.getAdministrativeStatus(student));

        area.setText(report.toString());
    }
}