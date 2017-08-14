package nz.co.mircle.v1.api.relationship.dao;

import nz.co.mircle.v1.api.relationship.model.Relationship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** CRUD operations on the permission table. */
@Repository
public interface RelationshipRepository extends CrudRepository<Relationship, Long> {}
