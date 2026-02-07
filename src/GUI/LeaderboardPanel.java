package GUI;

import QUIZ.RONCompetitor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class LeaderboardPanel extends JPanel {
    private QuizMainFrame mainFrame;
    private DefaultTableModel model;

    public LeaderboardPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());

        JLabel t = new JLabel("Global Leaderboard", SwingConstants.CENTER);
        t.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(t, BorderLayout.NORTH);

        // Table Setup
        String[] cols = {"Rank", "Name", "Level", "Country", "Overall Score"};
        model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- SMART BACK BUTTON ---
        JButton back = new JButton("Back");
        back.setFont(new Font("SansSerif", Font.BOLD, 14));
        back.addActionListener(e -> {
            // Check if a normal user is logged in
            if (mainFrame.getCurrentUser() != null) {
                // If User: Go to User Welcome Screen
                frame.showCard("WELCOME");
            } else {
                // If Null (Admin): Go to Admin Dashboard
                frame.showCard("ADMIN_DASHBOARD");
            }
        });
        add(back, BorderLayout.SOUTH);
    }

    public void refresh() {
        model.setRowCount(0);
        ArrayList<RONCompetitor> list = mainFrame.getCompetitorList().getLeaderboard();
        int rank = 1;
        for (RONCompetitor c : list) {
            model.addRow(new Object[]{
                    rank++, c.getCompetitorName().getFullName(),
                    c.getCompetitorLevel(), c.getCountry(),
                    String.format("%.1f", c.getOverallScore())
            });
        }
    }
}