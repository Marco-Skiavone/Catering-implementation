package catering.businesslogic.shift;

import java.util.*;

public class ShiftManager {
    private final ShiftTable shiftTable;
    private final HashSet<ShiftEventReceiver> eventReceivers;

    public ShiftManager() {
        shiftTable = ShiftTable.getInstance();
        eventReceivers = new HashSet<>();
    }

    // --- Operation methods ---

    public ShiftTable getShiftTable() {
        return shiftTable;
    }

    public void addShift(AbstractShift shift) {
        if (shift == null)
            throw new NullPointerException("Shift cannot be null");
        shiftTable.addShift(shift);
    }

    public void removeShift(AbstractShift shift) {
        if (shift == null)
            throw new NullPointerException("Shift cannot be null");
        shiftTable.removeShift(shift);
    }

    // --- Event sender Methods ---

    /** @throws IllegalArgumentException if argument passed is null. */
    public void addEventReceiver(ShiftEventReceiver er) {
        if (er == null) throw new IllegalArgumentException("Null receiver passed!");
        eventReceivers.add(er);
    }

    /** @throws IllegalArgumentException if argument passed is null or not present in the set. */
    public void removeEventReceiver(ShiftEventReceiver er) {
        if (er == null) throw new IllegalArgumentException("Null receiver passed!");
        if (!eventReceivers.remove(er))
            throw new IllegalArgumentException("Receiver does not exist!");
    }

    private void notifyShiftsCreated(ArrayList<AbstractShift> shifts) {
        eventReceivers.forEach(er -> er.updateShiftsCreated(shifts));
    }

    private void notifyShiftsModified(ArrayList<AbstractShift> shifts) {
        eventReceivers.forEach(er -> er.updateShiftsModified(shifts));
    }

    private void notifyShiftsRemoved(ArrayList<AbstractShift> shifts) {
        eventReceivers.forEach(er -> er.updateShiftsRemoved(shifts));
    }
}
