package GUI;

import DatabaseConfig.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddQuestionPanel extends JPanel {
    public AddQuestionPanel(QuizMainFrame frame) {
        setLayout(new GridBagLayout()); // Use GridBagLayout for better control
        setBorder(new EmptyBorder(30, 50, 30, 50));
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0); // Spacing between rows
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make fields stretch
        gbc.weightx = 1.0;

        // --- Title ---
        JLabel title = new JLabel("Add New Question", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.insets = new Insets(0, 0, 20, 0); // Extra space below title
        add(title, gbc);

        // --- Form Fields ---
        JTextField qText = new JTextField();
        JTextField opA = new JTextField();
        JTextField opB = new JTextField();
        JTextField opC = new JTextField();
        JTextField opD = new JTextField();

        String[] ansOptions = {"0", "1", "2", "3"};
        JComboBox<String> correctBox = new JComboBox<>(ansOptions);

        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        JComboBox<String> levelBox = new JComboBox<>(levels);

        // Add Labels and Fields using Helper
        addFormRow("Question Text:", qText, gbc);
        addFormRow("Option 1:", opA, gbc);
        addFormRow("Option 2:", opB, gbc);
        addFormRow("Option 3:", opC, gbc);
        addFormRow("Option 4:", opD, gbc);

        // Dropdowns
        addFormRow("Correct Option Index (0-3):", correctBox, gbc);
        addFormRow("Difficulty Level:", levelBox, gbc);

        // --- Buttons Panel (Centered) ---
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 0, 0); // Space above buttons
        gbc.fill = GridBagConstraints.NONE; // Don't stretch buttons
        gbc.anchor = GridBagConstraints.CENTER; // Center them

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10)); // Stack buttons vertically
        buttonPanel.setBackground(Color.WHITE);

        JButton saveBtn = new JButton("Save Question");
        styleButton(saveBtn, new Color(40, 167, 69)); // Green

        JButton backBtn = new JButton("Back to Dashboard");
        styleButton(backBtn, Color.GRAY);

        buttonPanel.add(saveBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, gbc);

        // --- Logic ---
        saveBtn.addActionListener(e -> {
            try {
                String sql = "INSERT INTO Questions (question_text, option_a, option_b, option_c, option_d, correct_ans, difficulty) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, qText.getText());
                    pstmt.setString(2, opA.getText());
                    pstmt.setString(3, opB.getText());
                    pstmt.setString(4, opC.getText());
                    pstmt.setString(5, opD.getText());
                    pstmt.setString(6, (String) correctBox.getSelectedItem());
                    pstmt.setString(7, (String) levelBox.getSelectedItem());

                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Question Added Successfully!");

                    // Clear fields
                    qText.setText(""); opA.setText(""); opB.setText(""); opC.setText(""); opD.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> frame.showCard("ADMIN_DASHBOARD"));
    }

    // Helper to add a label and component
    private void addFormRow(String labelText, JComponent field, GridBagConstraints gbc) {
        gbc.gridy++;
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.insets = new Insets(10, 0, 2, 0); // Label spacing
        add(l, gbc);

        gbc.gridy++;
        field.setPreferredSize(new Dimension(300, 35));
        gbc.insets = new Insets(0, 0, 5, 0); // Field spacing
        add(field, gbc);
    }

    private void styleButton(JButton b, Color bg) {
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(200, 40));
    }
}