package cz.muni.csirt.analyza.repository;

import cz.muni.csirt.analyza.entity.GenericObject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for GenericObject class.
 *
 * @author David Brilla*xbrilla*469054
 */
@Repository
public interface GenericObjectRepository extends CrudRepository<GenericObject, UUID>, JpaSpecificationExecutor<GenericObject> {
    Optional<GenericObject> findByUuid(UUID uuid);
}
