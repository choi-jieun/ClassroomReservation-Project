package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;

public class IntroPanel extends JPanel {

    private Image backgroundImage;
    private Font cuteFont;

    public IntroPanel(MainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        loadBackgroundImage("/images/polka_background.png");
        cuteFont = loadCustomFont("/images/ps.ttf", 55f);

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0)); // 위 여백

        // 이미지
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/gangjjim3.png"));
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("gangjjim3.png 이미지 로드 실패!");
        }

        Image original = icon.getImage();
        Image resized = original.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(resized));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 문구
        JLabel messageLabel = new JLabel("강찜콩");
        messageLabel.setFont(cuteFont);
        messageLabel.setForeground(Color.decode("#4d4d4d"));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        add(imageLabel);
        add(messageLabel);

        // 클릭 시 바로 넘어감
        imageLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.showPanel("start");
            }
        });

        // ✅ 타이머 후 Start로 이동, 이후 타이머 종료
        new Timer(8000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showPanel("start");
                ((Timer) e.getSource()).stop(); // 타이머 중복 방지
            }
        }).start();
    }

    private void loadBackgroundImage(String path) {
        backgroundImage = new ImageIcon(getClass().getResource(path)).getImage();
    }

    private Font loadCustomFont(String resourcePath, float size) {
        try {
            InputStream is = getClass().getResourceAsStream(resourcePath);
            if (is != null) {
                return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
            } else {
                System.err.println("폰트 파일을 찾을 수 없습니다: " + resourcePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Font("Malgun Gothic", Font.PLAIN, (int) size); // fallback
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
}

