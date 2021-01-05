package cz.muni.csirt.analyza.repository;

import cz.muni.csirt.analyza.entity.LinkType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository for LinkType class.
 *
 * @author David Brilla*xbrilla*469054
 */
public interface LinkTypeRepository extends CrudRepository<LinkType, String>, JpaSpecificationExecutor<LinkType> {
    Optional<LinkType> findByType(String type);
}
