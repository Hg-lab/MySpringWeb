package com.kideveloper.my_spring_web.repository;

import com.kideveloper.my_spring_web.model.User;

import java.util.List;

public interface CustomizedUserRepository {
    List<User> findByUsernameCustom(String username);

    List<User> findByUsernameJdbc(String username);
}
