package cz.muni.csirt.analyza.repository;

import cz.muni.csirt.analyza.entity.ObjectType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository for ObjectType class.
 *
 * @author David Brilla*xbrilla*469054
 */
public interface ObjectTypeRepository extends CrudRepository<ObjectType, String>, JpaSpecificationExecutor<ObjectType> {
    Optional<ObjectType> findByType(String type);
}
