package GUI;

import QUIZ.RONCompetitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Arrays;

public class ScorePanel extends JPanel {
    private QuizMainFrame mainFrame;
    private JLabel nameL, levelL, scoreL;
    private JPanel scoresPanel;

    public ScorePanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 245, 255));

        JPanel mainCard = new JPanel(new BorderLayout());
        mainCard.setBackground(Color.WHITE);
        mainCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 220, 255), 2, true),
                new EmptyBorder(30, 40, 30, 40)));
        mainCard.setPreferredSize(new Dimension(450, 500));

        // Header Section
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = createStyledLabel("Performance Summary", 18, new Color(100, 100, 100));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(20));
        
        nameL = createStyledLabel("Player Name", 26, new Color(50, 50, 50));
        levelL = createStyledLabel("Level", 16, new Color(120, 120, 120));
        headerPanel.add(nameL);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(levelL);
        
        mainCard.add(headerPanel, BorderLayout.NORTH);

        // Center Score Section
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        scoreL = createStyledLabel("0.0", 72, new Color(65, 105, 225));
        JLabel overallLabel = createStyledLabel("Overall Score", 14, new Color(100, 100, 100));
        
        centerPanel.add(scoreL);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(overallLabel);
        
        mainCard.add(centerPanel, BorderLayout.CENTER);

        // Scores Section
        JPanel scoresSection = new JPanel();
        scoresSection.setLayout(new BoxLayout(scoresSection, BoxLayout.Y_AXIS));
        scoresSection.setBackground(Color.WHITE);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(230, 230, 230));
        scoresSection.add(separator);
        scoresSection.add(Box.createVerticalStrut(20));
        
        JLabel scoresTitle = createStyledLabel("Quiz Attempts", 16, new Color(100, 100, 100));
        scoresTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoresSection.add(scoresTitle);
        scoresSection.add(Box.createVerticalStrut(15));
        
        scoresPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        scoresPanel.setBackground(Color.WHITE);
        scoresSection.add(scoresPanel);
        
        mainCard.add(scoresSection, BorderLayout.SOUTH);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 10, 20);
        add(mainCard, gbc);

        // Back Button
        JButton backBtn = new JButton("Back");
        styleBackButton(backBtn);
        backBtn.addActionListener(e -> frame.showCard("WELCOME"));
        
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(backBtn, gbc);
    }

    private JLabel createStyledLabel(String text, int fontSize, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    
    private JPanel createScoreCard(int score, int attemptNum) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(55, 70));
        card.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        
        if (score > 0) {
            card.setBackground(new Color(240, 255, 240));
        } else {
            card.setBackground(new Color(250, 250, 250));
        }
        
        JLabel attemptLabel = new JLabel("Q" + attemptNum, SwingConstants.CENTER);
        attemptLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        attemptLabel.setForeground(new Color(100, 100, 100));
        attemptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel scoreLabel = new JLabel(score > 0 ? String.valueOf(score) : "—", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        if (score > 0) {
            scoreLabel.setForeground(new Color(34, 139, 34));
        } else {
            scoreLabel.setForeground(new Color(180, 180, 180));
        }
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(Box.createVerticalStrut(8));
        card.add(attemptLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(scoreLabel);
        card.add(Box.createVerticalStrut(8));
        
        return card;
    }
    
    private void styleBackButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(new Color(65, 105, 225));
        button.setBackground(new Color(245, 250, 255));
        button.setBorder(new LineBorder(new Color(65, 105, 225), 1, true));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void refresh() {
        RONCompetitor c = mainFrame.getCurrentUser();
        if(c != null) {
            mainFrame.getCompetitorList().loadFromDatabase(); // reload DB
            c = mainFrame.getCompetitorList().findCompetitor(c.getCompetitorID()); // get fresh obj
            
            nameL.setText(c.getCompetitorName().getFullName());
            levelL.setText(c.getCompetitorLevel());
            
            double overallScore = c.getOverallScore();
            scoreL.setText(String.format("%.1f", overallScore));
            
            // Update individual score cards
            scoresPanel.removeAll();
            int[] scores = c.getScores();
            // Always show 5 quiz attempts (Q1-Q5)
            for(int i = 0; i < 5; i++) {
                int score = (i < scores.length) ? scores[i] : 0;
                JPanel scoreCard = createScoreCard(score, i + 1);
                scoresPanel.add(scoreCard);
            }
            
            scoresPanel.revalidate();
            scoresPanel.repaint();
        }
    }
}