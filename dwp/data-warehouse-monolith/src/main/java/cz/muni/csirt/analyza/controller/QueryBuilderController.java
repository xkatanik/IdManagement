package cz.muni.csirt.analyza.controller;

import cz.muni.csirt.analyza.entity.GenericObject;
import cz.muni.csirt.analyza.entity.Link;
import cz.muni.csirt.analyza.queryBuilder.QueryBuilder;
import cz.muni.csirt.analyza.service.AbstractObjectService;
import cz.muni.csirt.analyza.service.GenericObjectService;
import cz.muni.csirt.analyza.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.net.www.content.text.Generic;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/query-builder/")
public class QueryBuilderController {

        private EntityManager entityManager;

        @Autowired
        public QueryBuilderController(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public <E> ResponseEntity<Collection<E>> getObjects(@RequestBody String body) throws JSONException, SQLException {
        String query = QueryBuilder.CreateQuery(body);
        Collection<E> objects = new ArrayList<E>();
        try {
            objects = entityManager.createQuery(query).getResultList();
        }catch (Exception e) {
            throw new SQLException("SELECT exception => " + query, e);
        }
        return ResponseEntity.ok(objects);
    }
}
