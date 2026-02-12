package taskmanager.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import taskmanager.entity.Task;
import taskmanager.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository repository) {
        this.taskRepository = repository;
    }

    public Task createTask(Task task) {
        taskRepository.save(task);

        return task;
    }

    public Page<Task> getTasksByUser(Long userId, Pageable pageable) {
        Page<Task> taskPage = taskRepository.findByUserId(userId, pageable);

        return taskPage;
    }

    public Optional<Task> getTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);

        return task;
    }

    public Task updateTask(Task task) {
        taskRepository.save(task);

        return task;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
