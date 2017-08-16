package nz.co.mircle.v1.api.profileImage.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Created by tanj1 on 16/08/2017.
 */
@Entity
@Table(name = "profile_image")
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated profile image ID")
    private Long id;

    @Column(name = "uri")
    @ApiModelProperty(notes = "Profile picture uri link")
    private String uri;

    public ProfileImage() {
    }

    public ProfileImage(String uri) {
        this.uri = uri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "ProfileImage{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                '}';
    }
}
