package com.kideveloper_dart.my_spring_web.dart.application;


import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import com.kideveloper_dart.my_spring_web.dart.domain.column.ColumnHead;
import com.kideveloper_dart.my_spring_web.dart.domain.doctype.DocumentationType;
import com.kideveloper_dart.my_spring_web.dart.domain.row.RowHead;
import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.response.APIFinStatsDTO;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@NoArgsConstructor
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DartDataSCEParser {

    private List<Cell> cells = new ArrayList<>();
    private Set<ColumnHead> columns = new HashSet<>();
    private Set<RowHead> rows = new HashSet<>();

    public List<Cell> parseSCE(List<APIFinStatsDTO> apiFinStatsDTOList) {
        for (APIFinStatsDTO dto : apiFinStatsDTOList) {
            if (DocumentationType.valueOf(dto.getSj_div()) == DocumentationType.SCE) {

            }
        }
        return cells;
    }
}
