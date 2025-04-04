package cash.flow.backend.models;

import java.util.UUID;

import lombok.Data;

@Data
public class Category {
    private UUID cId;
    private String cName;
    private String color;
    
    private UUID noteFk;
}
