package backend;

import java.time.LocalTime;

public class TimeRange {
    private LocalTime start;
    private LocalTime end;

    public TimeRange(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    /**
     * 겹치는지 여부
     */
    public boolean overlaps(TimeRange other) {
        return !this.end.isBefore(other.start) && !this.start.isAfter(other.end);
    }

    /**
     * 다른 범위를 포함하는지 여부
     */
    public boolean contains(TimeRange other) {
        return !this.start.isAfter(other.start) && !this.end.isBefore(other.end);
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeRange)) return false;
        TimeRange that = (TimeRange) o;
        return start.equals(that.start) && end.equals(that.end);
    }

    @Override
    public int hashCode() {
        return start.hashCode() + 31 * end.hashCode();
    }
}
