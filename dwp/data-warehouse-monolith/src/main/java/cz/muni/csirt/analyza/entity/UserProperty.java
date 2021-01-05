package cz.muni.csirt.analyza.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.csirt.analyza.json.UserPropertyDeserializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Class that holds UserProperty and its attributes.
 *
 * @author David Brilla*xbrilla*469054
 */
@Entity
@JsonDeserialize(using = UserPropertyDeserializer.class)
public class UserProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime created;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime expired;

    @Column(nullable = false)
    private String propertyKey;

    private Long valueLong;

    private Double valueDouble;

    private String valueString;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "parent_id", nullable = false)
    @JsonIgnore
    private AbstractObject parent;

    private UUID parentUuid;

    public UserProperty() {}

    public UserProperty(UserProperty userProperty) {
        this.id = userProperty.getId();
        this.created = userProperty.getCreated();
        this.expired = userProperty.getExpired();
        this.parent = userProperty.getParent();
        this.parentUuid = parent.getUuid();
        this.propertyKey = userProperty.getPropertyKey();
        this.valueLong = userProperty.getValueLong();
        this.valueDouble = userProperty.getValueDouble();
        this.valueString = userProperty.getValueString();
    }

    public UserProperty(AbstractObject parentObject, LocalDateTime created, String propertyKey, String valueString) {
        this.created = created;
        this.parent = parentObject;
        this.parentUuid = parentObject.getUuid();
        this.propertyKey = propertyKey;
        this.valueString = valueString;
        this.valueLong = null;
        this.valueDouble = null;
    }

    public UserProperty(String propertyKey, String valueString) {
        this.propertyKey = propertyKey;
        this.valueString = valueString;
        this.valueLong = null;
        this.valueDouble = null;
    }

    public UserProperty(AbstractObject parentObject, LocalDateTime created, String propertyKey, Double valueDouble) {
        this.created = created;
        this.parent = parentObject;
        this.parentUuid = parentObject.getUuid();
        this.propertyKey = propertyKey;
        this.valueDouble = valueDouble;
        this.valueLong = null;
        this.valueString = null;
    }

    public UserProperty(String propertyKey, Double valueDouble) {
        this.propertyKey = propertyKey;
        this.valueDouble = valueDouble;
        this.valueLong = null;
        this.valueString = null;
    }

    public UserProperty(AbstractObject parentObject, LocalDateTime created, String propertyKey, Long valueLong) {
        this.created = created;
        this.parent = parentObject;
        this.parentUuid = parentObject.getUuid();
        this.propertyKey = propertyKey;
        this.valueLong = valueLong;
        this.valueString = null;
        this.valueDouble = null;
    }

    public UserProperty(String propertyKey, Long valueLong) {
        this.propertyKey = propertyKey;
        this.valueLong = valueLong;
        this.valueString = null;
        this.valueDouble = null;
    }

    public Long getId() {
        return id;
    }

    public UserProperty setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public UserProperty setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public UserProperty setExpired(LocalDateTime expired) {
        this.expired = expired;
        return this;
    }

    public AbstractObject getParent() {
        return parent;
    }

    public UserProperty setParent(AbstractObject parent) {
        this.parent = parent;
        this.parentUuid = parent.getUuid();
        return this;
    }

    public UUID getParentUuid() {
        return parentUuid;
    }

    public UserProperty setParentUuid(UUID parentUuid) {
        this.parentUuid = parentUuid;
        return this;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public UserProperty setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
        return this;
    }

    public Long getValueLong() {
        return valueLong;
    }

    public UserProperty setValueLong(Long valueLong) {
        this.valueLong = valueLong;
        return this;
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public UserProperty setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
        return this;
    }

    public String getValueString() {
        return valueString;
    }

    public UserProperty setValueString(String valueString) {
        this.valueString = valueString;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProperty)) return false;
        UserProperty that = (UserProperty) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(parentUuid, that.parentUuid) &&
                Objects.equals(created, that.created) &&
                Objects.equals(expired, that.expired) &&
                Objects.equals(propertyKey, that.propertyKey) &&
                Objects.equals(valueLong, that.valueLong) &&
                Objects.equals(valueDouble, that.valueDouble) &&
                Objects.equals(valueString, that.valueString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentUuid, propertyKey, valueLong, valueDouble, valueString);
    }

    @Override
    public String toString() {
        return "UserProperty{" +
                "id=" + id +
                ", created=" + getCreated() +
                ", expired=" + getExpired() +
                ", parentUuid=" + parentUuid +
                ", propertyKey='" + propertyKey + '\'' +
                ", value='" + valueString + '\'' +
                '}';
    }
}
