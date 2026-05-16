package gui;

import enums.AttendanceStatus;
import enums.SessionStatus;
import model.AttendanceRecord;
import model.Session;
import model.Student;
import model.Teacher;
import service.AttendanceManager;
import service.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AttendancePage extends JFrame {

    private Teacher teacher;
    private AttendanceManager attendanceManager;

    private JComboBox<Session> sessionBox;
    private JTable table;
    private DefaultTableModel tableModel;

    public AttendancePage(Teacher teacher, AttendanceManager attendanceManager) {

        this.teacher = teacher;
        this.attendanceManager = attendanceManager;

        setTitle("Take Attendance");
        setSize(1250, 760);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(90, 55, 1070, 630);

        JLabel title = LamayaTheme.title("Attendance Management");
        title.setBounds(310, 25, 520, 50);

        sessionBox = new JComboBox<>();
        sessionBox.setBounds(90, 100, 720, 45);
        sessionBox.setFont(new Font("Arial", Font.PLAIN, 16));

        loadTeacherSessions();

        JButton open = LamayaTheme.button("Open");
        open.setBounds(830, 95, 100, 50);

        JButton close = LamayaTheme.button("Close");
        close.setBounds(940, 95, 100, 50);

        String[] columns = {"Student ID", "Student Name", "Group", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(42);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(LamayaTheme.PINK);

        JComboBox<AttendanceStatus> statusBox =
                new JComboBox<>(AttendanceStatus.values());

        table.getColumnModel()
                .getColumn(3)
                .setCellEditor(new DefaultCellEditor(statusBox));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(90, 170, 860, 320);

        JButton save = LamayaTheme.button("Save Attendance");
        save.setBounds(180, 525, 300, 65);

        JButton export = LamayaTheme.button("Export attendance.txt");
        export.setBounds(560, 525, 330, 65);

        sessionBox.addActionListener(e -> loadStudentsForSelectedSession());

        open.addActionListener(e -> updateSessionStatus(SessionStatus.OPEN));
        close.addActionListener(e -> updateSessionStatus(SessionStatus.CLOSED));

        save.addActionListener(e -> saveAttendance());

        export.addActionListener(e -> {
            FileManager fileManager =
                    new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

            fileManager.exportAttendanceToTextFile();

            JOptionPane.showMessageDialog(
                    this,
                    "Attendance exported to attendance.txt"
            );
        });

        card.add(title);
        card.add(sessionBox);
        card.add(open);
        card.add(close);
        card.add(scrollPane);
        card.add(save);
        card.add(export);

        bg.add(card);
        add(bg);

        loadStudentsForSelectedSession();

        setVisible(true);
    }

    private void loadTeacherSessions() {

        sessionBox.removeAllItems();

        for (Session session : attendanceManager.getSessions()) {

            if (session.getModule()
                    .getTeacher()
                    .getUsername()
                    .equalsIgnoreCase(teacher.getUsername())) {

                sessionBox.addItem(session);
            }
        }
    }

    private void loadStudentsForSelectedSession() {

        tableModel.setRowCount(0);

        Session session = (Session) sessionBox.getSelectedItem();

        if (session == null) {
            return;
        }

        ArrayList<Student> students =
                session.getGroup().getStudents();

        for (Student student : students) {

            AttendanceStatus savedStatus =
                    findSavedStatus(student, session);

            tableModel.addRow(new Object[]{
                    student.getId(),
                    student.getName(),
                    student.getGroup(),
                    savedStatus
            });
        }
    }

    private AttendanceStatus findSavedStatus(Student student, Session session) {

        for (AttendanceRecord record : attendanceManager.getRecords()) {

            if (record.getStudent().getId() == student.getId()
                    && record.getSession().getSessionId() == session.getSessionId()) {

                return record.getStatus();
            }
        }

        return AttendanceStatus.PRESENT;
    }

    private void updateSessionStatus(SessionStatus status) {

        Session session = (Session) sessionBox.getSelectedItem();

        if (session == null) {
            JOptionPane.showMessageDialog(this, "No session selected.");
            return;
        }

        session.setStatus(status);

        JOptionPane.showMessageDialog(
                this,
                "Session state updated to: " + status
        );

        loadTeacherSessions();
    }

    private void saveAttendance() {

        Session session = (Session) sessionBox.getSelectedItem();

        if (session == null) {
            JOptionPane.showMessageDialog(this, "No session selected.");
            return;
        }

        if (session.getStatus() == SessionStatus.CLOSED) {
            JOptionPane.showMessageDialog(
                    this,
                    "This session is CLOSED. Open it before taking attendance.",
                    "Session Closed",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            int studentId = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
            Student student = attendanceManager.findStudentById(studentId);

            AttendanceStatus status =
                    (AttendanceStatus) tableModel.getValueAt(i, 3);

            AttendanceRecord existing = findRecord(student, session);

            if (existing == null) {
                attendanceManager.recordAttendance(
                        new AttendanceRecord(student, session, status)
                );
            } else {
                existing.setStatus(status);
            }
        }

        FileManager fileManager =
                new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

        fileManager.saveToFile();

        JOptionPane.showMessageDialog(
                this,
                "Attendance saved successfully."
        );
    }

    private AttendanceRecord findRecord(Student student, Session session) {

        for (AttendanceRecord record : attendanceManager.getRecords()) {

            if (record.getStudent().getId() == student.getId()
                    && record.getSession().getSessionId() == session.getSessionId()) {

                return record;
            }
        }

        return null;
    }
}