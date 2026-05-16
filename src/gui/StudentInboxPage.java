package gui;

import model.Notification;
import model.Student;
import service.AttendanceManager;

import javax.swing.*;
import java.awt.*;

public class StudentInboxPage extends JFrame {

    private Student student;
    private AttendanceManager attendanceManager;
    private JTextArea area;

    public StudentInboxPage(Student student,
                            AttendanceManager attendanceManager) {

        this.student = student;
        this.attendanceManager = attendanceManager;

        setTitle("Student Inbox");
        setSize(1000, 650);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Inbox");
        title.setBounds(430, 30, 300, 50);

        area = new JTextArea();
        area.setFont(new Font("Arial", Font.PLAIN, 16));
        area.setEditable(false);
        area.setBorder(BorderFactory.createLineBorder(LamayaTheme.PINK, 2));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(90, 110, 800, 390);

        JButton refresh = LamayaTheme.button("Refresh Inbox");
        refresh.setBounds(355, 535, 290, 55);

        refresh.addActionListener(e -> loadInbox());

        bg.add(title);
        bg.add(scroll);
        bg.add(refresh);

        add(bg);

        attendanceManager.addChangeListener(() ->
                SwingUtilities.invokeLater(this::loadInbox)
        );

        loadInbox();
        setVisible(true);
    }

    private void loadInbox() {

        StringBuilder text = new StringBuilder();

        text.append("MESSAGES FOR ")
                .append(student.getName())
                .append("\n\n");

        if (attendanceManager.getNotificationsForStudent(student).isEmpty()) {
            text.append("No messages yet.");
        }

        for (Notification notification :
                attendanceManager.getNotificationsForStudent(student)) {

            text.append(notification.toString())
                    .append("\n\n----------------------\n\n");
        }

        area.setText(text.toString());
    }
}