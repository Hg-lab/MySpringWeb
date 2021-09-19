package com.kideveloper.my_spring_web.repository;

import com.kideveloper.my_spring_web.model.Board;
import com.kideveloper.my_spring_web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
