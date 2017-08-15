package nz.co.mircle.v1.api.permission.services;

import nz.co.mircle.v1.api.permission.dao.PermissionRepository;
import nz.co.mircle.v1.api.permission.model.Permission;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** List of permission services implementation that are used to call the repository. */
@Service
public class PermissionServiceImpl implements PermissionService {
  @Autowired private PermissionRepository permissionRepository;

  @Override
  public void createPermission(Permission permission) {
    permissionRepository.save(permission);
  }

  @Override
  public Permission findPermission(Long id) {
    return permissionRepository.findById(id);
  }

  @Override
  public Permission findPermission(SocialMedia socialMedia, boolean hasAccess) {
    return permissionRepository.findBySocialMediaAndHasAccess(socialMedia, hasAccess);
  }
}