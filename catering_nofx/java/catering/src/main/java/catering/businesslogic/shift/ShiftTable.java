package catering.businesslogic.shift;

/** Singleton class */
public class ShiftTable {

    private static ShiftTable shiftTable = null;

    private ShiftTable() {
        shiftTable = this;
    }

    public static ShiftTable getInstance() {
        if (shiftTable == null)
            shiftTable = new ShiftTable();
        return shiftTable;
    }
}
