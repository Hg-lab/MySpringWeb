package com.kideveloper_dart.my_spring_web.dart.domain.company;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Company {

    // stock_code로 조회
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String corpCode;

    @Column
    private String name;

    @Column
    private String stockCode;

    @Column
    private Date modifyDate;
}
