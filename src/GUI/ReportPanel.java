package GUI;

import QUIZ.RONCompetitor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class ReportPanel extends JPanel {
    private QuizMainFrame mainFrame;
    private DefaultTableModel model;
    private JTextArea stats;

    public ReportPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Player Report", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Level", "Scores", "Overall"}, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Stats Box
        stats = new JTextArea(5, 40);
        stats.setEditable(false);
        stats.setBorder(BorderFactory.createTitledBorder("Performance Stats"));
        add(new JScrollPane(stats), BorderLayout.SOUTH);

        // --- SMART BACK BUTTON ---
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            if (mainFrame.getCurrentUser() != null) {
                frame.showCard("WELCOME");       // User Dashboard
            } else {
                frame.showCard("ADMIN_DASHBOARD"); // Admin Dashboard
            }
        });

        // Add button to top-left or bottom (Added to a separate panel for layout safety)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(back);
        add(topPanel, BorderLayout.PAGE_START);
    }

    public void refresh() {
        model.setRowCount(0);
        mainFrame.getCompetitorList().loadFromDatabase();
        RONCompetitor top = null;
        for (RONCompetitor c : mainFrame.getCompetitorList().getAllCompetitors()) {
            model.addRow(new Object[]{c.getCompetitorID(), c.getCompetitorName().getFullName(),
                    c.getCompetitorLevel(), Arrays.toString(c.getScores()),
                    String.format("%.1f", c.getOverallScore())});
            if (top == null || c.getOverallScore() > top.getOverallScore()) top = c;
        }
        if (top != null) stats.setText("Top Performer: " + top.getCompetitorName().getFullName() + "\nScore: " + top.getOverallScore());
    }
}