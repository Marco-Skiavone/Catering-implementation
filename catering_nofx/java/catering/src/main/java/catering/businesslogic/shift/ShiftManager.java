package catering.businesslogic.shift;

public class ShiftManager {
    private final ShiftTable shiftTable;

    public ShiftManager() {
        shiftTable = ShiftTable.getInstance();
    }

    public ShiftTable getShiftTable() {
        return shiftTable;
    }
}
