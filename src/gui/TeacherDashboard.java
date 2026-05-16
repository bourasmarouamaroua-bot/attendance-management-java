package gui;

import model.Teacher;
import service.AttendanceManager;
import service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class TeacherDashboard extends JFrame {

    public TeacherDashboard(Teacher teacher,
                            AttendanceManager attendanceManager,
                            AuthenticationService authService) {

        setTitle("Teacher Dashboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Hello, " + teacher.getName());
        title.setBounds(80, 45, 700, 55);

        JLabel sub = new JLabel("Module: " + teacher.getSpeciality());
        sub.setBounds(85, 100, 500, 30);
        sub.setFont(new Font("Arial", Font.BOLD, 20));
        sub.setForeground(LamayaTheme.GRAY);

        JButton create = LamayaTheme.button("Create Session");
        create.setBounds(180, 220, 300, 80);

        JButton sessions = LamayaTheme.button("My Sessions");
        sessions.setBounds(500, 220, 300, 80);

        JButton reports = LamayaTheme.button("Reports");
        reports.setBounds(820, 220, 300, 80);

        JButton logout = LamayaTheme.button("Logout");
        logout.setBounds(500, 560, 260, 60);

        create.addActionListener(e ->
                new CreateSessionPage(teacher, attendanceManager)
        );

        sessions.addActionListener(e ->
                new TeacherSessionsPage(teacher, attendanceManager)
        );

        reports.addActionListener(e ->
                new ReportPage(teacher, attendanceManager)
        );

        logout.addActionListener(e -> {
            new LoginPage(attendanceManager, authService);
            dispose();
        });

        bg.add(title);
        bg.add(sub);
        bg.add(create);
        bg.add(sessions);
        bg.add(reports);
        bg.add(logout);

        add(bg);
        setVisible(true);
    }
}