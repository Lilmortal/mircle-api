package nz.co.mircle.relationship.services;

import nz.co.mircle.relationship.model.Relationship;
import nz.co.mircle.user.model.User;

/**
 * Lists of services that can be used to call the relationship repository.
 */
public interface RelationshipService {
    void createRelationship(Relationship relationship);
}
