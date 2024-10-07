package exercise.controller;

import java.util.List;
import java.util.stream.Collectors;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TaskRepository tRepo;

    @Autowired
    private UserRepository uRepo;

    @Autowired
    private TaskMapper tMapper;

    @GetMapping
    public List<TaskDTO> getAll() {
        return tRepo.findAll()
                .stream()
                .map(task -> tMapper.map(task))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskDTO getCurrent(@PathVariable long id) {
        return tMapper.map(tRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create (@Valid @RequestBody TaskCreateDTO body) {
        return tMapper.map(tRepo.save(tMapper.map(body)));
    }

    @PutMapping("/{id}")
    public TaskDTO update (@PathVariable long id, @Valid @RequestBody TaskUpdateDTO body) {
        var task = tRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        var user = uRepo.findById(task.getAssignee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        user.removeTask(task);
        tMapper.update(body, task);
        user = uRepo.findById(task.getAssignee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        user.addTask(task);
        return tMapper.map(tRepo.save(task));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable long id) {
        var task = tRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        tRepo.deleteById(task.getId());
    }
}
