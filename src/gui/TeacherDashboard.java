package gui;

import model.Teacher;
import service.AttendanceManager;
import service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class TeacherDashboard extends JFrame {

    private Teacher teacher;
    private AttendanceManager attendanceManager;
    private AuthenticationService authService;

    public TeacherDashboard(Teacher teacher,
                            AttendanceManager attendanceManager,
                            AuthenticationService authService) {

        this.teacher = teacher;
        this.attendanceManager = attendanceManager;
        this.authService = authService;

        setTitle("Teacher Dashboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel hello = LamayaTheme.title("Hello, " + teacher.getName() + " ♡");
        hello.setBounds(80, 45, 700, 55);

        JLabel subtitle = new JLabel("Create sessions, take attendance, and generate reports.");
        subtitle.setBounds(85, 100, 700, 30);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitle.setForeground(LamayaTheme.GRAY);

        JPanel card1 = LamayaTheme.card(110, 190, 300, 300);
        JPanel card2 = LamayaTheme.card(475, 190, 300, 300);
        JPanel card3 = LamayaTheme.card(840, 190, 300, 300);

        JLabel t1 = LamayaTheme.title("＋");
        t1.setBounds(120, 25, 100, 70);
        JLabel l1 = new JLabel("Create Session", SwingConstants.CENTER);
        l1.setBounds(25, 115, 250, 40);
        l1.setFont(new Font("Arial", Font.BOLD, 24));
        JButton b1 = LamayaTheme.button("Start");
        b1.setBounds(55, 205, 190, 55);

        JLabel t2 = LamayaTheme.title("📋");
        t2.setBounds(110, 25, 100, 70);
        JLabel l2 = new JLabel("My Sessions", SwingConstants.CENTER);
        l2.setBounds(25, 115, 250, 40);
        l2.setFont(new Font("Arial", Font.BOLD, 24));
        JButton b2 = LamayaTheme.button("Open List");
        b2.setBounds(55, 205, 190, 55);

        JLabel t3 = LamayaTheme.title("📄");
        t3.setBounds(110, 25, 100, 70);
        JLabel l3 = new JLabel("Reports", SwingConstants.CENTER);
        l3.setBounds(25, 115, 250, 40);
        l3.setFont(new Font("Arial", Font.BOLD, 24));
        JButton b3 = LamayaTheme.button("Generate");
        b3.setBounds(55, 205, 190, 55);

        b1.addActionListener(e -> new CreateSessionPage(teacher, attendanceManager));
        b2.addActionListener(e -> new TeacherSessionsPage(teacher, attendanceManager));
        b3.addActionListener(e -> new ReportPage(teacher, attendanceManager));

        card1.add(t1); card1.add(l1); card1.add(b1);
        card2.add(t2); card2.add(l2); card2.add(b2);
        card3.add(t3); card3.add(l3); card3.add(b3);

        JButton logout = LamayaTheme.button("Logout");
        logout.setBounds(500, 575, 250, 60);
        logout.addActionListener(e -> {
            new LoginPage(attendanceManager, authService);
            dispose();
        });

        bg.add(hello);
        bg.add(subtitle);
        bg.add(card1);
        bg.add(card2);
        bg.add(card3);
        bg.add(logout);

        add(bg);
        setVisible(true);
    }
}