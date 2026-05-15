package gui;

import javax.swing.*;

public class TeacherDashboard extends JFrame {

    public TeacherDashboard() {
        setTitle("Teacher Dashboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(310, 110, 630, 500);

        JLabel title = LamayaTheme.title("Teacher Dashboard");
        title.setBounds(130, 50, 400, 50);

        JButton attendance = LamayaTheme.button("Take Attendance");
        attendance.setBounds(115, 150, 400, 65);

        JButton reports = LamayaTheme.button("Generate Reports");
        reports.setBounds(115, 250, 400, 65);

        JButton logout = LamayaTheme.button("Logout");
        logout.setBounds(115, 350, 400, 65);

        attendance.addActionListener(e -> new AttendancePage());
        reports.addActionListener(e -> new ReportPage());
        logout.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        card.add(title);
        card.add(attendance);
        card.add(reports);
        card.add(logout);

        bg.add(card);
        add(bg);
        setVisible(true);
    }
}