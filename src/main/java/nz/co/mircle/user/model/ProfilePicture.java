package nz.co.mircle.user.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by tanj1 on 11/08/2017.
 */
@Entity
@Table(name = "profile_picture")
public class ProfilePicture {
    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Column(name = "default")
    @ApiModelProperty(notes = "Determine if the current profile picture is the default picture", required = true)
    private boolean isDefaultImage;

    @Column(name = "image")
    @NotNull
    @ApiModelProperty(notes = "Profile picture")
    private Long image;

    @Column(name = "mime")
    @ApiModelProperty(notes = "Profile picture mime")
    private String mime;

    @Column(name = "size")
    @ApiModelProperty(notes = "Profile picture size")
    private Long size;

    @Column(name = "width")
    @ApiModelProperty(notes = "Profile picture width")
    private Long width;

    @Column(name = "height")
    @ApiModelProperty(notes = "Profile picture height")
    private Long height;
}
