package gui;

import model.Admin;
import model.Student;
import service.AttendanceManager;

import javax.swing.*;
import java.awt.*;

public class AdminSendNotificationPage extends JFrame {

    private AttendanceManager attendanceManager;
    private JComboBox<Student> studentBox;
    private JComboBox<String> typeBox;
    private JTextArea detailsArea;

    public AdminSendNotificationPage(Admin admin,
                                     AttendanceManager attendanceManager) {

        this.attendanceManager = attendanceManager;

        setTitle("Send Warning / Exclusion");
        setSize(950, 700);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(120, 45, 710, 560);

        JLabel title = LamayaTheme.title("Send Notification");
        title.setBounds(160, 30, 450, 50);

        studentBox = new JComboBox<>();
        studentBox.setBounds(120, 110, 470, 50);
        studentBox.setFont(new Font("Arial", Font.PLAIN, 16));

        for (Student student : attendanceManager.getStudents()) {
            studentBox.addItem(student);
        }

        typeBox = new JComboBox<>(new String[]{
                "WARNING",
                "EXCLUSION"
        });
        typeBox.setBounds(120, 180, 470, 50);
        typeBox.setFont(new Font("Arial", Font.PLAIN, 16));

        detailsArea = new JTextArea();
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 15));
        detailsArea.setBorder(BorderFactory.createLineBorder(LamayaTheme.PINK, 2));

        JScrollPane scroll = new JScrollPane(detailsArea);
        scroll.setBounds(120, 255, 470, 170);

        JButton send = LamayaTheme.button("Send To Student");
        send.setBounds(170, 460, 370, 60);

        send.addActionListener(e -> sendNotification(admin));

        card.add(title);
        card.add(studentBox);
        card.add(typeBox);
        card.add(scroll);
        card.add(send);

        bg.add(card);
        add(bg);
        setVisible(true);
    }

    private void sendNotification(Admin admin) {

        Student student = (Student) studentBox.getSelectedItem();

        if (student == null) {
            JOptionPane.showMessageDialog(this, "Select a student.");
            return;
        }

        String type = typeBox.getSelectedItem().toString();

        String automaticDetails =
                "Unjustified absences: "
                        + attendanceManager.countUnjustifiedAbsences(student)
                        + "\nJustified absences: "
                        + attendanceManager.countJustifiedAbsences(student)
                        + "\nCurrent status: "
                        + attendanceManager.getAdministrativeStatus(student);

        String adminMessage = detailsArea.getText().trim();

        String finalMessage =
                automaticDetails
                        + "\n\nAdmin message:\n"
                        + adminMessage;

        attendanceManager.sendNotification(
                student,
                admin.getName(),
                type,
                finalMessage,
                "20/05/2026"
        );

        JOptionPane.showMessageDialog(
                this,
                type + " sent to " + student.getName()
        );

        detailsArea.setText("");
    }
}