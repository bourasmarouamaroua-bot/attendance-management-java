package gui;

import model.Group;
import model.Module;
import model.Session;
import model.Student;
import model.Teacher;
import service.AttendanceManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreateSessionPage extends JFrame {

    private Teacher teacher;
    private AttendanceManager attendanceManager;

    public CreateSessionPage(Teacher teacher, AttendanceManager attendanceManager) {

        this.teacher = teacher;
        this.attendanceManager = attendanceManager;

        setTitle("Create Session");
        setSize(900, 620);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(120, 55, 660, 490);

        JLabel title = LamayaTheme.title("Create New Session");
        title.setBounds(130, 35, 450, 50);

        JTextField moduleField = LamayaTheme.textField("Module name");
        moduleField.setBounds(125, 120, 410, 55);

        JComboBox<String> groupBox = new JComboBox<>();
        groupBox.setBounds(125, 195, 410, 55);
        groupBox.setFont(new Font("Arial", Font.PLAIN, 17));
        loadGroups(groupBox);

        JTextField dateField = LamayaTheme.textField("Date example: 20/05/2026");
        dateField.setBounds(125, 270, 410, 55);

        JTextField timeField = LamayaTheme.textField("Time example: 08:00");
        timeField.setBounds(125, 345, 410, 55);

        JButton create = LamayaTheme.button("Create & Open Session");
        create.setBounds(125, 420, 410, 58);

        create.addActionListener(e -> {

            String moduleName = moduleField.getText().trim();
            String groupName = groupBox.getSelectedItem().toString();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();

            if (moduleName.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields.");
                return;
            }

            Group group = buildGroup(groupName);
            Module module = new Module(attendanceManager.generateNextSessionId(), moduleName, teacher);

            Session session = attendanceManager.createSession(
                    attendanceManager.generateNextSessionId(),
                    date,
                    time,
                    module,
                    group
            );

            JOptionPane.showMessageDialog(this, "Session created and opened.");
            new TakeAttendancePage(teacher, attendanceManager, session);
            dispose();
        });

        card.add(title);
        card.add(moduleField);
        card.add(groupBox);
        card.add(dateField);
        card.add(timeField);
        card.add(create);

        bg.add(card);
        add(bg);
        setVisible(true);
    }

    private void loadGroups(JComboBox<String> groupBox) {

        ArrayList<String> groups = new ArrayList<>();

        for (Student student : attendanceManager.getStudents()) {
            if (!groups.contains(student.getGroup())) {
                groups.add(student.getGroup());
                groupBox.addItem(student.getGroup());
            }
        }
    }

    private Group buildGroup(String groupName) {

        Group group = new Group(100 + attendanceManager.generateNextSessionId(), groupName);

        for (Student student : attendanceManager.getStudents()) {
            if (student.getGroup().equalsIgnoreCase(groupName)) {
                group.addStudent(student);
            }
        }

        return group;
    }
}