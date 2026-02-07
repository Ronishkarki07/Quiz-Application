package GUI;

import DatabaseConfig.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddQuestionPanel extends JPanel {

    // UI Components defined as class variables so they can be accessed by methods
    private JTextField qText, opA, opB, opC, opD;
    private JComboBox<String> correctBox, levelBox;

    public AddQuestionPanel(QuizMainFrame frame) {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(30, 50, 30, 50));
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- Title ---
        JLabel title = new JLabel("Add New Question", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.insets = new Insets(0, 0, 20, 0);
        add(title, gbc);

        // --- Initialize Fields ---
        qText = new JTextField();
        opA = new JTextField();
        opB = new JTextField();
        opC = new JTextField();
        opD = new JTextField();

        String[] ansOptions = {"0", "1", "2", "3"};
        correctBox = new JComboBox<>(ansOptions);

        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        levelBox = new JComboBox<>(levels);

        // --- Add Form Rows ---
        addFormRow("Question Text:", qText, gbc);
        addFormRow("Option 1:", opA, gbc);
        addFormRow("Option 2:", opB, gbc);
        addFormRow("Option 3:", opC, gbc);
        addFormRow("Option 4:", opD, gbc);

        addFormRow("Correct Option Index (0-3):", correctBox, gbc);
        addFormRow("Difficulty Level:", levelBox, gbc);

        // --- Buttons Panel ---
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveBtn = new JButton("Save Question");
        styleButton(saveBtn, new Color(40, 167, 69)); // Green

        JButton backBtn = new JButton("Back to Dashboard");
        styleButton(backBtn, Color.GRAY);

        buttonPanel.add(saveBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, gbc);

        // --- ACTIONS ---
        // Call the separate save method
        saveBtn.addActionListener(e -> saveQuestion());

        backBtn.addActionListener(e -> frame.showCard("ADMIN_DASHBOARD"));
    }

    private void saveQuestion() {
        // 1. Get Values & Remove extra spaces
        String question = qText.getText().trim();
        String a = opA.getText().trim();
        String b = opB.getText().trim();
        String c = opC.getText().trim();
        String d = opD.getText().trim();
        String correct = (String) correctBox.getSelectedItem();
        String level = (String) levelBox.getSelectedItem();

        // 2. --- VALIDATION CHECK ---
        // If ANY field is empty, show error and RETURN (stop the function)
        if (question.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Validation Failed!\n\nPlease fill in the Question and ALL 4 Options.",
                    "Missing Fields",
                    JOptionPane.ERROR_MESSAGE);
            return; // <--- THIS STOPS THE SAVE PROCESS
        }

        // 3. Database Insertion (Only runs if validation passes)
        String sql = "INSERT INTO Questions (question_text, option_a, option_b, option_c, option_d, correct_ans, difficulty) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, question);
            pstmt.setString(2, a);
            pstmt.setString(3, b);
            pstmt.setString(4, c);
            pstmt.setString(5, d);
            pstmt.setString(6, correct);
            pstmt.setString(7, level);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Question Added Successfully!");
                clearFields(); // Reset form
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        qText.setText("");
        opA.setText("");
        opB.setText("");
        opC.setText("");
        opD.setText("");
        correctBox.setSelectedIndex(0);
        levelBox.setSelectedIndex(0);
    }

    // --- Helpers ---
    private void addFormRow(String labelText, JComponent field, GridBagConstraints gbc) {
        gbc.gridy++;
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.insets = new Insets(10, 0, 2, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(l, gbc);

        gbc.gridy++;
        field.setPreferredSize(new Dimension(300, 35));
        gbc.insets = new Insets(0, 0, 5, 0);
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