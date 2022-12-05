package com.kideveloper_dart.my_spring_web.dart.domain;


import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO;
import com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO.ColumnHeadDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kideveloper_dart.my_spring_web.dart.application.dto.response.DartDocsResponseDTO.*;

@Component
public class TableAssembler {


    public DartDocsResponseDTO assembleTableByCells(List<Cell> cells) {
        DartDocsResponseDTO dartDocsResponseDTO = new DartDocsResponseDTO();

        for (Cell cell : cells) {
            ColumnHeadDTO columnHeadDTO = ColumnHeadDTO.from(cell.getColumnHead());
            RowHeadDTO rowHeadDTO = RowHeadDTO.from(cell.getRowHead());

            dartDocsResponseDTO.getColumnHeadDTOList().add(columnHeadDTO);

            Map<ColumnHeadDTO, CellDTO> columnCellMapDTO = new HashMap<>();
            columnCellMapDTO.put(columnHeadDTO, CellDTO.from(cell));
            dartDocsResponseDTO.getRowColumnCellDTOMap().put(rowHeadDTO, columnCellMapDTO);
        }
        return dartDocsResponseDTO;
    }

}
