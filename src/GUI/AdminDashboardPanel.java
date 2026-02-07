package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboardPanel extends JPanel {
    public AdminDashboardPanel(QuizMainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(Color.DARK_GRAY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        add(Box.createVerticalStrut(40)); // Spacing

        // --- List Items ---
        add(createListButton("View All Players (Report)", e -> frame.showCard("REPORT")));
        add(Box.createVerticalStrut(15));

        add(createListButton("View Leaderboard", e -> frame.showCard("LEADERBOARD")));
        add(Box.createVerticalStrut(15));

        add(createListButton("Add New Question", e -> frame.showCard("ADD_QUESTION")));
        add(Box.createVerticalStrut(15));

        add(createListButton("Logout", e -> frame.showCard("ROLE_SELECTION")));
    }

    private JButton createListButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Make it wide and tall enough
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // List Item Styling (Border only on bottom or simple box)
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1), // Light grey border
                new EmptyBorder(10, 20, 10, 20) // Padding inside
        ));

        // Mac Fix
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);

        // Hover Effect (Optional: Turns light grey when hovered)
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(245, 245, 245)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(Color.WHITE); }
        });

        btn.addActionListener(action);
        return btn;
    }
}