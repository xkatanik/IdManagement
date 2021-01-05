package cz.muni.csirt.analyza.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * Class that holds AbstractObject and its attributes.
 *
 * @author David Brilla*xbrilla*469054
 *
 * @modified by Kristian Katanik 445403
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractObject
{
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime created;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime expired;

    @OneToMany(mappedBy="parent", cascade = CascadeType.ALL)
    private Collection<UserProperty> properties = new ArrayList<>();

    protected AbstractObject() {

        this.uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        this.created = now;
    }

    protected AbstractObject(AbstractObject abstractObject) {
        this.id = abstractObject.getId();
        this.uuid = abstractObject.getUuid();
        this.created = abstractObject.getCreated();
        this.expired = abstractObject.getExpired();
        this.properties = abstractObject.getProperties();
    }

    protected AbstractObject(UUID uuid, LocalDateTime created, LocalDateTime expired) {
        this.uuid = uuid;
        this.created = created;
        this.expired = expired;
    }

    public Long getId() {
        return id;
    }

    public AbstractObject setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public AbstractObject setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public AbstractObject setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public AbstractObject setExpired(LocalDateTime expired) {
        this.expired = expired;
        return this;
    }

    public Collection<UserProperty> getProperties() {
        return properties;
    }

    public AbstractObject setProperties(Collection<UserProperty> properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractObject)) return false;
        AbstractObject that = (AbstractObject) o;

        return  Objects.equals(uuid, that.uuid) &&
                Objects.equals(created, that.created) &&
                Objects.equals(expired, that.expired) &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, created, expired, properties);
    }

    @Override
    public String toString() {
        return "AbstractObject{" +
                "id=" + getId() +
                ", uuid=" + getUuid() +
                ", created=" + getCreated() +
                ", expired=" + getExpired() +
                ", properties=" + getProperties() +
                '}';
    }
}
