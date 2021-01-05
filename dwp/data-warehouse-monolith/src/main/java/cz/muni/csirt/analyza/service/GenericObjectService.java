package cz.muni.csirt.analyza.service;

import cz.muni.csirt.analyza.entity.GenericObject;
import cz.muni.csirt.analyza.util.exception.ServiceLayerException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for generic objects.
 *
 * @author David Brilla*xbrilla*469054
 */
public interface GenericObjectService {

    /**
     * Uploads given Generic object into database.
     *
     * @param system of uploaded Generic object instance
     * @param objectType of uploaded Generic object instance
     * @param registeredId id of the Generic object instance
     * @return uploaded Generic object
     *
     * @modified by Kristian Katanik 445403
     */
    GenericObject upload(String system, String objectType, String registeredId);

    /**
     * Returns Generic object with given uuid.
     *
     * @param uuid of returned Generic object
     * @return Generic object with given uuid
     */
    GenericObject getByUuid(UUID uuid);

    /**
     * Return Generic objects from database.
     *
     * @param uuid of Generic object instance
     * @param system of  Generic object instance
     * @param type of  Generic object instance
     * @param registeredId id of the  Generic object instance
     * @return Generic objects
     *
     * @created by Kristian Katanik 445403
     */
    Collection<GenericObject> getGenericObjects(Optional<String> uuid,
                                                Optional<String> system,
                                                Optional<String> type,
                                                Optional<String> registeredId,
                                                Optional<String> from,
                                                Optional<String> to) throws SQLException, IllegalArgumentException, ServiceLayerException;


    /**
     * Deletes Generic Object (and opt. file) with given uuid.
     *
     * @param recursive delete
     * @param uuid of deleted Generic object
     *
     @modified by Kristian Katanik 445403
     */
    void deleteByUuid(UUID uuid, Optional<Boolean> recursive);
    /**
     * Method to set expire attribute when updating or deleting object
     *
     * @param uuid of object
     * @param time to be set as expired
     *
     * @author Kristian Katanik 445403
     */
    void expireObject(UUID uuid, LocalDateTime time);

    /**
     * Method to set expire attribute when updating or deleting object recursively
     *
     * @param uuid of object
     * @param time to be set as expired
     *
     * @author Kristian Katanik 445403
     */
    void recursiveExpireObjects(UUID uuid, LocalDateTime time);


    /**
     * Returns list of Generic Objects, filtered by query.
     *
     * @param query to be filtered
     * @return list of Generic Objects, filtered by query
     */
    Collection<GenericObject> filter(String query) throws SQLException;

    /**
     * Returns all generic objects from database.
     *
     * @return all generic objects from database.
     */
    Collection<GenericObject> getAll();

}
