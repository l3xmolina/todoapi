package com.todoapi.todoapi.api.repository;

import com.todoapi.todoapi.api.mapper.TodoMapper;
import com.todoapi.todoapi.api.model.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Todo> getAllTodos() {
        String sql = "SELECT * FROM todos";
        return jdbcTemplate.query(sql, new TodoMapper());
    }

    public Todo getTodoById(int id) {
        String sql = "SELECT * FROM todos WHERE id = ?";
        List<Todo> result = jdbcTemplate.query(sql, new TodoMapper(), id);
        return result.isEmpty() ? null : result.get(0);
    }

    public Todo createTodo(Todo todo) {
        String sql = "INSERT INTO todos (description, status) VALUES (?, ?)";
        jdbcTemplate.update(sql, todo.getDescription(), todo.getStatus());
        return todo;
    }

    public Todo updateTodo(Todo todo) {
        String sql = "UPDATE todos SET description = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql, todo.getDescription(), todo.getStatus(), todo.getId());
        return todo;
    }

    public boolean deleteTodo(int id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        return jdbcTemplate.update(sql, id) == 1;
    }
}
