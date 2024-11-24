package br.com.gabriel.todolist.repository;

import br.com.gabriel.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    User findByUserName(String userName);
}
