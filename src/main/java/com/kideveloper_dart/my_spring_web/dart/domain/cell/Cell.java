package com.kideveloper_dart.my_spring_web.dart.domain.cell;

import com.kideveloper_dart.my_spring_web.dart.application.dto.request.FinancialStatementsDTO;
import com.kideveloper_dart.my_spring_web.dart.domain.column.ColumnHead;
import com.kideveloper_dart.my_spring_web.dart.domain.documentation.Documentation;
import com.kideveloper_dart.my_spring_web.dart.domain.row.RowHead;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String value;

    @Column
    private Integer term;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documentation_id")
    private Documentation documentation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "row_id")
    private RowHead rowHead;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "column_id")
    private ColumnHead columnHead;


    // TODO: 2022/11/29 term별로 cell리턴
    public static Cell getCellByDTO(FinancialStatementsDTO dto, String term) {
        Map<String, String> termAmountMap = new HashMap<String, String>(){{
            put("thisTerm", dto.getThstrm_amount());
            put("fromTerm", dto.getFrmtrm_amount());
            put("beforeFromTerm", dto.getBfefrmtrm_amount());
        }};

        Map<String, Integer> termMap = new HashMap<String, Integer>(){{
            put("thisTerm", Integer.parseInt(dto.getThstrm_nm().replaceAll("[^0-9]", "")));
            put("fromTerm", Integer.parseInt(dto.getFrmtrm_nm().replaceAll("[^0-9]", "")));
            put("beforeFromTerm", Integer.parseInt(dto.getBfefrmtrm_nm().replaceAll("[^0-9]", "")));
        }};

        return Cell.builder()
                .value(termAmountMap.get(term))
                .term(termMap.get(term))
                .build();
    }
}
