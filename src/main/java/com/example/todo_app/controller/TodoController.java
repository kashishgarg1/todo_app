package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return service.findAll();
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo createdTodo = service.save(todo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED); // Return 201 Created
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        // Attempt to update the existing todo
        Todo updatedTodo = service.update(id, todo);

        if (updatedTodo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 Not Found
        }

        return new ResponseEntity<>(updatedTodo, HttpStatus.OK); // Return 200 OK
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if (service.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 Not Found if not found
        }
    }
}
