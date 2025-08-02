//완벽, but 뷰포트 안됨. 
package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.imageio.ImageIO;

public class Final {

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("src/frontend/background.png"));
            int scaledWidth = originalImage.getWidth() / 2;
            int scaledHeight = originalImage.getHeight() / 2;
            Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

            Font psFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/frontend/ps.ttf")).deriveFont(24f);
            Font titleFont = psFont.deriveFont(Font.BOLD, 28f);
            Font labelFont = psFont.deriveFont(Font.PLAIN, 24f);

            JFrame frame = new JFrame("맞춤형 강의실 예약 시스템");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new BackgroundPanel(scaledImage));
            frame.setLayout(null);

            int centerX = frame.getWidth() / 2;

            JLabel titleLabel = new JLabel("강의실 필터링", SwingConstants.CENTER);
            titleLabel.setFont(titleFont);
            titleLabel.setBounds(centerX - 300, 10, 600, 50);
            titleLabel.setOpaque(false);
            frame.add(titleLabel);

            JLabel dateLabel = new JLabel("사용일자:", SwingConstants.RIGHT);
            dateLabel.setFont(labelFont);
            dateLabel.setBounds(centerX - 300, 80, 150, 30);
            dateLabel.setOpaque(false);
            frame.add(dateLabel);

            SpinnerDateModel dateModel = new SpinnerDateModel();
            dateModel.setStart(getMinDate());

            JSpinner dateSpinner = new JSpinner(dateModel);
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy/MM/dd");
            dateSpinner.setEditor(dateEditor);
            dateSpinner.setBounds(centerX - 130, 80, 180, 30);
            dateSpinner.setOpaque(false);

            JTextField dateTextField = dateEditor.getTextField();
            dateTextField.setHorizontalAlignment(JTextField.CENTER);
            dateTextField.setFont(psFont.deriveFont(20f));
            dateTextField.setOpaque(false);
            frame.add(dateSpinner);

            JLabel timeLabel = new JLabel("사용시간:", SwingConstants.RIGHT);
            timeLabel.setFont(labelFont);
            timeLabel.setBounds(centerX - 300, 130, 150, 30);
            timeLabel.setOpaque(false);
            frame.add(timeLabel);

            String[] hours = generateHalfHourTimes(5, 21);
            JComboBox<String> startTimeBox = new JComboBox<>(hours);
            JComboBox<String> endTimeBox = new JComboBox<>(hours);
            startTimeBox.setFont(psFont.deriveFont(20f));
            endTimeBox.setFont(psFont.deriveFont(20f));
            startTimeBox.setBounds(centerX - 130, 130, 80, 30);
            endTimeBox.setBounds(centerX - 20, 130, 80, 30);
            startTimeBox.setOpaque(false);
            endTimeBox.setOpaque(false);

            JLabel tilde = new JLabel("~", SwingConstants.CENTER);
            tilde.setFont(labelFont);
            tilde.setBounds(centerX - 45, 130, 20, 30);
            tilde.setOpaque(false);

            frame.add(startTimeBox);
            frame.add(tilde);
            frame.add(endTimeBox);

            JLabel peopleLabel = new JLabel("사용인원:", SwingConstants.RIGHT);
            peopleLabel.setFont(labelFont);
            peopleLabel.setBounds(centerX - 300, 180, 150, 30);
            peopleLabel.setOpaque(false);
            frame.add(peopleLabel);

            JTextField peopleField = new JTextField();
            peopleField.setHorizontalAlignment(JTextField.CENTER);
            peopleField.setFont(psFont.deriveFont(20f));
            peopleField.setBounds(centerX - 130, 180, 80, 30);
            peopleField.setOpaque(false);
            frame.add(peopleField);

            JLabel unitLabel = new JLabel("명", SwingConstants.CENTER);
            unitLabel.setFont(labelFont);
            unitLabel.setBounds(centerX - 45, 180, 30, 30);
            unitLabel.setOpaque(false);
            frame.add(unitLabel);

            JLabel featureLabel = new JLabel("요소선택:", SwingConstants.RIGHT);
            featureLabel.setFont(labelFont);
            featureLabel.setBounds(centerX - 300, 230, 150, 30);
            featureLabel.setOpaque(false);
            frame.add(featureLabel);

            JCheckBox[] checkBoxes = {
                new JCheckBox("화이트보드"),
                new JCheckBox("콘센트"),
                new JCheckBox("전자교탁+마이크+빔프로젝터(세트)"),
                new JCheckBox("계단형 강의실")
            };

            JPanel checkboxPanel = new JPanel();
            checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
            checkboxPanel.setBounds(centerX - 130, 230, 500, 200);
            checkboxPanel.setOpaque(false);

            for (JCheckBox cb : checkBoxes) {
                cb.setFont(labelFont);
                cb.setOpaque(false);
                checkboxPanel.add(cb);
                checkboxPanel.add(Box.createVerticalStrut(5));
            }

            frame.add(checkboxPanel);

            JButton searchButton = new JButton("강의실 검색");
            searchButton.setFont(labelFont);
            searchButton.setForeground(Color.WHITE);
            searchButton.setBounds(centerX - 100, 450, 200, 40);
            searchButton.setBackground(Color.decode("#97c162"));
            searchButton.setFocusPainted(false);
            frame.add(searchButton);

            JLabel footerLabel = new JLabel("숙명여자대학교");
            footerLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
            footerLabel.setForeground(new Color(120, 80, 40, 150));
            footerLabel.setBounds(600, 520, 160, 30);
            footerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            footerLabel.setOpaque(false);
            frame.add(footerLabel);

            frame.setVisible(true);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    static class BackgroundPanel extends JPanel {
        private final Image background;

        public BackgroundPanel(Image image) {
            this.background = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int imgWidth = background.getWidth(this);
            int imgHeight = background.getHeight(this);

            for (int x = 0; x < getWidth(); x += imgWidth) {
                for (int y = 0; y < getHeight(); y += imgHeight) {
                    g.drawImage(background, x, y, this);
                }
            }
        }
    }

    private static String[] generateHalfHourTimes(int startHour, int endHour) {
        java.util.List<String> times = new java.util.ArrayList<>();
        for (int h = startHour; h <= endHour; h++) {
            times.add(String.format("%02d:00", h));
            if (h != endHour) {
                times.add(String.format("%02d:30", h));
            }
        }
        return times.toArray(new String[0]);
    }

    private static java.util.Date getMinDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MAY, 1);
        return cal.getTime();
    }
}