package cz.muni.csirt.analyza.repository;

import cz.muni.csirt.analyza.entity.Link;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Link class.
 *
 * @author David Brilla*xbrilla*469054
 */
@Repository
public interface LinkRepository extends CrudRepository<Link, UUID>, JpaSpecificationExecutor<Link> {
    Optional<Link> findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
    List<Link> findAllByLeftUuid(UUID uuid);
    List<Link> findAllByRightUuid(UUID uuid);
}