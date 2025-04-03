package cash.flow.backend.models;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Note {
    private UUID nId;
    
    private String userFk;
}
