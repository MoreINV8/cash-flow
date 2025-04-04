package cash.flow.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cash.flow.backend.dto.UserDTO;
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

    @Autowired
    private JWTService jwtService;

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

    public UserDTO login(User user) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        
        if (auth.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());

            User validateUser = userRepository.getUserByUsername(user.getUsername());
            validateUser.setActive(true);
            userRepository.updateUserStatus(validateUser);

            return new UserDTO(validateUser, token);
        }
        return null;
    }

    public boolean logoutUser(String u) {
        User user = userRepository.getUserByUsername(u);
        if (user != null) {
            user.setActive(false);
            userRepository.updateUserStatus(user);

            return true;
        }
        return false;
    }

    public boolean isActive(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user != null) {
            return user.isActive();
        }
        return false;
    }
}
