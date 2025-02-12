package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;
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

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return service.save(todo);
    }
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        // Check if the todo exists
        Todo existingTodo = service.findById(id);
        if (existingTodo == null) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        // Update the existing todo's properties
        existingTodo.setTitle(todo.getTitle());
        existingTodo.setCompleted(todo.isCompleted());

        // Save and return the updated todo
        return service.save(existingTodo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        service.delete(id);
    }
}
