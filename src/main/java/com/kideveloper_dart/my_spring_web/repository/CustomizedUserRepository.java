package com.kideveloper_dart.my_spring_web.repository;

import com.kideveloper_dart.my_spring_web.model.User;

import java.util.List;

public interface CustomizedUserRepository {
    List<User> findByUsernameCustom(String username);

    List<User> findByUsernameJdbc(String username);
}
