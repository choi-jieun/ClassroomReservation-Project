package backend;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RoomRecommender {

    public static List<RoomSelection> recommendRooms(
            List<RoomSelection> allRooms,
            String buildingName,
            int minCapacity,
            Set<String> requiredFacilities,
            boolean requireStepped,
            List<TimeRange> desiredTimeRanges,
            LocalDate targetDate,
            ReservationManager manager) {

        DayOfWeek dayOfWeek = targetDate.getDayOfWeek();

        return allRooms.stream()
                .filter(room -> roomMatches(
                        room,
                        buildingName,
                        minCapacity,
                        requiredFacilities,
                        requireStepped,
                        dayOfWeek,
                        desiredTimeRanges,
                        targetDate,
                        manager
                ))
                .collect(Collectors.toList());
    }

    private static boolean roomMatches(
            RoomSelection room,
            String buildingName,
            int minCapacity,
            Set<String> requiredFacilities,
            boolean requireStepped,
            DayOfWeek dayOfWeek,
            List<TimeRange> desiredTimeRanges,
            LocalDate date,
            ReservationManager manager) {

        // 건물 이름
        if (!room.getBuilding().equals(buildingName)) return false;

        // 수용 인원
        if (room.getCapacity() < minCapacity) return false;

        // 계단형 여부
        if (requireStepped && !room.isStepped()) return false;

        // 시설 조건
        if (!room.getFacilities().containsAll(requiredFacilities)) return false;

        // 시간대 조건 (요일별 사용 가능 여부)
        List<TimeRange> available = room.getAvailableTimes().getOrDefault(dayOfWeek, new ArrayList<>());
        for (TimeRange desired : desiredTimeRanges) {
            boolean fits = available.stream().anyMatch(avail -> avail.contains(desired));
            if (!fits) return false; // 사용 가능 시간 안에 없음
        }

        // 예약 가능 여부
        return manager.isAvailable(room, date, desiredTimeRanges);
    }
}