package cash.flow.backend.dto;

import cash.flow.backend.models.User;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String jwt_token;

    public UserDTO(User user, String jwtToken) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.jwt_token = jwtToken;
    }
}
