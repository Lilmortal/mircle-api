package nz.co.mircle.permission.services;

import nz.co.mircle.permission.dao.PermissionRepository;
import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.socialMedia.model.SocialMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * List of permission services implementation that are used to call the repository.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    private PermissionRepository permissionRepository;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void createPermission(Permission permission) {
        permissionRepository.save(permission);
    }

    @Override
    public Permission findPermission(SocialMedia socialMedia, boolean hasAccess) {
        return permissionRepository.findBySocialMediaAndHasAccess(socialMedia, hasAccess);
    }
}