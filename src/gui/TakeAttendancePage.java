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

    private AttendanceManager attendanceManager;
    private Session session;
    private DefaultTableModel model;
    private JTable table;

    public TakeAttendancePage(Teacher teacher,
                              AttendanceManager attendanceManager,
                              Session session) {

        this.attendanceManager = attendanceManager;
        this.session = session;

        setTitle("Take Attendance");
        setSize(1150, 700);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Take Attendance");
        title.setBounds(390, 25, 450, 55);

        JLabel info = new JLabel(session.toString());
        info.setBounds(90, 90, 950, 35);
        info.setFont(new Font("Arial", Font.BOLD, 17));
        info.setForeground(LamayaTheme.DARK_PINK);

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

        JComboBox<AttendanceStatus> statusBox =
                new JComboBox<>(AttendanceStatus.values());

        table.getColumnModel()
                .getColumn(3)
                .setCellEditor(new DefaultCellEditor(statusBox));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(90, 140, 950, 340);

        JButton save = LamayaTheme.button("Save Attendance");
        save.setBounds(150, 525, 260, 60);

        JButton close = LamayaTheme.button("Close Session");
        close.setBounds(445, 525, 260, 60);

        JButton export = LamayaTheme.button("Export Sheet");
        export.setBounds(740, 525, 260, 60);

        save.addActionListener(e -> saveAttendance());

        close.addActionListener(e -> {
            saveAttendance();
            attendanceManager.closeSession(session);
            JOptionPane.showMessageDialog(this, "Session closed. Attendance locked.");
            dispose();
        });

        export.addActionListener(e -> {
            FileManager fileManager =
                    new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

            fileManager.exportAttendanceToTextFile();
            JOptionPane.showMessageDialog(this, "Exported to attendance.txt");
        });

        if (session.isLocked()) {
            save.setEnabled(false);
            close.setEnabled(false);
        }

        bg.add(title);
        bg.add(info);
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

            AttendanceRecord record =
                    attendanceManager.getStudentRecordInSession(student, session);

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
            JOptionPane.showMessageDialog(this, "Session is closed.");
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {

            int studentId =
                    Integer.parseInt(model.getValueAt(i, 0).toString());

            Student student =
                    attendanceManager.findStudentById(studentId);

            AttendanceStatus status =
                    (AttendanceStatus) model.getValueAt(i, 3);

            attendanceManager.updateOrCreateAttendance(
                    student,
                    session,
                    status
            );
        }

        JOptionPane.showMessageDialog(this, "Attendance saved and dashboards updated.");
    }
}