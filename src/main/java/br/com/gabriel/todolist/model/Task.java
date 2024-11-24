package br.com.gabriel.todolist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "task_todolist")
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;


    private UUID idUser;

    private String description;

    @Column(length = 50)
    private String title;

    private String priority;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @CreationTimestamp
    private LocalDateTime creatAt;
}
