package taskmanager.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import taskmanager.entity.Task;
import taskmanager.entity.TaskStatus;
import taskmanager.entity.User;
import taskmanager.exception.ResourceNotFoundException;
import taskmanager.services.TaskService;
import taskmanager.services.UserService;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;
    private UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/users/{userId}")
    public Page<Task> getTasksByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dueDate").descending());
        return taskService.getTasksByUser(userId, pageable);
    }

    @PostMapping("/users/{userId}")
    public Task createTaskForUser(@PathVariable Long userId, @RequestBody Task task) {
        User user = userService.findByUserId(userId).orElseThrow();
        task.setUser(user);
        return createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task task = taskService.getTask(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new IllegalStateException("Cannot modify completed task");
        }

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        return taskService.updateTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
}
