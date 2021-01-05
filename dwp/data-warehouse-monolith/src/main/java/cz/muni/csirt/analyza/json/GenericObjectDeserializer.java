package cz.muni.csirt.analyza.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import cz.muni.csirt.analyza.entity.*;
import cz.muni.csirt.analyza.entity.System;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Generic Object JSON Deserializer
 *
 * @author David Brilla*xbrilla*469054
 */
public class GenericObjectDeserializer extends StdDeserializer<GenericObject> {

    public GenericObjectDeserializer() {
        this(null);
    }

    public GenericObjectDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public GenericObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        UUID uuid = UUID.fromString(node.get("uuid").asText());
        LocalDateTime created, expired;
        List<LocalDateTime> localDateTimes = BasicLocalDateTimeDeserializer.parseLocalDateTimes(node);
        created = localDateTimes.get(0);
        expired = localDateTimes.get(1);
        String registeredId = node.get("registeredId").asText();
        ObjectType type = new ObjectMapper().readValue(node.get("type").toString(), ObjectType.class);
        System system = new ObjectMapper().readValue(node.get("system").toString(), System.class);
        CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(Collection.class, UserProperty.class);
        Collection<UserProperty> properties = new ObjectMapper().readValue(node.get("properties").toString(), typeReference);

        GenericObject result = new GenericObject(uuid, created, expired, registeredId, system, type);
        result.setProperties(properties);
        return result;
    }
}
