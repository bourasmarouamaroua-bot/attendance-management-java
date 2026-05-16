package gui;

import model.AttendanceRecord;
import service.AttendanceManager;
import service.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminFullAttendancePage extends JFrame {

    private AttendanceManager attendanceManager;
    private DefaultTableModel model;

    public AdminFullAttendancePage(AttendanceManager attendanceManager) {

        this.attendanceManager = attendanceManager;

        setTitle("Full Attendance Sheet");
        setSize(1200, 700);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Full Attendance Sheet");
        title.setBounds(350, 30, 600, 50);

        String[] columns = {
                "Student", "Group", "Teacher", "Module", "Date", "Time", "Session", "Attendance"
        };

        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(55, 110, 1070, 420);

        JButton export = LamayaTheme.button("Export attendance.txt");
        export.setBounds(420, 560, 360, 55);

        export.addActionListener(e -> {
            FileManager fileManager =
                    new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

            fileManager.exportAttendanceToTextFile();

            JOptionPane.showMessageDialog(this, "Exported to attendance.txt");
        });

        bg.add(title);
        bg.add(scroll);
        bg.add(export);

        add(bg);

        attendanceManager.addChangeListener(() ->
                SwingUtilities.invokeLater(this::loadTable)
        );

        loadTable();
        setVisible(true);
    }

    private void loadTable() {

        model.setRowCount(0);

        for (AttendanceRecord record : attendanceManager.getRecords()) {
            model.addRow(new Object[]{
                    record.getStudent().getName(),
                    record.getStudent().getGroup(),
                    record.getSession().getModule().getTeacher().getName(),
                    record.getSession().getModule().getModuleName(),
                    record.getSession().getDate(),
                    record.getSession().getTime(),
                    record.getSession().getStatus(),
                    record.getStatus()
            });
        }
    }
}