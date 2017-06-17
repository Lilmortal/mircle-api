package nz.co.mircle.permission.model;

import nz.co.mircle.socialMedia.model.SocialMedia;

import javax.persistence.*;
import java.util.List;

/**
 * Permission entity.
 */
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "social_media_id")
    private SocialMedia socialMedia;

    @Column(name = "has_access")
    private boolean hasAccess;

    // empty no arg constructor needed for hibernate
    public Permission() {
    }

    public Permission(SocialMedia socialMedia, boolean hasAccess) {
        this.socialMedia = socialMedia;
        this.hasAccess = hasAccess;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }

    public boolean isHasAccess() {
        return hasAccess;
    }

    public void setHasAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }
}
