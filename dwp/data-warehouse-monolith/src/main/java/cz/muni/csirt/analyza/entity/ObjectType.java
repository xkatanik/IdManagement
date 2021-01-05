package cz.muni.csirt.analyza.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.csirt.analyza.json.ObjectTypeDeserializer;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class that holds ObjectType and its attributes.
 *
 * @author David Brilla*xbrilla*469054
 */
@Entity
@JsonDeserialize(using = ObjectTypeDeserializer.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ObjectType {

    @Id
    private String type;

    public ObjectType() { this.type = "unknown"; }

    public ObjectType(ObjectType objectType) {
        this.type = objectType.getType();
    }

    public ObjectType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public ObjectType setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectType)) return false;
        ObjectType linkType = (ObjectType) o;
        return Objects.equals(type, linkType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "ObjectType{" +
                "type='" + type + '\'' +
                '}';
    }
}
