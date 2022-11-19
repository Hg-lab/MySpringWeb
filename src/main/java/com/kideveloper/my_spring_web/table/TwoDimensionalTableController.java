package com.kideveloper.my_spring_web.table;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.kideveloper.my_spring_web.table.ColumnCellManager.*;

@Controller
@RequestMapping("/2d-table")
public class TwoDimensionalTableController {

    @GetMapping
    private String getTable(Model model) {

        Column column1 = new Column("column1");
        Column column2 = new Column("column2");
        Column column3 = new Column("column3");
        List<Column> columns = Arrays.asList(new Column[]{column1, column2, column3});

        Row row1 = new Row("row1");
        Row row2 = new Row("row2");
        Row row3 = new Row("row3");

        //row1
        Cell cell11 = new Cell("17050");
        Cell cell12 = new Cell("950");
        Cell cell13 = new Cell("24000");

        //row2
        Cell cell21 = new Cell("24000");
        Cell cell22 = new Cell("6000");
        Cell cell23 = new Cell("30000");

        //row2
        Cell cell31 = new Cell("20000");
        Cell cell32 = new Cell("5000");
        Cell cell33 = new Cell("25000");

        ColumnCellMap columnCellMapOfRow1 = createColumnCellMap();
        ColumnCellMap columnCellMapOfRow2 = createColumnCellMap();
        ColumnCellMap columnCellMapOfRow3 = createColumnCellMap();


        columnCellMapOfRow1.put(column1, cell11);
        columnCellMapOfRow1.put(column2, cell12);
        columnCellMapOfRow1.put(column3, cell13);

        columnCellMapOfRow2.put(column1, cell21);
        columnCellMapOfRow2.put(column2, cell22);
        columnCellMapOfRow2.put(column3, cell23);

        columnCellMapOfRow3.put(column1, cell31);
        columnCellMapOfRow3.put(column2, cell32);
        columnCellMapOfRow3.put(column3, cell33);


        RowCellManager.RowColumnCellMap rowColumnCellMap = RowCellManager.createRowColumnCellMap();
        rowColumnCellMap.put(row1, columnCellMapOfRow1);
        rowColumnCellMap.put(row2, columnCellMapOfRow2);
        rowColumnCellMap.put(row3, columnCellMapOfRow3);



        model.addAttribute("columns", columns);
        model.addAttribute("rows", rowColumnCellMap);

        return "table/2d-table";
    }
}
