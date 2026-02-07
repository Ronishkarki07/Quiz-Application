package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminLoginPanel extends JPanel {
    private QuizMainFrame mainFrame;

    public AdminLoginPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // This ensures labels stretch to match text fields

        // --- 1. Title ---
        JLabel title = new JLabel("Admin Portal", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(220, 53, 69));
        gbc.insets = new Insets(0, 0, 30, 0); // More space under title
        add(title, gbc);

        // --- 2. Username Label ---
        gbc.gridy++;
        // REMOVED SwingConstants.CENTER to align left
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userLabel.setForeground(Color.DARK_GRAY);
        gbc.insets = new Insets(10, 0, 5, 0); // Closer to the text box
        add(userLabel, gbc);

        // --- 3. Username Field ---
        gbc.gridy++;
        JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(280, 40));
        gbc.insets = new Insets(0, 0, 15, 0); // Space after field
        add(userField, gbc);

        // --- 4. Password Label ---
        gbc.gridy++;
        // REMOVED SwingConstants.CENTER to align left
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        passLabel.setForeground(Color.DARK_GRAY);
        gbc.insets = new Insets(0, 0, 5, 0); // Closer to the text box
        add(passLabel, gbc);

        // --- 5. Password Field ---
        gbc.gridy++;
        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(280, 40));
        gbc.insets = new Insets(0, 0, 30, 0); // Space after field
        add(passField, gbc);

        // --- 6. Login Button ---
        gbc.gridy++;
        JButton loginBtn = new JButton("Sign In");
        styleButton(loginBtn);
        gbc.insets = new Insets(0, 0, 15, 0);
        add(loginBtn, gbc);

        // --- 7. Back Button ---
        gbc.gridy++;
        JButton backBtn = new JButton("Back to Roles");
        styleLinkButton(backBtn);
        gbc.insets = new Insets(0, 0, 0, 0);
        add(backBtn, gbc);

        // --- LOGIC ---
        loginBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            if(u.equals("admin") && p.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Admin Login Successful!");
                userField.setText("");
                passField.setText("");
                mainFrame.showCard("ADMIN_DASHBOARD");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> frame.showCard("ROLE_SELECTION"));
    }

    private void styleButton(JButton b) {
        b.setBackground(new Color(220, 53, 69));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(280, 45)); // Match text field width
    }

    private void styleLinkButton(JButton b) {
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setForeground(Color.GRAY);
        b.setFont(new Font("SansSerif", Font.PLAIN, 12));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}