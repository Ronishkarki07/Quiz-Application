package GUI.Admin;

import GUI.QuizMainFrame;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboardPanel extends JPanel {
    private QuizMainFrame mainFrame;

    public AdminDashboardPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 247, 250)); // Light Grey Background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        // --- 1. CARD CONTAINER (White Box) ---
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        // Add a subtle border to mimic a shadow/card look
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(40, 60, 40, 60)
        ));

        // --- 2. HEADER SECTION ---
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));

        JLabel subtitleLabel = new JLabel("Manage Competitions & Players");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);

        // Add Header to Card
        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        cardGbc.insets = new Insets(0, 0, 10, 0);
        card.add(titleLabel, cardGbc);

        cardGbc.gridy++;
        cardGbc.insets = new Insets(0, 0, 30, 0); // Spacing below subtitle
        card.add(subtitleLabel, cardGbc);

        // --- 3. MENU BUTTONS ---

        // Button 1: View Reports (Primary)
        JButton reportBtn = new JButton("View All Players (Report)");
        stylePrimaryButton(reportBtn);
        reportBtn.addActionListener(e -> mainFrame.showAdminReport());

        cardGbc.gridy++;
        cardGbc.insets = new Insets(0, 0, 15, 0);
        card.add(reportBtn, cardGbc);

        // Button 2: Leaderboard (Secondary)
        JButton leaderBtn = new JButton("View Leaderboard");
        styleSecondaryButton(leaderBtn);
        leaderBtn.addActionListener(e -> mainFrame.showCard("LEADERBOARD"));

        cardGbc.gridy++;
        card.add(leaderBtn, cardGbc);

        // Button 3: Add Question (Secondary)
        JButton addQBtn = new JButton("Add New Question");
        styleSecondaryButton(addQBtn);
        addQBtn.addActionListener(e -> mainFrame.showCard("ADD_QUESTION"));

        cardGbc.gridy++;
        card.add(addQBtn, cardGbc);

        // Button 4: Logout (Link Style)
        JButton logoutBtn = new JButton("Log out");
        styleLogoutButton(logoutBtn);
        logoutBtn.addActionListener(e -> {
            mainFrame.setCurrentUser(null);
            mainFrame.showCard("LOGIN");
        });

        cardGbc.gridy++;
        cardGbc.insets = new Insets(20, 0, 0, 0); // Extra space for logout
        card.add(logoutBtn, cardGbc);

        // Add the finished Card to the main panel
        add(card, gbc);
    }

    // --- STYLING HELPERS ---

    private void stylePrimaryButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(new Color(65, 105, 225)); // Royal Blue
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 45));

        // Hover Effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(55, 95, 205)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(new Color(65, 105, 225)); }
        });
    }

    private void styleSecondaryButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(65, 105, 225)); // Blue Text
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 45));

        // Blue Border
        btn.setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225), 2));

        // Hover Effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(240, 248, 255)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(Color.WHITE); }
        });
    }

    private void styleLogoutButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(220, 53, 69)); // Red Text
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 45));

        // Grey Border
        btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Hover Effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(250, 240, 240)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(Color.WHITE); }
        });
    }
}