package GUI;

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

    private int[] localResults = new int[5];

    private JTextArea qTextArea;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JLabel progressLabel;

    public QuizPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); // Very light grey background

        // --- 1. HEADER PANEL (Question) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(65, 105, 225)); // Royal Blue
        headerPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        headerPanel.setPreferredSize(new Dimension(500, 140));

        // Progress Label (e.g., "Question 1 of 5")
        progressLabel = new JLabel("Question 1 of 5");
        progressLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        progressLabel.setForeground(new Color(200, 200, 255)); // Light Blue
        headerPanel.add(progressLabel, BorderLayout.NORTH);

        // Question Text
        qTextArea = new JTextArea("Question Text Goes Here");
        qTextArea.setFont(new Font("SansSerif", Font.BOLD, 20));
        qTextArea.setForeground(Color.WHITE);
        qTextArea.setBackground(new Color(65, 105, 225)); // Match header bg
        qTextArea.setLineWrap(true);
        qTextArea.setWrapStyleWord(true);
        qTextArea.setEditable(false);
        headerPanel.add(qTextArea, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // --- 2. OPTIONS PANEL (Center) ---
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(4, 1, 0, 15)); // 4 rows, spacing 15
        bodyPanel.setBackground(new Color(245, 247, 250));
        bodyPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        options = new JRadioButton[4];
        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            styleOptionCard(options[i]); // Apply custom card style
            bodyPanel.add(options[i]);
            group.add(options[i]);
        }
        add(bodyPanel, BorderLayout.CENTER);

        // --- 3. FOOTER PANEL (Next Button) ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(15, 20, 15, 40));
        footerPanel.setPreferredSize(new Dimension(500, 80));

        JButton nextBtn = new JButton("Next Question");
        styleNextButton(nextBtn);

        nextBtn.addActionListener(e -> processAnswer());
        footerPanel.add(nextBtn);

        add(footerPanel, BorderLayout.SOUTH);
    }

    public void setupQuiz(ArrayList<Question> questions) {
        this.questions = questions;
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
                // Reset colors
                options[i].setBackground(Color.WHITE);
                options[i].setForeground(Color.DARK_GRAY);
            }
            group.clearSelection();
        }
    }

    private void processAnswer() {
        int selected = -1;
        for (int i = 0; i < 4; i++) if (options[i].isSelected()) selected = i;

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Please select an answer to proceed.");
            return;
        }

        boolean correct = questions.get(currentIndex).isCorrect(selected);
        localResults[currentIndex] = correct ? 1 : 0;

        if (++currentIndex < 5) {
            showQuestion();
        } else {
            finishQuizAndShowSummary();
        }
    }

    private void finishQuizAndShowSummary() {
        int totalCorrect = 0;
        for(int s : localResults) totalCorrect += s;

        RONCompetitor user = mainFrame.getCurrentUser();

        if(user != null) {
            String summaryText =
                    "Quiz Finished!\n\n" +
                            "Competitor: " + user.getCompetitorName().getFullName() + "\n" +
                            "Result: " + totalCorrect + " out of 5 correct.\n\n" +
                            "This score has been saved to your history.";

            JOptionPane.showMessageDialog(mainFrame, summaryText, "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.saveQuizAttempt(totalCorrect);
        }
    }

    // --- STYLING HELPERS ---

    private void styleOptionCard(JRadioButton rb) {
        rb.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rb.setForeground(Color.DARK_GRAY);
        rb.setBackground(Color.WHITE);
        rb.setFocusPainted(false);
        rb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Create a padding border with a line border
        rb.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true), // Grey border
                new EmptyBorder(10, 15, 10, 15) // Inner padding
        ));
        rb.setBorderPainted(true);

        // Add Hover Effect
        rb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!rb.isSelected()) rb.setBackground(new Color(240, 248, 255)); // Alice Blue
            }
            public void mouseExited(MouseEvent e) {
                if (!rb.isSelected()) rb.setBackground(Color.WHITE);
            }
        });

        // Add Selection Effect
        rb.addActionListener(e -> {
            // Reset all others
            for(JRadioButton other : options) {
                other.setBackground(Color.WHITE);
                other.setForeground(Color.DARK_GRAY);
            }
            // Highlight selected
            rb.setBackground(new Color(230, 240, 255));
            rb.setForeground(new Color(65, 105, 225));
        });
    }

    private void styleNextButton(JButton b) {
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(40, 167, 69)); // Green
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 25, 10, 25));
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}