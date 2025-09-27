package com.example.todolist.bdd;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.service.TaskServices;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AddTaskSteps {

    private TaskServices taskService;
    private Task task;
    private Exception exception;
    private List<Task> taskList;

    public AddTaskSteps() {
        taskList = new ArrayList<>();

        // Mock the TaskRepository
        TaskRepository taskRepository = mock(TaskRepository.class);

        // When save is called, add the task to our taskList
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task t = invocation.getArgument(0);
            taskList.add(t);
            return t;
        });

        // When findAll is called, return our current taskList
        when(taskRepository.findAll()).thenAnswer(invocation -> new ArrayList<>(taskList));

        taskService = new TaskServices(taskRepository);
    }

    @Given("the task title is {string}")
    public void the_task_title_is(String title) {
        task = new Task(title, false);
    }

    @When("I add the task")
    public void i_add_the_task() {
        try {
            taskService.addTask(task);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the task list should contain {string}")
    public void the_task_list_should_contain(String expectedTitle) {
        Assertions.assertTrue(taskList.stream().anyMatch(t -> t.getTitle().equals(expectedTitle)));
    }

    @Then("an error {string} should be shown")
    public void an_error_should_be_shown(String expectedMessage) {
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
