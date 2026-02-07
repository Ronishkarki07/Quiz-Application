package GUI;

import QUIZ.RONCompetitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class ScorePanel extends JPanel {
    private QuizMainFrame mainFrame;
    private JLabel nameL, levelL, listL, scoreL;

    public ScorePanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 247, 250));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(40, 60, 40, 60)));

        nameL = label("Name", 24, Color.BLACK);
        levelL = label("Level", 16, Color.DARK_GRAY);
        scoreL = label("0.0", 80, new Color(65, 105, 225));
        listL = label("0 0 0 0 0", 18, Color.BLACK);

        card.add(label("Performance Summary", 16, Color.GRAY));
        card.add(Box.createVerticalStrut(20));
        card.add(nameL); card.add(levelL);
        card.add(Box.createVerticalStrut(30));
        card.add(scoreL);
        card.add(label("Overall Score", 12, Color.GRAY));
        card.add(Box.createVerticalStrut(30));
        card.add(new JSeparator());
        card.add(Box.createVerticalStrut(15));
        card.add(listL);

        add(card);

        JButton back = new JButton("Back");
        back.addActionListener(e -> frame.showCard("WELCOME"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1; gbc.insets = new Insets(20,0,0,0);
        add(back, gbc);
    }

    private JLabel label(String txt, int size, Color c) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("SansSerif", Font.BOLD, size));
        l.setForeground(c);
        l.setAlignmentX(CENTER_ALIGNMENT);
        return l;
    }

    public void refresh() {
        RONCompetitor c = mainFrame.getCurrentUser();
        if(c != null) {
            mainFrame.getCompetitorList().loadFromDatabase(); // reload DB
            c = mainFrame.getCompetitorList().findCompetitor(c.getCompetitorID()); // get fresh obj
            nameL.setText(c.getCompetitorName().getFullName());
            levelL.setText(c.getCompetitorLevel());
            scoreL.setText(String.format("%.1f", c.getOverallScore()));
            listL.setText(Arrays.toString(c.getScores()));
        }
    }
}