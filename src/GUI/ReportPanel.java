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
        setBackground(Color.WHITE);

        // --- Header ---
        JLabel title = new JLabel("Performance Report", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // --- Table ---
        // Columns matching your screenshot
        String[] cols = {"ID", "Name", "Level", "Scores", "Overall"};
        model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Bottom Panel (Stats & Back Button) ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Stats Box
        stats = new JTextArea(3, 40);
        stats.setEditable(false);
        stats.setFont(new Font("Monospaced", Font.PLAIN, 12));
        stats.setBorder(BorderFactory.createTitledBorder("Summary"));
        bottomPanel.add(new JScrollPane(stats), BorderLayout.CENTER);

        // Back Button
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            if (mainFrame.getCurrentUser() != null) {
                frame.showCard("WELCOME");       // User goes to User Dashboard
            } else {
                frame.showCard("ADMIN_DASHBOARD"); // Admin goes to Admin Dashboard
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(backBtn);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void refresh() {
        model.setRowCount(0); // Clear old data
        stats.setText("");

        RONCompetitor currentUser = mainFrame.getCurrentUser();

        // --- LOGIC: Check who is logged in ---
        if (currentUser != null) {
            // CASE 1: REGULAR USER (Show only THEIR data)
            String scoresStr = Arrays.toString(currentUser.getScores())
                    .replace("[", "").replace("]", "")
                    .replace("-1", "-"); // Show -1 as "-" for cleaner look

            model.addRow(new Object[]{
                    currentUser.getCompetitorID(),
                    currentUser.getCompetitorName().getFullName(),
                    currentUser.getCompetitorLevel(),
                    scoresStr,
                    String.format("%.1f", currentUser.getOverallScore())
            });

            stats.setText("Report for: " + currentUser.getCompetitorName().getFullName() + "\n" +
                    "Your Current Level: " + currentUser.getCompetitorLevel());

        } else {
            // CASE 2: ADMIN (Show EVERYONE)
            mainFrame.getCompetitorList().loadFromDatabase(); // Refresh DB data

            RONCompetitor top = null;
            for (RONCompetitor c : mainFrame.getCompetitorList().getAllCompetitors()) {
                String scoresStr = Arrays.toString(c.getScores())
                        .replace("[", "").replace("]", "")
                        .replace("-1", "-");

                model.addRow(new Object[]{
                        c.getCompetitorID(),
                        c.getCompetitorName().getFullName(),
                        c.getCompetitorLevel(),
                        scoresStr,
                        String.format("%.1f", c.getOverallScore())
                });

                // Calculate Top Performer for Admin Stats
                if (top == null || c.getOverallScore() > top.getOverallScore()) {
                    top = c;
                }
            }

            if (top != null) {
                stats.setText("Total Competitors: " + model.getRowCount() + "\n" +
                        "Top Performer: " + top.getCompetitorName().getFullName() +
                        " (Score: " + String.format("%.1f", top.getOverallScore()) + ")");
            }
        }
    }
}