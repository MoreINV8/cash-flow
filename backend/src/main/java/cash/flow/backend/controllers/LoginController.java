package cash.flow.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cash.flow.backend.dto.LoginDTO;
import cash.flow.backend.dto.UserDTO;
import cash.flow.backend.models.User;
import cash.flow.backend.services.UserService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginData) {
        User user = new User(loginData);
        UserDTO loginResult = userService.login(user);

        if (loginResult != null) {
            return ResponseEntity.ok(loginResult);
        }
        return ResponseEntity.badRequest().body(null);
    }
    
}
