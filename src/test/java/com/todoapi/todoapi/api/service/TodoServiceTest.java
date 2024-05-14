package com.todoapi.todoapi.api.service;

import com.todoapi.todoapi.api.model.Todo;
import com.todoapi.todoapi.api.repository.TodoRepository;
import com.todoapi.todoapi.api.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllTodos() {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1, "Task 1", "Incomplete"));
        todos.add(new Todo(2, "Task 2", "Complete"));

        when(todoRepository.getAllTodos()).thenReturn(todos);

        List<Todo> result = todoService.findAllTodos();

        assertEquals(todos.size(), result.size());
        assertEquals(todos, result);
        verify(todoRepository, times(1)).getAllTodos();
    }

    @Test
    void testFindTodoById_Found() {
        int id = 1;
        Todo todo = new Todo(id, "Task 1", "Incomplete");

        when(todoRepository.getTodoById(id)).thenReturn(todo);

        Todo result = todoService.findTodoById(id);

        assertNotNull(result);
        assertEquals(todo, result);
        verify(todoRepository, times(1)).getTodoById(id);
    }

    @Test
    void testFindTodoById_NotFound() {
        int id = 1;

        when(todoRepository.getTodoById(id)).thenReturn(null);

        Todo result = todoService.findTodoById(id);

        assertNull(result);
        verify(todoRepository, times(1)).getTodoById(id);
    }

    @Test
    void testCreateTodo() {
        Todo todo = new Todo();
        todo.setDescription("New Todo");
        todo.setStatus("Incomplete");

        when(todoRepository.createTodo(todo)).thenReturn(todo);

        Todo result = todoService.createTodo(todo);

        assertNotNull(result);
        assertEquals(todo, result);
        verify(todoRepository, times(1)).createTodo(todo);
    }

    @Test
    void testUpdateTodo_Found() {
        int id = 1;
        Todo existingTodo = new Todo(id, "Existing Todo", "Incomplete");
        Todo updatedTodo = new Todo(id, "Updated Todo", "Complete");

        when(todoRepository.getTodoById(id)).thenReturn(existingTodo);
        when(todoRepository.updateTodo(existingTodo)).thenReturn(updatedTodo);

        Todo result = todoService.updateTodo(id, updatedTodo);

        assertNotNull(result);
        assertEquals(updatedTodo, result);
        verify(todoRepository, times(1)).getTodoById(id);
        verify(todoRepository, times(1)).updateTodo(existingTodo);
    }

    @Test
    void testUpdateTodo_NotFound() {
        int id = 1;
        Todo updatedTodo = new Todo(id, "Updated Todo", "Complete");

        when(todoRepository.getTodoById(id)).thenReturn(null);

        Todo result = todoService.updateTodo(id, updatedTodo);

        assertNull(result);
        verify(todoRepository, times(1)).getTodoById(id);
        verify(todoRepository, never()).updateTodo(any());
    }

    @Test
    void testUpdateTodoAttributes_DescriptionNull() {
        // Arrange
        Todo todo = new Todo();
        todo.setStatus("New Status");
        Todo existingTodo = new Todo();
        existingTodo.setDescription("Existing Description");

        // Act
        new TodoService().updateTodoAttributes(todo, existingTodo);

        // Assert
        assertEquals("Existing Description", existingTodo.getDescription());
        assertEquals("New Status", existingTodo.getStatus());
    }

    @Test
    void testUpdateTodoAttributes_StatusNull() {
        // Arrange
        Todo todo = new Todo();
        todo.setDescription("New Description");
        Todo existingTodo = new Todo();
        existingTodo.setStatus("Existing Status");

        // Act
        new TodoService().updateTodoAttributes(todo, existingTodo);

        // Assert
        assertEquals("New Description", existingTodo.getDescription());
        assertEquals("Existing Status", existingTodo.getStatus());
    }

    @Test
    void testDeleteTodo_True() {
        int id = 1;

        when(todoRepository.deleteTodo(id)).thenReturn(true);

        boolean result = todoService.deleteTodo(id);

        assertTrue(result);
        verify(todoRepository, times(1)).deleteTodo(id);
    }

    @Test
    void testDeleteTodo_False() {
        int id = 1;

        when(todoRepository.deleteTodo(id)).thenReturn(false);

        boolean result = todoService.deleteTodo(id);

        assertFalse(result);
        verify(todoRepository, times(1)).deleteTodo(id);
    }
}