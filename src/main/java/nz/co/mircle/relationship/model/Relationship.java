package nz.co.mircle.relationship.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.user.model.User;

/** Relationship entity. */
@Entity
@Table(name = "relationship")
public class Relationship implements Serializable {
  @EmbeddedId private RelationshipPK id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id")
  @NotNull
  @ApiModelProperty(
    notes = "The permission the friend has to be able to see the users social media",
    required = true
  )
  private Permission permission;

  // Needs no arg constructor for hibernate
  public Relationship() {}

  public Relationship(User user, User friend, Permission permission) {
    this.id = new RelationshipPK(user, friend);
    this.permission = permission;
  }

  public Relationship(RelationshipPK id) {
    this.id = id;
  }

  public RelationshipPK getId() {
    return id;
  }

  public void setId(RelationshipPK id) {
    this.id = id;
  }

  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
  }
}
