package frontend;

import javax.swing.*;
import java.awt.*;
import frontend2.ElementSelectionPanel;
import backend.ReservationInputData;

public class MainFrame extends JFrame {
	public final ReservationInputData inputData = new ReservationInputData();
	private String selectedBuilding;

	private String studentId;

	public void setStudentId(String id) {
		this.studentId = id;
	}

	public String getStudentId() {
		return studentId;
	}

    CardLayout cardLayout;
    JPanel mainPanel;

    public MainFrame() {
        setTitle("맞춤형 강의실 예약 시스템");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 화면 패널들 추가
        mainPanel.add(new IntroPanel(this), "intro"); // 인트로 추가
        mainPanel.add(new StartPanel(this), "start"); // 첫 화면
        mainPanel.add(new LoginPanel(this), "login"); // 로그인
        mainPanel.add(new BuildingSelectionPanel(this), "building"); // 건물 선택
		mainPanel.add(new ElementSelectionPanel(this), "element"); // 🔥 이거 꼭 필요!


        add(mainPanel);
        setVisible(true);

 
       
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

	public void setSelectedBuilding(String name) {
    this.selectedBuilding = name;
	}

	public String getSelectedBuilding() {
    return selectedBuilding;
	}
}
