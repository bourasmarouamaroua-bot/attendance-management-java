package gui;

import model.Admin;
import service.AttendanceManager;
import service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard(Admin admin,
                          AttendanceManager attendanceManager,
                          AuthenticationService authService) {

        setTitle("Admin Dashboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Admin Dashboard");
        title.setBounds(80, 45, 700, 55);

        JLabel sub = new JLabel("Admin: " + admin.getName());
        sub.setBounds(85, 100, 700, 30);
        sub.setFont(new Font("Arial", Font.BOLD, 20));
        sub.setForeground(LamayaTheme.GRAY);

        JButton sheet = LamayaTheme.button("Full Attendance Sheet");
        sheet.setBounds(130, 220, 310, 80);

        JButton students = LamayaTheme.button("Student Status");
        students.setBounds(470, 220, 310, 80);

        JButton notify = LamayaTheme.button("Send Warning / Exclusion");
        notify.setBounds(810, 220, 310, 80);

        JButton logout = LamayaTheme.button("Logout");
        logout.setBounds(500, 560, 260, 60);

        sheet.addActionListener(e ->
                new AdminFullAttendancePage(attendanceManager)
        );

        students.addActionListener(e ->
                new AdminStudentStatusPage(attendanceManager)
        );

        notify.addActionListener(e ->
                new AdminSendNotificationPage(admin, attendanceManager)
        );

        logout.addActionListener(e -> {
            new LoginPage(attendanceManager, authService);
            dispose();
        });

        bg.add(title);
        bg.add(sub);
        bg.add(sheet);
        bg.add(students);
        bg.add(notify);
        bg.add(logout);

        add(bg);
        setVisible(true);
    }
}