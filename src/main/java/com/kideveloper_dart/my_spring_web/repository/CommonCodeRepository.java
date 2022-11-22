package com.kideveloper_dart.my_spring_web.repository;

import com.kideveloper_dart.my_spring_web.model.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {


    CommonCode findFirstByComCd(String comCd);
}
