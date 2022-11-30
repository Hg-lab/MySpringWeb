package com.kideveloper_dart.my_spring_web.dart.domain.company;

import com.kideveloper_dart.my_spring_web.dart.domain.documentation.Documentation;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
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

    @OneToMany(mappedBy = "company")
    private List<Documentation> docs;
}
