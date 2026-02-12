package taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    // Optional: OneToMany relationship with tasks
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    protected User() {}

    public Long getId(){
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        if (newEmail != null || !newEmail.isBlank()) {
            email = newEmail;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername) {
        if (newUsername != null || !newUsername.isBlank()) {
            username = newUsername;
        }
    }
}
