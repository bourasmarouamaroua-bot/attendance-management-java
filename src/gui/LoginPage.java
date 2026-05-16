package gui;

import exceptions.InvalidLoginException;
import model.Admin;
import model.Student;
import model.Teacher;
import model.User;
import service.AttendanceManager;
import service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private AttendanceManager attendanceManager;
    private AuthenticationService authService;

    public LoginPage(AttendanceManager attendanceManager,
                     AuthenticationService authService) {

        this.attendanceManager = attendanceManager;
        this.authService = authService;

        setTitle("Attendance Management System");
        setSize(1250, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(70, 60, 1110, 620);

        JPanel leftPanel = new JPanel(null);
        leftPanel.setBounds(0, 0, 440, 620);
        leftPanel.setBackground(LamayaTheme.PINK);

        JLabel icon = new JLabel("✓", SwingConstants.CENTER);
        icon.setBounds(155, 95, 130, 130);
        icon.setFont(new Font("Arial", Font.BOLD, 80));
        icon.setForeground(Color.WHITE);

        JLabel appTitle = new JLabel("ATTENDANCE", SwingConstants.CENTER);
        appTitle.setBounds(35, 260, 370, 55);
        appTitle.setFont(new Font("Arial", Font.BOLD, 42));
        appTitle.setForeground(Color.WHITE);

        JLabel sub = new JLabel("MANAGEMENT SYSTEM", SwingConstants.CENTER);
        sub.setBounds(35, 320, 370, 35);
        sub.setFont(new Font("Arial", Font.BOLD, 22));
        sub.setForeground(Color.WHITE);

        JLabel slogan = new JLabel("Track. Manage. Succeed.", SwingConstants.CENTER);
        slogan.setBounds(50, 450, 340, 40);
        slogan.setFont(new Font("Segoe Script", Font.PLAIN, 22));
        slogan.setForeground(LamayaTheme.DARK_PINK);

        leftPanel.add(icon);
        leftPanel.add(appTitle);
        leftPanel.add(sub);
        leftPanel.add(slogan);

        JLabel welcome = LamayaTheme.title("Welcome Back! ♡");
        welcome.setBounds(560, 95, 500, 55);

        JLabel text = new JLabel("Login to access your dashboard");
        text.setBounds(565, 155, 420, 30);
        text.setFont(new Font("Arial", Font.PLAIN, 22));
        text.setForeground(LamayaTheme.GRAY);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(560, 230, 200, 30);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));

        usernameField = LamayaTheme.textField("");
        usernameField.setBounds(560, 265, 490, 58);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(560, 355, 200, 30);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));

        passwordField = LamayaTheme.passwordField("");
        passwordField.setBounds(560, 390, 490, 58);

        JButton loginButton = LamayaTheme.button("LOGIN  →");
        loginButton.setBounds(560, 505, 490, 65);
        loginButton.addActionListener(e -> login());

        card.add(leftPanel);
        card.add(welcome);
        card.add(text);
        card.add(usernameLabel);
        card.add(usernameField);
        card.add(passwordLabel);
        card.add(passwordField);
        card.add(loginButton);

        bg.add(card);
        add(bg);
        setVisible(true);
    }

    private void login() {

        try {
            User user = authService.login(
                    usernameField.getText(),
                    new String(passwordField.getPassword())
            );

            if (user instanceof Admin) {
                new AdminDashboard((Admin) user, attendanceManager, authService);
                dispose();

            } else if (user instanceof Teacher) {
                new TeacherDashboard((Teacher) user, attendanceManager, authService);
                dispose();

            } else if (user instanceof Student) {
                new StudentDashboard((Student) user, attendanceManager, authService);
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Unknown user type.");
            }

        } catch (InvalidLoginException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}