package cz.muni.csirt.analyza.repository;

import cz.muni.csirt.analyza.entity.UserProperty;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for UserProperty class.
 *
 * @author David Brilla*xbrilla*469054
 */
@Repository
public interface UserPropertyRepository extends CrudRepository<UserProperty, UUID>, JpaSpecificationExecutor<UserProperty> {
}
