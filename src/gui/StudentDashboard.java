package gui;

import model.Student;
import model.Teacher;
import service.AttendanceManager;
import service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private Student student;
    private AttendanceManager attendanceManager;
    private AuthenticationService authService;

    public StudentDashboard(Student student,
                            AttendanceManager attendanceManager,
                            AuthenticationService authService) {

        this.student = student;
        this.attendanceManager = attendanceManager;
        this.authService = authService;

        setTitle("Student Dashboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JLabel title = LamayaTheme.title("Hello, " + student.getName() + " ♡");
        title.setBounds(80, 40, 700, 55);

        JLabel sub = new JLabel("Choose a teacher to see your sessions and your personal report.");
        sub.setBounds(85, 100, 800, 30);
        sub.setFont(new Font("Arial", Font.PLAIN, 20));
        sub.setForeground(LamayaTheme.GRAY);

        DefaultListModel<Teacher> listModel = new DefaultListModel<>();

        for (Teacher teacher : attendanceManager.getTeachersForStudent(student)) {
            listModel.addElement(teacher);
        }

        JList<Teacher> teacherList = new JList<>(listModel);
        teacherList.setFont(new Font("Arial", Font.BOLD, 20));
        teacherList.setFixedCellHeight(55);
        teacherList.setBorder(BorderFactory.createLineBorder(LamayaTheme.PINK, 2));

        JScrollPane scroll = new JScrollPane(teacherList);
        scroll.setBounds(180, 170, 880, 330);

        JButton open = LamayaTheme.button("Open Teacher Report");
        open.setBounds(310, 545, 300, 60);

        JButton logout = LamayaTheme.button("Logout");
        logout.setBounds(660, 545, 250, 60);

        open.addActionListener(e -> {

            Teacher selectedTeacher = teacherList.getSelectedValue();

            if (selectedTeacher == null) {
                JOptionPane.showMessageDialog(this, "Select a teacher first.");
                return;
            }

            new StudentTeacherReportPage(student, selectedTeacher, attendanceManager);
        });

        logout.addActionListener(e -> {
            new LoginPage(attendanceManager, authService);
            dispose();
        });

        bg.add(title);
        bg.add(sub);
        bg.add(scroll);
        bg.add(open);
        bg.add(logout);

        add(bg);
        setVisible(true);
    }
}