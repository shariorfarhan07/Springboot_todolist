package com.todolist.todolist.controller;

import com.todolist.todolist.dao.TodoRepo;
import com.todolist.todolist.dao.UserRepo;
import com.todolist.todolist.entity.Todo;
import com.todolist.todolist.entity.User;
import com.todolist.todolist.request.AddTodoRequest;
import com.todolist.todolist.request.AddUserRequest;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepo userRepo;
    private TodoRepo todoRepo;

    public UserController(UserRepo userRepo, TodoRepo todoRepo) {
        this.userRepo = userRepo;
        this.todoRepo = todoRepo;
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException());
        return user;
    }

    @PostMapping
    public User addUser(@RequestBody AddUserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        return userRepo.save(user);
    }

    @PostMapping("/{userId}/todos")
    public User addTodo(@PathVariable int userId, @RequestBody AddTodoRequest todoRequest) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException());
        Todo todo = new Todo();
        todo.setContent(todoRequest.getContent());
        user.getTodoList().add(todo);
        return userRepo.save(user);
    }


    @PostMapping("/todos/{todoId}")
    public void toggleTodoCompleted(@PathVariable int todoId) {
        Todo todo = todoRepo.findById(todoId).orElseThrow(() -> new NoSuchElementException());
        todo.setCompleted(!todo.getCompleted());
        todoRepo.save(todo);
    }


    @DeleteMapping("{userId}/todos/{todoId}")
    public void deleteTodo(@PathVariable int userId, @PathVariable int todoId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException());
        Todo todo = todoRepo.findById(todoId).orElseThrow(() -> new NoSuchElementException());
        user.getTodoList().remove(todo);
        todoRepo.delete(todo);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable int userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException());
    }
}