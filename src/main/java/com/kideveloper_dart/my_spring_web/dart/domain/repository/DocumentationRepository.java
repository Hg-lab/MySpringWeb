package com.kideveloper_dart.my_spring_web.dart.domain.repository;

import com.kideveloper_dart.my_spring_web.dart.domain.documentation.Documentation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentationRepository extends JpaRepository<Documentation, Long> {
}
