package frontend;

import backend.RoomSelection;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class MainFrame1 extends JFrame {
    private CardLayout layout;
    private JPanel cards;
    private RecommendPanel recommendPanel;

    public MainFrame1() {
        setTitle("맞춤형 강의실 예약 시스템");
        setSize(800, 600);
        getContentPane().setBackground(Color.decode("#feedb7"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        cards = new JPanel(layout);
        cards.setBackground(Color.decode("#feedb7"));

        //  recommendPanel을 한 번만 생성하고 변수와 cards에 함께 등록
        recommendPanel = new RecommendPanel(layout, cards, new ArrayList<>());
        cards.add(recommendPanel, "추천");

        cards.add(new DetailPanel(layout, cards), "상세");
        cards.add(new CompletePanel(layout, cards), "완료");

        add(cards);
        layout.show(cards, "추천");

        setVisible(true);
    }

    public void showFilteredRooms(List<RoomSelection> filteredRooms) {
        recommendPanel.setRoomList(filteredRooms);
        layout.show(cards, "추천");
    }

    public void changePanel(String name, Room0 room) {
        if ("상세".equals(name)) {
            for (Component comp : cards.getComponents()) {
                if (comp instanceof DetailPanel) {
                    ((DetailPanel) comp).setRoom(room);
                }
            }
        }
        layout.show(cards, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame1::new);
    }
}
