package nz.co.mircle.v1.api.relationship.services;

import nz.co.mircle.v1.api.relationship.model.Relationship;

/** Lists of services that can be used to call the relationship repository. */
public interface RelationshipService {
  void createRelationship(Relationship relationship);
}
