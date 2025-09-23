package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task) {
        if(task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, Task task) {
        Task existing = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        existing.setTitle(task.getTitle());
        existing.setCompleted(task.isCompleted());
        return taskRepository.save(existing);
    }
}