package com.kideveloper_dart.my_spring_web.dart.domain.column;

import com.kideveloper_dart.my_spring_web.dart.infrastructure.dto.response.APIFinStatsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ColumnHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer term;

    @Column
    private Integer columnOrder;

    public static ColumnHead getColumnHeadByDTO(APIFinStatsDTO dto, String term) {

        Map<String, String> termColumnHeadMap = new HashMap<String, String>(){{
            put("thisTerm", dto.getThstrm_nm());
            put("fromTerm", dto.getFrmtrm_nm());
            put("beforeFromTerm", dto.getBfefrmtrm_nm());
        }};

        Map<String, Integer> termOrderMap = new HashMap<String, Integer>(){{
            put("thisTerm", 0);
            put("fromTerm", 1);
            put("beforeFromTerm", 2);
        }};

        String columnHeadName = termColumnHeadMap.get(term);
        Integer columnTerm = Integer.parseInt(columnHeadName.replaceAll("[^0-9]", ""));
        Integer columnOrder = termOrderMap.get(term);

        return ColumnHead.builder()
                .name(columnHeadName)
                .term(columnTerm)
                .columnOrder(columnOrder).build();
    }

    public static class ColumnCreator{
        // Row newRow = Row.NewRowFrom(rowDTO); 와 같은 방식으로 생성
    }

    @Override
    public int hashCode() {
        if(this.getId() == null) return Objects.hash(name, term, columnOrder);
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;

        ColumnHead columnHead = (ColumnHead) obj;
        if(this.getName() == columnHead.getName() &&
                this.getTerm() == columnHead.getTerm() &&
                this.getColumnOrder() == columnHead.getColumnOrder()) return true;

        return super.equals(obj);
    }
}
