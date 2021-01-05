package cz.muni.csirt.analyza.controller;

import cz.muni.csirt.analyza.entity.*;
import cz.muni.csirt.analyza.queryBuilder.QueryBuilder;
import cz.muni.csirt.analyza.service.GenericObjectService;
import cz.muni.csirt.analyza.util.exception.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for work with GenericObjects.
 *
 * @author David Brilla*xbrilla*469054
 */
@RestController
@RequestMapping("/generic-objects")
public class GenericObjectController {

    private GenericObjectService genericObjectService;

    @Autowired
    public GenericObjectController(GenericObjectService genericObjectService) {
        this.genericObjectService = genericObjectService;
    }

    /**
     * Uploads new generic object into database.
     *
     * @param system of the generic object
     * @param type name of the object type
     * @param registeredId id of the object
     * @return uploaded generic objects
     *
     * @modified by Kristian Katanik 445403
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> putObject(@RequestParam("system")String system,
                                          @RequestParam("type")String type,
                                          @RequestParam("registeredId")String registeredId) {
        GenericObject genericObject = genericObjectService.upload(system, type, registeredId);
        return ResponseEntity.ok("{\n\t\"uuid\":\"" + genericObject.getUuid() + "\"\n}");
    }

    /**
     * Returns generic object with given uuid.
     *
     * @param uuid of the generic object
     * @return generic object with given uuid
     */
    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<GenericObject> getObject(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(genericObjectService.getByUuid(UUID.fromString(uuid)));
    }

    /**
     * Returns all generic objects from database.
     *
     * @return all generic objects from database.
     *
     *
     * @modified by Kristian Katanik 445403
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<GenericObject>> getAll(@RequestParam("uuid")Optional<String> uuid,
                                                            @RequestParam("system")Optional<String> system,
                                                            @RequestParam("type")Optional<String> type,
                                                            @RequestParam("registeredId")Optional<String> registeredId,
                                                            @RequestParam("from")Optional<String> from,
                                                            @RequestParam("to")Optional<String> to) {
        Collection<GenericObject> genericObjects = new ArrayList<>();
        try {
            genericObjects = genericObjectService.getGenericObjects(uuid, system, type, registeredId, from, to);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (ServiceLayerException | IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(genericObjects);
    }

    /**
     * Deletes generic object (and file) with given uuid.
     *
     * @param uuid of the generic object
     *
     * @modified by Kristian Katanik 445403
     */
    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteObject(@RequestParam("uuid")String uuid,
                                               @RequestParam("recursive")Optional<Boolean> recursive) {
        genericObjectService.deleteByUuid(UUID.fromString(uuid), recursive);
        return ResponseEntity.ok("{}");
    }

    /**
     * Returns list of generic objects, filtered by query.
     *
     * @return list of generic objects, filtered by query
     */
    @RequestMapping(value = "/filter", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<GenericObject>> filterObjects(@RequestParam("id")String id,
                                                                   @RequestParam("property")String property) {
        Collection<GenericObject> genericObjects;
        try {
            String query = "SELECT " + property + " FROM GenericObject WHERE case_id=" + "'" + id + "'";
            genericObjects = genericObjectService.filter(query);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (ServiceLayerException | IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(genericObjects);
    }

}

