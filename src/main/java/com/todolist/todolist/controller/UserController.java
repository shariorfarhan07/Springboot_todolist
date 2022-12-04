package com.todolist.todolist.controller;

import com.todolist.todolist.dao.TodoRepo;
import com.todolist.todolist.dao.UserRepo;
import com.todolist.todolist.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepo  userRepo;
    private TodoRepo todoRepo;

    public UserController(UserRepo userRepo, TodoRepo todoRepo) {
        this.userRepo = userRepo;
        this.todoRepo = todoRepo;
    }

    public User getUserById(@PathVariable int userId){
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException());
        return user;
    }
}
