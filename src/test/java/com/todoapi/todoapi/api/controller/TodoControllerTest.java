package com.todoapi.todoapi.api.controller;

import com.todoapi.todoapi.api.model.Todo;
import com.todoapi.todoapi.api.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTodos_NoContent() {
        when(todoService.findAllTodos()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Todo>> responseEntity = todoController.getAllTodos();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(todoService, times(1)).findAllTodos();
    }

    @Test
    void testGetAllTodos_OK() {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1, "Task 1", "Incomplete"));
        todos.add(new Todo(2, "Task 2", "Complete"));

        when(todoService.findAllTodos()).thenReturn(todos);

        ResponseEntity<List<Todo>> responseEntity = todoController.getAllTodos();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
        verify(todoService, times(1)).findAllTodos();
    }

    @Test
    void testGetTodoById_NotFound() {
        int id = 1;
        when(todoService.findTodoById(id)).thenReturn(null);

        ResponseEntity<Todo> responseEntity = todoController.getTodoById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(todoService, times(1)).findTodoById(id);
    }

    @Test
    void testGetTodoById_Found() {
        int id = 1;
        Todo todo = new Todo(id, "Task 1", "Incomplete");
        when(todoService.findTodoById(id)).thenReturn(todo);

        ResponseEntity<Todo> responseEntity = todoController.getTodoById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todo, responseEntity.getBody());
        verify(todoService, times(1)).findTodoById(id);
    }

    @Test
    void testCreateTodo() {
        Todo todo = new Todo();
        todo.setDescription("New Todo");
        todo.setStatus("Incomplete");

        when(todoService.createTodo(todo)).thenReturn(todo);

        ResponseEntity<Todo> responseEntity = todoController.createTodo(todo);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(todo, responseEntity.getBody());
        verify(todoService, times(1)).createTodo(todo);
    }

    @Test
    void testUpdateTodo_NotFound() {
        int id = 1;
        Todo todo = new Todo();
        todo.setDescription("Updated Todo");
        todo.setStatus("Complete");

        when(todoService.updateTodo(id, todo)).thenReturn(null);

        ResponseEntity<Todo> responseEntity = todoController.updateTodo(id, todo);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(todoService, times(1)).updateTodo(id, todo);
    }

    @Test
    void testUpdateTodo_Found() {
        int id = 1;
        Todo existingTodo = new Todo();
        existingTodo.setId(id);
        existingTodo.setDescription("Existing Todo");
        existingTodo.setStatus("Incomplete");

        Todo updatedTodo = new Todo();
        updatedTodo.setId(id);
        updatedTodo.setDescription("Updated Todo");
        updatedTodo.setStatus("Complete");

        when(todoService.updateTodo(id, updatedTodo)).thenReturn(updatedTodo);

        ResponseEntity<Todo> responseEntity = todoController.updateTodo(id, updatedTodo);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTodo, responseEntity.getBody());
        verify(todoService, times(1)).updateTodo(id, updatedTodo);
    }

    @Test
    void testDeleteTodo_NotFound() {
        int id = 1;
        when(todoService.deleteTodo(id)).thenReturn(false);

        ResponseEntity<Void> responseEntity = todoController.deleteTodo(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(todoService, times(1)).deleteTodo(id);
    }

    @Test
    void testDeleteTodo_Found() {
        int id = 1;
        when(todoService.deleteTodo(id)).thenReturn(true);

        ResponseEntity<Void> responseEntity = todoController.deleteTodo(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(todoService, times(1)).deleteTodo(id);
    }
}