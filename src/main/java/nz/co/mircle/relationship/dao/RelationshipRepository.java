package nz.co.mircle.relationship.dao;

import nz.co.mircle.relationship.model.Relationship;
import org.springframework.data.repository.CrudRepository;

/**
 * CRUD operations on the permission table.
 */
public interface RelationshipRepository extends CrudRepository<Relationship, Long> {
}
