package cz.muni.csirt.analyza.controller;

import cz.muni.csirt.analyza.entity.AbstractObject;
import cz.muni.csirt.analyza.service.AbstractObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * General controller for both generic objects and links user property issues.
 *
 * @author David Brilla*xbrilla*469054
 */
@RestController
public class AbstractObjectController {

    private AbstractObjectService abstractObjectService;

    @Autowired
    public AbstractObjectController(AbstractObjectService abstractObjectService) {
        this.abstractObjectService = abstractObjectService;
    }

    /**
     * Updates user property of Generic Object / Link with given uuid.
     *
     * @param uuid of the object
     * @param key of the property
     * @param value of the property
     * @return updated Generic Object / Link with given uuid
     */
    @ResponseBody
    @RequestMapping(value = {"/generic-objects/{uuid}", "/links/{uuid}"}, method = RequestMethod.PUT)
    public ResponseEntity<AbstractObject> updateGenericObjectsUserProperty(@PathVariable("uuid") String uuid,
                                                             @RequestParam("key") String key,
                                                             @RequestParam("value") String value) {
        return ResponseEntity.ok(abstractObjectService.updateProperties(UUID.fromString(uuid), key, value));
    }
}
