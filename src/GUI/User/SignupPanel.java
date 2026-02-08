package GUI.User;

import GUI.QuizMainFrame;
import QUIZ.Name;
import QUIZ.RONCompetitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SignupPanel extends JPanel {
    private QuizMainFrame mainFrame;

    public SignupPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout()); // Centers everything
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 50, 20, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0); // Vertical spacing
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make fields stretch
        gbc.weightx = 1.0;

        // --- 1. Title ---
        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(Color.BLACK);
        gbc.insets = new Insets(0, 0, 25, 0); // Space under title
        add(title, gbc);

        // --- 2. Form Fields ---
        JTextField idField = new JTextField();
        JTextField fNameField = new JTextField();
        JTextField lNameField = new JTextField();
        JTextField countryField = new JTextField();
        JTextField ageField = new JTextField();
        JPasswordField passField = new JPasswordField();

        // Helper to add label + field pairs
        addLabelAndField("ID", idField, gbc);
        addLabelAndField("First Name", fNameField, gbc);
        addLabelAndField("Last Name", lNameField, gbc);
        addLabelAndField("Country", countryField, gbc);
        addLabelAndField("Age", ageField, gbc);
        addLabelAndField("Password", passField, gbc);

        // --- 3. Buttons (Centered) ---
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 10, 0); // Space above buttons
        gbc.fill = GridBagConstraints.NONE; // Don't stretch buttons
        gbc.anchor = GridBagConstraints.CENTER; // Center them

        JButton createBtn = new JButton("Create Account");
        styleButton(createBtn);

        // Add Button
        add(createBtn, gbc);

        // --- 4. Back Link (Centered) ---
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 0, 0);
        JButton backBtn = new JButton("Back to Login");
        styleLinkButton(backBtn);
        backBtn.setForeground(new Color(65, 105, 225)); // Blue Link

        add(backBtn, gbc);

        // --- LOGIC ---
        createBtn.addActionListener(e -> {
            try {
                String id = idField.getText();
                String fName = fNameField.getText();
                String lName = lNameField.getText();
                String country = countryField.getText();
                int age = Integer.parseInt(ageField.getText());
                String pass = new String(passField.getPassword());

                Name name = new Name(fName, lName);
                RONCompetitor newComp = new RONCompetitor(id, name, "Beginner", country, age, pass);

                // Initialize with -1 for "Empty" slots
                newComp.setScores(new int[]{0, 0, 0, 0, 0});

                mainFrame.getCompetitorList().saveCompetitor(newComp);
                JOptionPane.showMessageDialog(this, "Account Created! Please Login.");
                mainFrame.showCard("LOGIN");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> mainFrame.showCard("LOGIN"));
    }

    // Helper to add rows cleanly
    private void addLabelAndField(String labelText, JTextField field, GridBagConstraints gbc) {
        gbc.gridy++;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.insets = new Insets(5, 0, 2, 0); // Tight spacing between label and field
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(label, gbc);

        gbc.gridy++;
        field.setPreferredSize(new Dimension(280, 35));
        gbc.insets = new Insets(0, 0, 10, 0); // Space after field
        add(field, gbc);
    }

    private void styleButton(JButton b) {
        b.setBackground(new Color(40, 167, 69)); // Green (Matching Login)
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(200, 45));
    }

    private void styleLinkButton(JButton b) {
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFont(new Font("SansSerif", Font.PLAIN, 12));
    }
}