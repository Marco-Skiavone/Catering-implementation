package catering.businesslogic.shift;

import catering.businesslogic.user.Staff;
import catering.businesslogic.user.AbstractUser;

import java.time.*;
import java.util.*;

public abstract class AbstractShift {
    /** To have a shift Collection */
    private static final Map<Integer, AbstractShift> shiftList = new HashMap<>();

    protected int id;
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected Period daysToRetire;
    protected HashMap<Integer, AbstractShift> availabilityGroup;
    protected ArrayList<Staff> availableWorkers;

    public abstract String getLocation();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate( ) {
        return start;
    }

    public DayOfWeek getDayOfWeek( ) {
        return start.getDayOfWeek();
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Period getDaysToRetire() {
        return daysToRetire;
    }

    public void setDaysToRetire(Period daysToRetire) {
        this.daysToRetire = daysToRetire;
    }

    public void setAvailabilityGroup(HashMap<Integer, AbstractShift> availabilityGroup) {
        this.availabilityGroup = availabilityGroup;
    }

    public void setAvailableWorkers(ArrayList<Staff> availableWorkers) {
        this.availableWorkers = availableWorkers;
    }

    public boolean isWorkerAvailableIn(AbstractUser usr) {
        if (availableWorkers == null || !usr.isStaff())
            return false;
        return availableWorkers.contains(usr);
    }

    /** You should call this method from a lower-class. */
    public AbstractShift copy() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void setAs(AbstractShift shift) {
        // missing implementation
    }
}
