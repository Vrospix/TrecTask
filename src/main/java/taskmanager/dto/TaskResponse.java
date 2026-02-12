package taskmanager.dto;

import taskmanager.entity.TaskStatus;

import java.time.LocalDate;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;

    public TaskResponse(Long id, String title, String desc, LocalDate dueDate, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = desc;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
