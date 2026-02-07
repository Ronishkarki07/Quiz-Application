package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LevelPanel extends JPanel {
    public LevelPanel(QuizMainFrame frame) {
        setLayout(new GridLayout(5, 1, 10, 10));
        setBorder(new EmptyBorder(50, 50, 50, 50));

        JLabel l = new JLabel("Select Difficulty", SwingConstants.CENTER);
        l.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(l);

        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        for (String lvl : levels) {
            JButton b = new JButton(lvl);
            b.setBackground(new Color(65, 105, 225));
            b.setForeground(Color.WHITE);
            b.setForeground(Color.BLACK);
            b.setFont(new Font("SansSerif", Font.BOLD, 18));
            b.addActionListener(e -> frame.startQuiz(lvl)); // Call MainFrame to start
            add(b);
        }

        JButton back = new JButton("Back");
        back.addActionListener(e -> frame.showCard("WELCOME"));
        add(back);
    }
}