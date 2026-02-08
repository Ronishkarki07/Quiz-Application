package GUI.Admin;

import GUI.QuizMainFrame;
import QUIZ.RONCompetitor;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AdminReportPanel extends JPanel {
    private QuizMainFrame mainFrame;
    private DefaultTableModel model;
    private JTextArea stats;
    private JTextField searchField;

    public AdminReportPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        // --- 1. HEADER & SEARCH ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("All Competitors Report");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(25, 0, 10, 0));
        topPanel.add(title);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        searchPanel.setBackground(new Color(245, 247, 250));
        searchPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(150, 30));

        JButton searchBtn = new JButton("Search");
        stylePrimaryButton(searchBtn);
        searchBtn.setPreferredSize(new Dimension(100, 30));
        searchBtn.addActionListener(e -> refresh());

        JButton clearBtn = new JButton("Clear");
        styleSecondaryButton(clearBtn);
        clearBtn.setPreferredSize(new Dimension(60, 30));
        clearBtn.addActionListener(e -> {
            searchField.setText("");
            refresh();
        });

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(clearBtn);
        topPanel.add(searchPanel);
        add(topPanel, BorderLayout.NORTH);

        // --- 2. TABLE SETUP ---
        String[] cols = {"ID", "Name", "Level", "Score1", "Score2", "Score3", "Score4", "Score5", "Overall"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable table = new JTable(model) {
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(242, 246, 252));
                return c;
            }
        };
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(20, 30, 0, 30));
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. BOTTOM SUMMARY & NAVIGATION ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 247, 250));
        bottomPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        stats = new JTextArea(4, 50);
        stats.setEditable(false);
        stats.setFont(new Font("SansSerif", Font.PLAIN, 15));
        stats.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY), new EmptyBorder(10, 10, 10, 10)));
        bottomPanel.add(stats, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        stylePrimaryButton(backBtn);
        backBtn.addActionListener(e -> mainFrame.showCard("ADMIN_DASHBOARD"));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(245, 247, 250));
        btnPanel.add(backBtn);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // --- REFRESH DATA USING COMPETITOR CLASS ---
    public void refresh() {
        model.setRowCount(0);
        String searchQuery = searchField.getText().trim().toLowerCase();

        // 1. Reload data from DB into the CompetitorList object
        mainFrame.getCompetitorList().loadFromDatabase();

        // 2. Get the list of RONCompetitor objects
        ArrayList<RONCompetitor> allCompetitors = mainFrame.getCompetitorList().getAllCompetitors();

        String topName = "";
        double topScore = -1.0;
        int count = 0;

        for (RONCompetitor c : allCompetitors) {
            // 3. Search Filter Logic (Java side)
            String name = c.getCompetitorName().getFullName();
            String idStr = String.valueOf(c.getCompetitorID());

            if (!searchQuery.isEmpty()) {
                if (!name.toLowerCase().contains(searchQuery) && !idStr.contains(searchQuery)) {
                    continue; // Skip if it doesn't match search
                }
            }

            // 4. Get Data from Object
            String id = c.getCompetitorID();
            String level = c.getCompetitorLevel();
            int[] s = c.getScores(); // Returns int[] {s1, s2, s3, s4, s5}
            double avg = c.getOverallScore();

            // Add row to table model
            model.addRow(new Object[]{id, name, level, s[0], s[1], s[2], s[3], s[4], String.format("%.1f", avg)});

            // 5. Calculate Stats
            count++;
            if (avg > topScore) {
                topScore = avg;
                topName = name;
            }
        }

        // Update Stats Box
        if (count > 0) {
            stats.setText("• Total number of competitors: " + count + "\n\n" +
                    "• Competitor with the highest score: " + topName +
                    " with an overall score of " + String.format("%.1f", topScore) + " •");
        } else {
            stats.setText("No competitors found.");
        }
    }

    private void styleTable(JTable table) {
        table.setRowHeight(35);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.getTableHeader().setBackground(new Color(65, 105, 225));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<table.getColumnCount(); i++) table.getColumnModel().getColumn(i).setCellRenderer(center);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
    }

    private void stylePrimaryButton(JButton b) {
        b.setBackground(new Color(65, 105, 225));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
    }

    private void styleSecondaryButton(JButton b) {
        b.setBackground(Color.WHITE);
        b.setForeground(Color.DARK_GRAY);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(Color.LIGHT_GRAY));
    }
}