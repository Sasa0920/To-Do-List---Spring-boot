package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceUpdateTest {

    private TaskRepository repo;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(TaskRepository.class);
        taskService = new TaskService(repo);
    }

    @Test
    void updateTask_shouldSaveUpdatedValues() {
        Task existing = new Task("Old", false);
        existing.setId(1L);

        Task updated = new Task("New Title", true);
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task result = taskService.updateTask(1L, updated);

        assertEquals("New Title", result.getTitle());
        assertTrue(result.isCompleted());
        verify(repo, times(1)).save(existing);
    }

    @Test
    void deleteTask_shouldCallRepository() {
        doNothing().when(repo).deleteById(2L);
        taskService.deleteTask(2L);
        verify(repo, times(1)).deleteById(2L);
    }
}

