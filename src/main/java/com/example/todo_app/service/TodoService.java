package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

//    @CachePut(value = "todo", key = "#todo.id")
    public Todo save(Todo todo) {
        return repository.save(todo);
    }

//    @CacheEvict(value = "todo", key = "#id")
    public boolean delete(Long id) {
        repository.deleteById(id);
        return repository.findById(id).isPresent();
    }

    public Todo update(Long id, Todo todo) {
        Todo existingTodo = findById(id);
        if (existingTodo != null) {
//            existingTodo.setTitle(todo.getTitle());
//            existingTodo.setCompleted(todo.isCompleted());
            return save(existingTodo);
        }
        return null; // Return null if the todo was not found
    }

//    @Cacheable(value = "todo", key = "#id")
    public Todo findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
