package com.todoapi.todoapi.api.service;

import com.todoapi.todoapi.api.model.Todo;
import com.todoapi.todoapi.api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    public TodoService() {
        // noop
    }

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAllTodos() {
        return todoRepository.getAllTodos();
    }

    public Todo findTodoById(int id) {
        return todoRepository.getTodoById(id);
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.createTodo(todo);
    }

    public Todo updateTodo(int id, Todo todo) {
        Todo existingTodo = todoRepository.getTodoById(id);
        if (existingTodo == null) {
            return null;
        }
        updateTodoAttributes(todo, existingTodo);
        return todoRepository.updateTodo(existingTodo);
    }

    public boolean deleteTodo(int id) {
        return todoRepository.deleteTodo(id);
    }

    protected void updateTodoAttributes(Todo todo, Todo existingTodo) {
        if (todo.getDescription() != null) {
            existingTodo.setDescription(todo.getDescription());
        }
        if (todo.getStatus() != null) {
            existingTodo.setStatus(todo.getStatus());
        }
    }
}
