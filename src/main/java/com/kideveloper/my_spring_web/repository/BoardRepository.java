package com.kideveloper.my_spring_web.repository;

import com.kideveloper.my_spring_web.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {


}
