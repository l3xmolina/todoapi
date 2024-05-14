package com.todoapi.todoapi.api.mapper;

import com.todoapi.todoapi.api.model.Todo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoMapper implements RowMapper<Todo> {
    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String description = rs.getString("description");
        String status = rs.getString("status");
        return new Todo(id, description, status);
    }
}