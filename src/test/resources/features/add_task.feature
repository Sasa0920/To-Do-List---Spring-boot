Feature: Add Task
  As a user
  I want to add a new task
  So that I can track my work

  Scenario: Successfully add a task
    Given the task title is "Finish homework"
    When I add the task
    Then the task list should contain "Finish homework"

  Scenario: Fail to add empty task
    Given the task title is ""
    When I add the task
    Then an error "Task title cannot be empty" should be shown
