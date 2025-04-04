package cash.flow.backend.models;

import java.util.UUID;

import lombok.Data;

@Data
public class Month {
    private UUID mId;
    private int year;
    private int month;

    private UUID noteFk;
}
