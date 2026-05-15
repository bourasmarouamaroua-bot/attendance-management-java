package gui;

import javax.swing.*;

public class StudentManagementPage extends JFrame {

    public StudentManagementPage() {
        setTitle("Student Management");
        setSize(1250, 760);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel(null);
        bg.setBackground(LamayaTheme.BG);

        JPanel card = LamayaTheme.card(310, 80, 630, 560);

        JLabel title = LamayaTheme.title("Student Management");
        title.setBounds(110, 45, 450, 50);

        JTextField id = LamayaTheme.textField("Student ID");
        id.setBounds(110, 150, 410, 58);

        JTextField name = LamayaTheme.textField("Student Name");
        name.setBounds(110, 240, 410, 58);

        JTextField group = LamayaTheme.textField("Group");
        group.setBounds(110, 330, 410, 58);

        JButton add = LamayaTheme.button("Add Student");
        add.setBounds(110, 445, 410, 65);

        card.add(title);
        card.add(id);
        card.add(name);
        card.add(group);
        card.add(add);

        bg.add(card);
        add(bg);
        setVisible(true);
    }
}