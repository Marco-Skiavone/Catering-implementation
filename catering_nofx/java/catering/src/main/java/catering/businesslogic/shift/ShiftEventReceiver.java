package catering.businesslogic.shift;

import java.util.*;

public interface ShiftEventReceiver {
    void updateShiftsCreated(ArrayList<AbstractShift> shifts);
    void updateShiftsModified(ArrayList<AbstractShift> shifts);
    void updateShiftsRemoved(ArrayList<AbstractShift> shifts);
}
