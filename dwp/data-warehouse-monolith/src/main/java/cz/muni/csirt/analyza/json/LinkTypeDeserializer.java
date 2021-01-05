package cz.muni.csirt.analyza.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.muni.csirt.analyza.entity.LinkType;

import java.io.IOException;

/**
 * Link Type JSON Deserializer
 *
 * @author David Brilla*xbrilla*469054
 */
public class LinkTypeDeserializer extends StdDeserializer<LinkType> {

    public LinkTypeDeserializer() {
        this(null);
    }

    protected LinkTypeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LinkType deserialize(com.fasterxml.jackson.core.JsonParser jp, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException, com.fasterxml.jackson.core.JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        return new LinkType(node.get("type").asText());
    }
}
