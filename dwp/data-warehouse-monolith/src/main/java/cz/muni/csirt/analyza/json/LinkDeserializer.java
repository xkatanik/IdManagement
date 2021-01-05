package cz.muni.csirt.analyza.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import cz.muni.csirt.analyza.entity.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Link JSON Deserializer
 *
 * @author David Brilla*xbrilla*469054
 */
public class LinkDeserializer extends StdDeserializer<Link> {

    public LinkDeserializer() {
        this(null);
    }

    public LinkDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Link deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        JsonNode node = jp.getCodec().readTree(jp);

        UUID uuid = UUID.fromString(node.get("uuid").asText());
        LocalDateTime created, expired;
        List<LocalDateTime> localDateTimes = BasicLocalDateTimeDeserializer.parseLocalDateTimes(node);
        created = localDateTimes.get(0);
        expired = localDateTimes.get(1);
        CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(Collection.class, UserProperty.class);
        Collection<UserProperty> properties = new ObjectMapper().readValue(node.get("properties").toString(), typeReference);
       // String caseId = node.get("caseId").asText();

        GenericObject left, right;
        left = new ObjectMapper().readValue(node.get("left").toString(), GenericObject.class);
        right = new ObjectMapper().readValue(node.get("right").toString(), GenericObject.class);
        boolean oriented = node.get("oriented").asBoolean();
        LinkType type = new ObjectMapper().readValue(node.get("type").toString(), LinkType.class);

        Link result = new Link(uuid, created, expired, left, right, oriented, type);
        result.setProperties(properties);
        return result;
    }
}
