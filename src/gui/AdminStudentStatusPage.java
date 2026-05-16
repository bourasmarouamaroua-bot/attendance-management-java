package gui;

import model.Student;
import service.AttendanceManager;
import service.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminStudentStatusPage extends JFrame {

    private AttendanceManager attendanceManager;
    private JTable table;
    private DefaultTableModel model;

    public AdminStudentStatusPage(AttendanceManager attendanceManager) {

        this.attendanceManager = attendanceManager;

        setTitle("Student Status");
        setSize(1150, 700);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Warnings & Exclusions");
        title.setBounds(345, 30, 550, 50);

        String[] columns = {
                "ID", "Student", "Group", "Present", "Late",
                "Unjustified", "Justified", "Status"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(LamayaTheme.PINK);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(60, 110, 1020, 390);

        JButton warning = LamayaTheme.button("Send Warning");
        warning.setBounds(190, 540, 230, 60);

        JButton exclusion = LamayaTheme.button("Send Exclusion");
        exclusion.setBounds(460, 540, 240, 60);

        JButton export = LamayaTheme.button("Export report.txt");
        export.setBounds(740, 540, 230, 60);

        warning.addActionListener(e -> sendMessage("WARNING"));
        exclusion.addActionListener(e -> sendMessage("EXCLUSION"));
        export.addActionListener(e -> exportReport());

        bg.add(title);
        bg.add(scroll);
        bg.add(warning);
        bg.add(exclusion);
        bg.add(export);

        add(bg);
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

    private void sendMessage(String type) {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first.");
            return;
        }

        String name = model.getValueAt(row, 1).toString();
        String status = model.getValueAt(row, 7).toString();

        JOptionPane.showMessageDialog(
                this,
                type + " sent to " + name + "\nCurrent status: " + status
        );
    }

    private void exportReport() {

        StringBuilder report = new StringBuilder();

        report.append("ADMIN STUDENT STATUS REPORT\n\n");

        for (int i = 0; i < model.getRowCount(); i++) {
            report.append(model.getValueAt(i, 1))
                    .append(" | Group: ")
                    .append(model.getValueAt(i, 2))
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

        JOptionPane.showMessageDialog(this, "Report exported.");
    }
}