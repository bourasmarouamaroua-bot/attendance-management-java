package gui;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    public StudentDashboard() {
        setTitle("Student Dashboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(250, 110, 750, 500);

        JLabel title = LamayaTheme.title("Student Dashboard");
        title.setBounds(190, 50, 450, 50);

        JTextArea info = new JTextArea();
        info.setBounds(150, 140, 450, 220);
        info.setFont(new Font("Arial", Font.PLAIN, 20));
        info.setBorder(BorderFactory.createLineBorder(LamayaTheme.PINK, 2));
        info.setText(
                "Attendance Information\n\n" +
                        "Present: 8\n" +
                        "Late: 2\n" +
                        "Absent: 1\n\n" +
                        "Session Status: OPEN"
        );

        JButton logout = LamayaTheme.button("Logout");
        logout.setBounds(200, 400, 350, 65);

        logout.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        card.add(title);
        card.add(info);
        card.add(logout);

        bg.add(card);
        add(bg);
        setVisible(true);
    }
}