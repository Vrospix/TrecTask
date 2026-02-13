package taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status; // NEW, IN_PROGRESS, DONE

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task(String title, String desc, LocalDate dueDate) {
        this.title = title;
        this.description = desc;
        this.dueDate = dueDate;
        this.status = TaskStatus.IN_PROGRESS;
    }

    public Task() {
        this.status = TaskStatus.IN_PROGRESS;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String newDesc) {
        if (newDesc != null) {
            description = newDesc;
        }
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

    public void setDueDate(LocalDate newDueDate) {
        if (newDueDate != null){
            dueDate = newDueDate;
        }
    }

    public void setStatus(TaskStatus newStatus) {
        status = newStatus;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User newUser) {
        if (newUser != null) {
            user = newUser;
        }
    }
}
