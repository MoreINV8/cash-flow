package cash.flow.backend.models;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Month {
    private UUID mId;
    private int year;
    private int month;
    
    private UUID noteFk;
}
