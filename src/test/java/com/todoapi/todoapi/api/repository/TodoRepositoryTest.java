package com.todoapi.todoapi.api.repository;

import com.todoapi.todoapi.api.mapper.TodoMapper;
import com.todoapi.todoapi.api.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TodoRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TodoRepository todoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTodos() {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1, "Task 1", "Incomplete"));
        todos.add(new Todo(2, "Task 2", "Complete"));

        when(jdbcTemplate.query(anyString(), any(TodoMapper.class))).thenReturn(todos);

        List<Todo> result = todoRepository.getAllTodos();

        assertEquals(todos.size(), result.size());
        assertEquals(todos, result);
        verify(jdbcTemplate, times(1)).query(anyString(), any(TodoMapper.class));
    }

    @Test
    void testGetTodoById_Found() {
        int id = 1;
        Todo todo = new Todo(id, "Task 1", "Incomplete");

        when(jdbcTemplate.query(anyString(), any(TodoMapper.class), anyInt()))
                .thenReturn(List.of(todo));

        Todo result = todoRepository.getTodoById(id);

        assertNotNull(result);
        assertEquals(todo, result);
        verify(jdbcTemplate, times(1)).query(anyString(), any(TodoMapper.class), anyInt());
    }

    @Test
    void testGetTodoById_NotFound() {
        int id = 1;

        when(jdbcTemplate.query(anyString(), any(TodoMapper.class), anyInt()))
                .thenReturn(new ArrayList<>());

        Todo result = todoRepository.getTodoById(id);

        assertNull(result);
        verify(jdbcTemplate, times(1)).query(anyString(), any(TodoMapper.class), anyInt());
    }

    @Test
    void testCreateTodo() {
        Todo todo = new Todo();
        todo.setDescription("New Todo");
        todo.setStatus("Incomplete");

        int rowsAffected = 1;

        when(jdbcTemplate.update(anyString(), anyString(), anyString())).thenReturn(rowsAffected);

        Todo result = todoRepository.createTodo(todo);

        assertNotNull(result);
        assertEquals(todo, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyString(), anyString());
    }

    @Test
    void testUpdateTodo() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setDescription("Updated Todo");
        todo.setStatus("Complete");

        int rowsAffected = 1;

        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyInt())).thenReturn(rowsAffected);

        Todo result = todoRepository.updateTodo(todo);

        assertNotNull(result);
        assertEquals(todo, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    void testDeleteTodo() {
        int id = 1;
        int rowsAffected = 1;

        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(rowsAffected);

        boolean result = todoRepository.deleteTodo(id);

        assertTrue(result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt());
    }

    @Test
    void testDeleteTodo_NotFound() {
        int id = 1;
        int rowsAffected = 0;

        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(rowsAffected);

        boolean result = todoRepository.deleteTodo(id);

        assertFalse(result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt());
    }
}