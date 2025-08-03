package backend;
// 사용자가 원하는 날짜를 선택하여 요일로 변환하고 조건에 저장하는 프로그램
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    // yyyy-MM-dd 형식 문자열을 LocalDate로 변환
    public static LocalDate parseDate(String input) {
        return LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // LocalDate로부터 요일을 한글 문자열로 반환
    public static String getKoreanDayOfWeek(LocalDate date) {
        return switch (date.getDayOfWeek()) {
            case MONDAY -> "월요일";
            case TUESDAY -> "화요일";
            case WEDNESDAY -> "수요일";
            case THURSDAY -> "목요일";
            case FRIDAY -> "금요일";
            case SATURDAY -> "토요일";
            case SUNDAY -> "일요일";
        };
    }
}
