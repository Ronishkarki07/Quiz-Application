package GUI.User;

import GUI.QuizMainFrame;
import QUIZ.RONCompetitor;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UserReportPanel extends JPanel {
    private QuizMainFrame mainFrame;
    private DefaultTableModel model;
    private JTextArea stats;
    private String returnDestination = "WELCOME"; // Default to user welcome screen

    public UserReportPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        // --- 1. HEADER ---
        JLabel title = new JLabel("My Performance Report");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(50, 50, 50));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(new EmptyBorder(30, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // --- 2. TABLE ---
        String[] cols = {"ID", "Name", "Level", "S1", "S2", "S3", "S4", "S5", "Avg"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable table = new JTable(model) {
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                c.setBackground(Color.WHITE); // Always white for single row
                return c;
            }
        };

        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 40, 0, 40));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. BOTTOM SUMMARY ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 247, 250));
        bottomPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        stats = new JTextArea(4, 50);
        stats.setEditable(false);
        stats.setFont(new Font("SansSerif", Font.PLAIN, 15));
        stats.setLineWrap(true);
        stats.setWrapStyleWord(true);
        stats.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        bottomPanel.add(stats, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        styleButton(backBtn);
        backBtn.addActionListener(e -> mainFrame.showCard(returnDestination));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(new Color(245, 247, 250));
        btnPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        btnPanel.add(backBtn);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Method to set where the back button should redirect
    public void setReturnDestination(String destination) {
        this.returnDestination = destination;
    }

    public void refresh() {
        model.setRowCount(0);
        stats.setText("");
        RONCompetitor user = mainFrame.getCurrentUser();
        if (user != null) {
            // Reload the competitor's latest data from database
            mainFrame.getCompetitorList().loadFromDatabase();
            RONCompetitor updatedUser = mainFrame.getCompetitorList().findCompetitor(user.getCompetitorID());
            if (updatedUser != null) {
                addRowToTable(updatedUser);
                stats.setText(generateUserSummary(updatedUser));
            } else {
                stats.setText("User data not found. Please log in again.");
            }
        } else {
            stats.setText("No user is currently logged in.");
        }
    }

    private void addRowToTable(RONCompetitor c) {
        int[] s = c.getScores();
        model.addRow(new Object[]{
                c.getCompetitorID(), c.getCompetitorName().getFullName(), c.getCompetitorLevel(),
                (s[0] == -1) ? "0" : s[0], (s[1] == -1) ? "0" : s[1], (s[2] == -1) ? "0" : s[2],
                (s[3] == -1) ? "0" : s[3], (s[4] == -1) ? "0" : s[4],
                String.format("%.1f", c.getOverallScore())
        });
    }

    private String generateUserSummary(RONCompetitor c) {
        StringBuilder scoresList = new StringBuilder();
        int[] s = c.getScores();
        boolean first = true;
        for (int score : s) {
            if (score != -1) {
                if (!first) scoresList.append(", ");
                scoresList.append(score);
                first = false;
            }
        }
        if (scoresList.length() == 0) scoresList.append("no scores yet");

        return "CompetitorID " + c.getCompetitorID() + ", name " + c.getCompetitorName().getFullName() + ".\n" +
                c.getCompetitorName().getFirstName() + " is a " + c.getCompetitorLevel() +
                " and received these scores: " + scoresList.toString() + ".\n" +
                "This gives an overall score of " + String.format("%.1f", c.getOverallScore()) + ".";
    }

    private void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(65, 105, 225));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 45));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<table.getColumnCount(); i++) table.getColumnModel().getColumn(i).setCellRenderer(center);

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
    }

    private void styleButton(JButton b) {
        b.setBackground(new Color(65, 105, 225));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(100, 35));
    }
}