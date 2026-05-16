package gui;

import model.Student;
import service.AttendanceManager;
import service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private Student student;
    private AttendanceManager attendanceManager;
    private AuthenticationService authService;

    public StudentDashboard(Student student,
                            AttendanceManager attendanceManager,
                            AuthenticationService authService) {

        this.student = student;
        this.attendanceManager = attendanceManager;
        this.authService = authService;

        setTitle("Student Dashboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Hello, " + student.getName());
        title.setBounds(80, 45, 700, 55);

        JLabel sub = new JLabel("Check your daily sessions, module status, and inbox.");
        sub.setBounds(85, 100, 800, 30);
        sub.setFont(new Font("Arial", Font.PLAIN, 20));
        sub.setForeground(LamayaTheme.GRAY);

        JButton daily = LamayaTheme.button("Daily Sessions");
        daily.setBounds(150, 220, 280, 80);

        JButton status = LamayaTheme.button("My Module Status");
        status.setBounds(485, 220, 280, 80);

        JButton inbox = LamayaTheme.button("Inbox");
        inbox.setBounds(820, 220, 280, 80);

        JButton logout = LamayaTheme.button("Logout");
        logout.setBounds(500, 560, 260, 60);

        daily.addActionListener(e ->
                new StudentDailySessionsPage(student, attendanceManager)
        );

        status.addActionListener(e ->
                new StudentStatusPage(student, attendanceManager)
        );

        inbox.addActionListener(e ->
                new StudentInboxPage(student, attendanceManager)
        );

        logout.addActionListener(e -> {
            new LoginPage(attendanceManager, authService);
            dispose();
        });

        bg.add(title);
        bg.add(sub);
        bg.add(daily);
        bg.add(status);
        bg.add(inbox);
        bg.add(logout);

        add(bg);
        setVisible(true);
    }
}