package backend;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Objects;

public class RoomSelection {
    private String room;                        // 강의실 이름 (예: "101호")
    private String buildingName;                // 건물 이름 (예: "명신관")
    private int capacity;                       // 수용 인원
    private Set<String> facilities;             // 강의실에 있는 시설들
    private boolean isStepped;                  // 계단형 여부
    private Map<DayOfWeek, List<TimeRange>> availableTimes; // 요일별 사용 가능 시간대
	private String imgPath = "/images/room.png"; 

    // 생성자
    public RoomSelection(String name, String building, int capacity, Set<String> facilities,
                         boolean isStepped, Map<DayOfWeek, List<TimeRange>> availableTimes) {
        this.room = name;
        this.buildingName = building;
        this.capacity = capacity;
        this.facilities = facilities;
        this.isStepped = isStepped;
        this.availableTimes = availableTimes;
    }

    // getter 메서드들
    public String getName() {
        return room;
    }

    public String getBuilding() {
        return buildingName;
    }

    public int getCapacity() {
        return capacity;
    }

    public Set<String> getFacilities() {
        return facilities;
    }

    public boolean isStepped() {
        return isStepped;
    }

    public Map<DayOfWeek, List<TimeRange>> getAvailableTimes() {
        return availableTimes;
    }

	public String getImgPath() {
		return imgPath;
	}

    // equals, hashCode: RoomSelection을 Map key로 사용할 수 있게 함
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomSelection)) return false;
        RoomSelection that = (RoomSelection) o;
        return Objects.equals(room, that.room) &&
                Objects.equals(buildingName, that.buildingName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, buildingName);
    }

    // 디버깅용 toString
    @Override
    public String toString() {
        return buildingName + " " + room + " (" + capacity + "명)";
    }
}
