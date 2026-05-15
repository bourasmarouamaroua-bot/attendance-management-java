package gui;

import enums.AttendanceStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AttendancePage extends JFrame {

    public AttendancePage() {
        setTitle("Attendance");
        setSize(1250, 760);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(120, 70, 1010, 590);

        JLabel title = LamayaTheme.title("Attendance Management");
        title.setBounds(290, 35, 500, 50);

        String[] columns = {"Student ID", "Student Name", "Status"};

        Object[][] data = {
                {"1", "Sara", AttendanceStatus.PRESENT},
                {"2", "Ali", AttendanceStatus.ABSENT},
                {"3", "Meriem", AttendanceStatus.LATE}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(42);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(LamayaTheme.PINK);

        JComboBox<AttendanceStatus> statusBox =
                new JComboBox<>(AttendanceStatus.values());

        table.getColumnModel()
                .getColumn(2)
                .setCellEditor(new DefaultCellEditor(statusBox));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 120, 810, 320);

        JButton save = LamayaTheme.button("Save Attendance");
        save.setBounds(330, 485, 350, 65);

        save.addActionListener(e -> {
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < model.getRowCount(); i++) {
                String id = model.getValueAt(i, 0).toString();
                String name = model.getValueAt(i, 1).toString();
                Object statusValue = model.getValueAt(i, 2);

                if (!(statusValue instanceof AttendanceStatus)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid attendance status for " + name,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                AttendanceStatus status =
                        (AttendanceStatus) statusValue;

                result.append("Student ID: ")
                        .append(id)
                        .append(" | Name: ")
                        .append(name)
                        .append(" | Status: ")
                        .append(status)
                        .append("\n");
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Attendance saved successfully!\n\n" + result,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        card.add(title);
        card.add(scrollPane);
        card.add(save);

        bg.add(card);
        add(bg);
        setVisible(true);
    }
}