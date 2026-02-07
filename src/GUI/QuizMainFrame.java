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
        mainPanel.add(new AdminDashboardPanel(this), "ADMIN_DASHBOARD"); // NEW
        mainPanel.add(new AddQuestionPanel(this), "ADD_QUESTION");       // NEW

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
        cardLayout.show(mainPanel, "ROLE_SELECTION");
    }

    public void showCard(String cardName) {
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
        this.currentQuestions = questionBank.getQuestionsForLevel(level);

        if (currentQuestions.size() < 5) {
            JOptionPane.showMessageDialog(this, "Not enough questions for " + level);
        } else {
            quizPanel.setupQuiz(currentQuestions);
            showCard("QUIZ");
        }
    }

    public void saveQuizScore(int[] scores) {
        if (currentCompetitor != null) {
            currentCompetitor.setScores(scores);
            try { competitorList.saveCompetitor(currentCompetitor); } catch (Exception e) { e.printStackTrace(); }
        }
        showCard("REPORT");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizMainFrame().setVisible(true));
    }
}