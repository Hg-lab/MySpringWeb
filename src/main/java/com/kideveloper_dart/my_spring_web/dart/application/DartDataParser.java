package com.kideveloper_dart.my_spring_web.dart.application;

import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import com.kideveloper_dart.my_spring_web.dart.domain.column.ColumnHead;
import com.kideveloper_dart.my_spring_web.dart.domain.doctype.DocumentationType;
import com.kideveloper_dart.my_spring_web.dart.domain.row.RowHead;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.response.APIFinStatsDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DartDataParser {
    private static final String THIS_TERM_KEY = "thisTerm";
    private static final String FROM_TERM_KEY = "fromTerm";
    private static final String BEFORE_FROM_TERM_KEY = "beforeFromTerm";

    private Integer thisTerm;
    private Map<String, Integer> termMap = new HashMap<>();

    private List<Cell> cells = new ArrayList<>();
    private Set<ColumnHead> columns = new HashSet<>();
    private Set<RowHead> rows = new HashSet<>();

    public List<Cell> parse(List<APIFinStatsDTO> apiFinStatsDTOList, DocumentationType documentationType) {

        if(documentationType == DocumentationType.SCE) {
            DartDataSCEParser dartDataSCEParser = new DartDataSCEParser();
            return dartDataSCEParser.parseSCE(apiFinStatsDTOList);
        }

        for (APIFinStatsDTO dto : apiFinStatsDTOList) {
            if (DocumentationType.valueOf(dto.getSj_div()) == documentationType) {
                setThisTerm(dto);
                parseColumns(dto);
                parseRows(dto);
                parseCells(dto);
            }
        }
        return cells;
    }

    private void setThisTerm(APIFinStatsDTO apiFinStatsDTO) {
        if(thisTerm == null) {
            thisTerm = Integer.parseInt(apiFinStatsDTO.getThstrm_nm().replaceAll("[^0-9]", ""));

            termMap.put(THIS_TERM_KEY, thisTerm);
            termMap.put(FROM_TERM_KEY, thisTerm - 1);
            termMap.put(BEFORE_FROM_TERM_KEY, thisTerm - 2);
        }
    }

    private void parseColumns(APIFinStatsDTO dto) {
        columns.add(ColumnHead.getColumnHeadByDTO(dto, THIS_TERM_KEY));
        columns.add(ColumnHead.getColumnHeadByDTO(dto, FROM_TERM_KEY));
        columns.add(ColumnHead.getColumnHeadByDTO(dto, BEFORE_FROM_TERM_KEY));
    }

    private void parseRows(APIFinStatsDTO dto) {
        rows.add(RowHead.getRowHeadByDTO(dto));
    }


    private void parseCells(APIFinStatsDTO dto) {
        Integer rowOrder = Integer.valueOf(dto.getOrd());

        Cell thisTermCell = parseEachTermCell(dto, THIS_TERM_KEY, rowOrder);
        Cell fromTermCell = parseEachTermCell(dto, FROM_TERM_KEY, rowOrder);
        Cell beforeFromTermCell = parseEachTermCell(dto, BEFORE_FROM_TERM_KEY, rowOrder);

        cells.add(thisTermCell);
        cells.add(fromTermCell);
        cells.add(beforeFromTermCell);
    }

    private Cell parseEachTermCell(APIFinStatsDTO dto, String termKey, Integer rowOrder) {
        Cell thisTermCell = Cell.getCellByDTO(dto, termKey);
        thisTermCell.setColumnHead(getColumnHeadForTargetTerm(termKey));
        thisTermCell.setRowHead(getRowHeadByOrder(rowOrder));
        return thisTermCell;
    }

    private ColumnHead getColumnHeadForTargetTerm(String targetTermKey) {
        ColumnHead resColumnHead = null;
        for (ColumnHead columnHead : columns) {
            if(columnHead.getTerm() == termMap.get(targetTermKey)) {
                resColumnHead = columnHead;
                break;
            }
        }
        return resColumnHead;
    }

    private RowHead getRowHeadByOrder(Integer rowOrder) {
        RowHead resRowHead = null;
        for (RowHead rowHead : rows) {
            if(rowHead.getRowOrder() == rowOrder) {
                resRowHead = rowHead;
                break;
            }
        }
        return resRowHead;
    }

}
