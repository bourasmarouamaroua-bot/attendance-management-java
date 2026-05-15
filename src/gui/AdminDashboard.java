package gui;

import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(310, 110, 630, 500);

        JLabel title = LamayaTheme.title("Admin Dashboard");
        title.setBounds(145, 50, 400, 50);

        JButton manageStudents = LamayaTheme.button("Manage Students");
        manageStudents.setBounds(115, 150, 400, 65);

        JButton reports = LamayaTheme.button("Reports");
        reports.setBounds(115, 250, 400, 65);

        JButton logout = LamayaTheme.button("Logout");
        logout.setBounds(115, 350, 400, 65);

        manageStudents.addActionListener(e -> new StudentManagementPage());
        reports.addActionListener(e -> new ReportPage());
        logout.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        card.add(title);
        card.add(manageStudents);
        card.add(reports);
        card.add(logout);

        bg.add(card);
        add(bg);
        setVisible(true);
    }
}