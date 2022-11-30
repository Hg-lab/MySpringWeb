package com.kideveloper_dart.my_spring_web.dart.domain.column;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Column {

    @Id
    private Long id;


    public static class ColumnCreator{
        // Row newRow = Row.NewRowFrom(rowDTO); 와 같은 방식으로 생성
    }
}
