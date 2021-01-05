package cz.muni.csirt.analyza.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.muni.csirt.analyza.entity.UserProperty;

import java.io.IOException;
import java.util.UUID;

/**
 * User PropertyJSON Deserializer
 *
 * @author David Brilla*xbrilla*469054
 */
public class UserPropertyDeserializer extends StdDeserializer<UserProperty> {

    public UserPropertyDeserializer() {
        this(null);
    }

    protected UserPropertyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public UserProperty deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        JsonNode node = jp.getCodec().readTree(jp);

        Long id = null;
        if (node.get("id") != null) {
            id = node.get("id").asLong();
        }

        UUID parent = UUID.fromString(node.get("parentUuid").asText());
        String key = node.get("propertyKey").asText();
        if (!node.get("valueLong").toString().equals("null")) {
            return new UserProperty(key, node.get("valueLong").asLong()).setParentUuid(parent).setId(id);
        }
        if (!node.get("valueDouble").toString().equals("null")) {
            return new UserProperty(key, node.get("valueDouble").asDouble()).setParentUuid(parent).setId(id);
        }
        return new UserProperty(key, node.get("valueString").asText()).setParentUuid(parent).setId(id);

    }
}
