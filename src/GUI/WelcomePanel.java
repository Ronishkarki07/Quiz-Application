package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WelcomePanel extends JPanel {
    private QuizMainFrame mainFrame;
    private JLabel welcomeLabel;

    public WelcomePanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0); // Spacing between items
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- 1. Welcome Message ---
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.DARK_GRAY);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 40, 0); // Extra space below title
        add(welcomeLabel, gbc);

        // --- 2. Start Quiz Button (Primary Action) ---
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0); // Standard spacing
        JButton startBtn = new JButton("START QUIZ");
        stylePrimaryButton(startBtn);
        startBtn.addActionListener(e -> mainFrame.showCard("LEVEL"));
        add(startBtn, gbc);

        // --- 3. My Scores Button (Secondary Action) ---
        gbc.gridy++;
        JButton myScoresBtn = new JButton("MY SCORES");
        styleSecondaryButton(myScoresBtn);
        myScoresBtn.addActionListener(e -> mainFrame.showCard("MY_SCORES"));
        add(myScoresBtn, gbc);

        // --- 4. Leaderboard Button (Secondary Action) ---
        gbc.gridy++;
        JButton leaderBtn = new JButton("LEADERBOARD");
        styleSecondaryButton(leaderBtn);
        leaderBtn.addActionListener(e -> mainFrame.showCard("LEADERBOARD"));
        add(leaderBtn, gbc);

        // --- 5. Logout Link ---
        gbc.gridy++;
        gbc.insets = new Insets(30, 0, 0, 0); // Extra space above logout
        JButton logoutBtn = new JButton("Logout");
        styleLinkButton(logoutBtn);
        logoutBtn.setBackground(Color.DARK_GRAY);
        logoutBtn.setForeground(Color.RED);
        logoutBtn.addActionListener(e -> {
            mainFrame.setCurrentUser(null);
            mainFrame.showCard("LOGIN");
        });
        add(logoutBtn, gbc);
    }

    public void updateWelcomeText() {
        if(mainFrame.getCurrentUser() != null) {
            welcomeLabel.setText("Welcome, " + mainFrame.getCurrentUser().getCompetitorName().getFirstName() + "!");
        }
    }

    // --- Helper: Primary Button Style (Blue Filled) ---
    private void stylePrimaryButton(JButton b) {
        b.setBackground(new Color(65, 105, 225)); // Royal Blue
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(280, 50)); // Wide and tall
    }

    // --- Helper: Secondary Button Style (White with Border) ---
    private void styleSecondaryButton(JButton b) {
        b.setBackground(Color.WHITE);
        b.setForeground(new Color(65, 105, 225)); // Blue Text
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setOpaque(true);
        // Blue Border
        b.setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225), 2));
        b.setPreferredSize(new Dimension(280, 45));
    }

    private void styleLinkButton(JButton b) {
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setForeground(Color.GRAY);
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}