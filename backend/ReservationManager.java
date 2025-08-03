package backend;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class ReservationManager {

    private Map<RoomSelection, Map<LocalDate, List<TimeRange>>> reservations = new HashMap<>();
    private Map<String, List<ReservationRecord>> userReservations = new HashMap<>();

    public boolean isAvailable(RoomSelection room, LocalDate date, List<TimeRange> requestedRanges) {
        DayOfWeek day = date.getDayOfWeek();
        Map<DayOfWeek, List<TimeRange>> available = room.getAvailableTimes();

        for (TimeRange requested : requestedRanges) {
            boolean within = available.getOrDefault(day, List.of()).stream()
                    .anyMatch(a -> a.contains(requested));
            if (!within) return false;
        }

        if (reservations.containsKey(room) && reservations.get(room).containsKey(date)) {
            for (TimeRange existing : reservations.get(room).get(date)) {
                for (TimeRange requested : requestedRanges) {
                    if (requested.overlaps(existing)) return false;
                }
            }
        }

        return true;
    }

    // 예약 메서드에 주말 예외 로직 포함
    public void reserve(String studentId, RoomSelection room, LocalDate date, List<TimeRange> timeRanges)
            throws WeekendException {

        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            throw new WeekendException();
        }

        if (!isAvailable(room, date, timeRanges)) {
            throw new IllegalStateException("이미 예약된 시간입니다.");
        }

        reservations.putIfAbsent(room, new HashMap<>());
        reservations.get(room).putIfAbsent(date, new ArrayList<>());
        reservations.get(room).get(date).addAll(timeRanges);

        ReservationRecord record = new ReservationRecord(studentId, room, date, timeRanges);
        userReservations.putIfAbsent(studentId, new ArrayList<>());
        userReservations.get(studentId).add(record);
    }


    public List<ReservationRecord> getReservationsByStudentId(String studentId) {
        return userReservations.getOrDefault(studentId, new ArrayList<>());
    }
}
