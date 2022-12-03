package com.kideveloper_dart.my_spring_web.dart.domain.row;

import com.kideveloper_dart.my_spring_web.dart.domain.column.ColumnHead;
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
public class RowHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer term;

    @Column
    private Integer rowOrder;

    public static RowHead getRowHeadByDTO(APIFinStatsDTO dto, String term) {

        Map<String, Integer> termMap = new HashMap<String, Integer>(){{
            put("thisTerm", Integer.parseInt(dto.getThstrm_nm().replaceAll("[^0-9]", "")));
            put("fromTerm", Integer.parseInt(dto.getFrmtrm_nm().replaceAll("[^0-9]", "")));
            put("beforeFromTerm", Integer.parseInt(dto.getBfefrmtrm_nm().replaceAll("[^0-9]", "")));
        }};

        String rowHeadName = dto.getAccount_nm();
        Integer rowTerm = termMap.get(term);
        Integer rowOrder = Integer.parseInt(dto.getOrd());

        return RowHead.builder()
                .name(rowHeadName)
                .term(rowTerm)
                .rowOrder(rowOrder).build();
    }

    public static class RowCreator{
        // Row newRow = Row.NewRowFrom(rowDTO); 와 같은 방식으로 생성
    }


    @Override
    public int hashCode() {
        if(this.getId() == null) return Objects.hash(name, term, rowOrder);
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;

        RowHead rowHead = (RowHead) obj;
        if(this.getName().equals(rowHead.getName()) &&
                this.getTerm() == rowHead.getTerm() &&
                this.getRowOrder() == rowHead.getRowOrder()) return true;

        return super.equals(obj);
    }
}
