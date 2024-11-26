package catering.businesslogic.shift;

import java.util.*;

/** Singleton class */
public class ShiftTable {
    private final ArrayList<AbstractShift> shiftList;
    private static ShiftTable shiftTable = null;

    private ShiftTable() {
        shiftTable = this;
        shiftList = new ArrayList<>();
    }

    public static ShiftTable getInstance() {
        if (shiftTable == null)
            shiftTable = new ShiftTable();
        return shiftTable;
    }

    public void addShift(AbstractShift shift) {
        // not in past UC's. Still to implement
        shiftList.add(shift);
    }

    public void removeShift(AbstractShift shift) {
        // not in past UC's. Still to implement
        if (!shiftList.remove(shift))
            throw new RuntimeException("Shift not found");
    }

    @Override
    public String toString() {
        StringBuilder shiftStr = new StringBuilder("\n");
        for (AbstractShift as : shiftList)
            shiftStr.append("\t").append(as.toString()).append("\n");
        return "ShiftTable [\n" + shiftStr + "]";
    }
}
