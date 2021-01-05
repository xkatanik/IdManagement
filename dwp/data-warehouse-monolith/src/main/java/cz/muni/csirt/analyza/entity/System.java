package cz.muni.csirt.analyza.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.csirt.analyza.json.ObjectTypeDeserializer;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class that holds System of Generic object
 *
 * @author Kristian Katanik 445403
 */
@Entity
@JsonDeserialize(using = ObjectTypeDeserializer.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class System {

    @Id
    private String systemType;

    public System() { this.systemType = "unknown"; }

    public System(System organization) {
        this.systemType = organization.getType();
    }

    public System(String type) {
        this.systemType = type;
    }

    public String getType() {
        return systemType;
    }

    public System setType(String type) {
        this.systemType = type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof System)) return false;
        System system = (System) o;
        return Objects.equals(systemType, system.systemType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemType);
    }

    @Override
    public String toString() {
        return "System{" +
                "type='" + systemType + '\'' +
                '}';
    }
}
