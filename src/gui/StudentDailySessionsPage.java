package gui;

import model.Session;
import model.Student;
import service.AttendanceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentDailySessionsPage extends JFrame {

    private Student student;
    private AttendanceManager attendanceManager;
    private JComboBox<String> dateBox;
    private DefaultTableModel model;

    public StudentDailySessionsPage(Student student,
                                    AttendanceManager attendanceManager) {

        this.student = student;
        this.attendanceManager = attendanceManager;

        setTitle("Daily Sessions");
        setSize(1150, 700);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Daily Sessions");
        title.setBounds(395, 30, 450, 50);

        dateBox = new JComboBox<>(new String[]{
                "20/05/2026",
                "21/05/2026",
                "22/05/2026",
                "23/05/2026"
        });
        dateBox.setBounds(380, 100, 390, 50);
        dateBox.setFont(new Font("Arial", Font.PLAIN, 17));

        String[] columns = {
                "Time Slot", "Module", "Teacher", "Group", "Session Status", "My Status"
        };

        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.setRowHeight(38);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(70, 180, 1000, 380);

        JButton refresh = LamayaTheme.button("Refresh");
        refresh.setBounds(445, 590, 260, 55);

        dateBox.addActionListener(e -> loadSessions());
        refresh.addActionListener(e -> loadSessions());

        bg.add(title);
        bg.add(dateBox);
        bg.add(scroll);
        bg.add(refresh);

        add(bg);

        attendanceManager.addChangeListener(() ->
                SwingUtilities.invokeLater(this::loadSessions)
        );

        loadSessions();
        setVisible(true);
    }

    private void loadSessions() {

        model.setRowCount(0);

        String selectedDate = dateBox.getSelectedItem().toString();

        for (Session session :
                attendanceManager.getSessionsForStudentByDate(student, selectedDate)) {

            model.addRow(new Object[]{
                    session.getTime(),
                    session.getModule().getModuleName(),
                    session.getModule().getTeacher().getName(),
                    session.getGroup().getGroupName(),
                    session.getStatus(),
                    attendanceManager.getStudentStatusInSession(student, session)
            });
        }
    }
}