package cz.muni.csirt.analyza.service;

import cz.muni.csirt.analyza.entity.Link;
import cz.muni.csirt.analyza.entity.LinkType;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface of the Link.
 *
 * @author David Brilla*xbrilla*469054
 */
public interface LinkService {

    /**
     * Creates link between two given generic objects.
     *
     * @param leftUUID generic object of link
     * @param rightUUID generic object of link
     * @param linkType of link
     * @param oriented indicates orientation of link
     * @return created link between two given generic objects
     */
    Link create(UUID leftUUID, UUID rightUUID, String linkType, boolean oriented);

    /**
     * Returns Link with given uuid.

     * @param uuid of returned Generic object
     * @return Link with given uuid
     */
    Link getByUuid(UUID uuid);

    /**
     * Returns Link with given objects.

     * @return Link with given objects
     */
    Link getByObjects(UUID left, UUID right, Optional<String> type, Optional<Boolean> oriented);

    /**
     * Returns all Links from database.
     *
     * @return all Links from database
     */
    Collection<Link> getAll();

    /**
     * Deletes Link of given uuid.
     *
     * @param uuid of deleted Link
     *
     * @modified by Kristian Katanik 445403
     */
    void deleteByUuid(UUID uuid);

    /**
     * Returns list of Links, filtered by Generic object with given uuid.
     *
     * @param uuid of generic object to filter
     * @return list of Links, filtered by Generic object with given uuid
     */
    Collection<Link> filter(UUID uuid);

}
