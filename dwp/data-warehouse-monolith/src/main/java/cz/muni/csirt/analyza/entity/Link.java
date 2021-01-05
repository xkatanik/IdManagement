package cz.muni.csirt.analyza.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.csirt.analyza.json.LinkDeserializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Class that holds Link and its attributes.
 *
 * @author David Brilla*xbrilla*469054
 */
@Entity
@JsonDeserialize(using = LinkDeserializer.class)
public class Link extends AbstractObject {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "left_object_id", nullable = false)
    private GenericObject left;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "right_object_id", nullable = false)
    private GenericObject right;

    @Column(nullable = false)
    private boolean oriented;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "link_type", nullable = false)
    private LinkType type;

    public Link() {}

    public Link(GenericObject left, GenericObject right, LinkType type, boolean oriented) {
        this.left = left;
        this.right = right;
        this.type = type;
        this.oriented = oriented;
    }

    public Link(Link link) {
        super(link);
        this.left = link.getLeft();
        this.right = link.getRight();
        this.oriented = link.isOriented();
        this.type = link.getType();
    }

    public Link(UUID uuid, LocalDateTime created, LocalDateTime modified, GenericObject left, GenericObject right, boolean oriented, LinkType type) {
        super(uuid, created, modified);
        this.left = left;
        this.right = right;
        this.type = type;
        this.oriented = oriented;
    }

    public GenericObject getLeft() {
        return left;
    }

    public Link setLeft(GenericObject left) {
        this.left = left;
        return this;
    }

    public GenericObject getRight() {
        return right;
    }

    public Link setRight(GenericObject right) {
        this.right = right;
        return this;
    }

    public LinkType getType() {
        return type;
    }

    public Link setType(LinkType type) {
        this.type = type;
        return this;
    }

    public boolean isOriented() {
        return oriented;
    }

    public Link setOriented(boolean oriented) {
        this.oriented = oriented;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;
        Link link = (Link) o;
        return oriented == link.oriented &&
                Objects.equals(left, link.left) &&
                Objects.equals(right, link.right) &&
                Objects.equals(type, link.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, oriented, type);
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + getId() +
                ", uuid=" + getUuid() +
                ", created=" + getCreated() +
                ", modified=" + getExpired() +
                ", properties=" + getProperties() +
                ", left=" + left +
                ", right=" + right +
                ", oriented=" + oriented +
                ", linkType=" + type +
                '}';
    }
}
