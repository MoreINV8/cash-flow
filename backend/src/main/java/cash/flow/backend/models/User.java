package cash.flow.backend.models;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class User {
    private String username;
    private String password;
    private String email;
}
