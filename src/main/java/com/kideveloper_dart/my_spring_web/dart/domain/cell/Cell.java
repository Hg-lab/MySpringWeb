package com.kideveloper_dart.my_spring_web.dart.domain.cell;

import com.kideveloper_dart.my_spring_web.dart.application.dto.request.FinancialStatementsDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.column.Column;
import com.kideveloper_dart.my_spring_web.dart.domain.documentation.Documentation;
import com.kideveloper_dart.my_spring_web.dart.domain.row.Row;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Builder
@Entity
public class Cell {

    @Id
    private Long id;

    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documentation_id")
    private Documentation documentation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "row_id")
    private Row row;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private Column column;


    // TODO: 2022/11/29 term별로 cell리턴
    public static Cell getCellByDTO(FinancialStatementsDTO dto) {
        return Cell.builder()
                .value(dto.getThstrm_amount())
                .build();
    }
}
