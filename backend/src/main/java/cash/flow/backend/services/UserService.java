package cash.flow.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cash.flow.backend.models.User;
import cash.flow.backend.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    public boolean signUp(User user) {
        User queryUser = userRepository.getUserByUsername(user.getUsername());
        if (queryUser != null) {
            return false; // User already exists
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.createUser(user);
            return true; // User created successfully
        }
    }

    public User login(User user) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        
        if (auth.isAuthenticated()) {
            return userRepository.getUserByUsername(user.getUsername());
        }
        return null;
    }
}
