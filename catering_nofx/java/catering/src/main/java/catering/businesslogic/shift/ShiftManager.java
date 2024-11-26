package catering.businesslogic.shift;

import catering.persistence.*;
import java.sql.*;
import java.time.*;
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
        updateFakeShiftTable();
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

    public ArrayList<AbstractShift> getShifts() {
        return new ArrayList<>(shiftTable.getShiftList());
    }

    private void updateFakeShiftTable() {
        String queryStr = "SELECT * FROM catering.Shifts";
        PersistenceManager.executeQuery(queryStr, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                AbstractShift as;
                if (rs.getString("type").equals("c")) {
                    as = new KitchenShift(
                            LocalDateTime.of(rs.getDate("start").toLocalDate(),
                                    LocalTime.now()),
                            LocalDateTime.of(rs.getDate("end").toLocalDate(),
                                    LocalTime.now()),
                            Period.ZERO, "Terabithia");
                } else
                    as = new ServiceShift(null, 1580L,200L, Period.ZERO);
                as.setId(rs.getInt("id"));
                addShift(as);
            }
        });
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
