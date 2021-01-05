package cz.muni.csirt.analyza.repository;

import cz.muni.csirt.analyza.entity.AbstractObject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for AbstractObject class.
 *
 * @author David Brilla*xbrilla*469054
 */
@Repository
public interface AbstractObjectRepository extends CrudRepository<AbstractObject, UUID>, JpaSpecificationExecutor<AbstractObject> {
    Optional<AbstractObject> findByUuid(UUID uuid);
}
