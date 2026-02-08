package GUI.User;

import GUI.QuizMainFrame;
import QUIZ.RONCompetitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderboardPanel extends JPanel {
    private QuizMainFrame mainFrame;
    private DefaultTableModel model;

    public LeaderboardPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        // --- 1. TITLE ---
        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(50, 50, 50));
        title.setBorder(new EmptyBorder(25, 0, 25, 0));
        add(title, BorderLayout.NORTH);

        // --- 2. TABLE SETUP (Database Style with Scores) ---
        // Added S1 - S5 columns
        String[] cols = {
                "Rank", "Name", "Level", "Country",
                "Score1", "Score2", "Score3", "Score4", "Score5",
                "Overall"
        };

        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                // Zebra Striping: Light Grey vs White
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 248, 250) : Color.WHITE);
                }
                return c;
            }
        };

        // --- DATABASE TABLE STYLING ---
        table.setRowHeight(35);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setFillsViewportHeight(true);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(220, 220, 220));

        // Header Style (Database Blue/Grey)
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setBackground(new Color(223, 231, 240));
        header.setForeground(new Color(35, 90, 129));
        header.setPreferredSize(new Dimension(0, 40));
        header.setOpaque(true);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(180, 190, 200)));

        // Center Align Columns (Rank, Scores, Overall)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Rank
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Level
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Country
        // Center Align S1 to S5 and Overall
        for (int i = 4; i <= 9; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Left Align Name
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        leftRenderer.setBorder(new EmptyBorder(0, 5, 0, 0));
        table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);

        // Adjust Column Widths (Tight fit for 10 columns)
        table.getColumnModel().getColumn(0).setPreferredWidth(40);  // Rank
        table.getColumnModel().getColumn(1).setPreferredWidth(130); // Name
        table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Level
        table.getColumnModel().getColumn(3).setPreferredWidth(70);  // Country
        // S1-S5 (Small)
        for (int i = 4; i <= 8; i++) table.getColumnModel().getColumn(i).setPreferredWidth(30);
        table.getColumnModel().getColumn(9).setPreferredWidth(50);  // Overall

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. BOTTOM PANEL ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(245, 247, 250));
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton backBtn = new JButton("Back");
        styleButton(backBtn);

        backBtn.addActionListener(e -> {
            if (mainFrame.getCurrentUser() != null) {
                frame.showCard("WELCOME");
            } else {
                frame.showCard("ADMIN_DASHBOARD");
            }
        });

        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void refresh() {
        model.setRowCount(0);
        mainFrame.getCompetitorList().loadFromDatabase();
        ArrayList<RONCompetitor> list = mainFrame.getCompetitorList().getAllCompetitors();

        // Sort by Overall Score (Highest first)
        Collections.sort(list, Comparator.comparingDouble(RONCompetitor::getOverallScore).reversed());

        int rank = 1;
        for (RONCompetitor c : list) {
            String country = "Nepal"; // Placeholder
            String level = c.getCompetitorLevel();
            int[] s = c.getScores();

            Object[] rowData = {
                    rank++,
                    c.getCompetitorName().getFullName(),
                    level,
                    country,
                    (s[0] == -1) ? "0" : s[0], // S1
                    (s[1] == -1) ? "0" : s[1], // S2
                    (s[2] == -1) ? "0" : s[2], // S3
                    (s[3] == -1) ? "0" : s[3], // S4
                    (s[4] == -1) ? "0" : s[4], // S5
                    String.format("%.1f", c.getOverallScore()) // Overall
            };
            model.addRow(rowData);
        }
    }

    private void styleButton(JButton b) {
        b.setBackground(new Color(223, 231, 240));
        b.setForeground(Color.BLACK);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(new Color(180, 190, 200), 1));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(80, 30));
    }
}