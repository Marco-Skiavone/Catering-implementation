package catering.businesslogic.shift;

import java.time.*;

public class KitchenShift extends AbstractShift {
    private String location;

    public  KitchenShift(LocalDateTime start, LocalDateTime end, Period daysToRet, String location) {
        super.start = start;
        super.end = end;
        super.daysToRetire = daysToRet;
        this.location = location;   // can be null
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
