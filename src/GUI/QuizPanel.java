package GUI;

import QUIZ.Question;
import QUIZ.RONCompetitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class QuizPanel extends JPanel {
    private QuizMainFrame mainFrame;
    private ArrayList<Question> questions;
    private int currentIndex = 0;
    private int[] scores = new int[5];

    private JTextArea qTextArea;
    private JRadioButton[] options;
    private ButtonGroup group;

    public QuizPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- 1. HEADER PANEL ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(230, 230, 230)); // Light Grey
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        headerPanel.setPreferredSize(new Dimension(500, 100));

        qTextArea = new JTextArea("Question Text Goes Here");
        qTextArea.setFont(new Font("SansSerif", Font.BOLD, 16));
        qTextArea.setForeground(new Color(0, 0, 139)); // Dark Blue
        qTextArea.setBackground(new Color(230, 230, 230));
        qTextArea.setLineWrap(true);
        qTextArea.setWrapStyleWord(true);
        qTextArea.setEditable(false);

        headerPanel.add(qTextArea, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // --- 2. OPTIONS PANEL ---
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(Color.WHITE);
        bodyPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        options = new JRadioButton[4];
        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton("Option " + (i+1));
            options[i].setFont(new Font("SansSerif", Font.PLAIN, 16));
            options[i].setForeground(Color.BLACK);
            options[i].setBackground(Color.WHITE);
            options[i].setFocusPainted(false);

            bodyPanel.add(options[i]);
            bodyPanel.add(Box.createVerticalStrut(15));
            group.add(options[i]);
        }
        add(bodyPanel, BorderLayout.CENTER);

        // --- 3. FOOTER PANEL ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        footerPanel.setPreferredSize(new Dimension(500, 60));

        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        nextBtn.setForeground(Color.BLUE);
        nextBtn.setBackground(Color.WHITE);
        nextBtn.setFocusPainted(false);
        nextBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(5, 20, 5, 20)
        ));

        nextBtn.addActionListener(e -> processAnswer());
        footerPanel.add(nextBtn);

        add(footerPanel, BorderLayout.SOUTH);
    }

    public void setupQuiz(ArrayList<Question> questions) {
        this.questions = questions;
        this.currentIndex = 0;
        this.scores = new int[5];
        showQuestion();
    }

    private void showQuestion() {
        if (currentIndex < questions.size()) {
            Question q = questions.get(currentIndex);
            qTextArea.setText("Q" + (currentIndex+1) + ": " + q.getQuestionText());

            String[] opts = q.getOptions();
            for (int i = 0; i < 4; i++) {
                options[i].setText(opts[i]);
                options[i].setVisible(true);
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
        scores[currentIndex] = correct ? 5 : 0; // 5 points for correct, 0 for wrong

        // Check if it's the last question
        if (++currentIndex < 5) {
            showQuestion();
        } else {
            // --- QUIZ FINISHED: SHOW SUMMARY ---
            finishQuizAndShowSummary();
        }
    }

    private void finishQuizAndShowSummary() {
        // 1. Save data first (Update the user object)
        RONCompetitor user = mainFrame.getCurrentUser();
        if(user != null) {
            user.setScores(scores);
            // Calculate overall score locally for display
            double overall = user.getOverallScore();

            // 2. Build the Summary String matching your image
            String scoresString = Arrays.toString(scores).replace("[", "").replace("]", "");

            String summaryText =
                    "CompetitorID " + user.getCompetitorID() + ", name " + user.getCompetitorName().getFullName() + ".\n" +
                            user.getCompetitorName().getFirstName() + " is a " + user.getCompetitorLevel() + " and received these scores: " + scoresString + ".\n" +
                            "This gives an overall score of " + String.format("%.1f", overall) + ".";

            // 3. Show Popup
            JOptionPane.showMessageDialog(mainFrame, summaryText, "Quiz Result", JOptionPane.INFORMATION_MESSAGE);

            // 4. Save to DB and go to Report Screen
            mainFrame.saveQuizScore(scores);
        }
    }
}