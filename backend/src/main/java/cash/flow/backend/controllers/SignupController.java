package cash.flow.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cash.flow.backend.dto.SignupDTO;
import cash.flow.backend.models.User;
import cash.flow.backend.services.UserService;

@RestController
@RequestMapping("/api/signup")
public class SignupController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<String> signup(@RequestBody SignupDTO signupData) {
        User user = new User(signupData);

        boolean isSuccess = userService.signUp(user);

        if (isSuccess) {
            return ResponseEntity.ok("User created successfully!");
        }
        return ResponseEntity.badRequest().body("Something went wrong!");
    }

}
