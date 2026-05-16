package gui;

import model.Admin;
import service.AttendanceManager;
import service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private Admin admin;
    private AttendanceManager attendanceManager;
    private AuthenticationService authService;

    public AdminDashboard(Admin admin,
                          AttendanceManager attendanceManager,
                          AuthenticationService authService) {

        this.admin = admin;
        this.attendanceManager = attendanceManager;
        this.authService = authService;

        setTitle("Admin Dashboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Admin Dashboard ♡");
        title.setBounds(80, 45, 600, 55);

        JLabel sub = new JLabel("Monitor all students, warnings, exclusions, and full attendance sheets.");
        sub.setBounds(85, 100, 800, 30);
        sub.setFont(new Font("Arial", Font.PLAIN, 20));
        sub.setForeground(LamayaTheme.GRAY);

        JPanel c1 = LamayaTheme.card(170, 210, 380, 270);
        JPanel c2 = LamayaTheme.card(700, 210, 380, 270);

        JLabel l1 = new JLabel("Full Attendance Sheet", SwingConstants.CENTER);
        l1.setBounds(35, 75, 310, 45);
        l1.setFont(new Font("Arial", Font.BOLD, 24));

        JButton b1 = LamayaTheme.button("Open");
        b1.setBounds(75, 165, 230, 60);

        JLabel l2 = new JLabel("Student Status", SwingConstants.CENTER);
        l2.setBounds(35, 75, 310, 45);
        l2.setFont(new Font("Arial", Font.BOLD, 24));

        JButton b2 = LamayaTheme.button("Warnings / Exclusions");
        b2.setBounds(55, 165, 270, 60);

        b1.addActionListener(e -> new AdminFullAttendancePage(attendanceManager));
        b2.addActionListener(e -> new AdminStudentStatusPage(attendanceManager));

        c1.add(l1);
        c1.add(b1);
        c2.add(l2);
        c2.add(b2);

        JButton logout = LamayaTheme.button("Logout");
        logout.setBounds(500, 570, 250, 60);
        logout.addActionListener(e -> {
            new LoginPage(attendanceManager, authService);
            dispose();
        });

        bg.add(title);
        bg.add(sub);
        bg.add(c1);
        bg.add(c2);
        bg.add(logout);

        add(bg);
        setVisible(true);
    }
}