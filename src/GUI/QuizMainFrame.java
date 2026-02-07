package GUI;

import DatabaseConfig.CompetitorList;
import QUIZ.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QuizMainFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // --- Shared Data ---
    private CompetitorList competitorList = new CompetitorList();
    private QuestionBank questionBank = new QuestionBank();
    private RONCompetitor currentCompetitor;
    private ArrayList<Question> currentQuestions;

    // --- Panel References ---
    private QuizPanel quizPanel;
    private ScorePanel scorePanel;
    private LeaderboardPanel leaderboardPanel;
    private ReportPanel reportPanel;
    private WelcomePanel welcomePanel;

    public QuizMainFrame() {
        setTitle("Quiz Competition Platform");
        setSize(500, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize Shared Panels
        quizPanel = new QuizPanel(this);
        scorePanel = new ScorePanel(this);
        leaderboardPanel = new LeaderboardPanel(this);
        reportPanel = new ReportPanel(this);
        welcomePanel = new WelcomePanel(this);

        // --- REGISTER ALL SCREENS ---
        mainPanel.add(new RoleSelectionPanel(this), "ROLE_SELECTION");

        // ADMIN SCREENS
        mainPanel.add(new AdminLoginPanel(this), "ADMIN_LOGIN");
        mainPanel.add(new AdminDashboardPanel(this), "ADMIN_DASHBOARD");
        mainPanel.add(new AddQuestionPanel(this), "ADD_QUESTION");

        // USER SCREENS
        mainPanel.add(new LoginPanel(this), "LOGIN");
        mainPanel.add(new SignupPanel(this), "SIGNUP");
        mainPanel.add(welcomePanel, "WELCOME");
        mainPanel.add(new LevelPanel(this), "LEVEL");
        mainPanel.add(quizPanel, "QUIZ");
        mainPanel.add(reportPanel, "REPORT");
        mainPanel.add(scorePanel, "MY_SCORES");
        mainPanel.add(leaderboardPanel, "LEADERBOARD");

        add(mainPanel);

        // Start at Role Selection
        cardLayout.show(mainPanel, "ROLE_SELECTION");
    }

    public void showCard(String cardName) {
        // Refresh data before showing screens
        if (cardName.equals("MY_SCORES")) scorePanel.refresh();
        if (cardName.equals("LEADERBOARD")) leaderboardPanel.refresh();
        if (cardName.equals("REPORT")) reportPanel.refresh();
        if (cardName.equals("WELCOME")) welcomePanel.updateWelcomeText();

        cardLayout.show(mainPanel, cardName);
    }

    // --- Getters & Setters ---
    public CompetitorList getCompetitorList() { return competitorList; }
    public QuestionBank getQuestionBank() { return questionBank; }
    public RONCompetitor getCurrentUser() { return currentCompetitor; }
    public void setCurrentUser(RONCompetitor user) { this.currentCompetitor = user; }

    // --- Quiz Logic ---
    public void startQuiz(String level) {
        if (currentCompetitor != null) currentCompetitor.setCompetitorLevel(level);

        // This gets 5 random questions from the DB for the selected level
        this.currentQuestions = questionBank.getQuestionsForLevel(level);

        if (currentQuestions.size() < 5) {
            JOptionPane.showMessageDialog(this, "Not enough questions in DB for " + level);
        } else {
            quizPanel.setupQuiz(currentQuestions);
            showCard("QUIZ");
        }
    }

    // --- NEW: Save Attempt Logic (Replaces old saveQuizScore) ---
    public void saveQuizAttempt(int totalScore) {
        if (currentCompetitor != null) {
            // Add this score (e.g., 4) to the next empty slot (Attempt 1, Attempt 2...)
            // Ensure your RONCompetitor class uses -1 logic for empty slots!
            boolean added = currentCompetitor.addQuizAttemptScore(totalScore);

            if (added) {
                try {
                    competitorList.saveCompetitor(currentCompetitor);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error saving score to database.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "You have reached the maximum of 5 attempts!");
            }
        }
        // Go to Report screen to see results
        showCard("REPORT");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizMainFrame().setVisible(true));
    }
}