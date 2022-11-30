package com.kideveloper_dart.my_spring_web.dart.domain.documentation;


import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import com.kideveloper_dart.my_spring_web.dart.domain.company.Company;
import com.kideveloper_dart.my_spring_web.dart.domain.doctype.DocumentationType;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private DocumentationType documentationType;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "documentation")
    private List<Cell> cell;

    public Documentation(Company company, Integer businessYear, DocumentationType documentationType) {
        this.company = company;
        this.businessYear = businessYear;
        this.documentationType = documentationType;
    }

    public Documentation() {

    }
}
