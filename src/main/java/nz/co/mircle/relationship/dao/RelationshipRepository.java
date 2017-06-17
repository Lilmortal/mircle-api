package nz.co.mircle.relationship.dao;

import nz.co.mircle.relationship.model.Relationship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CRUD operations on the permission table.
 */
@Repository
public interface RelationshipRepository extends CrudRepository<Relationship, Long> {
}
