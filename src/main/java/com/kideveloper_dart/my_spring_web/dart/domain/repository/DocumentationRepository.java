package com.kideveloper_dart.my_spring_web.dart.domain.repository;

import com.kideveloper_dart.my_spring_web.dart.domain.company.Company;
import com.kideveloper_dart.my_spring_web.dart.domain.documentation.Documentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DocumentationRepository extends JpaRepository<Documentation, Long> {

    List<Documentation> findByCompany(Company company);

    boolean existsByCompanyAndBusinessYear(Company company, Integer businessYear);

    Documentation findByCompanyAndAndBusinessYear(Company company, Integer businessYear);
}
