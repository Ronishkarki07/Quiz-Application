package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoleSelectionPanel extends JPanel {
    public RoleSelectionPanel(QuizMainFrame frame) {
        // Use GridBagLayout for better centering and control than GridLayout
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(50, 50, 50, 50));
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0); // Spacing between items
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Title
        JLabel title = new JLabel("Select Role", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(Color.DARK_GRAY);
        add(title, gbc);

        // 2. User Button
        gbc.gridy++;
        JButton userBtn = new JButton("Competitor (User)");
        styleButton(userBtn, new Color(65, 105, 225)); // Blue
        userBtn.addActionListener(e -> frame.showCard("LOGIN"));
        add(userBtn, gbc);

        // 3. Admin Button
        gbc.gridy++;
        JButton adminBtn = new JButton("Admin (Manager)");
        styleButton(adminBtn, new Color(220, 53, 69)); // Red
        adminBtn.addActionListener(e -> frame.showCard("ADMIN_LOGIN"));
        add(adminBtn, gbc);
    }

    private void styleButton(JButton b, Color bg) {
        b.setFont(new Font("SansSerif", Font.BOLD, 18));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(300, 60)); // Make them big

        // --- MAC OS FIX ---
        b.setOpaque(true);
        b.setBorderPainted(false);
        // ------------------
    }
}