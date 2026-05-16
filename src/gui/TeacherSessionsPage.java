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
    private JTable table;
    private DefaultTableModel model;

    public TeacherSessionsPage(Teacher teacher, AttendanceManager attendanceManager) {

        this.teacher = teacher;
        this.attendanceManager = attendanceManager;

        setTitle("My Sessions");
        setSize(1100, 650);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("My Sessions");
        title.setBounds(420, 35, 350, 50);

        String[] columns = {"ID", "Module", "Group", "Date", "Time", "Status", "Locked"};
        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setBackground(LamayaTheme.PINK);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(90, 110, 900, 350);

        JButton open = LamayaTheme.button("Take Attendance");
        open.setBounds(350, 500, 360, 60);

        open.addActionListener(e -> openSelectedSession());

        bg.add(title);
        bg.add(scroll);
        bg.add(open);

        add(bg);
        loadTable();
        setVisible(true);
    }

    private void loadTable() {

        model.setRowCount(0);

        for (Session session : attendanceManager.getSessionsByTeacher(teacher)) {
            model.addRow(new Object[]{
                    session.getSessionId(),
                    session.getModule().getModuleName(),
                    session.getGroup().getGroupName(),
                    session.getDate(),
                    session.getTime(),
                    session.getStatus(),
                    session.isLocked() ? "YES" : "NO"
            });
        }
    }

    private void openSelectedSession() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a session first.");
            return;
        }

        int sessionId = Integer.parseInt(model.getValueAt(row, 0).toString());
        Session session = attendanceManager.findSessionById(sessionId);

        new TakeAttendancePage(teacher, attendanceManager, session);
        dispose();
    }
}