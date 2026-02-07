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

        model = new DefaultTableModel(new String[]{"ID", "Name", "Level", "Scores", "Overall"}, 0);
        add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);

        stats = new JTextArea(5, 40);
        stats.setEditable(false);
        add(new JScrollPane(stats), BorderLayout.SOUTH);

        JButton home = new JButton("Home");
        home.addActionListener(e -> frame.showCard("WELCOME"));
        add(home, BorderLayout.NORTH);
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
        if (top != null) stats.setText("Top Performer: " + top.getCompetitorName().getFullName());
    }
}