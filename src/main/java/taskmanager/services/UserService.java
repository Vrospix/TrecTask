package taskmanager.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanager.dto.UserRequest;
import taskmanager.dto.UserResponse;
import taskmanager.repositories.UserRepository;
import taskmanager.entity.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserRequest request) {
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);

        return user;
    }

    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
