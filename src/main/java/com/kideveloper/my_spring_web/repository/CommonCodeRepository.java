package com.kideveloper.my_spring_web.repository;

import com.kideveloper.my_spring_web.model.CommonCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommonCodeRepository extends QuerydslPredicateExecutor {

    @Query(value = "select c.com_cd_val from COMMON_CODE c where u.com_cd = %?1%")
    String findByCommonCodeQuery(String com_cd);
}
