package gui;

import model.Session;
import model.Teacher;
import service.AttendanceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TeacherSessionsPage extends JFrame {

    private Teacher teacher;
    private AttendanceManager attendanceManager;
    private DefaultTableModel model;
    private JTable table;

    public TeacherSessionsPage(Teacher teacher,
                               AttendanceManager attendanceManager) {

        this.teacher = teacher;
        this.attendanceManager = attendanceManager;

        setTitle("My Sessions");
        setSize(1100, 650);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("My Sessions");
        title.setBounds(420, 35, 350, 50);

        String[] columns = {"ID", "Date", "Time", "Module", "Group", "Status", "Locked"};

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(70, 110, 950, 370);

        JButton open = LamayaTheme.button("Open Selected Session");
        open.setBounds(370, 520, 350, 60);

        open.addActionListener(e -> openSession());

        bg.add(title);
        bg.add(scroll);
        bg.add(open);

        add(bg);

        attendanceManager.addChangeListener(() ->
                SwingUtilities.invokeLater(this::loadSessions)
        );

        loadSessions();
        setVisible(true);
    }

    private void loadSessions() {

        model.setRowCount(0);

        for (Session session : attendanceManager.getSessionsByTeacher(teacher)) {
            model.addRow(new Object[]{
                    session.getSessionId(),
                    session.getDate(),
                    session.getTime(),
                    session.getModule().getModuleName(),
                    session.getGroup().getGroupName(),
                    session.getStatus(),
                    session.isLocked() ? "YES" : "NO"
            });
        }
    }

    private void openSession() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a session first.");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());

        Session session = attendanceManager.findSessionById(id);

        new TakeAttendancePage(teacher, attendanceManager, session);
    }
}