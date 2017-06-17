package nz.co.mircle.relationship.services;

import nz.co.mircle.relationship.dao.RelationshipRepository;
import nz.co.mircle.relationship.model.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * List of relationship services implementation that are used to call the repository.
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {
    private RelationshipRepository relationshipRepository;

    @Autowired
    public RelationshipServiceImpl(RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }

    @Override
    public void createRelationship(Relationship relationship) {
        relationshipRepository.save(relationship);
    }

}
