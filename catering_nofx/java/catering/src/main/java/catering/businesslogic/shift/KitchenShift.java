package catering.businesslogic.shift;

import java.time.*;
import java.util.*;

public class KitchenShift extends AbstractShift {
    private String location;

    public  KitchenShift(LocalDateTime start, LocalDateTime end, Period daysToRet, String location) {
        super.start = start;
        super.end = end;
        super.daysToRetire = daysToRet;
        this.location = location;   // can be null
        super.availableWorkers = new ArrayList<>();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "KitchenShift (#" + id + ", @: '" + location + "', s: " + start +
                ", end: " + end + ", availableWorkers: " + availableWorkers.toString() + ')';
    }
}
