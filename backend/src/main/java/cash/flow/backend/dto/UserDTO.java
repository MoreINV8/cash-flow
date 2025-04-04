package cash.flow.backend.dto;

import cash.flow.backend.models.User;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String jwtToken;

    public UserDTO(User user, String jwtToken) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.jwtToken = jwtToken;
    }
}
