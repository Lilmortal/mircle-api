package nz.co.mircle.v1.api.user.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by tanj1 on 15/08/2017.
 */
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated image ID")
    private Long id;

    @Column(name = "uri")
    @ApiModelProperty(notes = "Base 64 image")
    private String uri;

    public Image() {
    }

    public Image(String uri) {
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
        return "Image{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                '}';
    }
}
