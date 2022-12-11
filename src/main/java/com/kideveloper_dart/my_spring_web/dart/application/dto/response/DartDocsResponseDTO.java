package com.kideveloper_dart.my_spring_web.dart.application.dto.response;

import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import com.kideveloper_dart.my_spring_web.dart.domain.column.ColumnHead;
import com.kideveloper_dart.my_spring_web.dart.domain.row.RowHead;
import lombok.*;

import java.util.*;

@Getter
@Builder
public class DartDocsResponseDTO {

    private final PriorityQueue<ColumnHeadDTO> columnHeadDTOList
            = new PriorityQueue<>(Comparator.comparing(ColumnHeadDTO::getColumnOrder));
    private final TreeMap<RowHeadDTO, Map<ColumnHeadDTO, CellDTO>> rowColumnCellDTOMap
            = new TreeMap<>(Comparator.comparing(RowHeadDTO::getRowOrder));

    public DartDocsResponseDTO() {

    }

    // TODO: 2022/12/01 Entity -> DTO, Entity 변동발생시 API 스펙을 보존하기위함
    @Builder
    @Data
    public static class CellDTO {
        private String value;
        private Integer term;

        public static CellDTO from(Cell cell) {
            return CellDTO.builder()
                    .value(cell.getValue())
                    .term(cell.getTerm())
                    .build();
        }
    }

    @Builder
    @Data
    public static class RowHeadDTO {
        private String name;
        private Integer term;
        private Integer rowOrder;

        public static RowHeadDTO from(RowHead rowHead) {
            return RowHeadDTO.builder()
                    .name(rowHead.getName())
                    .term(rowHead.getTerm())
                    .rowOrder(rowHead.getRowOrder())
                    .build();
        }
    }

    @Builder
    @Data
    public static class ColumnHeadDTO {
        private String name;
        private Integer term;
        private Integer columnOrder;

        public static ColumnHeadDTO from(ColumnHead columnHead) {
            return ColumnHeadDTO.builder()
                    .name(columnHead.getName())
                    .term(columnHead.getTerm())
                    .columnOrder(columnHead.getColumnOrder())
                    .build();
        }
    }
}
