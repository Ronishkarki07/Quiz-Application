package GUI;

import DatabaseConfig.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddQuestionPanel extends JPanel {
    public AddQuestionPanel(QuizMainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(30, 40, 30, 40));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Add New Question");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setAlignmentX(LEFT_ALIGNMENT);
        add(title);
        add(Box.createVerticalStrut(20));

        // Form Fields
        JTextField qText = new JTextField();
        JTextField opA = new JTextField();
        JTextField opB = new JTextField();
        JTextField opC = new JTextField();
        JTextField opD = new JTextField();

        String[] ansOptions = {"0", "1", "2", "3"}; // Index of correct answer
        JComboBox<String> correctBox = new JComboBox<>(ansOptions);

        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        JComboBox<String> levelBox = new JComboBox<>(levels);

        // Add to panel
        addLabelAndField("Question Text:", qText);
        addLabelAndField("Option 1:", opA);
        addLabelAndField("Option 2:", opB);
        addLabelAndField("Option 3:", opC);
        addLabelAndField("Option 4:", opD);

        add(new JLabel("Correct Option Index (0-3):"));
        correctBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        correctBox.setAlignmentX(LEFT_ALIGNMENT);
        add(correctBox);

        add(new JLabel("Difficulty Level:"));
        levelBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        levelBox.setAlignmentX(LEFT_ALIGNMENT);
        add(levelBox);

        add(Box.createVerticalStrut(20));

        // Buttons
        JButton saveBtn = new JButton("Save Question");
        saveBtn.setBackground(new Color(40, 167, 69));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setOpaque(true);
        saveBtn.setBorderPainted(false);

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
                    // Convert combo box index to string "0", "1" etc.
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

        JButton backBtn = new JButton("Back to Dashboard");
        backBtn.addActionListener(e -> frame.showCard("ADMIN_DASHBOARD"));

        add(saveBtn);
        add(Box.createVerticalStrut(10));
        add(backBtn);
    }

    private void addLabelAndField(String label, JTextField field) {
        JLabel l = new JLabel(label);
        l.setAlignmentX(LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        field.setAlignmentX(LEFT_ALIGNMENT);
        add(l);
        add(field);
        add(Box.createVerticalStrut(5));
    }
}