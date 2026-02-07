package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomePanel extends JPanel {
    private QuizMainFrame mainFrame;
    private JLabel welcomeLabel;

    public WelcomePanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Spacing between items
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- 1. Welcome Message ---
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(50, 50, 50)); // Dark Grey
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, gbc);

        // --- 2. Subtitle ---
        gbc.gridy++;
        JLabel subtitle = new JLabel("Ready to challenge yourself?");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitle.setForeground(Color.GRAY);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 40, 0); // Extra space below subtitle
        add(subtitle, gbc);

        // --- 3. Start Quiz Button (Primary) ---
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0);

        JButton startBtn = new JButton("START QUIZ");
        styleButton(startBtn, new Color(65, 105, 225), Color.WHITE); // Blue Background
        startBtn.addActionListener(e -> mainFrame.showCard("LEVEL"));
        add(startBtn, gbc);

        // --- 4. My Scores Button (Secondary) ---
        gbc.gridy++;
        JButton myScoresBtn = new JButton("MY SCORES");
        styleButton(myScoresBtn, Color.WHITE, new Color(65, 105, 225)); // White Background
        // Add a border for the white button so it's visible
        myScoresBtn.setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225), 2));
        myScoresBtn.addActionListener(e -> mainFrame.showCard("MY_SCORES"));
        add(myScoresBtn, gbc);

        // --- 5. Leaderboard Button (Secondary) ---
        gbc.gridy++;
        JButton leaderBtn = new JButton("LEADERBOARD");
        styleButton(leaderBtn, Color.WHITE, new Color(65, 105, 225));
        leaderBtn.setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225), 2));
        leaderBtn.addActionListener(e -> mainFrame.showCard("LEADERBOARD"));
        add(leaderBtn, gbc);

        // --- 6. Logout Link ---
        gbc.gridy++;
        gbc.insets = new Insets(30, 0, 0, 0);
        JButton logoutBtn = new JButton("Log out");
        styleLinkButton(logoutBtn);
        logoutBtn.addActionListener(e -> {
            mainFrame.setCurrentUser(null);
            mainFrame.showCard("LOGIN");
        });
        add(logoutBtn, gbc);
    }

    public void updateWelcomeText() {
        if(mainFrame.getCurrentUser() != null) {
            String firstName = mainFrame.getCurrentUser().getCompetitorName().getFirstName();
            welcomeLabel.setText("Welcome, " + firstName + "!");
        }
    }

    // --- Simple Standard Button Styling ---
    private void styleButton(JButton btn, Color bgColor, Color textColor) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(bgColor);
        btn.setForeground(textColor);
        btn.setFocusPainted(false);
        // btn.setBorderPainted(false); // Removed to allow borders on white buttons
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 50));

        // Optional: Simple Hover Effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (bgColor != Color.WHITE) btn.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                if (bgColor != Color.WHITE) btn.setBackground(bgColor);
            }
        });
    }

    private void styleLinkButton(JButton b) {
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setForeground(new Color(220, 53, 69)); // Red
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}