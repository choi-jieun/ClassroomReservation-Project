package backend;

public class WeekendException extends Exception {
    public WeekendException() {
        super("주말에는 강의실 예약이 불가능합니다.");
    }

    public WeekendException(String message) {
        super(message);
    }
}
