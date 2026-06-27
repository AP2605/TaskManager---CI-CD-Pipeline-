package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // ==========================
    // GET ALL TASKS
    // ==========================
    @GetMapping
    public List<Task> getTasks() {

        return service.getAllTasks();

    }

    // ==========================
    // GET TASK BY ID
    // ==========================
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Integer id) {

        return service.getTaskById(id);

    }

    // ==========================
    // ADD NEW TASK
    // ==========================
    @PostMapping
    public String addTask(@RequestBody Task task) {

        service.addTask(task);

        return "Task Added Successfully";

    }

    // ==========================
    // UPDATE TASK
    // ==========================
    @PutMapping("/{id}")
    public String updateTask(@PathVariable Integer id,
                             @RequestBody Task task) {

        service.updateTask(id, task);

        return "Task Updated Successfully";

    }

    // ==========================
    // DELETE TASK
    // ==========================
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Integer id) {

        service.deleteTask(id);

        return "Task Deleted Successfully";

    }

}