package cash.flow.backend.utils;

import java.util.UUID;

public class Helper {
    public static String getStringUUID(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        return uuid.toString().replace("-", "");
    }

    public static String getStringUUID() {
        return getStringUUID(UUID.randomUUID());
    }

    public static UUID convertUUID(String uuid) {
        if (uuid == null || uuid.length() != 32)
            return null;

        String covered = "";

        for (int i = 0; i < uuid.length(); i++) {
            if (i >= 8 && i <= 20 && i % 4 == 0) {
                covered += "-";
            }
            covered += uuid.charAt(i);
        }

        return UUID.fromString(covered);
    }
}
