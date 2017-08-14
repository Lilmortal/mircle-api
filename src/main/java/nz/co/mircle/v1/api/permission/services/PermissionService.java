package nz.co.mircle.v1.api.permission.services;

import nz.co.mircle.v1.api.permission.model.Permission;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;

/** Lists of services that can be used to call the permission repository. */
public interface PermissionService {
  void createPermission(Permission permission);

  Permission findPermission(Long id);

  Permission findPermission(SocialMedia socialMedia, boolean hasAccess);
}
