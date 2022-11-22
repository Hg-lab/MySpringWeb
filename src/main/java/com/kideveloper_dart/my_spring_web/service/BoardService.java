package com.kideveloper_dart.my_spring_web.service;

import com.kideveloper_dart.my_spring_web.model.Board;
import com.kideveloper_dart.my_spring_web.model.User;
import com.kideveloper_dart.my_spring_web.repository.BoardRepository;
import com.kideveloper_dart.my_spring_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board) {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
