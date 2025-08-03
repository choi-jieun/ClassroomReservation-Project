package frontend;

import javax.swing.*;
import java.awt.*;

public class DetailPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel imageLabel;
    private JPanel infoPanel;

    public DetailPanel(CardLayout layout, JPanel cardPanel) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#faf5cd"));
        nameLabel = new JLabel("", SwingConstants.CENTER);
        nameLabel.setFont(FontUtil.getPsFont(26f));
        nameLabel.setForeground(Color.decode("#4d4d4d"));
        add(nameLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(400, 250));
        centerPanel.add(imageLabel, BorderLayout.NORTH);

        infoPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        centerPanel.add(infoPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setOpaque(false);

        JButton backButton = createStyledButton("뒤로");
        JButton reserveButton = createStyledButton("예약하기");

        backButton.addActionListener(e -> layout.show(cardPanel, "추천"));
        reserveButton.addActionListener(e -> layout.show(cardPanel, "완료"));

        buttonPanel.add(backButton);
        buttonPanel.add(reserveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        button.setBackground(Color.decode("#97c162"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    public void setRoom(Room0 room) {
        nameLabel.setText("상세 정보: " + room.name);

        infoPanel.removeAll();
        infoPanel.add(createInfoLabel("화이트보드", room.hasWhiteboard));
        infoPanel.add(createInfoLabel("콘센트", room.hasOutlet));
        infoPanel.add(createInfoLabel("전자교탁+마이크+빔", room.hasMic));
        infoPanel.add(createInfoLabel("계단형 강의실", room.isStepped));

        infoPanel.revalidate();
        infoPanel.repaint();

        java.net.URL imgURL = getClass().getResource(room.imagePath);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image scaled = icon.getImage().getScaledInstance(330, 230, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setIcon(null);
            System.err.println("이미지를 찾을 수 없습니다: " + room.imagePath);
        }
    }

    private JPanel createInfoLabel(String label, boolean value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        JLabel name = new JLabel("✔ [" + label + "] ");
        name.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        name.setForeground(value ? Color.decode("#97c162") : Color.GRAY);
        JLabel result = new JLabel(value ? "있음" : "없음");
        result.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        result.setForeground(value ? Color.decode("#4d4d4d") : Color.LIGHT_GRAY);
        panel.add(name);
        panel.add(result);
        return panel;
    }
}
