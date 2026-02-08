package GUI.User;

import GUI.QuizMainFrame;
import QUIZ.RONCompetitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel extends JPanel {
    private QuizMainFrame mainFrame;

    public LoginPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ensures labels align with fields

        // --- 1. Title ---
        JLabel title = new JLabel("User Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(new Color(40, 167, 69)); // Green Title
        gbc.insets = new Insets(0, 0, 30, 0); // Space under title
        add(title, gbc);

        // --- 2. ID Label ---
        gbc.gridy++;
        JLabel idLabel = new JLabel("Competitor ID"); // Left Aligned
        idLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        idLabel.setForeground(Color.DARK_GRAY);
        gbc.insets = new Insets(10, 0, 5, 0); // Closer to text box
        add(idLabel, gbc);

        // --- 3. ID Field ---
        gbc.gridy++;
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(280, 40));
        gbc.insets = new Insets(0, 0, 15, 0);
        add(idField, gbc);

        // --- 4. Password Label ---
        gbc.gridy++;
        JLabel passLabel = new JLabel("Password"); // Left Aligned
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        passLabel.setForeground(Color.DARK_GRAY);
        gbc.insets = new Insets(0, 0, 5, 0);
        add(passLabel, gbc);

        // --- 5. Password Field ---
        gbc.gridy++;
        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(280, 40));
        gbc.insets = new Insets(0, 0, 30, 0);
        add(passField, gbc);

        // --- 6. Sign In Button (GREEN) ---
        gbc.gridy++;
        JButton loginBtn = new JButton("Sign In");
        styleButton(loginBtn); // Applies Green Style
        gbc.insets = new Insets(0, 0, 15, 0);
        add(loginBtn, gbc);

        // --- 7. Sign Up Link ---
        gbc.gridy++;
        JButton signupLink = new JButton("Do you have an account? Sign up");
        styleLinkButton(signupLink);
        signupLink.setForeground(new Color(65, 105, 225)); // Blue link
        gbc.insets = new Insets(0, 0, 5, 0);
        add(signupLink, gbc);

        // --- 8. Back Button ---
        gbc.gridy++;
        JButton backBtn = new JButton("Back to Roles");
        styleLinkButton(backBtn);
        gbc.insets = new Insets(0, 0, 0, 0);
        add(backBtn, gbc);

        // --- LOGIC ---
        loginBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String pass = new String(passField.getPassword());

            String status = mainFrame.getCompetitorList().checkLogin(id, pass);

            if (status.equals("SUCCESS")) {
                RONCompetitor user = mainFrame.getCompetitorList().findCompetitor(id);
                mainFrame.setCurrentUser(user);
                idField.setText("");
                passField.setText("");
                mainFrame.showCard("WELCOME");
            } else {
                JOptionPane.showMessageDialog(this, "Login Failed: " + status);
            }
        });

        signupLink.addActionListener(e -> mainFrame.showCard("SIGNUP"));
        backBtn.addActionListener(e -> mainFrame.showCard("ROLE_SELECTION"));
    }

    private void styleButton(JButton b) {
        b.setBackground(new Color(40, 167, 69)); // GREEN BACKGROUND
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(280, 45)); // Match field width
    }

    private void styleLinkButton(JButton b) {
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFont(new Font("SansSerif", Font.PLAIN, 12));
        b.setForeground(Color.GRAY);
    }
}