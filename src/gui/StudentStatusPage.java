package gui;

import model.Student;
import service.AttendanceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentStatusPage extends JFrame {

    private Student student;
    private AttendanceManager attendanceManager;
    private DefaultTableModel model;

    public StudentStatusPage(Student student,
                             AttendanceManager attendanceManager) {

        this.student = student;
        this.attendanceManager = attendanceManager;

        setTitle("My Module Status");
        setSize(1050, 650);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("My Module Status");
        title.setBounds(330, 30, 500, 50);

        String[] columns = {
                "Module", "Unjustified Absences", "Justified Absences", "Status"
        };

        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(90, 115, 850, 370);

        JButton refresh = LamayaTheme.button("Refresh");
        refresh.setBounds(390, 525, 260, 55);

        refresh.addActionListener(e -> loadStatus());

        bg.add(title);
        bg.add(scroll);
        bg.add(refresh);

        add(bg);

        attendanceManager.addChangeListener(() ->
                SwingUtilities.invokeLater(this::loadStatus)
        );

        loadStatus();
        setVisible(true);
    }

    private void loadStatus() {

        model.setRowCount(0);

        for (String module : attendanceManager.getModulesForStudent(student)) {
            model.addRow(new Object[]{
                    module,
                    attendanceManager.countUnjustifiedAbsencesByModule(student, module),
                    attendanceManager.countJustifiedAbsencesByModule(student, module),
                    attendanceManager.getStatusByModule(student, module)
            });
        }
    }
}