package com.kideveloper.my_spring_web.repository;

import com.kideveloper.my_spring_web.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);

    List<Board> findByTitleOrContent(String title, String content);
}
