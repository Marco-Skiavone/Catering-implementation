package catering.businesslogic.shift;

import catering.businesslogic.*;
import java.util.*;

/** Singleton class */
public class ShiftTable {
    private ArrayList<AbstractShift> shiftList;
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



    @Override
    public String toString() {
        return "ShiftTable{}";
    }
}
