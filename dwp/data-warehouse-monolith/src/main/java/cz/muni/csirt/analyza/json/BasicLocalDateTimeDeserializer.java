package cz.muni.csirt.analyza.json;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Basic Local Date Time JSON Deserializer
 *
 * @author David Brilla*xbrilla*469054
 */
public class BasicLocalDateTimeDeserializer {

    private static LocalDateTime deserialize(JsonNode node) {
        int year = node.get("year").asInt();
        int month = node.get("monthValue").asInt();
        int day = node.get("dayOfMonth").asInt();
        int hour = node.get("hour").asInt();
        int min = node.get("minute").asInt();
        int sec = node.get("second").asInt();

        return LocalDateTime.of(year, month, day, hour, min, sec);
    }

    private static LocalDateTime deserializeTimestamp(String localDateTime) {
        String[] splits = localDateTime.split(" ");
        String[] dates = splits[0].split("-");
        String[] times = splits[1].split(":");
        return LocalDateTime.of(
                Integer.parseInt(dates[0]),
                Integer.parseInt(dates[1]),
                Integer.parseInt(dates[2]),
                Integer.parseInt(times[0]),
                Integer.parseInt(times[1]),
                Integer.parseInt(times[2]));
    }

    public static List<LocalDateTime> parseLocalDateTimes(JsonNode node) {
        LocalDateTime created, expired;
        if (node.get("created").asText().isEmpty()) {
            created = deserialize(node.get("created"));
            expired = deserialize(node.get("expired"));
        } else {
            created = deserializeTimestamp(node.get("created").asText());
            expired = deserializeTimestamp(node.get("expired").asText());
        }
        return Arrays.asList(created, expired);
    }
}
