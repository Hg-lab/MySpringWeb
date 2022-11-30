package com.kideveloper_dart.my_spring_web.dart.application;

import com.kideveloper_dart.my_spring_web.dart.application.dto.request.FinancialStatementsDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import com.kideveloper_dart.my_spring_web.dart.domain.doctype.DocumentationType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DartDataParser {

    public List<Cell> parse(List<FinancialStatementsDTO> financialStatementsDTOS) {
        List<Cell> cells =new ArrayList<>();
        for (FinancialStatementsDTO financialStatementsDTO : financialStatementsDTOS) {
            if (DocumentationType.valueOf(financialStatementsDTO.getSj_div()) == DocumentationType.BS) {
                Cell cell = Cell.getCellByDTO(financialStatementsDTO);
                cells.add(cell);
            }
        }
        return cells;
    }

}
