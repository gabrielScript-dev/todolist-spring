package br.com.gabriel.todolist.repository;

import br.com.gabriel.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITaskRepository extends JpaRepository<Task, UUID> {

}
