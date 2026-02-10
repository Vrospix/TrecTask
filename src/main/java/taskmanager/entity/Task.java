package taskmanager.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;

    public Task(String title, String desc, LocalDate dueDate) {
        this.title = title;
        this.description = desc;
        this.dueDate = dueDate;
    }

    protected Task() {}

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String newDesc) {
        description = newDesc;
    }

    public String getDescription() {
        return description;
    }

    public Long getId(){
        return id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Enumerated(EnumType.STRING)
    private TaskStatus status; // NEW, IN_PROGRESS, DONE

    public void setStatus(TaskStatus newStatus) {
        status = newStatus;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User newUser) {
        user = newUser;
    }
}
