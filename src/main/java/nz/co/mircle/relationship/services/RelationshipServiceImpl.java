package nz.co.mircle.relationship.services;

import nz.co.mircle.relationship.dao.RelationshipRepository;
import nz.co.mircle.relationship.model.Relationship;
import nz.co.mircle.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jack on 13/06/2017.
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
