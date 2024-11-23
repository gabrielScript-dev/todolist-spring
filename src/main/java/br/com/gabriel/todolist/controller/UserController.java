package br.com.gabriel.todolist.controller;

import br.com.gabriel.todolist.model.User;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/")
    public void create(@RequestBody User user) {
        System.out.println(user.getName());
    }
}
