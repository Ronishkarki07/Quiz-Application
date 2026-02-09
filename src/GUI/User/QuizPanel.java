package GUI.User;

import GUI.QuizMainFrame;
import QUIZ.Question;
import QUIZ.RONCompetitor;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class QuizPanel extends JPanel {
    private QuizMainFrame mainFrame;
    private ArrayList<Question> questions;
    private int currentIndex = 0;
    private String currentLevel; // Store current difficulty level

    private int[] localResults = new int[5];

    private JTextArea qTextArea;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JLabel progressLabel;

    public QuizPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); // Very light grey background

        // --- 1. HEADER PANEL (Compact & Blue) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(65, 105, 225)); // Royal Blue
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        // Fixed height to prevent excessive stretching
        headerPanel.setPreferredSize(new Dimension(500, 120));

        // Progress Label
        progressLabel = new JLabel("Question 1 of 5");
        progressLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        progressLabel.setForeground(new Color(220, 220, 255)); // Light Blue
        headerPanel.add(progressLabel, BorderLayout.NORTH);

        // Question Text
        qTextArea = new JTextArea("Question Text Here");
        qTextArea.setFont(new Font("SansSerif", Font.BOLD, 18));
        qTextArea.setForeground(Color.WHITE);
        qTextArea.setBackground(new Color(65, 105, 225));
        qTextArea.setLineWrap(true);
        qTextArea.setWrapStyleWord(true);
        qTextArea.setEditable(false);
        qTextArea.setBorder(new EmptyBorder(10, 0, 0, 0)); // Spacing from progress label
        headerPanel.add(qTextArea, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // --- 2. OPTIONS PANEL (GridBagLayout for Compact Buttons) ---
        JPanel bodyPanel = new JPanel(new GridBagLayout()); // GridBag prevents stretching
        bodyPanel.setBackground(new Color(245, 247, 250));
        bodyPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill width only
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 10, 0); // Gap between buttons

        options = new JRadioButton[4];
        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            styleOptionCard(options[i]);

            // Add to panel with constraints
            bodyPanel.add(options[i], gbc);
            group.add(options[i]);
            gbc.gridy++; // Move to next row
        }

        // Add a filler component to push buttons to the top
        gbc.weighty = 1.0;
        bodyPanel.add(Box.createGlue(), gbc);

        add(bodyPanel, BorderLayout.CENTER);

        // --- 3. FOOTER PANEL ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(15, 20, 15, 30));

        JButton nextBtn = new JButton("Next Question");
        styleNextButton(nextBtn);

        nextBtn.addActionListener(e -> processAnswer());
        footerPanel.add(nextBtn);

        add(footerPanel, BorderLayout.SOUTH);
    }

    public void setupQuiz(ArrayList<Question> questions, String level) {
        this.questions = questions;
        this.currentLevel = level;
        this.currentIndex = 0;
        this.localResults = new int[5];
        showQuestion();
    }

    private void showQuestion() {
        if (currentIndex < questions.size()) {
            Question q = questions.get(currentIndex);
            qTextArea.setText(q.getQuestionText());
            progressLabel.setText("Question " + (currentIndex + 1) + " of 5");

            String[] opts = q.getOptions();
            for (int i = 0; i < 4; i++) {
                options[i].setText(opts[i]);
                options[i].setSelected(false);
                resetOptionStyle(options[i]);
            }
            group.clearSelection();
        }
    }

    private void processAnswer() {
        int selected = -1;
        for (int i = 0; i < 4; i++) if (options[i].isSelected()) selected = i;

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Please select an answer.");
            return;
        }

        boolean correct = questions.get(currentIndex).isCorrect(selected);
        
        // Weighted scoring based on difficulty level
        int pointValue = getPointValueForLevel(currentLevel);
        localResults[currentIndex] = correct ? pointValue : 0;

        if (++currentIndex < 5) {
            showQuestion();
        } else {
            finishQuizAndShowSummary();
        }
    }

    private void finishQuizAndShowSummary() {
        int totalScore = 0;
        for(int s : localResults) totalScore += s;

        RONCompetitor user = mainFrame.getCurrentUser();
        if(user != null) {
            int maxPossibleScore = getMaxScoreForLevel(currentLevel);
            
            String summaryText =
                    "Quiz Finished!\n\n" +
                            "Competitor: " + user.getCompetitorName().getFullName() + "\n" +
                            "Level: " + currentLevel + "\n" +
                            "Score: " + totalScore + " / " + maxPossibleScore + "\n\n" +
                            "Result saved.";

            JOptionPane.showMessageDialog(mainFrame, summaryText, "Result", JOptionPane.INFORMATION_MESSAGE);

            // --- FIX: Using the correct method name from QuizMainFrame ---
            mainFrame.saveQuizAttempt(totalScore);
        }
    }

    // --- STYLING HELPERS ---

    private void styleOptionCard(JRadioButton rb) {
        rb.setFont(new Font("SansSerif", Font.PLAIN, 15));
        rb.setForeground(Color.DARK_GRAY);
        rb.setBackground(Color.WHITE);
        rb.setFocusPainted(false);
        rb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Compact padding (Smaller height)
        rb.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(12, 15, 12, 15) // Reduced vertical padding
        ));
        rb.setBorderPainted(true);

        // Hover
        rb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!rb.isSelected()) rb.setBackground(new Color(245, 250, 255));
            }
            public void mouseExited(MouseEvent e) {
                if (!rb.isSelected()) rb.setBackground(Color.WHITE);
            }
        });

        // Selection Logic
        rb.addActionListener(e -> {
            for(JRadioButton other : options) resetOptionStyle(other);
            rb.setBackground(new Color(230, 240, 255)); // Light Blue fill
            rb.setForeground(new Color(30, 60, 150)); // Dark Blue Text
            rb.setBorder(new CompoundBorder(
                    new LineBorder(new Color(65, 105, 225), 1, true), // Blue Border
                    new EmptyBorder(12, 15, 12, 15)
            ));
        });
    }

    private void resetOptionStyle(JRadioButton rb) {
        rb.setBackground(Color.WHITE);
        rb.setForeground(Color.DARK_GRAY);
        rb.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(12, 15, 12, 15)
        ));
    }

    private void styleNextButton(JButton b) {
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(40, 167, 69)); // Green
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 25, 10, 25));
        b.setOpaque(true);
        b.setBorderPainted(false);
    }

    /**
     * Returns point value per correct answer based on difficulty level
     */
    private int getPointValueForLevel(String level) {
        switch (level.toLowerCase()) {
            case "beginner": return 1;
            case "intermediate": return 2;
            case "advanced": return 3;
            default: return 1; // Default to beginner scoring
        }
    }

    /**
     * Returns maximum possible score for the difficulty level
     */
    private int getMaxScoreForLevel(String level) {
        return getPointValueForLevel(level) * 5; // 5 questions
    }
}