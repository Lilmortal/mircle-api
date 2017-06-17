package nz.co.mircle.relationship.dao;

import nz.co.mircle.relationship.model.Relationship;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Jack on 13/06/2017.
 */
public interface RelationshipRepository extends CrudRepository<Relationship, Long> {
}
