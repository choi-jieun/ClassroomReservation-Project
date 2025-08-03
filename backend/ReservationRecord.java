package backend;

import java.time.LocalDate;
import java.util.List;

public class ReservationRecord {

    private String studentId;
    private RoomSelection room;
    private LocalDate date;
    private List<TimeRange> timeRanges;

    public ReservationRecord(String studentId, RoomSelection room, LocalDate date, List<TimeRange> timeRanges) {
        this.studentId = studentId;
        this.room = room;
        this.date = date;
        this.timeRanges = timeRanges;
    }

    public String getStudentId() { return studentId; }
    public RoomSelection getRoom() { return room; }
    public LocalDate getDate() { return date; }
    public List<TimeRange> getTimeRanges() { return timeRanges; }

    @Override
    public String toString() {
        return studentId + ": " + room.getName() + " (" + room.getBuilding() + "), 날짜: " + date + ", 시간: " + timeRanges;
    }
}
