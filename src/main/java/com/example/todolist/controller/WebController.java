package com.example.todolist.controller;

import org.springframework.ui.Model;
import com.example.todolist.model.Task;
import com.example.todolist.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final TaskService taskService;

    public WebController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "index";
    }

    @PostMapping("/tasks")
    public String addTask(@RequestParam String title) {
        taskService.addTask(new Task(title, false));
        return "redirect:/";
    }
    @PutMapping("/tasks/{id}")
    public String toggleCompleted(@PathVariable Long id, @RequestParam String title, @RequestParam boolean completed) {
        taskService.updateTask(id, new Task(title, completed));
        return "redirect:/";
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }
}
