package frontend;

import backend.RoomSelection;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class RecommendPanel extends BackgroundPanel {
    private JPanel roomListPanel;

    public RecommendPanel(CardLayout layout, JPanel cardPanel, List<RoomSelection> filteredRooms) {
        super("/image/background.jpg");

        setLayout(new BorderLayout());

        JLabel title = new JLabel("추천 강의실 목록", SwingConstants.CENTER);
        title.setFont(FontUtil.getPsFont(26f));
        title.setForeground(Color.decode("#4d4d4d"));
        add(title, BorderLayout.NORTH);

        roomListPanel = new JPanel();
        roomListPanel.setLayout(new BoxLayout(roomListPanel, BoxLayout.Y_AXIS));
        roomListPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        roomListPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(roomListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

        // 초기 추천 결과 출력
        setRoomList(filteredRooms);
    }

    public void setRoomList(List<RoomSelection> rooms) {
        roomListPanel.removeAll();

        for (RoomSelection room : rooms) {
            String imgPath = room.getImgPath() != null ? room.getImgPath() : "/image/room.png";
            ImageIcon icon = createScaledIcon(imgPath, 80, 80);

            JButton roomButton = new JButton(room.getBuilding() + " " + room.getName(), icon);
            roomButton.setPreferredSize(new Dimension(0, 100));
            roomButton.setFont(FontUtil.getPsFont(20f));
            roomButton.setBackground(Color.decode("#e6f4ea"));
            roomButton.setForeground(Color.decode("#4d4d4d"));
            roomButton.setFocusPainted(false);
            roomButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            roomButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            roomButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            roomButton.setHorizontalAlignment(SwingConstants.LEFT);
            roomButton.setIconTextGap(20);

            roomButton.addActionListener(e -> {
                Room0 room0 = convertToRoom0(room);
                ((MainFrame1) SwingUtilities.getWindowAncestor(this)).changePanel("상세", room0);
            });

            roomListPanel.add(roomButton);
            roomListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        roomListPanel.revalidate();
        roomListPanel.repaint();
    }

    private ImageIcon createScaledIcon(String path, int width, int height) {
        ImageIcon icon;
        try {
            Image img = new ImageIcon(getClass().getResource(path)).getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);
        } catch (Exception e) {
            icon = new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        }
        return icon;
    }

    private Room0 convertToRoom0(RoomSelection room) {
        boolean hasWhiteboard = room.getFacilities().contains("화이트보드");
        boolean hasOutlet = room.getFacilities().contains("콘센트");
        boolean hasMic = room.getFacilities().contains("전자교탁")
                       || room.getFacilities().contains("마이크")
                       || room.getFacilities().contains("빔");

        return new Room0(
            room.getName(),
            room.getImgPath() != null ? room.getImgPath() : "/image/default_room.jpg",
            hasWhiteboard,
            hasOutlet,
            hasMic,
            room.isStepped()
        );
    }
}
