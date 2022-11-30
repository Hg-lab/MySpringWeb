package com.kideveloper_dart.my_spring_web.dart.domain.row;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Row {

    @Id
    private Long id;



    public static class RowCreator{
        // Row newRow = Row.NewRowFrom(rowDTO); 와 같은 방식으로 생성
    }
}
