package com.kideveloper_dart.my_spring_web.table;

import java.util.LinkedHashMap;

public class RowCellManager {

    public static class RowColumnCellMap extends LinkedHashMap<Object, Object> {
    }

    public static RowColumnCellMap createRowColumnCellMap() {
        return new RowCellManager.RowColumnCellMap();
    }
}
