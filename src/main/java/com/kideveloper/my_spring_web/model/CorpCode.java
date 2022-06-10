package com.kideveloper.my_spring_web.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CORPCODE")
public class CorpCode {

    @Id
    @Column(name = "corp_code")
    private String corpCode;

    @Column(name = "corp_name")
    private String corpName;

    @Column(name = "stock_code")
    private String stockCode;

    @Column(name = "modify_date")
    private String modifyDate;

}
