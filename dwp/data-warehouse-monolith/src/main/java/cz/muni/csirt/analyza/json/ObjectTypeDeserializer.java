package cz.muni.csirt.analyza.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.muni.csirt.analyza.entity.ObjectType;

import java.io.IOException;

/**
 * Object Type JSON Deserializer
 *
 * @author David Brilla*xbrilla*469054
 */
public class ObjectTypeDeserializer extends StdDeserializer<ObjectType> {

    public ObjectTypeDeserializer() {
        this(null);
    }

    protected ObjectTypeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ObjectType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        return new ObjectType(node.get("type").asText());
    }
}
