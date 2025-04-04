package cash.flow.backend.models;

import java.util.UUID;

import lombok.Data;

@Data
public class Note {
    private UUID nId;

    private String userFk;
}
