package cz.muni.csirt.analyza.service;

import cz.muni.csirt.analyza.entity.AbstractObject;

import java.util.UUID;

/**
 * General service interface for both generic objects and links user property issues.
 *
 * @author David Brilla*xbrilla*469054
 */
public interface AbstractObjectService {

    /**
     * Returns object with updated User Properties of object with given uuid.
     *
     * @param uuid of the object
     * @param key of the userProperty
     * @param value of the userProperty
     * @return object with updated User Properties of object with given uuid.
     */
    AbstractObject updateProperties(UUID uuid, String key, String value);

}
