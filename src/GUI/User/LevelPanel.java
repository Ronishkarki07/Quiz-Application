package GUI.User;

import GUI.QuizMainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelPanel extends JPanel {
    public LevelPanel(QuizMainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 248, 255));

        // Header Section
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(245, 248, 255));
        headerPanel.setBorder(new EmptyBorder(40, 30, 30, 30));

        JLabel titleLabel = new JLabel("Select Your Difficulty Level", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Choose the challenge that matches your skill level", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(subtitleLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Main Content Section
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 248, 255));
        contentPanel.setBorder(new EmptyBorder(0, 50, 40, 50));

        // Difficulty Level Cards
        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        String[] descriptions = {
            "Perfect for newcomers - Basic questions to get you started",
            "Ready for more? - Moderately challenging questions",
            "Expert level - Complex questions for seasoned players"
        };
        Color[] cardColors = {
             // Green for Beginner
            new Color(76, 175, 80),  
             // Orange for Intermediate  
            new Color(255, 152, 0), 
             // Red for Advanced 
            new Color(244, 67, 54)   
        };

        for (int i = 0; i < levels.length; i++) {
            JPanel card = createLevelCard(levels[i], descriptions[i], cardColors[i], frame);
            contentPanel.add(card);
            contentPanel.add(Box.createVerticalStrut(15));
        }

        add(contentPanel, BorderLayout.CENTER);

        // Back Button Section
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(245, 248, 255));
        bottomPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton backButton = new JButton("Back to Menu");
        styleBackButton(backButton);
        backButton.addActionListener(e -> frame.showCard("WELCOME"));

        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createLevelCard(String level, String description, Color themeColor, QuizMainFrame frame) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        card.setPreferredSize(new Dimension(400, 100));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Left colored stripe
        JPanel colorStripe = new JPanel();
        colorStripe.setBackground(themeColor);
        colorStripe.setPreferredSize(new Dimension(5, 100));
        card.add(colorStripe, BorderLayout.WEST);

        // Content area
        JPanel contentArea = new JPanel();
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel levelLabel = new JLabel(level);
        levelLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        levelLabel.setForeground(new Color(50, 50, 50));
        levelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel("<html><div style='width:320px;'>" + description + "</div></html>");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descLabel.setForeground(new Color(120, 120, 120));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentArea.add(levelLabel);
        contentArea.add(Box.createVerticalStrut(5));
        contentArea.add(descLabel);

        card.add(contentArea, BorderLayout.CENTER);

        // Hover effects
        addCardHoverEffects(card, themeColor);

        // Click action
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.startQuiz(level);
            }
        });

        return card;
    }

    private void addCardHoverEffects(JPanel card, Color themeColor) {
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 252, 255));
                card.setBorder(new LineBorder(themeColor, 2, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
            }
        });
    }

    private void styleBackButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(new Color(100, 100, 100));
        button.setBackground(Color.WHITE);
        button.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect for back button
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
    }
}