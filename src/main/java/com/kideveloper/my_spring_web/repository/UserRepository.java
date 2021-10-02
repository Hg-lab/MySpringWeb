package com.kideveloper.my_spring_web.repository;

import com.kideveloper.my_spring_web.model.Board;
import com.kideveloper.my_spring_web.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = { "boards" })
    List<User> findAll();

    User findByUsername(String username);

    @Query(value = "select u from User u where u.username like %?1%")
    List<User> findByUsernameQuery(String username);

    @Query(nativeQuery = true, value = "select u.* from User u where u.username like %?1%")
    List<User> findByUsernameNativeQuery(String username);
}
