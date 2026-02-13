package taskmanager.services;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanager.dto.LoginRequest;
import taskmanager.dto.UserRequest;
import taskmanager.dto.UserResponse;
import taskmanager.exception.ResourceNotFoundException;
import taskmanager.repositories.UserRepository;
import taskmanager.entity.User;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

    public User updateUser(User user) {
        userRepository.save(user);

        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User login(LoginRequest request) {
        System.out.println("Email from req: " + request.getEmail());
        System.out.println("Pass from req: " + request.getPassword());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email"));

        System.out.println("Pass from user (encrpt): " + user.getPassword());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid password");
        }

        return user;
    }
}
