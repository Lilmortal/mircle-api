package nz.co.mircle.relationship.model;

import io.swagger.annotations.ApiModelProperty;
import nz.co.mircle.user.model.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Jack on 13/06/2017.
 */
@Entity
@Table(name = "relationship")
public class Relationship implements Serializable {
    @EmbeddedId
    private RelationshipId id;

    // Needs no arg constructor for hibernate
    public Relationship() {
    }

    public Relationship(User user, User friend) {
        this.id = new RelationshipId(user, friend);
    }

    public Relationship(RelationshipId id) {
        this.id = id;
    }

    public RelationshipId getId() {
        return id;
    }

    public void setId(RelationshipId id) {
        this.id = id;
    }
}
