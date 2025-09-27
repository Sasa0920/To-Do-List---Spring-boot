package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskServices taskService;

    @BeforeEach
    void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskService = new TaskServices(taskRepository);
    }

    // RED: test should fail first
    @Test
    void testAddTask() {
        Task task = new Task("Learn TDD", false);

        when(taskRepository.save(task)).thenReturn(task);

        Task savedTask = taskService.addTask(task);

        assertNotNull(savedTask);
        assertEquals("Learn TDD", savedTask.getTitle());
        assertFalse(savedTask.isCompleted());
    }

    @Test
    void testAddTaskShouldFailWhenTitleIsEmpty() {
        Task task = new Task("", false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.addTask(task);
        });

        assertEquals("Task title cannot be empty", exception.getMessage());
    }
}
