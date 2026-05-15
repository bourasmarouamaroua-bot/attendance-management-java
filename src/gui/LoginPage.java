package gui;

import model.Admin;
import model.Student;
import model.Teacher;
import model.User;
import service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    private final AuthenticationService authService = new AuthenticationService();

    public LoginPage() {

        setTitle("Attendance Management System");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(70, 60, 1110, 620);

        JPanel left = new JPanel(null);
        left.setBounds(0, 0, 440, 620);
        left.setBackground(LamayaTheme.PINK);

        JLabel icon = new JLabel("✓", SwingConstants.CENTER);
        icon.setBounds(165, 110, 110, 110);
        icon.setFont(new Font("Arial", Font.BOLD, 70));
        icon.setForeground(Color.WHITE);

        JLabel app = new JLabel("ATTENDANCE", SwingConstants.CENTER);
        app.setBounds(40, 260, 360, 55);
        app.setFont(new Font("Arial", Font.BOLD, 42));
        app.setForeground(Color.WHITE);

        JLabel sub = new JLabel("MANAGEMENT SYSTEM", SwingConstants.CENTER);
        sub.setBounds(40, 315, 360, 35);
        sub.setFont(new Font("Arial", Font.BOLD, 23));
        sub.setForeground(Color.WHITE);

        JLabel slogan = new JLabel("Track. Manage. Succeed.", SwingConstants.CENTER);
        slogan.setBounds(40, 455, 360, 40);
        slogan.setFont(new Font("Segoe Script", Font.PLAIN, 22));
        slogan.setForeground(LamayaTheme.DARK_PINK);

        left.add(icon);
        left.add(app);
        left.add(sub);
        left.add(slogan);

        JLabel welcome = new JLabel("Welcome Back! ♡");
        welcome.setBounds(560, 95, 500, 55);
        welcome.setFont(new Font("Arial", Font.BOLD, 44));
        welcome.setForeground(LamayaTheme.DARK_PINK);

        JLabel info = new JLabel("Please login to continue");
        info.setBounds(565, 150, 350, 30);
        info.setFont(new Font("Arial", Font.PLAIN, 22));
        info.setForeground(LamayaTheme.GRAY);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(560, 225, 200, 30);
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JTextField usernameField = LamayaTheme.textField("Enter username");
        usernameField.setBounds(560, 260, 490, 58);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(560, 355, 200, 30);
        passLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPasswordField passwordField = LamayaTheme.passwordField("Enter password");
        passwordField.setBounds(560, 390, 490, 58);

        JCheckBox remember = new JCheckBox("Remember me");
        remember.setBounds(560, 480, 180, 30);
        remember.setFont(new Font("Arial", Font.PLAIN, 16));
        remember.setBackground(Color.WHITE);

        JButton loginButton = LamayaTheme.button("LOGIN  →");
        loginButton.setBounds(560, 545, 490, 65);

        loginButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            try {

                User user = authService.login(username, password);

                JOptionPane.showMessageDialog(
                        this,
                        "Welcome " + user.getName()
                );

                if (user instanceof Admin) {
                    new AdminDashboard();
                } else if (user instanceof Teacher) {
                    new TeacherDashboard();
                } else if (user instanceof Student) {
                    new StudentDashboard();
                }

                dispose();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        card.add(left);
        card.add(welcome);
        card.add(info);
        card.add(userLabel);
        card.add(usernameField);
        card.add(passLabel);
        card.add(passwordField);
        card.add(remember);
        card.add(loginButton);

        bg.add(card);
        add(bg);

        setVisible(true);
    }
}