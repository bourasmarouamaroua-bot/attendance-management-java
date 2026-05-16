package gui;

import model.Student;
import service.AttendanceManager;
import service.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminStudentStatusPage extends JFrame {

    private AttendanceManager attendanceManager;
    private DefaultTableModel model;

    public AdminStudentStatusPage(AttendanceManager attendanceManager) {

        this.attendanceManager = attendanceManager;

        setTitle("Student Status");
        setSize(1200, 700);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Student Status");
        title.setBounds(420, 30, 450, 50);

        String[] columns = {
                "ID", "Student", "Group", "Present", "Late", "Unjustified", "Justified", "Status"
        };

        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(60, 110, 1060, 420);

        JButton export = LamayaTheme.button("Export report.txt");
        export.setBounds(420, 560, 360, 55);

        export.addActionListener(e -> exportReport());

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

        for (Student student : attendanceManager.getStudents()) {
            model.addRow(new Object[]{
                    student.getId(),
                    student.getName(),
                    student.getGroup(),
                    attendanceManager.countPresent(student),
                    attendanceManager.countLate(student),
                    attendanceManager.countUnjustifiedAbsences(student),
                    attendanceManager.countJustifiedAbsences(student),
                    attendanceManager.getAdministrativeStatus(student)
            });
        }
    }

    private void exportReport() {

        StringBuilder report = new StringBuilder();

        report.append("ADMIN STUDENT STATUS REPORT\n\n");

        for (int i = 0; i < model.getRowCount(); i++) {
            report.append(model.getValueAt(i, 1))
                    .append(" | Group: ")
                    .append(model.getValueAt(i, 2))
                    .append(" | Present: ")
                    .append(model.getValueAt(i, 3))
                    .append(" | Late: ")
                    .append(model.getValueAt(i, 4))
                    .append(" | Unjustified: ")
                    .append(model.getValueAt(i, 5))
                    .append(" | Justified: ")
                    .append(model.getValueAt(i, 6))
                    .append(" | Status: ")
                    .append(model.getValueAt(i, 7))
                    .append("\n");
        }

        FileManager fileManager =
                new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

        fileManager.exportReportToTextFile(report.toString());

        JOptionPane.showMessageDialog(this, "Report exported to report.txt");
    }
}