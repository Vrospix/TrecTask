package taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taskmanager.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
