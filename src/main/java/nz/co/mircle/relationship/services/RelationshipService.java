package nz.co.mircle.relationship.services;

import nz.co.mircle.relationship.model.Relationship;

/**
 * Lists of services that can be used to call the relationship repository.
 */
public interface RelationshipService {
    void createRelationship(Relationship relationship);
}
