package cash.flow.backend.models;

import cash.flow.backend.dto.LoginDTO;
import cash.flow.backend.dto.SignupDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private String email;

    public User(SignupDTO signupDTO) {
        this.username = signupDTO.getUsername();
        this.password = signupDTO.getPassword();
        this.email = signupDTO.getEmail();
    }

    public User(LoginDTO loginDTO) {
        this.username = loginDTO.getUsername();
        this.password = loginDTO.getPassword();
    }
}
