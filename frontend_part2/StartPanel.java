package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class StartPanel extends JPanel {

	private Image backgroundImage;

	public StartPanel(MainFrame frame) {
		setLayout(new BorderLayout());
		loadBackgroundImage("/images/polka_background.png");

		Font mainFont = new Font("Pretendard", Font.BOLD, 24);
		Font footerFont = new Font("Pretendard", Font.PLAIN, 18);
		Font cuteFont = loadCustomFont("/images/ps.ttf", 45f); // 폰트 적용

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

		ImageIcon logoImg = resizeImage("images/gangjjim3.png", 200, 200);
		JLabel logoLabel = new JLabel(logoImg);
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel messageLabel = new JLabel("강의실을 찜콩하시겠어요?");
		messageLabel.setFont(cuteFont); // 귀여운 폰트 적용
		messageLabel.setForeground(Color.decode("#4d4d4d"));
		messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		JButton yesButton = new JButton("네");
		yesButton.setFont(cuteFont);
		yesButton.setBackground(Color.decode("#97c162"));
		yesButton.setForeground(Color.WHITE);
		yesButton.setBorderPainted(false);
		yesButton.setFocusPainted(false);
		yesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		yesButton.setMaximumSize(new Dimension(100, 50));
		yesButton.addActionListener(e -> frame.showPanel("login"));
		yesButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				yesButton.setBackground(Color.decode("#7fa750"));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				yesButton.setBackground(Color.decode("#97c162"));
			}
		});

		centerPanel.add(logoLabel);
		centerPanel.add(messageLabel);
		centerPanel.add(yesButton);
		add(centerPanel, BorderLayout.CENTER);

		JLabel footerLabel = new JLabel("숙명여자대학교");
		footerLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 18)); // 폰트 변경
		footerLabel.setForeground(new Color(120, 80, 40, 150)); // 더 진한 갈색 + 약간 투명도
		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		footerPanel.setOpaque(false);
		footerPanel.add(footerLabel);
		add(footerPanel, BorderLayout.SOUTH);
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
