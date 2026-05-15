package gui;

import javax.swing.*;
import java.awt.*;

public class ReportPage extends JFrame {

    public ReportPage() {
        setTitle("Reports");
        setSize(1250, 760);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(190, 80, 870, 560);

        JLabel title = LamayaTheme.title("Attendance Reports");
        title.setBounds(250, 35, 450, 50);

        JTextArea reportArea = new JTextArea();
        reportArea.setBounds(135, 120, 600, 300);
        reportArea.setFont(new Font("Arial", Font.PLAIN, 18));
        reportArea.setBorder(BorderFactory.createLineBorder(LamayaTheme.PINK, 2));
        reportArea.setText(
                "Student Report\n\n" +
                        "Name: Sara\n" +
                        "Present: 10\n" +
                        "Late: 2\n" +
                        "Absent: 3\n\n" +
                        "Status: Warning"
        );

        JButton export = LamayaTheme.button("Export Report");
        export.setBounds(260, 460, 350, 65);

        card.add(title);
        card.add(reportArea);
        card.add(export);

        bg.add(card);
        add(bg);
        setVisible(true);
    }
}