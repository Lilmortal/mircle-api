package nz.co.mircle.v1.api.relationship.services;

import nz.co.mircle.v1.api.relationship.dao.RelationshipRepository;
import nz.co.mircle.v1.api.relationship.model.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** List of relationship services implementation that are used to call the repository. */
@Service
public class RelationshipServiceImpl implements RelationshipService {
  @Autowired private RelationshipRepository relationshipRepository;

  @Override
  public void createRelationship(Relationship relationship) {
    relationshipRepository.save(relationship);
  }
}
