package GUI;

import QUIZ.Name;
import QUIZ.RONCompetitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SignupPanel extends JPanel {
    private QuizMainFrame mainFrame;

    public SignupPanel(QuizMainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(30, 40, 30, 40));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setAlignmentX(LEFT_ALIGNMENT);

        JTextField idF = new JTextField();
        idF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JTextField fnF = new JTextField();
        fnF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JTextField lnF = new JTextField();
        lnF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JTextField ctryF = new JTextField();
        ctryF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JTextField ageF = new JTextField();
        ageF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JPasswordField passF = new JPasswordField();
        passF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton regBtn = new JButton("Create Account");
        regBtn.setBackground(new Color(65, 105, 225));
        regBtn.setForeground(Color.WHITE);
        regBtn.setForeground(Color.black);

        JButton backBtn = new JButton("Back to Login");
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setForeground(new Color(65, 105, 225));

        // Layout
        add(title);
        add(Box.createVerticalStrut(20));
        add(new JLabel("ID"));
        add(idF);
        add(new JLabel("First Name"));
        add(fnF);
        add(new JLabel("Last Name"));
        add(lnF);
        add(new JLabel("Country"));
        add(ctryF);
        add(new JLabel("Age"));
        add(ageF);
        add(new JLabel("Password"));
        add(passF);
        add(Box.createVerticalStrut(20));
        add(regBtn);
        add(backBtn);

        regBtn.addActionListener(e -> {
            try {
                RONCompetitor newC = new RONCompetitor(
                        idF.getText(),
                        new Name(fnF.getText(), lnF.getText()),
                        "Beginner",
                        ctryF.getText(),
                        Integer.parseInt(ageF.getText()),
                        new String(passF.getPassword()));
                mainFrame.getCompetitorList().saveCompetitor(newC);
                JOptionPane.showMessageDialog(this, "Success! Please Login.");
                mainFrame.showCard("LOGIN");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> mainFrame.showCard("LOGIN"));
    }
}