package cz.muni.csirt.analyza.controller;

import cz.muni.csirt.analyza.entity.Link;
import cz.muni.csirt.analyza.entity.LinkType;
import cz.muni.csirt.analyza.service.LinkService;
import cz.muni.csirt.analyza.util.exception.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for work with Links.
 *
 * @author David Brilla*xbrilla*469054
 */
@RestController
@RequestMapping(value = "/links")
public class LinkController {

    private LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * Creates and returns link between two given generic objects.
     *
     * @param leftUUID uuid of the left object
     * @param rightUUID uuid of the right object
     * @param type name of the LinkType
     * @param oriented indicates whether link is oriented
     * @return created link between two given generic objects
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> putLink(@RequestParam("left")String leftUUID,
                                        @RequestParam("right")String rightUUID,
                                        @RequestParam("type")String type,
                                        @RequestParam("oriented")boolean oriented) {
        Link link = linkService.create(UUID.fromString(leftUUID), UUID.fromString(rightUUID), type, oriented);
        return ResponseEntity.ok("{\n\t\"uuid\":\"" + link.getUuid() + "\"\n}");
    }

    /**
     * Returns link with given uuid.
     *
     * @param uuid of the link
     * @return link with given uuid
     */
    @ResponseBody
    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Link> getLink(@PathVariable("uuid")UUID uuid) {
        return ResponseEntity.ok(linkService.getByUuid(uuid));
    }

    /**
     * Returns link with given objects
     *
     * @return link with given objects
     */
    @ResponseBody
    @RequestMapping(value = "/link", method = RequestMethod.GET)
    public ResponseEntity<Link> getLinkBetweenObjects(@RequestParam("left")String leftUUID,
                                        @RequestParam("right")String rightUUID,
                                        @RequestParam("type")Optional<String> type,
                                        @RequestParam("oriented")Optional<Boolean> oriented) {
        return ResponseEntity.ok(linkService.getByObjects(UUID.fromString(leftUUID), UUID.fromString(rightUUID), type, oriented));
    }


    /**
     * Returns all links from database.
     *
     * @return all links from database
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Link>> getAll() {
        return ResponseEntity.ok(linkService.getAll());
    }

    /**
     * Deletes link with given uuid.
     *
     * @param uuid of the link
     *
     * @modified by Kristian Katanik 445403
     */
    @ResponseBody
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteLink(@PathVariable("uuid")String uuid) {
        linkService.deleteByUuid(UUID.fromString(uuid));
        return ResponseEntity.ok("{}");
    }

    /**
     * return list of links linked to generic Object with given uuid
     * @param uuid of the link
     * @return list of links linked to generic object with given uuid
     */
    @ResponseBody
    @RequestMapping(value = "/filter/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Link>> filterObjects(@PathVariable("uuid") String uuid) {
        Collection<Link> links;
        try {
            links = linkService.filter(UUID.fromString(uuid));
        } catch (ServiceLayerException | IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(links);
    }
}
