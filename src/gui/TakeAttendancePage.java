package gui;

import enums.AttendanceStatus;
import model.AttendanceRecord;
import model.Session;
import model.Student;
import model.Teacher;
import service.AttendanceManager;
import service.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TakeAttendancePage extends JFrame {

    private Teacher teacher;
    private AttendanceManager attendanceManager;
    private Session session;
    private JTable table;
    private DefaultTableModel model;

    public TakeAttendancePage(Teacher teacher,
                              AttendanceManager attendanceManager,
                              Session session) {

        this.teacher = teacher;
        this.attendanceManager = attendanceManager;
        this.session = session;

        setTitle("Take Attendance");
        setSize(1150, 700);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Take Attendance");
        title.setBounds(390, 30, 450, 50);

        JLabel sessionInfo = new JLabel(session.toString());
        sessionInfo.setBounds(110, 90, 900, 35);
        sessionInfo.setFont(new Font("Arial", Font.BOLD, 17));
        sessionInfo.setForeground(LamayaTheme.DARK_PINK);

        String[] columns = {"Student ID", "Student Name", "Group", "Status"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 3 && session.canEditAttendance();
            }
        };

        table = new JTable(model);
        table.setRowHeight(38);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setBackground(LamayaTheme.PINK);

        JComboBox<AttendanceStatus> statusBox = new JComboBox<>(AttendanceStatus.values());
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(statusBox));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(110, 140, 900, 340);

        JButton save = LamayaTheme.button("Save Attendance");
        save.setBounds(150, 525, 260, 60);

        JButton close = LamayaTheme.button("Close Session");
        close.setBounds(455, 525, 260, 60);

        JButton export = LamayaTheme.button("Export Sheet");
        export.setBounds(760, 525, 220, 60);

        save.addActionListener(e -> saveAttendance());
        close.addActionListener(e -> closeSession());
        export.addActionListener(e -> exportSheet());

        if (session.isLocked()) {
            save.setEnabled(false);
            close.setEnabled(false);
        }

        bg.add(title);
        bg.add(sessionInfo);
        bg.add(scroll);
        bg.add(save);
        bg.add(close);
        bg.add(export);

        add(bg);
        loadStudents();
        setVisible(true);
    }

    private void loadStudents() {

        model.setRowCount(0);

        for (Student student : session.getGroup().getStudents()) {

            AttendanceRecord record = attendanceManager.getStudentRecordInSession(student, session);

            AttendanceStatus status = AttendanceStatus.PRESENT;

            if (record != null) {
                status = record.getStatus();
            }

            model.addRow(new Object[]{
                    student.getId(),
                    student.getName(),
                    student.getGroup(),
                    status
            });
        }
    }

    private void saveAttendance() {

        if (!session.canEditAttendance()) {
            JOptionPane.showMessageDialog(this, "Session is closed. You cannot edit attendance.");
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {

            int studentId = Integer.parseInt(model.getValueAt(i, 0).toString());
            Student student = attendanceManager.findStudentById(studentId);
            AttendanceStatus status = (AttendanceStatus) model.getValueAt(i, 3);

            attendanceManager.updateOrCreateAttendance(student, session, status);
        }

        FileManager fileManager =
                new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

        fileManager.saveToFile();

        JOptionPane.showMessageDialog(this, "Attendance saved.");
    }

    private void closeSession() {

        saveAttendance();

        attendanceManager.closeSession(session);

        FileManager fileManager =
                new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

        fileManager.saveToFile();

        JOptionPane.showMessageDialog(this, "Session closed. Attendance is now locked.");
        dispose();
    }

    private void exportSheet() {

        FileManager fileManager =
                new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

        fileManager.exportAttendanceToTextFile();

        JOptionPane.showMessageDialog(this, "Exported to attendance.txt");
    }
}