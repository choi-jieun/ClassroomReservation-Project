package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import frontend2.ElementSelectionPanel;

public class BuildingSelectionPanel extends JPanel {

    private Image backgroundImage;
    private Font customFont;

    public BuildingSelectionPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        loadBackgroundImage("/images/polka_background.png");
        customFont = loadCustomFont("/images/ps.ttf", 20f); // 폰트 적용

        JPanel buildingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 40));
        buildingPanel.setOpaque(false);

        JPanel sunheonPanel = createBuildingItem("images/sunheon.jpg", "순헌관", customFont, frame);
        JPanel myungshinPanel = createBuildingItem("images/myungshin.png", "명신관", customFont, frame);
        JPanel primePanel = createBuildingItem("images/prime.jpg", "프라임관", customFont, frame);

        buildingPanel.add(sunheonPanel);
        buildingPanel.add(myungshinPanel);
        buildingPanel.add(primePanel);

        add(buildingPanel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("숙명여자대학교");
        footerLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 18)); // 맑은 고딕 적용
        footerLabel.setForeground(new Color(120, 80, 40, 150)); // 진한 갈색

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createBuildingItem(String imagePath, String name, Font font, MainFrame frame) {
        ImageIcon icon = resizeImage(imagePath, 230, 170);
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = createStyledButton(name, font);
        addHoverEffect(button);
        button.addActionListener(e -> {
    frame.inputData.building = name;  // 🔹 여기에 저장
    frame.setSelectedBuilding(name);  // (선택적 저장, UI 전환용이면 유지 가능)
    frame.showPanel("element");
});



        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(240, 220));
        panel.add(imageLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(button);

        return panel;
    }

    private Font loadCustomFont(String path, float size) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is != null) {
                return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
            } else {
                System.err.println("폰트 파일을 찾을 수 없습니다: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Font("Malgun Gothic", Font.PLAIN, (int) size); // fallback
    }

    private void loadBackgroundImage(String path) {
        backgroundImage = new ImageIcon(getClass().getResource(path)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int tileWidth = backgroundImage.getWidth(null) / 2;
            int tileHeight = backgroundImage.getHeight(null) / 2;
            for (int x = 0; x < getWidth(); x += tileWidth) {
                for (int y = 0; y < getHeight(); y += tileHeight) {
                    g.drawImage(backgroundImage, x, y, tileWidth, tileHeight, this);
                }
            }
        }
    }

    private JButton createStyledButton(String text, Font font) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setBackground(Color.decode("#97c162"));
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(150, 40));
        return btn;
    }

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#7fa750"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#97c162"));
            }
        });
    }

    private ImageIcon resizeImage(String path, int width, int height) {
        java.net.URL imageUrl = getClass().getResource("/" + path);
        if (imageUrl == null) {
            System.out.println("이미지 못 찾음: " + path);
            return new ImageIcon();
        }
        ImageIcon icon = new ImageIcon(imageUrl);
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized); 
    }
}

