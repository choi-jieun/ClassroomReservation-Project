package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import backend.LoginValidator;
import backend.InvalidLoginIdException;

public class LoginPanel extends JPanel {

    private Image backgroundImage;

    public LoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        loadBackgroundImage("/images/polka_background.png");

        Font customFont = loadCustomFont("/images/ps.ttf", 32f); // 학번 문구용
        Font gothicFont = new Font("Malgun Gothic", Font.PLAIN, 18);
        Font gothicBold = new Font("Malgun Gothic", Font.BOLD, 20);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JLabel label = new JLabel("학번을 입력하세요:");
        label.setFont(customFont);
        label.setForeground(Color.decode("#4d4d4d"));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField idField = new JTextField();
        idField.setMaximumSize(new Dimension(300, 40));
        idField.setFont(gothicFont);

        JButton loginBtn = new JButton("로그인");
        loginBtn.setFont(customFont);
        loginBtn.setBackground(Color.decode("#97c162"));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(120, 40));

        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(Color.decode("#7fa750"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(Color.decode("#97c162"));
            }
        });

        loginBtn.addActionListener(e -> {
            String studentId = idField.getText().trim();
            if (!studentId.isEmpty()) {
                try {
                    LoginValidator.validateLoginId(studentId); // 학번 유효성 검사
					frame.setStudentId(studentId); // 로그인 성공 시 ID 저장
                    frame.showPanel("building"); // 성공 시 화면 전환
                } catch (InvalidLoginIdException ex) {
                    JOptionPane.showMessageDialog(LoginPanel.this, ex.getMessage(), "로그인 오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        centerPanel.add(label);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(idField);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(loginBtn);
        add(centerPanel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("숙명여자대학교");
        footerLabel.setFont(gothicFont);
        footerLabel.setForeground(new Color(120, 80, 40, 150)); // 갈색

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
}
