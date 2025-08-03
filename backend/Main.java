package backend;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final ReservationInputData inputData = new ReservationInputData();

	public static List<RoomSelection> runReservationFlow(ReservationInputData data) throws WeekendException {
		List<RoomSelection> recommended = new ArrayList<>(); 

        try {
            System.out.println("🛠 [입력 데이터 확인]");
            System.out.println("건물: " + data.building);
            System.out.println("날짜: " + data.date);
            System.out.println("시간: " + data.start + " ~ " + data.end);
            System.out.println("인원: " + data.minCapacity);
            System.out.println("시설: " + data.requiredFacilities);
            System.out.println("계단형 여부: " + data.isStepped);
            System.out.println("학생 ID: " + data.studentId);

            List<RoomSelection> allRooms = RoomFileReader.readRoomsFromFile("roomtest2.txt");
            ReservationManager manager = new ReservationManager();

            DayOfWeek day = data.date.getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                System.out.println("주말에는 강의실 예약이 불가능합니다.");
                throw new WeekendException();
            }

            List<TimeRange> desiredTimes = List.of(new TimeRange(data.start, data.end));

            recommended = RoomRecommender.recommendRooms(
                allRooms,
                data.building,
                data.minCapacity,
                data.requiredFacilities,
                data.isStepped,
                desiredTimes,
                data.date,
                manager
            );

            if (recommended.isEmpty()) {
                System.out.println("추천 가능한 강의실이 없습니다.");
            } else {
                System.out.println("추천 강의실 목록:");
                for (RoomSelection room : recommended) {
                    System.out.println("- " + room.getName());
                }
            }

        } catch (WeekendException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("오류 발생:");
            e.printStackTrace();
        }
		return recommended;
    }
}
