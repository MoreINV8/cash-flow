package cash.flow.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cash.flow.backend.services.UserService;

@RestController
@RequestMapping("/api/logout")
public class LogoutController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> logout(@RequestParam String username) {
        boolean result = userService.logoutUser(username);
        if (result) {
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.status(500).body("Logout failed");
        }
    }
}
