package taskmanager.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import taskmanager.dto.UserRequest;
import taskmanager.dto.UserResponse;
import taskmanager.entity.User;
import taskmanager.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRequest request) {
        return userService.register(request);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
