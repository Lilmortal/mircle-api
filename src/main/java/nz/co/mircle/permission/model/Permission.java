package nz.co.mircle.permission.model;

import javax.persistence.*;

/**
 * Created by jacktan on 17/06/17.
 */
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    // social media id

    @Column(name = "has_access")
    private boolean hasAccess;


}
