package backend;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class ReservationInputData {
    public String studentId;
    public LocalDate date;
    public LocalTime start;
    public LocalTime end;
    public String building;
    public int minCapacity;
    public Set<String> requiredFacilities = new HashSet<>();
    public boolean isStepped;
}
