package nz.co.mircle.permission.dao;

import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.socialMedia.model.SocialMedia;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jacktan on 17/06/17.
 */
public interface PermissionRepository extends CrudRepository<Permission, Long> {
    Permission findBySocialMediaAndHasAccess(SocialMedia socialMedia, boolean hasAccess);
}
