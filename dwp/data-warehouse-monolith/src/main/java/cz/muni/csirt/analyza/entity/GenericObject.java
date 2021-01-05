package cz.muni.csirt.analyza.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.csirt.analyza.json.GenericObjectDeserializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class that holds GenericObject and its attributes.
 *
 * @author David Brilla*xbrilla*469054
 *
 * @modified by Kristian Katanik 445403
 */
@Entity
@JsonDeserialize(using = GenericObjectDeserializer.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class GenericObject extends AbstractObject {

    @Column(nullable = false)
    private String registeredId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "system", nullable = false)
    private System system;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "object_type", nullable = false)
    private ObjectType type;

    @JsonIgnore
    @OneToMany(mappedBy = "left", cascade = CascadeType.ALL)
    private Collection<Link> leftLinks = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
    private Collection<Link> rightLinks = new ArrayList<>();

    public GenericObject() {}

    public GenericObject(GenericObject genericObject) {
        this.registeredId = genericObject.getRegisteredId();
        this.system = genericObject.getSystem();
        this.type = genericObject.getType();

        this.leftLinks = genericObject.getLeftLinks();
        this.rightLinks = genericObject.getRightLinks();
    }

    public GenericObject(String registeredId, System system, ObjectType type) {
        super();
        this.registeredId = registeredId;
        this.system = system;
        this.type = type;
    }

    public GenericObject(UUID uuid, LocalDateTime created, LocalDateTime expired, String registeredId, System system, ObjectType type) {
        super(uuid, created, expired);
        this.registeredId = registeredId;
        this.system = system;
        this.type = type;
    }

    public String getRegisteredId() {
        return registeredId;
    }

    public void setRegisteredId(String registeredId) {
        this.registeredId = registeredId;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public Collection<Link> getLeftLinks() {
        return leftLinks;
    }

    public GenericObject setLeftLinks(Collection<Link> leftLinks) {
        this.leftLinks = leftLinks;
        return this;
    }

    public Collection<Link> getRightLinks() {
        return rightLinks;
    }

    public GenericObject setRightLinks(Collection<Link> rightLinks) {
        this.rightLinks = rightLinks;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericObject)) return false;
        GenericObject that = (GenericObject) o;
        return Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

    @Override
    public String toString() {
        return "GenericObject{" +
                "id=" + getId() +
                ", uuid=" + getUuid() +
                ", created=" + getCreated() +
                ", expired=" + getExpired() +
                ", registeredId=" + getRegisteredId() +
                ", system=" + getSystem() +
                ", type=" + getType() +
                '}';
    }
}
