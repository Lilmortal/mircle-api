package nz.co.mircle.userSocialMedia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jacktan on 17/06/17.
 */
@Entity
@Table(name = "user_social_media")
public class UserSocialMedia {
    @Column(name = "url")
    private String url;
}
