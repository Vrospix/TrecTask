package taskmanager.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import taskmanager.dto.CreateTaskRequest;
import taskmanager.dto.TaskResponse;
import taskmanager.entity.Task;
import taskmanager.entity.TaskStatus;
import taskmanager.entity.User;
import taskmanager.exception.ResourceNotFoundException;
import taskmanager.services.TaskService;
import taskmanager.services.UserService;

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

    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus()
        );
    }

    @GetMapping("/users/{userId}")
    public Page<TaskResponse> getTasksByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dueDate").descending());
        Page<Task> taskPage = taskService.getTasksByUser(userId, pageable);
        return taskPage.map(t -> mapToResponse(t));
    }

    @PostMapping("/users/{userId}")
    public TaskResponse createTaskForUser(@PathVariable Long userId, @RequestBody CreateTaskRequest request) {
        User user = userService.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with userId" + userId));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setStatus(TaskStatus.NEW);
        task.setUser(user);

        return mapToResponse(taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task task = taskService.getTask(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new IllegalStateException("Cannot modify completed task");
        }

        task.setUser(taskDetails.getUser());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setTitle(taskDetails.getTitle());
        task.setDueDate(taskDetails.getDueDate());

        return mapToResponse(taskService.updateTask(task));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks()
                .stream()
                .map(t -> mapToResponse(t))
                .toList();
    }
}
