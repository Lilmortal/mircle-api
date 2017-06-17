package nz.co.mircle.permission.services;

import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.socialMedia.model.SocialMedia;
import org.springframework.stereotype.Service;

/**
 * Lists of services that can be used to call the permission repository.
 */
public interface PermissionService {
    void createPermission(Permission permission);

    Permission findPermission(SocialMedia socialMedia, boolean hasAccess);
}
