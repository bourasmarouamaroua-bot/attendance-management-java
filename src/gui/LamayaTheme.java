package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LamayaTheme {

    public static final Color BG = new Color(255, 239, 245);
    public static final Color CARD = Color.WHITE;
    public static final Color PINK = new Color(255, 176, 200);
    public static final Color DARK_PINK = new Color(155, 55, 100);
    public static final Color YELLOW = new Color(255, 211, 35);
    public static final Color TEXT = new Color(45, 45, 45);
    public static final Color GRAY = new Color(120, 120, 120);

    public static JButton button(String text) {
        JButton button = new JButton(text);
        button.setBackground(YELLOW);
        button.setForeground(TEXT);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(14, 25, 14, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static JTextField textField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setForeground(GRAY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PINK, 2),
                new EmptyBorder(12, 18, 12, 18)
        ));
        return field;
    }

    public static JPasswordField passwordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setForeground(GRAY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PINK, 2),
                new EmptyBorder(12, 18, 12, 18)
        ));
        return field;
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 38));
        label.setForeground(DARK_PINK);
        return label;
    }

    public static JPanel card(int x, int y, int w, int h) {
        JPanel panel = new JPanel(null);
        panel.setBounds(x, y, w, h);
        panel.setBackground(CARD);
        return panel;
    }
}