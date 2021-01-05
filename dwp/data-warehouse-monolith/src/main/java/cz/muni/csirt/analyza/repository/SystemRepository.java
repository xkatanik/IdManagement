package cz.muni.csirt.analyza.repository;

import cz.muni.csirt.analyza.entity.ObjectType;
import cz.muni.csirt.analyza.entity.System;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository for System class.
 *
 * @author Kristian Katanik 445403
 */
public interface SystemRepository extends CrudRepository<System, String>, JpaSpecificationExecutor<ObjectType> {
    Optional<System> findBySystemType(String systemType);
}
