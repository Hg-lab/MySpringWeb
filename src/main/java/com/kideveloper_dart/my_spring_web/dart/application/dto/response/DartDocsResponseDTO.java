package com.kideveloper_dart.my_spring_web.dart.application.dto.response;

import lombok.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DartDocsResponseDTO {

    private List<CellDTO> cellDTOList;

    private LinkedHashMap<RowDTO, HashMap<ColumnDTO, CellDTO>> rowColumnCellDTOMap;

    // TODO: 2022/12/01 Entity -> DTO, Entity 변동발생시 API 스펙을 보존하기위함
    @Builder
    @Data
    public static class CellDTO {
        private String value;
        private Integer term;
    }

    @Builder
    @Data
    public static class RowDTO {
        private String name;
        private Integer term;
        private Integer rowOrder;
    }

    @Builder
    @Data
    public static class ColumnDTO {
        private String name;
    }
}
