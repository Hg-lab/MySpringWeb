package com.kideveloper_dart.my_spring_web.dart.domain;


import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO.ColumnHeadDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO.*;

@Component
public class TableAssembler {


    public DartDocsResponseDTO assembleTableByCells(List<Cell> cells) {
        DartDocsResponseDTO dartDocsResponseDTO = new DartDocsResponseDTO();
        Set<ColumnHeadDTO> columnSet = new HashSet<>();
        Set<String> rowNameSet = new HashSet<>();
        Map<ColumnHeadDTO, CellDTO> columnCellMapDTO = new HashMap<>();
        for (Cell cell : cells) {
            ColumnHeadDTO columnHeadDTO = ColumnHeadDTO.from(cell.getColumnHead());
            RowHeadDTO rowHeadDTO = RowHeadDTO.from(cell.getRowHead());

            if(columnSet.add(columnHeadDTO))
                dartDocsResponseDTO.getColumnHeadDTOList().add(columnHeadDTO);

            if(rowNameSet.add(rowHeadDTO.getName())) {
                columnCellMapDTO = new HashMap<>();
            }

            columnCellMapDTO.put(columnHeadDTO, CellDTO.from(cell));
            dartDocsResponseDTO.getRowColumnCellDTOMap().put(rowHeadDTO, columnCellMapDTO);

        }

        return dartDocsResponseDTO;
    }

}
