package gui;

import service.AttendanceManager;
import service.FileManager;
import service.ReportGenerator;

import javax.swing.*;
import java.awt.*;

public class AdminReportPage extends JFrame {

    public AdminReportPage(AttendanceManager attendanceManager) {

        setTitle("Admin Reports");
        setSize(1250, 760);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(160, 70, 930, 600);

        JLabel title = LamayaTheme.title("Excluded Students Report");
        title.setBounds(240, 35, 550, 50);

        JTextArea area = new JTextArea();
        area.setFont(new Font("Arial", Font.PLAIN, 17));
        area.setEditable(false);
        area.setBorder(BorderFactory.createLineBorder(LamayaTheme.PINK, 2));

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBounds(90, 120, 740, 310);

        ReportGenerator reportGenerator =
                new ReportGenerator(attendanceManager);

        area.setText(reportGenerator.generateExcludedStudentsReport());

        JButton export = LamayaTheme.button("Export report.txt");
        export.setBounds(280, 475, 350, 65);

        export.addActionListener(e -> {
            FileManager fileManager =
                    new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

            fileManager.exportReportToTextFile(area.getText());

            JOptionPane.showMessageDialog(
                    this,
                    "Report exported to report.txt"
            );
        });

        card.add(title);
        card.add(scrollPane);
        card.add(export);

        bg.add(card);
        add(bg);
        setVisible(true);
    }
}