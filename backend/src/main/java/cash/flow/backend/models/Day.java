package cash.flow.backend.models;

import java.util.UUID;

import lombok.Data;

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
