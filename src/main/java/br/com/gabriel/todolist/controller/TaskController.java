package br.com.gabriel.todolist.controller;

import br.com.gabriel.todolist.model.Task;
import br.com.gabriel.todolist.repository.ITaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository repository;

    @PostMapping("/")
    public ResponseEntity<Task> create(@RequestBody Task task, HttpServletRequest request) {

        UUID userId = (UUID) request.getAttribute("idUser");
        task.setIdUser(userId);

        LocalDateTime currentDate = LocalDateTime.now();

        if(currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if(task.getStartAt().isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        repository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
