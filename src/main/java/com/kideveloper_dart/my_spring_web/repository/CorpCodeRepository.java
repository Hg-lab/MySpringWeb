package com.kideveloper_dart.my_spring_web.repository;

import com.kideveloper_dart.my_spring_web.model.CorpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorpCodeRepository extends JpaRepository<CorpCode, Long> {

    CorpCode findByStockCode(String stockCode);

    @Override
    List<CorpCode> findAll();
}
