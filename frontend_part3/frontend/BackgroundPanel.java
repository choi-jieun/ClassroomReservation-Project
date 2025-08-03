package frontend;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        this.backgroundImage = new ImageIcon(getClass().getResource("/image/background.jpg")).getImage();
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imgWidth = backgroundImage.getWidth(this);
            int imgHeight = backgroundImage.getHeight(this);

            double widthRatio = (double) panelWidth / imgWidth;
            double heightRatio = (double) panelHeight / imgHeight;
            double scale = Math.max(widthRatio, heightRatio);

            int newWidth = (int) (imgWidth * scale);
            int newHeight = (int) (imgHeight * scale);

            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;

            g.drawImage(backgroundImage, x, y, newWidth, newHeight, this);
        }

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        g2d.setColor(new Color(120, 80, 40, 150));

        FontMetrics fm = g2d.getFontMetrics();
        int textX = getWidth() - fm.stringWidth("숙명여자대학교") - 10;
        int textY = getHeight() - 10;

        g2d.drawString("숙명여자대학교", textX, textY);
        g2d.dispose();
    }
}
