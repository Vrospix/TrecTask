package taskmanager.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import taskmanager.dto.UserRequest;
import taskmanager.dto.UserResponse;
import taskmanager.entity.User;
import taskmanager.exception.ResourceNotFoundException;
import taskmanager.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRequest request) {
        return mapToResponse(userService.register(request));
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(s -> mapToResponse(s))
                .toList();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) throws ResourceNotFoundException {
         return userService.findByUserId(userId)
                 .map(s -> mapToResponse(s))
                 .orElseThrow(() -> new ResourceNotFoundException("User not found with userId " + userId));
    }
}
