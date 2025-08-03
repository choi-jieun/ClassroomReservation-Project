package backend;

import java.io.*;
import java.time.*;
import java.util.*;
import java.nio.charset.StandardCharsets;


public class RoomFileReader {
    public static List<RoomSelection> readRoomsFromFile(String filename) throws IOException {
        List<RoomSelection> rooms = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.isBlank()) continue;

            String[] mainParts = line.split("!", 2);
            String[] headerParts = mainParts[0].split(",", 5);

            String building = headerParts[0].trim();
            String room = headerParts[1].trim();
            int capacity = Integer.parseInt(headerParts[2].trim());
            boolean isStepped = Boolean.parseBoolean(headerParts[3].trim());

            Set<String> facilities = new HashSet<>();
            if (!headerParts[4].isBlank()) {
                facilities.addAll(Arrays.asList(headerParts[4].split(";")));
            }

            Map<DayOfWeek, List<TimeRange>> availableTimes = new HashMap<>();

            if (mainParts.length > 1) {
                String[] timeParts = mainParts[1].split("!");
                for (String part : timeParts) {
                    int openParen = part.indexOf('(');
                    int closeParen = part.indexOf(')');
                    if (openParen == -1 || closeParen == -1) continue;

                    String dayStr = part.substring(0, openParen).toLowerCase();
                    String timeRanges = part.substring(openParen + 1, closeParen).trim();
                    if (timeRanges.isEmpty()) continue;

                    DayOfWeek day = mapDayStringToEnum(dayStr);
                    // ✅ 토요일, 일요일 제외
                    if (day == null || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) continue;

                    List<TimeRange> timeRangeList = new ArrayList<>();
                    for (String range : timeRanges.split("/")) {
                        String[] times = range.trim().split("-");
                        if (times.length == 2) {
                            LocalTime start = LocalTime.parse(times[0].trim());
                            LocalTime end = LocalTime.parse(times[1].trim());
                            timeRangeList.add(new TimeRange(start, end));
                        }
                    }

                    if (!timeRangeList.isEmpty()) {
                        availableTimes.put(day, timeRangeList);
                    }
                }
            }

            RoomSelection roomObj = new RoomSelection(room, building, capacity, facilities, isStepped, availableTimes);
            rooms.add(roomObj);
        }

        reader.close();
        return rooms;
    }

    private static DayOfWeek mapDayStringToEnum(String str) {
        switch (str.toLowerCase()) {
            case "mon": return DayOfWeek.MONDAY;
            case "tue": return DayOfWeek.TUESDAY;
            case "wed": return DayOfWeek.WEDNESDAY;
            case "thu": return DayOfWeek.THURSDAY;
            case "fri": return DayOfWeek.FRIDAY;
            case "sat": return DayOfWeek.SATURDAY;
            case "sun": return DayOfWeek.SUNDAY;
            default: return null;
        }
    }
}
