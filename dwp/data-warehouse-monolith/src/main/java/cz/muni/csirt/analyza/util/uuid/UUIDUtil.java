package cz.muni.csirt.analyza.util.uuid;

import java.util.UUID;

/**
 * @author David Brilla*xbrilla*469054
 */
public class UUIDUtil {
    public static UUID getUUIDFromJson(String json) {
        return UUID.fromString(json.split(":", 2)[1].substring(1, UUID.randomUUID().toString().length() + 1));
    }
}
