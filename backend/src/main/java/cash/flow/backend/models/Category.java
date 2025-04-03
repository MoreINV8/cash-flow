package cash.flow.backend.models;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Category {
    private UUID cId;
    private String cName;
    private String color;
    
    private UUID noteFk;
}
