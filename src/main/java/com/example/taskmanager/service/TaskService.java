package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public TaskService() {

        tasks.add(new Task(
                1,
                "Learn Spring Boot",
                "Create REST API",
                "High",
                "Pending",
                "30-Jun-2026"
        ));

    }

    // Get All Tasks
    public List<Task> getAllTasks() {
        return tasks;
    }

    // Get Task By ID
    public Task getTaskById(Integer id) {

        for (Task task : tasks) {

            if (task.getId().equals(id)) {
                return task;
            }

        }

        return null;
    }

    // Add New Task
    public void addTask(Task task) {

        task.setId(tasks.size() + 1);

        tasks.add(task);

    }

    // Update Task
    public void updateTask(Integer id, Task updatedTask) {

        for (Task task : tasks) {

            if (task.getId().equals(id)) {

                task.setTaskName(updatedTask.getTaskName());
                task.setDescription(updatedTask.getDescription());
                task.setPriority(updatedTask.getPriority());
                task.setStatus(updatedTask.getStatus());
                task.setDueDate(updatedTask.getDueDate());

                return;
            }

        }

    }

    // Delete Task
    public void deleteTask(Integer id) {

        tasks.removeIf(task -> task.getId().equals(id));

    }

}