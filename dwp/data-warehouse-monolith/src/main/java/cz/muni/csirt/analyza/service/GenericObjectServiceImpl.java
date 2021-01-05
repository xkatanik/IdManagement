package cz.muni.csirt.analyza.service;

import cz.muni.csirt.analyza.entity.*;
import cz.muni.csirt.analyza.entity.System;
import cz.muni.csirt.analyza.repository.*;
import cz.muni.csirt.analyza.util.exception.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Implementation of GenericObjectService interface.
 *
 * @author David Brilla*xbrilla*469054
 */
@Service
public class GenericObjectServiceImpl implements GenericObjectService {

    private GenericObjectRepository genericObjectRepository;
    private ObjectTypeRepository objectTypeRepository;
    private SystemRepository systemRepository;
    private LinkRepository linkRepository;
    private EntityManager entityManager;

    @Autowired
    public GenericObjectServiceImpl(GenericObjectRepository genericObjectRepository,
                                    ObjectTypeRepository objectTypeRepository,
                                    SystemRepository systemRepository,
                                    LinkRepository linkRepository,
                                    EntityManager entityManager) {
        this.genericObjectRepository = genericObjectRepository;
        this.objectTypeRepository = objectTypeRepository;
        this.systemRepository = systemRepository;
        this.linkRepository = linkRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public GenericObject upload(String system, String objectType, String registeredId) {
        ObjectType type;
        if (objectType == null) {
            type = new ObjectType();
        } else {
            type = objectTypeRepository.findByType(objectType)
                    .orElse(objectTypeRepository.save(new ObjectType(objectType)));
        }

        System systemType;
        if (system == null) {
            systemType = new System();
        } else {
            systemType = systemRepository.findBySystemType(system)
                    .orElse(systemRepository.save(new System(system)));
        }
        GenericObject genericObject = genericObjectRepository.save((GenericObject) new GenericObject(registeredId,systemType,type));
        return genericObject;
    }

    @Override
    public GenericObject getByUuid(UUID uuid) {
        return genericObjectRepository.findByUuid(uuid).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Collection<GenericObject> getGenericObjects(Optional<String> uuid, Optional<String> system, Optional<String> type, Optional<String> registeredId, Optional<String> from, Optional<String> to)
            throws SQLException, IllegalArgumentException, ServiceLayerException {
        if (uuid.isPresent()) {
            Collection<GenericObject> genericObjects = new ArrayList<>();
            GenericObject object = genericObjectRepository.findByUuid(UUID.fromString(uuid.orElse(""))).orElseThrow(IllegalArgumentException::new);
            genericObjects.add(object);
            return genericObjects;

        }
        String query = "SELECT a FROM GenericObject a WHERE ";
        if (system.isPresent()) {
            query += "system = " + "'" + system.orElse(null) + "' AND ";
        }
        if (type.isPresent()) {
            query += "type = " + "'" + type.orElse(null) + "' AND ";
        }
        if (registeredId.isPresent()) {
            query += "registeredId = " + "'" + registeredId.orElse(null) + "' AND ";
        }
        if (from.isPresent()) {
            query += "created >= " + "'" + from.orElse(null) + "' AND ";
        }
        if (to.isPresent()) {
            query += "created < " + "'" + to.orElse(null) + "' AND ";
        }
        query += "expired IS NULL";
        Collection<GenericObject> genericObjects;
        try {
            genericObjects = filter(query);
        } catch (SQLException e) {
            throw e;
        } catch (ServiceLayerException | IllegalArgumentException e) {
            throw e;
        }
        return genericObjects;
    }

    @Transactional
    @Override
    public void deleteByUuid(UUID uuid, Optional<Boolean> recursive) {
        LocalDateTime time = LocalDateTime.now();
        if (!recursive.isPresent()) {
            expireObject(uuid, time);
        } else {
            recursiveExpireObjects(uuid, time);
        }
    }

    @Transactional
    @Override
    public void expireObject(UUID uuid, LocalDateTime time) {
        GenericObject genericObject = genericObjectRepository.findByUuid(uuid).orElseThrow(IllegalArgumentException::new);
        if (genericObject.getExpired() == null) {
            genericObject.setExpired(time);
            genericObjectRepository.save(genericObject);
        }
        Collection<Link> rightLinks = genericObject.getRightLinks();
        if (!rightLinks.isEmpty()) {
            for (Link link : rightLinks) {
                if (link.getExpired() != null) {
                    link.setExpired(time);
                }
            }
        }
        Collection<Link> leftLinks = genericObject.getLeftLinks();
        if (!leftLinks.isEmpty()) {
            for (Link link : leftLinks) {
                if (link.getExpired() != null) {
                    link.setExpired(time);
                }
            }
        }
    }

    @Transactional
    @Override
    public void recursiveExpireObjects(UUID uuid, LocalDateTime time) {
        GenericObject genericObject = genericObjectRepository.findByUuid(uuid).orElseThrow(IllegalArgumentException::new);
        if (genericObject.getExpired() == null) {
            genericObject.setExpired(time);
            genericObjectRepository.save(genericObject);
            Collection<Link> rightLinks = genericObject.getRightLinks();
            if (!rightLinks.isEmpty()) {
                for (Link link : rightLinks) {
                    if (link.getExpired() == null) {
                        link.setExpired(time);
                    }
                }
            }

            Collection<Link> leftLinks = genericObject.getLeftLinks();
            if (!leftLinks.isEmpty()) {
                for (Link link : leftLinks) {
                    if (link.getExpired() == null) {
                        recursiveExpireObjects(link.getRight().getUuid(), time);
                    }
                }
            } else {
                return;
            }
        }
        return;
    }

    @Override
    public Collection<GenericObject> filter(String query) throws SQLException {
        if (query.matches(".*([;\"]).*")) {
            throw new IllegalArgumentException(query);
        }
        try {
            final String queryBasic = "SELECT a FROM GenericObject a WHERE " + query;
            return (entityManager.createQuery(query).getResultList());
        } catch(Exception ex) {
            throw new SQLException("SELECT exception => " + query, ex);
        }
    }

    @Override
    public Collection<GenericObject> getAll() {
        return genericObjectRepository.findAll(null);
    }
}
