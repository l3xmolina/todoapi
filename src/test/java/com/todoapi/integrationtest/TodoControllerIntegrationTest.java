package com.todoapi.integrationtest;

import com.todoapi.todoapi.TodoapiApplication;
import com.todoapi.todoapi.api.model.Todo;
import com.todoapi.todoapi.api.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TodoapiApplication.class)
@Transactional
@AutoConfigureMockMvc
class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        // Populate the database with test data
        todoService.createTodo(new Todo(1, "Test Todo 1", "Incomplete"));
        todoService.createTodo(new Todo(2, "Test Todo 2", "Complete"));
    }

    @Test
    void testGetAllTodos_ReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateTodo_ReturnsCreated() throws Exception {
        String requestBody = "{\"description\":\"Test Todo\",\"status\":\"Incomplete\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Test Todo"));
    }

    @Test
    void testGetTodoById_ReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateTodo_ReturnsOk() throws Exception {
        String requestBody = "{\"description\":\"Updated Todo\",\"status\":\"Complete\"}";
        mockMvc.perform(MockMvcRequestBuilders.patch("/todos/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Updated Todo"))
                .andExpect(jsonPath("$.status").value("Complete"));
    }

    @Test
    void testDeleteTodo_ReturnsNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/1"))
                .andExpect(status().isNoContent());
    }
}