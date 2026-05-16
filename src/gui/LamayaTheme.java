package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LamayaTheme {

    // =========================
    // COLORS
    // =========================

    public static final Color BG =
            new Color(255, 239, 245);

    public static final Color CARD =
            Color.WHITE;

    public static final Color PINK =
            new Color(255, 176, 200);

    public static final Color DARK_PINK =
            new Color(155, 55, 100);

    public static final Color YELLOW =
            new Color(255, 211, 35);

    public static final Color TEXT =
            new Color(45, 45, 45);

    public static final Color GRAY =
            new Color(120, 120, 120);

    // =========================
    // ROUNDED BUTTON
    // =========================

    public static JButton button(String text) {

        JButton button = new JButton(text) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 =
                        (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                // Hover / Press effect

                if (getModel().isPressed()) {

                    g2.setColor(
                            new Color(235, 190, 20)
                    );

                } else if (getModel().isRollover()) {

                    g2.setColor(
                            new Color(255, 225, 80)
                    );

                } else {

                    g2.setColor(YELLOW);
                }

                g2.fillRoundRect(
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        35,
                        35
                );

                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {

                Graphics2D g2 =
                        (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(
                        new Color(230, 180, 10)
                );

                g2.drawRoundRect(
                        0,
                        0,
                        getWidth() - 1,
                        getHeight() - 1,
                        35,
                        35
                );

                g2.dispose();
            }
        };

        button.setForeground(TEXT);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        button.setFocusPainted(false);

        button.setContentAreaFilled(false);

        button.setOpaque(false);

        button.setBorder(
                new EmptyBorder(
                        14,
                        25,
                        14,
                        25
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    // =========================
    // TEXT FIELD WITH PLACEHOLDER
    // =========================

    public static JTextField textField(String placeholder) {

        JTextField field = new JTextField();

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        field.setForeground(GRAY);

        field.setText(placeholder);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PINK, 2),
                        new EmptyBorder(12, 18, 12, 18)
                )
        );

        field.addFocusListener(
                new java.awt.event.FocusAdapter() {

                    @Override
                    public void focusGained(
                            java.awt.event.FocusEvent e
                    ) {

                        if (field.getText().equals(placeholder)) {

                            field.setText("");

                            field.setForeground(TEXT);
                        }
                    }

                    @Override
                    public void focusLost(
                            java.awt.event.FocusEvent e
                    ) {

                        if (field.getText().trim().isEmpty()) {

                            field.setText(placeholder);

                            field.setForeground(GRAY);
                        }
                    }
                }
        );

        return field;
    }

    // =========================
    // PASSWORD FIELD WITH PLACEHOLDER
    // =========================

    public static JPasswordField passwordField(String placeholder) {

        JPasswordField field = new JPasswordField();

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        field.setForeground(GRAY);

        field.setText(placeholder);

        field.setEchoChar((char) 0);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PINK, 2),
                        new EmptyBorder(12, 18, 12, 18)
                )
        );

        field.addFocusListener(
                new java.awt.event.FocusAdapter() {

                    @Override
                    public void focusGained(
                            java.awt.event.FocusEvent e
                    ) {

                        String text =
                                new String(field.getPassword());

                        if (text.equals(placeholder)) {

                            field.setText("");

                            field.setForeground(TEXT);

                            field.setEchoChar('•');
                        }
                    }

                    @Override
                    public void focusLost(
                            java.awt.event.FocusEvent e
                    ) {

                        String text =
                                new String(field.getPassword());

                        if (text.trim().isEmpty()) {

                            field.setText(placeholder);

                            field.setForeground(GRAY);

                            field.setEchoChar((char) 0);
                        }
                    }
                }
        );

        return field;
    }

    // =========================
    // TITLE
    // =========================

    public static JLabel title(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        38
                )
        );

        label.setForeground(DARK_PINK);

        return label;
    }

    // =========================
    // CARD PANEL
    // =========================

    public static JPanel card(
            int x,
            int y,
            int w,
            int h
    ) {

        JPanel panel = new JPanel(null);

        panel.setBounds(x, y, w, h);

        panel.setBackground(CARD);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(240, 220, 230),
                                1
                        ),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );

        return panel;
    }
}