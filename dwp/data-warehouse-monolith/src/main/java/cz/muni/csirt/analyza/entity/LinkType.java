package cz.muni.csirt.analyza.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.csirt.analyza.json.LinkTypeDeserializer;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class that holds LinkType and its attributes.
 *
 * @author David Brilla*xbrilla*469054
 */
@Entity
@JsonDeserialize(using = LinkTypeDeserializer.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LinkType {

    @Id
    private String type;

    public LinkType() { this.type = "unknown"; }

    public LinkType(LinkType linkType) {
        this.type = linkType.getType();
    }

    public LinkType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public LinkType setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkType)) return false;
        LinkType linkType = (LinkType) o;
        return Objects.equals(type, linkType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "LinkType{" +
                "type='" + type + '\'' +
                '}';
    }
}
