package com.kideveloper_dart.my_spring_web.dart.application;

import com.kideveloper_dart.my_spring_web.dart.application.dto.request.FinancialStatementsDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import com.kideveloper_dart.my_spring_web.dart.domain.column.ColumnHead;
import com.kideveloper_dart.my_spring_web.dart.domain.doctype.DocumentationType;
import com.kideveloper_dart.my_spring_web.dart.domain.row.RowHead;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DartDataParser {

    public List<Cell> parse(List<FinancialStatementsDTO> financialStatementsDTOS) {
        List<Cell> cells =new ArrayList<>();
        for (FinancialStatementsDTO financialStatementsDTO : financialStatementsDTOS) {
            if (DocumentationType.valueOf(financialStatementsDTO.getSj_div()) == DocumentationType.BS) {
                parseCells(financialStatementsDTO, cells);
            }
        }
        return cells;
    }

    private void parseCells(FinancialStatementsDTO dto, List<Cell> cells) {
//        final String THIS_TERM_KEY = "thisTerm";
//        final String FROM_TERM_KEY = "thisTerm";
//        final String BEFORE_TERM_KEY = "thisTerm";

        Cell thisTermCell = Cell.getCellByDTO(dto, "thisTerm");
        thisTermCell.setColumnHead(ColumnHead.getColumnHeadByDTO(dto, "thisTerm"));
        thisTermCell.setRowHead(RowHead.getRowHeadByDTO(dto,"thisTerm"));

        Cell fromTermCell = Cell.getCellByDTO(dto, "fromTerm");
        fromTermCell.setColumnHead(ColumnHead.getColumnHeadByDTO(dto, "fromTerm"));
        fromTermCell.setRowHead(RowHead.getRowHeadByDTO(dto,"fromTerm"));

        Cell beforeFromTermCell = Cell.getCellByDTO(dto, "beforeFromTerm");
        beforeFromTermCell.setColumnHead(ColumnHead.getColumnHeadByDTO(dto, "beforeFromTerm"));
        beforeFromTermCell.setRowHead(RowHead.getRowHeadByDTO(dto,"beforeFromTerm"));

        cells.add(thisTermCell);
        cells.add(fromTermCell);
        cells.add(beforeFromTermCell);
    }

}
