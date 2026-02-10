package taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taskmanager.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long UserId);
}
