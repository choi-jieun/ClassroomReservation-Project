package frontend;

import javax.swing.*;
import java.awt.*;

public class CompletePanel extends BackgroundPanel {
    public CompletePanel(CardLayout layout, JPanel cardPanel) {
        super("/image/background.jpg");

        setLayout(new BorderLayout());

        JLabel message = new JLabel("강의실이 찜꽁되었습니다!", SwingConstants.CENTER);
        message.setFont(FontUtil.getPsFont(28f));
        message.setForeground(Color.decode("#4d4d4d"));
        add(message, BorderLayout.CENTER);

        JButton homeButton = new JButton("홈으로 돌아가기");
        homeButton.setFont(new Font("Noto Sans KR", Font.BOLD, 16));
        homeButton.setBackground(Color.decode("#97c162"));
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        homeButton.addActionListener(e -> layout.show(cardPanel, "추천"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  
        buttonPanel.add(homeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
