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
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- 1. Welcome Message ---
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(50, 50, 50));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, gbc);

        // --- 2. Subtitle ---
        gbc.gridy++;
        JLabel subtitle = new JLabel("Ready to challenge yourself?");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitle.setForeground(Color.GRAY);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 40, 0);
        add(subtitle, gbc);

        // --- 3. Start Quiz Button (Primary) ---
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0);
        JButton startBtn = new JButton("START QUIZ");
        stylePrimaryButton(startBtn);
        startBtn.addActionListener(e -> mainFrame.showCard("LEVEL"));
        add(startBtn, gbc);

        // --- 4. My Scores Button (Secondary) ---
        gbc.gridy++;
        JButton myScoresBtn = new JButton("MY SCORES");
        styleSecondaryButton(myScoresBtn);
        myScoresBtn.addActionListener(e -> mainFrame.showCard("MY_SCORES"));
        add(myScoresBtn, gbc);

        // --- 5. Leaderboard Button (Secondary) ---
        gbc.gridy++;
        JButton leaderBtn = new JButton("LEADERBOARD");
        styleSecondaryButton(leaderBtn);
        leaderBtn.addActionListener(e -> mainFrame.showCard("LEADERBOARD"));
        add(leaderBtn, gbc);

        // --- 6. Logout Button (Grey Border) ---
        gbc.gridy++;
        gbc.insets = new Insets(30, 0, 0, 0); // Extra space above logout
        JButton logoutBtn = new JButton("Log out");
        styleLogoutButton(logoutBtn); // Updated style
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

    // --- Primary Button Style (Solid Blue) ---
    private void stylePrimaryButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(new Color(65, 105, 225)); // Royal Blue
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 50));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(55, 95, 205));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(65, 105, 225));
            }
        });
    }

    // --- Secondary Button Style (White with Blue Border) ---
    private void styleSecondaryButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(65, 105, 225));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 50));

        btn.setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225), 2));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(240, 248, 255));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.WHITE);
            }
        });
    }

    // --- Logout Button Style (White with Grey Border) ---
    private void styleLogoutButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(220, 53, 69)); // Red Text
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 50)); // Same size as others

        // Grey Border
        btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(245, 245, 245)); // Very light grey hover
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.WHITE);
            }
        });
    }
}