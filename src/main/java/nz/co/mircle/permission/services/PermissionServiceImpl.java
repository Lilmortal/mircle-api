package nz.co.mircle.permission.services;

import nz.co.mircle.permission.dao.PermissionRepository;
import nz.co.mircle.permission.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jacktan on 17/06/17.
 */
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
}
