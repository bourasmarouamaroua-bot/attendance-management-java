package gui;

import model.AttendanceRecord;
import service.AttendanceManager;
import service.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminFullAttendancePage extends JFrame {

    private AttendanceManager attendanceManager;

    public AdminFullAttendancePage(AttendanceManager attendanceManager) {

        this.attendanceManager = attendanceManager;

        setTitle("Full Attendance Sheet");
        setSize(1150, 700);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Full Attendance Sheet");
        title.setBounds(360, 30, 500, 50);

        String[] columns = {
                "Student", "Group", "Teacher", "Module", "Date", "Time", "Session", "Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

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

        JTable table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(LamayaTheme.PINK);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(60, 110, 1020, 420);

        JButton export = LamayaTheme.button("Export attendance.txt");
        export.setBounds(400, 560, 330, 60);

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
        setVisible(true);
    }
}