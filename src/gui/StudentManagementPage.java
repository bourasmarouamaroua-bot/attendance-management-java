package gui;

import exceptions.DuplicateStudentException;
import model.Student;
import service.AttendanceManager;
import service.AuthenticationService;
import service.FileManager;

import javax.swing.*;

public class StudentManagementPage extends JFrame {

    private AttendanceManager attendanceManager;
    private AuthenticationService authService;

    public StudentManagementPage(AttendanceManager attendanceManager,
                                 AuthenticationService authService) {

        this.attendanceManager = attendanceManager;
        this.authService = authService;

        setTitle("Student Management");
        setSize(1250, 760);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(310, 60, 630, 610);

        JLabel title = LamayaTheme.title("Student Management");
        title.setBounds(110, 35, 450, 50);

        JTextField id = LamayaTheme.textField("Student ID");
        id.setBounds(110, 120, 410, 55);

        JTextField name = LamayaTheme.textField("Student Name");
        name.setBounds(110, 195, 410, 55);

        JTextField username = LamayaTheme.textField("Username");
        username.setBounds(110, 270, 410, 55);

        JTextField password = LamayaTheme.textField("Password");
        password.setBounds(110, 345, 410, 55);

        JTextField group = LamayaTheme.textField("Group");
        group.setBounds(110, 420, 410, 55);

        JButton add = LamayaTheme.button("Add Student");
        add.setBounds(110, 500, 410, 65);

        add.addActionListener(e -> {

            try {
                int studentId = Integer.parseInt(id.getText().trim());

                Student student = new Student(
                        studentId,
                        name.getText().trim(),
                        username.getText().trim(),
                        password.getText().trim(),
                        "STUDENT",
                        group.getText().trim()
                );

                attendanceManager.addStudent(student);
                authService.addUser(student);

                FileManager fileManager =
                        new FileManager(attendanceManager.getStudents(), attendanceManager.getRecords());

                fileManager.saveToFile();
                fileManager.exportStudentsToTextFile();

                JOptionPane.showMessageDialog(
                        this,
                        "Student added successfully."
                );

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "ID must be a number.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (DuplicateStudentException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Duplicate Student",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        card.add(title);
        card.add(id);
        card.add(name);
        card.add(username);
        card.add(password);
        card.add(group);
        card.add(add);

        bg.add(card);
        add(bg);
        setVisible(true);
    }
}