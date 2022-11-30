package com.kideveloper_dart.my_spring_web.dart.domain.repository;

import com.kideveloper_dart.my_spring_web.dart.domain.cell.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
}
