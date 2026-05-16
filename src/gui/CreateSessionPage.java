package gui;

import model.Session;
import model.Teacher;
import service.AttendanceManager;

import javax.swing.*;
import java.awt.*;

public class CreateSessionPage extends JFrame {

    public CreateSessionPage(Teacher teacher,
                             AttendanceManager attendanceManager) {

        setTitle("Create Session");
        setSize(900, 650);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(120, 55, 660, 520);

        JLabel title = LamayaTheme.title("Create New Session");
        title.setBounds(130, 30, 450, 50);

        JLabel module = new JLabel("Module: " + teacher.getSpeciality());
        module.setBounds(125, 100, 450, 35);
        module.setFont(new Font("Arial", Font.BOLD, 20));
        module.setForeground(LamayaTheme.DARK_PINK);

        JComboBox<String> dateBox = new JComboBox<>(new String[]{
                "20/05/2026",
                "21/05/2026",
                "22/05/2026",
                "23/05/2026"
        });
        dateBox.setBounds(125, 160, 410, 55);
        dateBox.setFont(new Font("Arial", Font.PLAIN, 17));

        JComboBox<String> timeBox = new JComboBox<>(new String[]{
                "08:00 - 10:00",
                "10:00 - 12:00",
                "13:30 - 15:30",
                "15:30 - 17:30"
        });
        timeBox.setBounds(125, 235, 410, 55);
        timeBox.setFont(new Font("Arial", Font.PLAIN, 17));

        JComboBox<String> groupBox = new JComboBox<>(new String[]{
                "G1",
                "G2"
        });
        groupBox.setBounds(125, 310, 410, 55);
        groupBox.setFont(new Font("Arial", Font.PLAIN, 17));

        JButton create = LamayaTheme.button("Create & Open Session");
        create.setBounds(125, 405, 410, 65);

        create.addActionListener(e -> {

            Session session = attendanceManager.createSessionFromTeacher(
                    teacher,
                    dateBox.getSelectedItem().toString(),
                    timeBox.getSelectedItem().toString(),
                    groupBox.getSelectedItem().toString()
            );

            JOptionPane.showMessageDialog(this, "Session created and opened.");

            new TakeAttendancePage(teacher, attendanceManager, session);
            dispose();
        });

        card.add(title);
        card.add(module);
        card.add(dateBox);
        card.add(timeBox);
        card.add(groupBox);
        card.add(create);

        bg.add(card);
        add(bg);
        setVisible(true);
    }
}