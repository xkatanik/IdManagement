package cz.muni.csirt.analyza.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.muni.csirt.analyza.entity.System;

import java.io.IOException;

/**
 * Object Type JSON Deserializer
 *
 * @author Kristian Katanik 445403
 */
public class SystemDeserializer extends StdDeserializer<System> {

    public SystemDeserializer() {
        this(null);
    }

    protected SystemDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public System deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        return new System(node.get("system").asText());
    }
}
