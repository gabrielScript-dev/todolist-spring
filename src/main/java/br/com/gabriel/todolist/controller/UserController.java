package br.com.gabriel.todolist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.gabriel.todolist.model.User;
import br.com.gabriel.todolist.repository.IUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody User user) {

        User findByUserName = userRepository.findByUserName(user.getUserName());

        if(findByUserName != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String encryptedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(encryptedPassword);

        User userCreated = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
