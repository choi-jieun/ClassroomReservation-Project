package frontend2;

import backend.RoomSelection;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import frontend.MainFrame;

public class ElementSelectionPanel extends JPanel {

    private Font customFont;
    private Image backgroundImage;

    public ElementSelectionPanel(MainFrame frame) {

        setLayout(null);
        setOpaque(false);
        loadBackgroundImage("/images/background.png");
        customFont = loadCustomFont("/images/ps.ttf", 24f);

        int centerX = 400;

        JLabel titleLabel = new JLabel("강의실 필터링", SwingConstants.CENTER);
        titleLabel.setFont(customFont.deriveFont(Font.BOLD, 28f));
        titleLabel.setBounds(centerX - 300, 10, 600, 50);
        add(titleLabel);

        JLabel dateLabel = new JLabel("사용일자:", SwingConstants.RIGHT);
        dateLabel.setFont(customFont);
        dateLabel.setBounds(centerX - 300, 80, 150, 30);
        add(dateLabel);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateModel.setStart(getMinDate());

        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy/MM/dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setBounds(centerX - 130, 80, 180, 30);
        JTextField dateTextField = dateEditor.getTextField();
        dateTextField.setHorizontalAlignment(JTextField.CENTER);
        dateTextField.setFont(customFont.deriveFont(20f));
        add(dateSpinner);

        JLabel timeLabel = new JLabel("사용시간:", SwingConstants.RIGHT);
        timeLabel.setFont(customFont);
        timeLabel.setBounds(centerX - 300, 130, 150, 30);
        add(timeLabel);

        String[] hours = generateHalfHourTimes(5, 21);
        JComboBox<String> startTimeBox = new JComboBox<>(hours);
        JComboBox<String> endTimeBox = new JComboBox<>(hours);
        startTimeBox.setFont(customFont.deriveFont(20f));
        endTimeBox.setFont(customFont.deriveFont(20f));
        startTimeBox.setBounds(centerX - 130, 130, 80, 30);
        endTimeBox.setBounds(centerX - 20, 130, 80, 30);

        JLabel tilde = new JLabel("~", SwingConstants.CENTER);
        tilde.setFont(customFont);
        tilde.setBounds(centerX - 45, 130, 20, 30);

        add(startTimeBox);
        add(tilde);
        add(endTimeBox);

        JLabel peopleLabel = new JLabel("사용인원:", SwingConstants.RIGHT);
        peopleLabel.setFont(customFont);
        peopleLabel.setBounds(centerX - 300, 180, 150, 30);
        add(peopleLabel);

        JTextField peopleField = new JTextField();
        peopleField.setHorizontalAlignment(JTextField.CENTER);
        peopleField.setFont(customFont.deriveFont(20f));
        peopleField.setBounds(centerX - 130, 180, 80, 30);
        add(peopleField);

        JLabel unitLabel = new JLabel("명", SwingConstants.CENTER);
        unitLabel.setFont(customFont);
        unitLabel.setBounds(centerX - 45, 180, 30, 30);
        add(unitLabel);

        JLabel featureLabel = new JLabel("요소선택:", SwingConstants.RIGHT);
        featureLabel.setFont(customFont);
        featureLabel.setBounds(centerX - 300, 230, 150, 30);
        add(featureLabel);

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
            cb.setFont(customFont.deriveFont(20f));
            cb.setOpaque(false);
            checkboxPanel.add(cb);
            checkboxPanel.add(Box.createVerticalStrut(5));
        }

        add(checkboxPanel);

        JButton searchButton = new JButton("강의실 검색");
        searchButton.setFont(customFont);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBounds(centerX - 100, 450, 200, 40);
        searchButton.setBackground(Color.decode("#97c162"));
        searchButton.setFocusPainted(false);
        add(searchButton);

		searchButton.addActionListener(e -> {
			try {
				// 백엔드에서 추천 리스트 가져오기
				List<RoomSelection> result = backend.Main.runReservationFlow(frame.inputData);

				if (result.isEmpty()) {
					JOptionPane.showMessageDialog(this, "추천 가능한 강의실이 없습니다.");
					return;
				}

				// front3 RecommendPanel이 있는 MainFrame1로 화면 전환
				SwingUtilities.invokeLater(() -> {
					frontend.MainFrame1 window = new frontend.MainFrame1();
					window.showFilteredRooms(result);
				});

			} catch (backend.WeekendException we) {
				JOptionPane.showMessageDialog(this, "주말에는 강의실 예약이 불가능합니다.");
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "오류 발생");
			}
		});

        JLabel footerLabel = new JLabel("숙명여자대학교");
        footerLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        footerLabel.setForeground(new Color(0, 0, 255, 120));
        footerLabel.setBounds(600, 520, 160, 30);
        footerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(footerLabel);

        searchButton.addActionListener(e -> {
            String date = dateTextField.getText();
            String StartTime = (String) startTimeBox.getSelectedItem();
            String EndTime = (String) endTimeBox.getSelectedItem();

            int PeopleNumber;
            try {
                PeopleNumber = Integer.parseInt(peopleField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "사용인원은 숫자로 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            java.util.Set<String> selectedFeatures = new java.util.HashSet<>();
            for (JCheckBox cb : checkBoxes) {
                if (cb.isSelected()) {
                    selectedFeatures.add(cb.getText());
                }
            }

            try {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd");
                frame.inputData.studentId = frame.getStudentId();
                frame.inputData.date = java.time.LocalDate.parse(date, formatter);
                frame.inputData.start = java.time.LocalTime.parse(StartTime);
                frame.inputData.end = java.time.LocalTime.parse(EndTime);
                frame.inputData.minCapacity = PeopleNumber;
                frame.inputData.requiredFacilities = selectedFeatures;
                frame.inputData.isStepped = selectedFeatures.contains("계단형 강의실");

                backend.Main.runReservationFlow(frame.inputData);

            } catch (backend.WeekendException we) {
                JOptionPane.showMessageDialog(this, "주말에는 강의실 예약이 불가능합니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
                return;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "입력 형식 오류입니다.", "오류", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                return;
            }

            // 확인용 출력
            System.out.println("사용일자: " + date);
            System.out.println("시작시간: " + StartTime);
            System.out.println("종료시간: " + EndTime);
            System.out.println("사용인원: " + PeopleNumber);
            System.out.println("선택한 요소: " + selectedFeatures);
        });
    }

    private void loadBackgroundImage(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            backgroundImage = ImageIO.read(is);
        } catch (Exception e) {
            System.err.println("배경 이미지 로딩 실패: " + path);
        }
    }

    private Font loadCustomFont(String path, float size) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
        } catch (Exception e) {
            return new Font("Malgun Gothic", Font.PLAIN, (int) size);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int imgWidth = backgroundImage.getWidth(this);
            int imgHeight = backgroundImage.getHeight(this);
            for (int x = 0; x < getWidth(); x += imgWidth) {
                for (int y = 0; y < getHeight(); y += imgHeight) {
                    g.drawImage(backgroundImage, x, y, this);
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
