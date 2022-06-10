package com.kideveloper.my_spring_web.repository;

import com.kideveloper.my_spring_web.model.CorpCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorpCodeRepository extends JpaRepository<CorpCode, Long> {

    CorpCode findByStockCode(String stockCode);
}
