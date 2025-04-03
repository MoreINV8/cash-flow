package cash.flow.backend.models;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Day {
    private UUID dId;
    private String detail;
    private int transactionValue;
    private String note;
    
    private UUID monthFk;
    private UUID categoryFk;
    
    private String categoryName;
    private String categoryColor;
}
