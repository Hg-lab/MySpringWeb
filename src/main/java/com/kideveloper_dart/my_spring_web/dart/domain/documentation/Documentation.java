package com.kideveloper_dart.my_spring_web.dart.domain.documentation;


import com.kideveloper_dart.my_spring_web.dart.domain.company.Company;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Year;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Documentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column
    private Integer businessYear;

    @Column
    private String financialStatDiv;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Documentation(Company company, Integer businessYear, String financialStatDiv) {
        this.company = company;
        this.businessYear = businessYear;
        this.financialStatDiv = financialStatDiv;
    }

    public Documentation() {

    }
}
